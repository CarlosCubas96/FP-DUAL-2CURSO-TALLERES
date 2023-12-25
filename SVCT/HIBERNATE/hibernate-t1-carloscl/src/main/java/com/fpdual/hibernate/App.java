package com.fpdual.hibernate;

import java.util.Date;

import org.hibernate.Session;

import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase principal.
 * 
 * @author Carlos
 *
 */
public class App {

	/** Sesión de Hibernate para la interacción con la base de datos */
	static Session session = HibernateUtil.getSessionFactory().openSession();

	/** Servicio para la gestión de clientes */
	static ClientManagementServiceImpl service = new ClientManagementServiceImpl(session);

	/**
	 * Objeto para inicializar datos de estudiantes de forma aleatoria.
	 */
	private static InitializeData initializer = new InitializeData(service);

	/**
	 * Método principal que inicia la aplicación.
	 * 
	 * @param args Argumentos de la línea de comandos (no utilizado)
	 */
	public static void main(String[] args) {

		// Inicia el registro de la aplicación
		initLogging();

		// Genera clientes de forma aleatoria al iniciar la aplicación
		initializer.generateRandomClients();

		// Gestiona las opciones del menú principal
		manageClients();

		// Fin de programa
		Utils.endProgram();
	}

	/**
	 * Inicializa el registro de la aplicación.
	 */
	private static void initLogging() {
		Utils.log(Utils.INFO, "Iniciando la aplicación...");
	}

	/**
	 * Método para gestionar las opciones del menú.
	 */
	private static void manageClients() {

		// Opción a elegir
		int option;

		// Se confirma salida para salir del bucle
		boolean exit = false;
		do {
			showMainMenu();

			// Menú de opciones
			try {
				option = Utils.readIntln("Option [0 - Exit]: ");
				switch (option) {
				case 0:
					// Se verifica si queremos salir del programa
					exit = Utils.confirmExit();
					break;
				case 1:
					showClientsView();
					break;
				case 2:
					createClientView();
					break;
				case 3:
					updateClientView();
					break;
				case 4:
					deleteClientView();
					break;
				default:
					Utils.writeError("INTRODUZCA UNA OPCIÓN CORRECTA");
					break;
				}
			} catch (NumberFormatException e) {
				Utils.log(Utils.ERROR, "ERROR " + e.getMessage());
			} catch (Exception e) {
				Utils.log(Utils.ERROR, "ERROR GENERAL " + e.getMessage());
				e.printStackTrace();
			}

		} while (!exit);

		// Fin de programa
		Utils.log(Utils.INFO, "Cerrando la aplicación...");
	}

	/**
	 * Método para mostrar todos los clientes de la base de datos.
	 */
	private static void showClientsView() {
		Utils.log(Utils.INFO, "Mostrando todos los clientes...");
		service.searchAll().forEach(System.out::println);
		Utils.log(Utils.INFO, "Listado de clientes mostrado correctamente.");
	}

	/**
	 * Método para crear un nuevo cliente.
	 */
	private static void createClientView() {
		Utils.log(Utils.INFO, "Creando un nuevo cliente...");

		// Se capturan los datos del nuevo cliente
		String firstName = Utils.readString("Introduzca el nombre del Cliente: ");
		String lastName = Utils.readString("Introduzca el apellido del Cliente: ");
		String secondLastName = Utils.readString("Introduzca el segundo apellido del Cliente: ");
		String identityDocument = Utils.readString("Introduzca el DNI del Cliente: ");

		// Se crea una instancia de Client con los datos proporcionados
		Client newClient = new Client();

		// Se agregan sus parametros
		newClient.setFirstName(firstName);
		newClient.setLastName(lastName);
		newClient.setSecondLastName(secondLastName);
		newClient.setIdentityDocument(identityDocument);
		newClient.setUpdatedDate(new Date());
		newClient.setUpdatedUser(Utils.getHostName());

		// Se confirma si se ha insertado el nuevo cliente
		if (Boolean.TRUE.equals(service.insertNewClient(newClient))) {
			Utils.log(Utils.INFO, "Cliente creado exitosamente.");
		} else {
			Utils.log(Utils.ERROR, "El cliente no ha sido creado.");
		}
	}

	/**
	 * Método para actualizar los datos de un cliente existente.
	 */
	private static void updateClientView() {
		Utils.log(Utils.INFO, "Actualizando datos de un cliente...");

		// Se captura el ID del cliente a modificar
		Long clientId = Utils.readLong("Ingrese el ID del cliente a modificar: ");

		// Se verifica si el cliente existe
		Client existingClient = service.searchById(clientId);
		if (existingClient == null) {
			Utils.log(Utils.ERROR, "Cliente no encontrado con el ID proporcionado.");
			return;
		}

		// Se capturan los nuevos datos del cliente
		String newFirstName = Utils.readString("Ingrese el nuevo nombre: ");
		String newLastName = Utils.readString("Ingrese el nuevo apellido: ");
		String newSecondLastName = Utils.readString("Ingrese el nuevo segundo apellido: ");
		String newIdentityDocument = Utils.readString("Ingrese el nuevo documento de identidad: ");

		// Se actualizan los datos del cliente
		existingClient.setFirstName(newFirstName);
		existingClient.setLastName(newLastName);
		existingClient.setSecondLastName(newSecondLastName);
		existingClient.setIdentityDocument(newIdentityDocument);
		existingClient.setUpdatedDate(new Date());

		// Se confirma si se ha actualizado el nuevo cliente
		if (Boolean.TRUE.equals(service.updateClient(existingClient))) {
			Utils.log(Utils.INFO, "Cliente actualizado correctamente.");
		} else {
			Utils.log(Utils.ERROR, "El cliente no ha sido actualizado.");
		}
	}

	/**
	 * Método para eliminar un cliente existente.
	 */
	private static void deleteClientView() {
		Utils.log(Utils.INFO, "Eliminando un cliente...");

		// Se captura el ID del cliente a eliminar
		Long clientId = Utils.readLong("Ingrese el ID del cliente a eliminar: ");

		// Se verifica si el cliente existe
		Client existingClient = service.searchById(clientId);
		if (existingClient == null) {
			Utils.log(Utils.ERROR, "Cliente no encontrado con el ID proporcionado.");
			return;
		}

		// Se confirma si se ha eliminado el nuevo cliente
		if (Boolean.TRUE.equals(service.deleteClient(existingClient))) {
			Utils.log(Utils.INFO, "Cliente eliminado correctamente.");
		} else {
			Utils.log(Utils.ERROR, "El cliente no ha sido eliminado.");
		}
	}

	/**
	 * Muestra el menú principal de la aplicación.
	 */
	private static void showMainMenu() {
		Utils.write("\n---------- Menú Principal ----------");
		Utils.write("1. Mostrar todos los clientes");
		Utils.write("2. Crear un nuevo cliente");
		Utils.write("3. Actualizar datos de un cliente");
		Utils.write("4. Eliminar un cliente");
		Utils.write("0. Salir");
		Utils.write("-----------------------------------");
	}
}
