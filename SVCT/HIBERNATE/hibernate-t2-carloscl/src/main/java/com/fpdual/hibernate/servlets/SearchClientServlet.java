package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

/**
 * 
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Servlet para buscar clientes. Este servlet maneja las solicitudes para buscar
 * clientes por nombre y apellidos. Utiliza el método doGet para procesar las
 * solicitudes GET.
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
	 * Maneja las solicitudes GET para buscar clientes por nombre y apellidos.
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

			// Dividir la cadena en nombre, primer apellido y segundo apellido
			String[] searchTerms = searchKeyword.split("\\s+");

			// Inicializar los valores para la búsqueda
			String firstName = "";
			String lastName = "";
			String secondLastName = "";

			// Asignar los valores correspondientes
			if (searchTerms.length > 0) {
				firstName = searchTerms[0].trim();
			}
			if (searchTerms.length > 1) {
				lastName = searchTerms[1].trim();
			}
			if (searchTerms.length > 2) {
				secondLastName = searchTerms[2].trim();
			}

			// Realizar la búsqueda en el servicio de gestión de clientes
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);
			List<Client> searchResults = clientService.searchByNameAndLastName(firstName, lastName, secondLastName);

			// Guardar los resultados en el request
			request.setAttribute("clients", searchResults);

			// Log para registrar la búsqueda realizada
			Utils.log(Utils.INFO, "Búsqueda realizada por nombre y apellidos: " + searchKeyword);

			// Redirigir a la página de lista de clientes con los resultados de la búsqueda
			request.getRequestDispatcher("JSP/listClients.jsp").forward(request, response);

		} catch (Exception e) {
			// Log para registrar errores en la búsqueda
			Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en SearchClientServlet: " + e);
			response.sendRedirect("JSP/error/error.jsp");
		}
	}
}
