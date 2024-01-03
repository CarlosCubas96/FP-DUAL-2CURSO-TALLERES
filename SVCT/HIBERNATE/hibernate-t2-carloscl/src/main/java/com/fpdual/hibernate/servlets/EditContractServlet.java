package com.fpdual.hibernate.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

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
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Servlet para la edición de contratos. Este servlet maneja las solicitudes de
 * edición de contratos, busca el contrato correspondiente en la base de datos y
 * permite la modificación de sus datos.
 * 
 * @author Carlos
 */
public class EditContractServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public EditContractServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET para la edición de contratos.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String contractIdParam = request.getParameter("contractId");
		String clientIdParam = request.getParameter("clientId");

		// Se Obtienen los parametros y verificamos
		if (contractIdParam != null && clientIdParam != null) {

			try {

				// Obtenemos los parametros del JSP de cliente y contrato
				long contractId = Long.parseLong(contractIdParam);
				long clientId = Long.parseLong(clientIdParam);

				try (Session session = HibernateUtil.getSessionFactory().openSession()) {
					ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);

					// Buscar el contrato por su ID
					Contract existingContract = contractService.searchById(contractId);

					request.setAttribute("contract", existingContract);
					request.setAttribute("clientId", clientId);

					// Redirigir al formulario de actualización del contrato
					RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/updateContract.jsp");
					dispatcher.forward(request, response);
				}
			} catch (NumberFormatException e) {

				// Redirigir a la página de error si la actualización falla
				response.sendRedirect("JSP/error/error.jsp");
			}
		} else {

			// Redirigir a la página de error si la actualización falla
			response.sendRedirect("JSP/error/error.jsp");
		}
	}

	/**
	 * Maneja las solicitudes POST para la edición de contratos.
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

			// Iniciar una transacción de Hibernate
			Transaction transaction = null;

			try {

				// Obtenemos los parámetros del formulario
				String contractIdParam = request.getParameter("contractId");
				String clientIdParam = request.getParameter("clientId");
				String startDateStr = request.getParameter("startDate");
				String endDateStr = request.getParameter("endDate");
				String monthlyPriceStr = request.getParameter("monthlyPrice");

				// Verificamos si los parámetros obligatorios no son nulos o vacíos
				if (contractIdParam != null && !contractIdParam.isEmpty() && clientIdParam != null
						&& !clientIdParam.isEmpty() && startDateStr != null && !startDateStr.isEmpty()
						&& endDateStr != null && !endDateStr.isEmpty() && monthlyPriceStr != null
						&& !monthlyPriceStr.isEmpty()) {

					// Iniciar la transacción después de verificar los parámetros
					transaction = session.beginTransaction();

					long contractId = Long.parseLong(contractIdParam);
					long clientId = Long.parseLong(clientIdParam);

					// Buscamos el cliente y el contrato en la base de datos
					Client client = clientService.searchById(clientId);
					Contract existingContract = contractService.searchById(contractId);

					// Parseamos las fechas y el precio mensual
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date startDate = dateFormat.parse(startDateStr);
					Date endDate = dateFormat.parse(endDateStr);
					BigDecimal monthlyPrice = new BigDecimal(monthlyPriceStr);

					// Actualizamos los datos del contrato
					existingContract.setStartDate(startDate);
					existingContract.setEndDate(endDate);
					existingContract.setMonthlyPrice(monthlyPrice);
					existingContract.setUpdatedDate(new Date());
					existingContract.setUpdatedUser(Utils.getHostName());

					// Actualizamos el contrato en la base de datos
					if (Boolean.TRUE.equals(contractService.updateContract(existingContract))) {

						// Configuramos atributos para la vista de detalle de cliente
						request.setAttribute("client", client);
						request.setAttribute("contracts", contractService.searchContractByClientId(clientId));

						// Redirigimos a la página de detalle de cliente
						RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/viewClient.jsp");
						dispatcher.forward(request, response);

						// Log exitoso
						Utils.log(Utils.INFO, "Contrato con ID: " + contractId + " actualizado con éxito.");

						// Commit de la transition si está activa
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

					} else {
						// Rollback en caso de falla en la actualización del contrato
						Utils.log(Utils.ERROR, "Error al actualizar el contrato con ID: " + contractId);
						response.sendRedirect("JSP/error/error.jsp");
					}
				} else {
					// Manejamos el caso en el que algunos parámetros son nulos o vacíos
					Utils.log(Utils.ERROR, "Alguno de los parámetros es nulo o vacío.");
					response.sendRedirect("JSP/error/error.jsp");
				}

			} catch (Exception e) {
				// Manejamos errores y realizamos rollback en caso de excepción
				if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {
					transaction.rollback();
				}
				// Redirigimos a la página de error en caso de excepción
				Utils.log(Utils.ERROR, "Error al procesar la solicitud POST: " + e);
				response.sendRedirect("JSP/error/error.jsp");
			}
		}

	}
}
