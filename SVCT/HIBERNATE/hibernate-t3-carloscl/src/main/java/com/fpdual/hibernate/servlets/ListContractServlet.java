package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fpdual.hibernate.Constants;
import com.fpdual.hibernate.HibernateUtil;

import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Contract;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;

import java.io.IOException;
import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para listar contratos por DNI de cliente. Este servlet maneja las
 * solicitudes para mostrar una lista de contratos asociados a un cliente por su
 * DNI. Inicializa datos si aún no se han generado y muestra la lista de
 * contratos.
 * 
 * Utiliza el método doGet para procesar las solicitudes GET.
 * 
 * @author Carlos
 */
public class ListContractServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public ListContractServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET para listar contratos
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

            ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);

            List<Contract> contracts = contractService.searchAll();
            request.setAttribute("contracts", contracts);

            request.getRequestDispatcher("JSP/listContracts.jsp").forward(request, response);
        } catch (Exception e) {
            Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en ListContractsServlet: " + e);
            response.sendRedirect(Constants.JSP_ERROR_JSP);
        }
    }

}
