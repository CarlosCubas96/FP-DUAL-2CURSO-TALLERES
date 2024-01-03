package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;
import com.fpdual.hibernate.Utils;

import java.io.IOException;
import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Servlet para eliminar clientes. Este servlet maneja las solicitudes de
 * eliminación de clientes, busca el cliente correspondiente en la base de datos
 * y lo elimina.
 * 
 * Extiende HttpServlet para manejar solicitudes HTTP.
 * 
 * @author Carlos
 */
public class DeleteClientServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public DeleteClientServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes POST. Elimina el cliente especificado y redirige a la
	 * lista de clientes.
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

				// Obtenemos el cliente
				String idParam = request.getParameter("clientId");
				if (idParam == null || idParam.isEmpty()) {

					// Log de error y evita el bloque else, luego sale del método si el ID es nulo o
					// vacío
					Utils.log(Utils.ERROR, "Error al procesar la solicitud POST: ID de cliente nulo o vacío.");
					response.sendRedirect("JSP/error/error.jsp");
					return;
				}

				long clientId = Long.parseLong(idParam);
				Client client = clientService.searchById(clientId);

				// Eliminar el cliente
				if (Boolean.TRUE.equals(clientService.deleteClient(client))) {

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
					Utils.log(Utils.INFO, "Cliente eliminado: " + client.toString());

					// Obtener la lista actualizada de clientes
					List<Client> clients = clientService.searchAll();
					request.setAttribute("clients", clients);

					// Redirigir a la página de lista de clientes
					request.getRequestDispatcher("JSP/listClients.jsp").forward(request, response);

				} else {

					// Log de error y redirigir a la página de error
					Utils.log(Utils.ERROR, "Error al procesar la solicitud POST: No se pudo eliminar el cliente.");
					response.sendRedirect("JSP/error/error.jsp");
				}

			} catch (Exception e) {

				// Log de error en caso de excepción
				Utils.log(Utils.ERROR, "Error al procesar la solicitud POST: " + e);

				// Manejar errores y realizar rollback en caso de excepción
				if (transaction != null && transaction.getStatus() == TransactionStatus.ACTIVE) {

					// Rollback en caso de excepción
					transaction.rollback();
				}
				// Redirigir a la página de error en caso de excepción
				response.sendRedirect("JSP/error/error.jsp");
			}
		}
	}
}
