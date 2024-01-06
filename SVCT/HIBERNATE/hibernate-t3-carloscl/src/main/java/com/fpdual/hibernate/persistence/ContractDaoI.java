package com.fpdual.hibernate.persistence;

import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Interfaz DAO para la tabla FPDUAL_HEX_CONTRACT. Extiende CommonDaoI para
 * heredar métodos comunes.
 * 
 * @author Carlos
 *
 */
public interface ContractDaoI extends CommonDaoI<Contract> {

	/**
	 * Busca contratos por el ID del cliente asociado.
	 * 
	 * @param clientId El ID del cliente.
	 * @return Una lista de contratos asociados con el ID de cliente especificado.
	 */
	List<Contract> searchContractByClientId(Long clientId);

	/**
	 * Busca contratos por el DNI del cliente asociado.
	 * 
	 * @param identityDocument El DNI del cliente.
	 * @return Una lista de contratos asociados con el ID de cliente especificado.
	 */
	List<Contract> searchContractByClientIdentityDocument(String identityDocument);

}
