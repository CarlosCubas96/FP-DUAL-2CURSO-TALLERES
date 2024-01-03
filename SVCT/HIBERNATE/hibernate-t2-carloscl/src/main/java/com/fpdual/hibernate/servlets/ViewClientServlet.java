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
import com.fpdual.hibernate.services.ClientManagementServiceImpl;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;

import java.io.IOException;

/**
 * 
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Servlet para ver la información de un cliente y sus contratos asociados. Este
 * servlet maneja las solicitudes GET para ver la información de un cliente.
 * Utiliza el método doGet para procesar las solicitudes GET y mostrar la vista
 * del cliente. También se encarga de gestionar la transacción con la base de
 * datos.
 * 
 * @author Carlos
 */
public class ViewClientServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto
	 */
	public ViewClientServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET para ver la información de un cliente y sus
	 * contratos asociados.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP que se enviará.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida durante el
	 *                          procesamiento de la solicitud.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Se crean instancias de los servicios de gestión de clientes y contratos.
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);
			ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);

			// Se inicia la transacción.
			Transaction transaction = null;

			try {

				// Obtenemos la transaccion de la sesion en curso
				transaction = session.beginTransaction();

				// Se obtiene el parámetro del ID del cliente de la solicitud.
				String idParam = request.getParameter(Constants.PARAM_CLIENT_ID);

				// Log para registrar la solicitud de visualización de cliente
				Utils.log(Utils.INFO, "Visualización de cliente solicitada con ID: " + idParam);

				// Se verifica si el parámetro no es nulo ni vacío.
				if (idParam != null && !idParam.isEmpty()) {

					// Se convierte el ID del cliente a tipo long.
					long clientId = Long.parseLong(idParam);

					// Se busca el cliente por ID.
					Client client = clientService.searchById(clientId);

					// Se verifica si el cliente es nulo.
					if (client == null) {

						// Log para registrar que el cliente no se encuentra
						Utils.log(Utils.ERROR, "Cliente no encontrado con ID: " + idParam);

						// Redirige a la página de error si el cliente no se encuentra.
						response.sendRedirect(Constants.JSP_ERROR_JSP);
						return;
					}

					// Obtener la lista de contratos asociados al cliente.
					request.setAttribute("client", client);
					request.setAttribute("contracts", contractService.searchContractByClientId(clientId));

					// Se obtiene el despachador para redirigir a la vista del cliente.
					RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/viewClient.jsp");
					dispatcher.forward(request, response);

				} else {

					// Log para registrar que el parámetro está vacío.
					Utils.log(Utils.ERROR, "Parámetro " + Constants.PARAM_CLIENT_ID
							+ " vacío en la solicitud de visualización de cliente.");

					// Redirige a la página de error si el parámetro está vacío.
					response.sendRedirect(Constants.JSP_ERROR_JSP);
				}

				// Realizar el commit solo si no se produjo ninguna excepción.
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

			} catch (Exception e) {

				// Manejar errores y realizar rollback en caso de excepción
				if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {

					// Rollback en caso de excepción
					transaction.rollback();
				}

				// Log para registrar errores en la solicitud de visualización de cliente
				Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en ViewClientServlet: " + e);

				// Redirigir a la página de error en caso de excepción
				response.sendRedirect(Constants.JSP_ERROR_JSP);
			}
		}
	}
}
