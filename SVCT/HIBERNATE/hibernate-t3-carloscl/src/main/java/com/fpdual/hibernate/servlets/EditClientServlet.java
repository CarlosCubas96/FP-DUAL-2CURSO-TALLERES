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

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para la edición de clientes. Este servlet maneja las solicitudes de
 * edición de clientes, busca el cliente correspondiente en la base de datos y
 * permite la modificación de sus datos.
 * 
 * @author Carlos
 */
public class EditClientServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public EditClientServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET para la edición de clientes.
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

					// Configurar el cliente como atributo para la vista de actualización
					request.setAttribute("client", client);

					// Redirigir al formulario de actualización del cliente
					RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/updateClient.jsp");
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
	 * Maneja las solicitudes POST para la edición de clientes.
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

			// Crear una instancia del servicio de gestión de clientes
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);

			// Inicializar la transacción
			Transaction transaction = null;

			try {
				// Iniciar la transacción de Hibernate
				transaction = session.beginTransaction();

				// Obtener parámetros del formulario de actualización del cliente
				String idParam = request.getParameter(Constants.PARAM_CLIENT_ID);
				long clientId = Long.parseLong(idParam);
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String secondLastName = request.getParameter("secondLastName");
				String identityDocument = request.getParameter("identityDocument");

				// Verificar que los parámetros no sean nulos o vacíos
				if (idParam != null && !idParam.isEmpty() && firstName != null && !firstName.isEmpty()
						&& lastName != null && !lastName.isEmpty() && identityDocument != null
						&& !identityDocument.isEmpty()) {

					// Crear un cliente actualizado con los nuevos datos
					Client updatedClient = new Client();
					updatedClient.setClientId(clientId);
					updatedClient.setFirstName(firstName);
					updatedClient.setLastName(lastName);
					updatedClient.setSecondLastName(secondLastName);
					updatedClient.setIdentityDocument(identityDocument);
					updatedClient.setUpdatedDate(new Date());
					updatedClient.setUpdatedUser(Utils.getHostName());

					// Actualizar el cliente en la base de datos
					if (Boolean.TRUE.equals(clientService.updateClient(updatedClient))) {

						Utils.log(Utils.ERROR, "Cliente con ID: " + updatedClient.getId() + "  actualizado con éxito.");

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

						// Obtener la lista actualizada de clientes
						List<Client> clients = clientService.searchAll();

						// Configurar atributos para la vista de listado de clientes
						request.setAttribute("clients", clients);

						// Log de verificacion
						Utils.log(Utils.DEBUG, "Cliente actualizado con ID: " + clientId);

						// Redirigir a la página de listado de clientes
						request.getRequestDispatcher("JSP/listClients.jsp").forward(request, response);

					} else {
						// Redirigir a la página de error si la actualización falla
						Utils.log(Utils.ERROR, "Error al actualizar el cliente con ID: " + clientId);
						response.sendRedirect(Constants.JSP_ERROR_JSP);
					}
				} else {
					// Redirigir a la página de error si los parámetros son nulos o vacíos
					Utils.log(Utils.ERROR, "Parámetros nulos o vacíos en la solicitud POST.");
					response.sendRedirect(Constants.JSP_ERROR_JSP);
				}
			} catch (Exception e) {

				// Manejar errores y realizar rollback en caso de excepción
				if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {

					// Rollback en caso de excepción
					transaction.rollback();
				}
				// Redirigir a la página de error en caso de excepción
				Utils.log(Utils.ERROR, "Error al procesar la solicitud POST: " + e);
				response.sendRedirect(Constants.JSP_ERROR_JSP);
			}
		}
	}
}
