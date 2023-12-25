package com.fpdual.hibernate.services;

import java.util.List;

import com.fpdual.hibernate.persistence.Client;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Interfaz para el servicio de gestión de clientes.
 * Proporciona métodos para realizar operaciones CRUD y búsquedas relacionadas con clientes.
 * 
 * @author Carlos
 *
 */
public interface ClientManagementServiceI {

	/**
	 * Inserta un nuevo cliente en la base de datos.
	 * 
	 * @param newClient El cliente a insertar.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	public Boolean insertNewClient(Client newClient);

	/**
	 * Actualiza la información de un cliente en la base de datos.
	 * 
	 * @param updatedClient El cliente con la información actualizada.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	public Boolean updateClient(Client updatedClient);

	/**
	 * Elimina un cliente de la base de datos.
	 * 
	 * @param deletedClient El cliente a eliminar.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	public Boolean deleteClient(final Client deletedClient);

	/**
	 * Busca un cliente por su identificador (ID).
	 * 
	 * @param clientId El identificador del cliente.
	 * @return El cliente encontrado o null si no se encuentra.
	 */
	public Client searchById(final Long clientId);

	/**
	 * Obtiene la lista de todos los clientes en la base de datos.
	 * 
	 * @return Lista de clientes.
	 */
	public List<Client> searchAll();

	/**
	 * Busca clientes por nombre y apellidos.
	 * 
	 * @param firstName       El nombre del cliente.
	 * @param lastName        El primer apellido del cliente.
	 * @param secondLastName  El segundo apellido del cliente.
	 * @return Lista de clientes que coinciden con los parámetros de búsqueda.
	 */
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName);

}
