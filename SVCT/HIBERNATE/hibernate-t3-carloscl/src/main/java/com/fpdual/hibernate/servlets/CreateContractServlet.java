package com.fpdual.hibernate.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.fpdual.hibernate.Constants;
import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.persistence.Contract;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para la creación de contratos. Este servlet maneja las solicitudes de
 * creación de contratos, recopila los datos del formulario y los almacena en la
 * base de datos.
 * 
 * Extiende HttpServlet para manejar solicitudes HTTP.
 * 
 * @author Carlos
 */
public class CreateContractServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public CreateContractServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET. Obtiene información del cliente y redirige al
	 * formulario de creación de contrato.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtenemos la sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Crear una instancia del servicio de gestión de clientes
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);

			// Iniciar una transacción de Hibernate
			Transaction transaction = null;

			try {
				// Iniciar la transacción de Hibernate
				transaction = session.beginTransaction();

				// Obtener el ID del cliente desde los parámetros de la solicitud
				String idParam = request.getParameter("clientId");

				if (idParam != null && !idParam.isEmpty()) {
					long clientId = Long.parseLong(idParam);

					// Buscar el cliente por su ID
					Client client = clientService.searchById(clientId);

					// Redirigir a la página de error si el cliente no existe
					if (client == null) {
						Utils.log(Utils.ERROR, "Cliente no encontrado con ID: " + clientId);
						response.sendRedirect(Constants.JSP_ERROR_JSP);
						return;
					}

					// Commit de la transacción si está activa
					if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {

						// Forzar la escritura de los cambios al almacenamiento (base de datos)
						session.flush();

						// Confirmar los cambios en la base de datos
						transaction.commit();

						/*
						 * Limpiar la sesión para liberar recursos y prepararla para la siguiente
						 * transacción
						 */
						session.clear();
					}

					// Guardar el cliente en el request para usarlo en createContract.jsp
					request.setAttribute("client", client);

					// Redirigir al formulario de creación de contrato
					RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/createContract.jsp");
					dispatcher.forward(request, response);

				}

			} catch (Exception e) {

				// Manejar errores y realizar rollback en caso de excepción
				if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {

					// Rollback en caso de excepción
					transaction.rollback();
				}
				// Redirigir a la página de error en caso de excepción
				Utils.log(Utils.ERROR, "Error al procesar la solicitud GET: " + e);
				response.sendRedirect(Constants.JSP_ERROR_JSP);
			}
		}
	}

	/**
	 * Maneja las solicitudes POST. Procesa el formulario de creación de contratos,
	 * valida los campos y almacena el nuevo contrato en la base de datos.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtenemos la sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Crear instancias de servicios que manejan contratos y clientes
			ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);

			// Inicializar la transacción
			Transaction transaction = null;

			try {

				// Obtenemos la transaccion de la sesion en curso
				transaction = session.beginTransaction();

				// Obtener parámetros del formulario
				long clientId = Long.parseLong(request.getParameter(Constants.PARAM_CLIENT_ID));

				// Obtenemos el Cliente con el parametro
				Client client = clientService.searchById(clientId);

				String startDateStr = request.getParameter("startDate");
				String endDateStr = request.getParameter("endDate");
				BigDecimal monthlyPrice = new BigDecimal(request.getParameter("monthlyPrice"));

				// Convertir fechas
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = dateFormat.parse(startDateStr);
				Date endDate = dateFormat.parse(endDateStr);

				// Crear nuevo contrato
				Contract newContract = new Contract();
				newContract.setClient(client);
				newContract.setStartDate(startDate);
				newContract.setEndDate(endDate);
				newContract.setMonthlyPrice(monthlyPrice);
				newContract.setUpdatedDate(new Date());
				newContract.setUpdatedUser(Utils.getHostName());

				// Insertar el nuevo contrato
				if (Boolean.TRUE.equals(contractService.insertNewContract(newContract))) {

					// Commit de la transacción si está activa
					if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {

						// Forzar la escritura de los cambios al almacenamiento (base de datos)
						session.flush();

						// Confirmar los cambios en la base de datos
						transaction.commit();

						/*
						 * Limpiar la sesión para liberar recursos y prepararla para la siguiente
						 * transacción
						 */
						session.clear();
					}

					// Log de la operación.
					Utils.log(Utils.INFO, "Contrato creado: " + newContract.toString());

					// Obtener la lista de contratos asociados al cliente
					request.setAttribute("client", client);
					request.setAttribute("contracts", contractService.searchContractByClientId(clientId));

					// Redirigir a la página de vista del cliente
					RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/viewClient.jsp");
					dispatcher.forward(request, response);

				} else {

					// Log de error si los campos están vacíos
					Utils.log(Utils.ERROR, "Campos del formulario vacíos.");

					// Redirigir a la página de error si los campos están vacíos
					response.sendRedirect(Constants.JSP_ERROR_JSP);
				}

			} catch (Exception e) {

				// Manejar errores y realizar rollback en caso de excepción
				if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {

					// Rollback en caso de excepción
					transaction.rollback();
				}

				// Log de error en caso de excepción
				Utils.log(Utils.ERROR, "Error al procesar el formulario de creación de contratos. " + e);

				// Redirigir a la página de error en caso de excepción
				response.sendRedirect(Constants.JSP_ERROR_JSP);
			}
		}

	}
}