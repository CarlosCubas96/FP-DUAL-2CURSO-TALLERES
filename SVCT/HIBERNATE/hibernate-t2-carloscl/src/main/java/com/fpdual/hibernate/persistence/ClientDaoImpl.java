package com.fpdual.hibernate.persistence;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fpdual.hibernate.Utils;

/**
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Implementación del DAO para la tabla FPDUAL_HEX_CLIENT. Extiende
 * CommonDaoImpl para heredar la funcionalidad común.
 * 
 * Esta clase proporciona métodos específicos para manipular la entidad Client
 * en la base de datos.
 * 
 * @author Carlos
 */
public class ClientDaoImpl extends CommonDaoImpl<Client> implements ClientDaoI {

	/** Sesión de conexión a BD */
	private Session session;

	/**
	 * Constructor que recibe la sesión de Hibernate.
	 * 
	 * @param session Sesión de conexión a BD.
	 */
	public ClientDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	/**
	 * Busca clientes por nombre y apellidos.
	 *
	 * @param firstName      El nombre del cliente.
	 * @param lastName       El primer apellido del cliente.
	 * @param secondLastName El segundo apellido del cliente.
	 * @return Lista de clientes que coinciden con alguno de los parámetros dados.
	 */
	@Override
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName) {
		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Consulta HQL para buscar clientes por nombre y apellidos.
		String hql = "FROM Client c WHERE "
				+ "(:firstName IS NULL OR :firstName = '' OR c.firstName LIKE :firstName) OR "
				+ "(:lastName IS NULL OR :lastName = '' OR c.lastName LIKE :lastName) OR "
				+ "(:secondLastName IS NULL OR :secondLastName = '' OR c.secondLastName LIKE :secondLastName)";

		Query<Client> query = session.createQuery(hql, Client.class);
		query.setParameter("firstName", "%" + firstName + "%");
		query.setParameter("lastName", "%" + lastName + "%");
		query.setParameter("secondLastName", "%" + secondLastName + "%");

		// Agregar log para la consulta realizada
		Utils.log(Utils.INFO, "Realizando consulta para buscar clientes por nombre y apellidos.");

		// Ejecutar la consulta y devolver los resultados.
		List<Client> clients = query.list();

		// Agregar log para la cantidad de resultados encontrados
		Utils.log(Utils.INFO, "Número de clientes encontrados: " + clients.size());

		// Confirmar la transacción.
		session.getTransaction().commit();

		return clients;
	}

}
