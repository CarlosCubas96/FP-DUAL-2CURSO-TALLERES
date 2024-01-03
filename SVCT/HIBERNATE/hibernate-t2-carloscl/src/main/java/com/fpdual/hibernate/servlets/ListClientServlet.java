package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.InitializeData;
import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Servlet para listar clientes. Este servlet maneja las solicitudes para
 * mostrar una lista de clientes. Inicializa datos si aún no se han generado y
 * muestra la lista de clientes.
 * 
 * Utiliza el método doGet para procesar las solicitudes GET.
 * 
 * @author Carlos
 */
public class ListClientServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public ListClientServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET para listar clientes.
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

		// Obtenemos la sesion
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Crear instancias de servicios que manejan contratos y clientes
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);
			ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);

			// Se crea una instancia de InitializeData con los servicios necesarios.
			InitializeData initializeData = new InitializeData(clientService, contractService);

			/*
			 * Se verifica si los datos de clientes ya han sido generados, y si no, se
			 * generan aleatoriamente.
			 */
			if (!initializeData.hasGeneratedClients()) {
				initializeData.generateRandomClients();
				Utils.log(Utils.INFO, "Datos de clientes generados aleatoriamente.");
			} else {
				Utils.log(Utils.INFO, "Los datos de clientes ya han sido generados previamente.");
			}

			// Se obtiene la lista de clientes y se establece como atributo en la solicitud.
			List<Client> clients = clientService.searchAll();
			request.setAttribute("clients", clients);

			// Se redirige a la página JSP que muestra la lista de clientes.
			request.getRequestDispatcher("JSP/listClients.jsp").forward(request, response);
		} catch (Exception e) {
			Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en ListClientServlet: " + e);
			response.sendRedirect("JSP/error/error.jsp");
		}
	}
}
