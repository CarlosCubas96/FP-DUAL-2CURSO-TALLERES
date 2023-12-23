package com.fpdual.hibernate;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase para inicializar datos de clientes.
 * 
 * @author Carlos
 *
 */
public class InitializeData {

	/** Logger para la salida de registros */
	private static final Logger logger = LoggerFactory.getLogger(InitializeData.class);

	/** Servicio para realizar operaciones relacionadas con clientes. */
	private final ClientManagementServiceImpl service;

	/** Objeto para generar datos ficticios de clientes de forma aleatoria. */
	private final Faker faker;

	/**
	 * Constructor de la clase InitializeData. Inicializa la instancia de
	 * ClientManagementServiceImpl y Faker.
	 * 
	 * @param service Servicio de Implementacion
	 */
	public InitializeData(ClientManagementServiceImpl service) {
		this.service = service;
		faker = new Faker();
	}

	/**
	 * Genera clientes de forma aleatoria y los inserta en la base de datos.
	 */
	public void generateRandomClients() {

		for (int i = 0; i < Constants.N_CLIENTS; i++) {
			Client client = new Client();

			// ID se generará automáticamente

			client.setFirstName(faker.name().firstName());
			client.setLastName(faker.name().lastName());
			client.setSecondLastName(faker.name().lastName());
			client.setIdentityDocument(faker.numerify("########") + faker.letterify("?"));
			client.setUpdatedDate(new Date());
			client.setUpdatedUser(Utils.getHostName());

			// Se añade cada cliente
			service.insertNewClient(client);
		}

		// Registro de finalización de generación
		logger.info("Generación de clientes aleatorios completada.");
	}
}
