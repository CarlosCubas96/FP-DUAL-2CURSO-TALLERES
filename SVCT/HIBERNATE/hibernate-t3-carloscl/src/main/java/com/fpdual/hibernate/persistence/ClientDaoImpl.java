package com.fpdual.hibernate.persistence;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fpdual.hibernate.Utils;

/**
 * FPDUAL - HIBERNATE - Taller3
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
	 * @return Lista de clientes que coinciden con los parámetros dados.
	 */
	@Override
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName) {
		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Consulta HQL para buscar clientes por nombre y apellidos.
		String hql = "FROM Client c WHERE " + "(:firstName IS NULL OR c.firstName = :firstName) OR "
				+ "(:lastName IS NULL OR c.lastName = :lastName) OR "
				+ "(:secondLastName IS NULL OR c.secondLastName = :secondLastName)";

		Query<Client> query = session.createQuery(hql, Client.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		query.setParameter("secondLastName", secondLastName);

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

	/**
	 * Busca clientes por nombre.
	 * 
	 * @param firstName El nombre del cliente.
	 * @return Lista de clientes que coinciden con el nombre dado.
	 */
	@Override
	public List<Client> searchByName(String firstName) {

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Creación del CriteriaBuilder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		// Creación del CriteriaQuery para Client
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> clientRoot = criteriaQuery.from(Client.class);

		// Agregar condición para hacer coincidir el primer nombre
		Predicate condition = criteriaBuilder.equal(clientRoot.get("firstName"), firstName);
		criteriaQuery.where(condition);

		// Realizar la consulta
		List<Client> clients = session.createQuery(criteriaQuery).getResultList();

		// Registro
		Utils.log(Utils.INFO, "Realizando consulta para buscar clientes por nombre.");
		Utils.log(Utils.INFO, "Número de clientes encontrados: " + clients.size());

		// Confirmar la transacción
		session.getTransaction().commit();

		return clients;
	}
}
