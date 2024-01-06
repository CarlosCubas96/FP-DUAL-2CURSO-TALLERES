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
 * Servlet para la creación de clientes. Este servlet maneja las solicitudes de
 * creación de clientes, recopila los datos del formulario y los almacena en la
 * base de datos.
 * 
 * Extiende HttpServlet para manejar solicitudes HTTP.
 * 
 * @author Carlos
 */
public class CreateClientServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public CreateClientServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET. Redirige al formulario de creación de clientes.
	 * 
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Redirigir al formulario de creación de cliente
		RequestDispatcher dispatcher = request.getRequestDispatcher("JSP/createClient.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Maneja las solicitudes POST. Procesa el formulario de creación de clientes,
	 * valida los campos y almacena el nuevo cliente en la base de datos.
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

			// Iniciar una transacción de Hibernate
			Transaction transaction = null;

			try {
				// Iniciar la transacción
				transaction = session.beginTransaction();

				// Obtener parámetros del formulario
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String secondLastName = request.getParameter("secondLastName");
				String identityDocument = request.getParameter("identityDocument");

				// Validar que los campos no estén vacíos
				if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()
						&& identityDocument != null && !identityDocument.isEmpty()) {

					// Crear un nuevo cliente
					Client newClient = new Client();
					newClient.setFirstName(firstName);
					newClient.setLastName(lastName);
					newClient.setSecondLastName(secondLastName);
					newClient.setIdentityDocument(identityDocument);
					newClient.setUpdatedDate(new Date());
					newClient.setUpdatedUser(Utils.getHostName());

					// Guardar el nuevo cliente en la base de datos
					if (Boolean.TRUE.equals(clientService.insertNewClient(newClient))) {

						// Log de la operación.
						Utils.log(Utils.INFO, "Cliente creado: " + newClient.toString());

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

						// Obtener la lista actualizada de clientes
						List<Client> clients = clientService.searchAll();
						request.setAttribute("clients", clients);

						// Redirigir a la página de lista de clientes
						request.getRequestDispatcher("JSP/listClients.jsp").forward(request, response);

					} else {

						// Log de error si los campos están vacíos
						Utils.log(Utils.ERROR, "Campos del formulario vacíos.");

						// Redirigir a la página de error si los campos están vacíos
						response.sendRedirect(Constants.JSP_ERROR_JSP);

					}

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
				Utils.log(Utils.ERROR, "Error al procesar el formulario de creación de clientes." + e);

				// Redirigir a la página de error en caso de excepción
				response.sendRedirect(Constants.JSP_ERROR_JSP);
			}
		}
	}
}
