package com.fpdual.hibernate;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.persistence.Contract;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase para inicializar datos de clientes.
 * 
 * <p>
 * Esta clase proporciona métodos para generar datos ficticios de clientes y
 * contratos, insertándolos en la base de datos mediante los servicios de
 * gestión de clientes y contratos.
 * </p>
 * 
 * @author Carlos
 */
public class InitializeData {

	/** Servicio para realizar operaciones relacionadas con clientes. */
	private final ClientManagementServiceImpl clientService;

	/** Servicio para realizar operaciones relacionadas con contratos. */
	private final ContractManagementServiceImpl contractService;

	/** Objeto para generar datos ficticios de clientes de forma aleatoria. */
	private final Faker faker;

	/** Indica si se han generado clientes. */
	private static boolean hasGeneratedClients;

	/**
	 * Constructor de la clase InitializeData.
	 * 
	 * @param clientService   Servicio de gestión de clientes.
	 * @param contractService Servicio de gestión de contratos.
	 */
	public InitializeData(ClientManagementServiceImpl clientService, ContractManagementServiceImpl contractService) {
		this.clientService = clientService;
		this.contractService = contractService;
		faker = new Faker();
	}

	/**
	 * Genera clientes de forma aleatoria y los inserta en la base de datos.
	 */
	public void generateRandomClients() {

		// Verificar si ya se han generado clientes
		if (!hasGeneratedClients) {
			for (int i = 0; i < Constants.N_CLIENTS; i++) {
				Client client = new Client();

				// ID se generará automáticamente

				client.setFirstName(faker.name().firstName());
				client.setLastName(faker.name().lastName());
				client.setSecondLastName(faker.name().lastName());
				client.setIdentityDocument(faker.numerify("########") + faker.letterify("?").toUpperCase());
				client.setUpdatedDate(new Date());
				client.setUpdatedUser(Utils.getHostName());

				// Se añade cada cliente
				clientService.insertNewClient(client);

				for (int j = 0; j < 2; j++) {

					Contract contract = new Contract();
					contract.setClient(client);
					contract.setStartDate(faker.date().future(30, TimeUnit.DAYS));
					contract.setEndDate(faker.date().future(365, TimeUnit.DAYS));
					contract.setMonthlyPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 500)));
					contract.setUpdatedDate(new Date());
					contract.setUpdatedUser(Utils.getHostName());

					// Insertar el nuevo contrato asociado al cliente
					contractService.insertNewContract(contract);

				}

			}

			// Registro de finalización de generación
			Utils.log(Utils.INFO, "Generación de clientes aleatorios completada.");

			// Actualizar el estado
			hasGeneratedClients = true;

		}

	}

	/**
	 * Comprueba si se han generado clientes.
	 * 
	 * @return {@code true} si se han generado clientes, {@code false} en caso
	 *         contrario.
	 */
	public boolean hasGeneratedClients() {
		return hasGeneratedClients;
	}
}
