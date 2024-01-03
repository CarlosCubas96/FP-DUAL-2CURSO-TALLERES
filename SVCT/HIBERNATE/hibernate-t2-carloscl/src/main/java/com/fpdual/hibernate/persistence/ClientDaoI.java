package com.fpdual.hibernate.persistence;

import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller2
 * 
 * Interfaz DAO para la tabla FPDUAL_HEX_CLIENT. Extiende CommonDaoI para
 * heredar métodos comunes.
 * 
 * @author Carlos
 *
 */
public interface ClientDaoI extends CommonDaoI<Client> {

	/**
	 * Busca clientes por nombre y apellidos.
	 * 
	 * @param firstName      El nombre del cliente.
	 * @param lastName       El primer apellido del cliente.
	 * @param secondLastName El segundo apellido del cliente.
	 * @return Lista de clientes que coinciden con los parámetros dados.
	 */
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName);

}
