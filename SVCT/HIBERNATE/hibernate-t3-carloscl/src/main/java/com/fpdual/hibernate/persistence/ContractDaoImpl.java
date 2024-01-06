package com.fpdual.hibernate.persistence;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fpdual.hibernate.Utils;

/**
 * 
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Implementación de la interfaz ContractDaoI que proporciona métodos para
 * acceder y manipular datos relacionados con contratos en la base de datos.
 * 
 * Extiende la clase CommonDaoImpl para reutilizar funcionalidades comunes.
 * 
 * @author Carlos
 * 
 */
public class ContractDaoImpl extends CommonDaoImpl<Contract> implements ContractDaoI {

	/** Sesión de conexión a BD */
	private Session session;

	/**
	 * Constructor que recibe la sesión de Hibernate.
	 * 
	 * @param session Sesión de conexión a BD.
	 */
	public ContractDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	/**
	 * Busca contratos por el ID del cliente asociado.
	 * 
	 * @param clientId El ID del cliente.
	 * @return Una lista de contratos asociados con el ID de cliente especificado.
	 */
	@Override
	public List<Contract> searchContractByClientId(Long clientId) {
		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Consulta HQL para buscar contratos por el ID del cliente asociado.
		String hql = "FROM Contract c WHERE c.client.id = :clientId";
		Query<Contract> query = session.createQuery(hql, Contract.class);
		query.setParameter("clientId", clientId);

		// Agregar log para la consulta realizada
		Utils.log(Utils.INFO, "Realizando consulta para buscar contratos por el ID del cliente.");

		// Ejecutar la consulta y devolver los resultados.
		List<Contract> contracts = query.list();

		// Agregar log para la cantidad de resultados encontrados
		Utils.log(Utils.INFO, "Número de contratos encontrados: " + contracts.size());

		// Confirmar la transacción.
		session.getTransaction().commit();

		return contracts;
	}

	/**
	 * Busca contratos por el DNI del cliente asociado utilizando JPA Criteria.
	 *
	 * @param identityDocument El DNI del cliente.
	 * @return Una lista de contratos asociados con el DNI de cliente especificado.
	 */
	@Override
	public List<Contract> searchContractByClientIdentityDocument(String identityDocument) {

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Creación del CriteriaBuilder para Contract
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		// Creación del CriteriaQuery
		CriteriaQuery<Contract> criteriaQuery = criteriaBuilder.createQuery(Contract.class);
		Root<Contract> contractRoot = criteriaQuery.from(Contract.class);

		// Unión con la entidad Client
		Join<Contract, Client> clientJoin = contractRoot.join("client");

		// Agregar condición para hacer coincidir el DNI del cliente
		Predicate condition = criteriaBuilder.equal(clientJoin.get("identityDocument"), identityDocument);
		criteriaQuery.where(condition);

		// Realizar la consulta
		List<Contract> contracts = session.createQuery(criteriaQuery).getResultList();

		// Registros
		Utils.log(Utils.INFO, "Realizando consulta para buscar contratos por DNI del cliente.");
		Utils.log(Utils.INFO, "Número de contratos encontrados: " + contracts.size());

		// Confirmar la transacción
		session.getTransaction().commit();

		return contracts;
	}

}
