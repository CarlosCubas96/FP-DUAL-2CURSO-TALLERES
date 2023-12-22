package com.fpdual.hibernate.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * DAO de tabla FPDUAL_HEX_CLIENT
 * 
 * @author Carlos
 *
 */
public class ClientDaoImpl extends CommonDaoImpl<Client> implements ClientDaoI {

	/** Sesión de conexión a BD */
	private Session session;

	/**
	 * Método constructor
	 */
	public ClientDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	@Override
	public List<Client> searchByName(String firstName, String lastName, String secondLastName) {
		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Consulta HQL para buscar clientes por nombre y apellidos.
		String hql = "FROM Client c WHERE c.firstName = :firstName AND c.lastName = :lastName AND c.secondLastName = :secondLastName";
		Query<Client> query = session.createQuery(hql, Client.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		query.setParameter("secondLastName", secondLastName);

		// Ejecutar la consulta y devolver los resultados.
		List<Client> clients = query.list();

		// Confirmar la transacción.
		session.getTransaction().commit();

		return clients;
	}

}
