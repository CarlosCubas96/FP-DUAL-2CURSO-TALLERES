package com.fpdual.hibernate.persistence;

import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * DAO de tabla FPDUAL_HEX_CLIENT
 * 
 * @author Carlos
 *
 */
public interface ClientDaoI extends CommonDaoI<Client> {

	public List<Client> searchByName(String firstName, String lastName, String secondLastName);

}
