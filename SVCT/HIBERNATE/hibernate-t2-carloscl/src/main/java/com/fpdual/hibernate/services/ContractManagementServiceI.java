package com.fpdual.hibernate.services;

import java.util.List;

import com.fpdual.hibernate.persistence.Contract;

/**
 * FPDUAL - HIBERNATE - Taller2
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
	 * @return True si la operación fue exitosa, False si falló.
	 */
	public Boolean insertNewContract(Contract newContract);

	/**
	 * Actualiza un contrato existente.
	 * 
	 * @param updatedContract El contrato actualizado.
	 * @return True si la operación fue exitosa, False si falló.
	 */
	public Boolean updateContract(Contract updatedContract);

	/**
	 * Elimina un contrato existente.
	 * 
	 * @param deletedContract El contrato a eliminar.
	 * @return True si la operación fue exitosa, False si falló.
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
}
