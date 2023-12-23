package com.fpdual.hibernate.services;

import java.util.List;
import org.hibernate.Session;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.persistence.ClientDaoI;
import com.fpdual.hibernate.persistence.ClientDaoImpl;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Implementación del Servicio de Clientes.
 * Proporciona métodos para realizar operaciones CRUD y búsquedas de clientes en la base de datos.
 * 
 * @author Carlos
 *
 */
public class ClientManagementServiceImpl implements ClientManagementServiceI {

	/** DAO: FPDUAL_HEX_CLIENT */
	private ClientDaoI clientDao;

	/**
	 * Constructor de la clase ClientManagementServiceImpl.
	 * 
	 * @param session La sesión de Hibernate utilizada para la conexión a la base de datos.
	 */
	public ClientManagementServiceImpl(Session session) {
		this.clientDao = new ClientDaoImpl(session);
	}

	@Override
	public Boolean insertNewClient(Client newClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (newClient != null && newClient.getClientId() == null) {

			// Inserción del nuevo cliente.
			ok = clientDao.insert(newClient);
		}

		return ok;
	}

	@Override
	public Boolean updateClient(Client updatedClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (updatedClient != null && updatedClient.getClientId() != null) {

			// Actualización del Cliente.
			ok = clientDao.update(updatedClient);
		}

		return ok;
	}

	@Override
	public Boolean deleteClient(Client deletedClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (deletedClient != null && deletedClient.getClientId() != null) {

			// Eliminación del Cliente.
			ok = clientDao.delete(deletedClient);
		}

		return ok;
	}

	@Override
	public Client searchById(Long clientId) {

		// Resultado.
		Client client = null;

		// Verificación del Cliente.
		if (clientId != null) {

			// Obtención del Cliente por ID.
			client = clientDao.searchById(clientId);
		}

		return client;
	}

	@Override
	public List<Client> searchAll() {

		// Obtención de Clientes.
		return clientDao.searchAll();

	}

	@Override
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName) {

		// Obtención de Clientes.
		return clientDao.searchByNameAndLastName(firstName, lastName, secondLastName);

	}
}
