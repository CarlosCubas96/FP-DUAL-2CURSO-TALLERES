package com.fpdual.hibernate.services;

import java.util.List;

import org.hibernate.Session;

import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.persistence.ClientDaoI;
import com.fpdual.hibernate.persistence.ClientDaoImpl;

/**
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Implementación del Servicio de Clientes. Proporciona métodos para realizar
 * operaciones CRUD y búsquedas de clientes en la base de datos.
 * 
 * @author Carlos
 */
public class ClientManagementServiceImpl implements ClientManagementServiceI {

	/** DAO: FPDUAL_HEX_CLIENT */
	private ClientDaoI clientDao;

	/**
	 * Constructor de la clase ClientManagementServiceImpl.
	 * 
	 * @param session La sesión de Hibernate utilizada para la conexión a la base de
	 *                datos.
	 */
	public ClientManagementServiceImpl(Session session) {
		this.clientDao = new ClientDaoImpl(session);
	}

	/**
	 * Inserta un nuevo cliente en la base de datos.
	 * 
	 * @param newClient El nuevo cliente a insertar.
	 * @return true si la inserción fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean insertNewClient(Client newClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (newClient != null && newClient.getClientId() == null) {

			// Inserción del nuevo cliente.
			ok = clientDao.insert(newClient);

			// Log para la inserción de un nuevo cliente
			Utils.log(Utils.INFO, "Inserción exitosa de un nuevo cliente con ID: " + newClient.getClientId());
		}

		return ok;
	}

	/**
	 * Actualiza un cliente existente en la base de datos.
	 * 
	 * @param updatedClient El cliente actualizado.
	 * @return true si la actualización fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean updateClient(Client updatedClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (updatedClient != null && updatedClient.getClientId() != null) {

			// Actualización del Cliente.
			ok = clientDao.update(updatedClient);

			// Log para la actualización de un cliente
			Utils.log(Utils.INFO, "Actualización exitosa del cliente con ID: " + updatedClient.getClientId());
		}

		return ok;
	}

	/**
	 * Elimina un cliente de la base de datos.
	 * 
	 * @param deletedClient El cliente a eliminar.
	 * @return true si la eliminación fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean deleteClient(Client deletedClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (deletedClient != null && deletedClient.getClientId() != null) {

			// Eliminación del Cliente.
			ok = clientDao.delete(deletedClient);

			// Log para la eliminación de un cliente
			Utils.log(Utils.INFO, "Eliminación exitosa del cliente con ID: " + deletedClient.getClientId());
		}

		return ok;
	}

	/**
	 * Busca un cliente por su ID en la base de datos.
	 * 
	 * @param clientId El ID del cliente a buscar.
	 * @return El cliente encontrado, o null si no se encuentra.
	 */
	@Override
	public Client searchById(Long clientId) {

		// Resultado.
		Client client = null;

		// Verificación del Cliente.
		if (clientId != null) {

			// Obtención del Cliente por ID.
			client = clientDao.searchById(clientId);

			// Log para la búsqueda de un cliente por ID
			if (client != null) {
				Utils.log(Utils.INFO, "Búsqueda exitosa del cliente con ID: " + clientId);
			} else {
				Utils.log(Utils.INFO, "Cliente no encontrado con ID: " + clientId);
			}
		}

		return client;
	}

	/**
	 * Obtiene una lista de todos los clientes en la base de datos.
	 * 
	 * @return Lista de clientes.
	 */
	@Override
	public List<Client> searchAll() {

		// Obtención de Clientes.
		List<Client> clients = clientDao.searchAll();

		// Log para la búsqueda de todos los clientes
		Utils.log(Utils.INFO, "Búsqueda exitosa de todos los clientes");

		return clients;
	}

	/**
	 * Busca clientes por nombre y apellidos en la base de datos.
	 * 
	 * @param firstName      El nombre del cliente.
	 * @param lastName       El primer apellido del cliente.
	 * @param secondLastName El segundo apellido del cliente.
	 * @return Lista de clientes que coinciden con los parámetros dados.
	 */
	@Override
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName) {

		// Obtención de Clientes.
		List<Client> clients = clientDao.searchByNameAndLastName(firstName, lastName, secondLastName);

		// Log para la búsqueda de clientes por nombre y apellidos
		Utils.log(Utils.INFO, "Búsqueda exitosa de clientes por nombre y apellidos");
		System.out.println(clients);

		return clients;
	}
}
