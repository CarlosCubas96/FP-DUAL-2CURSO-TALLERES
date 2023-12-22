package com.fpdual.hibernate;

import java.util.List;

import org.hibernate.Session;

import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.persistence.ClientDaoImpl;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase principal.
 * 
 * @author Carlos
 *
 */
public class App {
	public static void main(String[] args) {

		// Apertura de sesión.
		final Session session = HibernateUtil.getSessionFactory().openSession();

		// Crear instancia de ClientDAO (puedes ajustar esto según la estructura de tu
		// aplicación)
		ClientDaoImpl clientDAO = new ClientDaoImpl(session);

		// Crear un nuevo cliente (puedes ajustar esto según la estructura de tu
		// aplicación)
		Client newClient = new Client();
		newClient.setFirstName("John");
		newClient.setLastName("Doe");
		newClient.setSecondLastName("Smith");
		newClient.setIdentityDocument("123456789");

		// Insertar el nuevo cliente
		clientDAO.insert(newClient);

		// Realizar la búsqueda por nombre y apellidos
		String searchFirstName = "John";
		String searchLastName = "Doe";
		String searchSecondLastName = "Smith";

		List<Client> searchResults = clientDAO.searchByName(searchFirstName, searchLastName, searchSecondLastName);

		// Mostrar los resultados
		System.out.println("Resultados de la búsqueda:");
		for (Client client : searchResults) {
			System.out.println("ID: " + client.getClientId() + ", Nombre: " + client.getFirstName() + ", Apellido: "
					+ client.getLastName() + ", Segundo Apellido: " + client.getSecondLastName()
					+ ", Documento de Identidad: " + client.getIdentityDocument());
		}

	}
}
