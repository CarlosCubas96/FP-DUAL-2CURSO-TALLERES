package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.fpdual.hibernate.Constants;
import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.persistence.Contract;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;
import com.fpdual.hibernate.Utils;

import java.io.IOException;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para eliminar contratos. Este servlet maneja las solicitudes de
 * eliminación de contratos, busca el contrato correspondiente en la base de
 * datos y lo elimina.
 * 
 * Extiende HttpServlet para manejar solicitudes HTTP.
 * 
 * @author Carlos
 */
public class DeleteContractServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public DeleteContractServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes POST. Elimina el contrato especificado y redirige a la
	 * lista de contratos asociados al cliente.
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

			// Crear una instancia del servicio de gestión de contratos
			ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);

			// Iniciar una transacción de Hibernate
			Transaction transaction = null;

			try {

				// Iniciar la transacción
				transaction = session.beginTransaction();

				// Obtenemos el contrato
				String idParam = request.getParameter("contractId");
				if (idParam == null || idParam.isEmpty()) {

					/*
					 * Log de error y evita el bloque else, luego sale del método si el ID es nulo o
					 * vacío
					 */
					Utils.log(Utils.ERROR, "Error al procesar la solicitud POST: ID de contrato nulo o vacío.");
					response.sendRedirect(Constants.JSP_ERROR_JSP);
					return;
				}

				long contractId = Long.parseLong(idParam);
				Contract contract = contractService.searchById(contractId);

				// Eliminar el contrato
				if (Boolean.TRUE.equals(contractService.deleteContract(contract))) {

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
					Utils.log(Utils.INFO, "Contrato eliminado: " + contract.toString());

					// Obtener la lista de contratos asociados al cliente
					long clientId = contract.getClient().getClientId();
					request.setAttribute("client", contract.getClient());
					request.setAttribute("contracts", contractService.searchContractByClientId(clientId));

					// Redirigir a la página de vista del cliente
					request.getRequestDispatcher("JSP/viewClient.jsp").forward(request, response);

				} else {

					// Log de error y redirigir a la página de error
					Utils.log(Utils.ERROR, "Error al procesar la solicitud POST: No se pudo eliminar el contrato.");
					response.sendRedirect(Constants.JSP_ERROR_JSP);
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
				response.sendRedirect(Constants.JSP_ERROR_JSP);
			}
		}
	}
}
