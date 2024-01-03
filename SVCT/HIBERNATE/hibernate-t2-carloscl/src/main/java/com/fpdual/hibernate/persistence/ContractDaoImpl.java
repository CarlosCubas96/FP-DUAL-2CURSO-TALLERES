package com.fpdual.hibernate.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fpdual.hibernate.Utils;

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

}
