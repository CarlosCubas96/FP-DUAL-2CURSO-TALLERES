package com.fpdual.hibernate.services;

import java.util.List;

import com.fpdual.hibernate.persistence.Contract;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Interfaz del servicio de contratos.
 * 
 * Esta interfaz proporciona métodos para la gestión de contratos.
 * 
 * @author Carlos
 */
public interface ContractManagementServiceI {

	/**
	 * Inserta un nuevo contrato.
	 * 
	 * @param newContract El nuevo contrato a insertar.
	 */
	public Boolean insertNewContract(Contract newContract);

	/**
	 * Actualiza un contrato existente.
	 * 
	 * @param updatedContract El contrato actualizado.
	 * @return
	 */
	public Boolean updateContract(Contract updatedContract);

	/**
	 * Elimina un contrato existente.
	 * 
	 * @param deletedContract El contrato a eliminar.
	 */
	public Boolean deleteContract(Contract deletedContract);

	/**
	 * Obtiene un contrato mediante su ID.
	 * 
	 * @param contractId El ID del contrato a buscar.
	 * @return El contrato encontrado, o null si no se encuentra.
	 */
	public Contract searchById(Long contractId);

	/**
	 * Obtiene todos los contratos existentes.
	 * 
	 * @return Una lista de todos los contratos.
	 */
	public List<Contract> searchAll();

	/**
	 * Obtiene una lista de contratos asociados a un cliente específico mediante su
	 * ID.
	 * 
	 * @param clientId El ID del cliente para el cual buscar contratos.
	 * @return Una lista de contratos asociados al cliente, o una lista vacía si no
	 *         hay coincidencias.
	 */
	public List<Contract> searchContractByClientId(Long clientId);

	/**
	 * Busca contratos asociados a un cliente específico por su número de documento
	 * de identidad (DNI).
	 * 
	 * @param identityDocument El número de documento de identidad (DNI) del
	 *                         cliente.
	 * @return Una lista de contratos asociados al cliente con el DNI especificado,
	 *         o una lista vacía si no hay coincidencias.
	 * 
	 */
	public List<Contract> searchContractByClientIdentityDocument(String identityDocument);
}
