package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fpdual.hibernate.Constants;
import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;
import com.fpdual.hibernate.persistence.Contract;

import java.io.IOException;
import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para buscar contratos por DNI del cliente. Maneja solicitudes GET.
 * 
 * @author Carlos
 */
public class SearchContractServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public SearchContractServlet() {
		super();
	}

	/**
	 * Maneja solicitudes GET para buscar contratos por DNI del cliente.
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

		// Obtener la sesión
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Crear una instancia del servicio de contratos
			ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);

			// Obtener el parámetro DNI de la solicitud
			String searchDNI = request.getParameter("searchDNI");

			// Buscar contratos por DNI
			List<Contract> contracts = contractService.searchContractByClientIdentityDocument(searchDNI);

			// Establecer los contratos como atributo en la solicitud
			request.setAttribute("contracts", contracts);

			// Redirigir a la página JSP de contratos
			request.getRequestDispatcher("JSP/listContracts.jsp").forward(request, response);

		} catch (Exception e) {
			Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en SearchContractServlet: " + e);
			response.sendRedirect(Constants.JSP_ERROR_JSP);
		}
	}
}
