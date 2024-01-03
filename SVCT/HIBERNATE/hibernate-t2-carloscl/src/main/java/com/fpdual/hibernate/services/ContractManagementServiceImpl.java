package com.fpdual.hibernate.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.fpdual.hibernate.persistence.Contract;
import com.fpdual.hibernate.persistence.ContractDaoI;
import com.fpdual.hibernate.persistence.ContractDaoImpl;
import com.fpdual.hibernate.Utils;

/**
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Implementación del servicio de contratos.
 * 
 * Este servicio implementa la interfaz ContractManagementServiceI y proporciona
 * operaciones para gestionar contratos en la base de datos.
 * 
 * @author Carlos
 */
public class ContractManagementServiceImpl implements ContractManagementServiceI {

	/** DAO para acceder a la base de datos de contratos. */
	private ContractDaoI contractDao;

	/**
	 * Constructor de la clase.
	 * 
	 * @param session Sesión de Hibernate para la conexión a la base de datos.
	 */
	public ContractManagementServiceImpl(Session session) {
		this.contractDao = new ContractDaoImpl(session);
	}

	/**
	 * Inserta un nuevo contrato en la base de datos.
	 * 
	 * @param newContract El nuevo contrato a insertar.
	 * @return true si la inserción fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean insertNewContract(Contract newContract) {
		Boolean ok = Boolean.FALSE;

		// Verificación de nulidad e inexistencia.
		if (newContract != null && newContract.getContractId() == null) {

			// Inserción del nuevo contrato.
			ok = contractDao.insert(newContract);
			// Log de la operación.
			Utils.log(Utils.INFO, "Contrato insertado: " + newContract.toString());
		} else {
			// Log de error si el contrato es nulo o ya tiene un ID.
			Utils.log(Utils.ERROR, "Contrato nulo o ya tiene un ID asignado.");
		}
		return ok;
	}

	/**
	 * Actualiza un contrato existente en la base de datos.
	 * 
	 * @param updatedContract El contrato actualizado.
	 * @return true si la actualización fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean updateContract(Contract updatedContract) {
		Boolean ok = Boolean.FALSE;

		// Verificación de nulidad y existencia.
		if (updatedContract != null && updatedContract.getContractId() != null) {

			// Actualización del contrato.
			ok = contractDao.update(updatedContract);
			// Log de la operación.
			Utils.log(Utils.INFO, "Contrato actualizado: " + updatedContract.toString());
		} else {
			// Log de error si el contrato es nulo o no tiene un ID asignado.
			Utils.log(Utils.ERROR, "Contrato nulo o sin ID asignado.");
		}
		return ok;
	}

	/**
	 * Elimina un contrato existente de la base de datos.
	 * 
	 * @param deletedContract El contrato a eliminar.
	 * @return true si la eliminación fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean deleteContract(Contract deletedContract) {
		Boolean ok = Boolean.FALSE;

		// Verificación de nulidad y existencia.
		if (deletedContract != null && deletedContract.getContractId() != null) {

			// Eliminación del contrato.
			ok = contractDao.delete(deletedContract);
			// Log de la operación.
			Utils.log(Utils.INFO, "Contrato eliminado: " + deletedContract.toString());
		} else {
			// Log de error si el contrato es nulo o no tiene un ID asignado.
			Utils.log(Utils.ERROR, "Contrato nulo o sin ID asignado.");
		}
		return ok;
	}

	/**
	 * Busca un contrato por su ID en la base de datos.
	 * 
	 * @param contractId El ID del contrato a buscar.
	 * @return El contrato encontrado, o null si no se encuentra.
	 */
	@Override
	public Contract searchById(Long contractId) {

		// Resultado.
		Contract contract = null;

		// Verificación de nulidad.
		if (contractId != null) {

			// Obtención del contrato por ID.
			contract = contractDao.searchById(contractId);

			// Log de la operación.
			if (contract != null) {
				Utils.log(Utils.INFO, "Contrato encontrado por ID: " + contractId);
			} else {
				Utils.log(Utils.INFO, "Contrato no encontrado por ID: " + contractId);
			}
		} else {
			// Log de error si el ID es nulo.
			Utils.log(Utils.ERROR, "ID de contrato nulo.");
		}

		return contract;
	}

	/**
	 * Busca todos los contratos existentes en la base de datos.
	 * 
	 * @return Una lista de todos los contratos.
	 */
	@Override
	public List<Contract> searchAll() {

		// Resultado.
		List<Contract> contractsList;

		// Obtención de contratos.
		contractsList = contractDao.searchAll();

		// Log de la operación.
		Utils.log(Utils.INFO, "Lista de contratos obtenida.");

		return contractsList;
	}

	/**
	 * Busca contratos asociados a un cliente específico por su ID.
	 * 
	 * @param clientId El ID del cliente para el cual buscar contratos.
	 * @return Una lista de contratos asociados al cliente, o una lista vacía si no
	 *         hay coincidencias.
	 */
	@Override
	public List<Contract> searchContractByClientId(Long clientId) {

		// Resultado.
		List<Contract> contractsList = new ArrayList<>();

		// Verificación de nulidad.
		if (clientId != null) {

			// Obtención de contratos por ID de cliente.
			contractsList = contractDao.searchContractByClientId(clientId);

			// Log de la operación.
			Utils.log(Utils.INFO, "Contratos encontrados por ID de cliente: " + clientId);
		} else {
			// Log de error si el ID de cliente es nulo.
			Utils.log(Utils.ERROR, "ID de cliente nulo.");
		}

		return contractsList;
	}
}
