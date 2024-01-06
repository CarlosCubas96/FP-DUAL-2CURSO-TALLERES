package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpdual.hibernate.Constants;
import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

/**
 * 
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para buscar clientes. Este servlet maneja las solicitudes para buscar
 * clientes por nombre. Utiliza el método doGet para procesar las solicitudes
 * GET.
 * 
 * @author Carlos
 */
public class SearchClientServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public SearchClientServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET para buscar clientes por nombre.
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

			// Obtener el parámetro de búsqueda del formulario
			String searchKeyword = request.getParameter("searchKeyword");

			// Realizar la búsqueda en el servicio de gestión de clientes
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);
			List<Client> searchResults = clientService.searchByName(searchKeyword);

			// Guardar los resultados en el request
			request.setAttribute("clients", searchResults);

			// Log para registrar la búsqueda realizada
			Utils.log(Utils.INFO, "Búsqueda realizada por nombre: " + searchKeyword);

			// Redirigir a la página de lista de clientes con los resultados de la búsqueda
			request.getRequestDispatcher("JSP/listClients.jsp").forward(request, response);

		} catch (Exception e) {
			// Log para registrar errores en la búsqueda
			Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en SearchClientServlet: " + e);
			response.sendRedirect(Constants.JSP_ERROR_JSP);
		}
	}
}
