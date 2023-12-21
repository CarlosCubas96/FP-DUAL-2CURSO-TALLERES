package com.fpdual.jdbc;

import com.fpdual.jdbc.dbm.ConnectionDB;
import com.fpdual.jdbc.model.Student;
import com.fpdual.jdbc.service.StudentServiceImpl;

/**
 * FP-DUAL - JDBC - Taller1
 * 
 * Clase principal.
 * 
 * @author Carlos
 *
 */
public class App {

	/**
	 * Servicio para operaciones relacionadas con estudiantes.
	 */
	private static StudentServiceImpl service = new StudentServiceImpl();

	/**
	 * Objeto para inicializar datos de estudiantes de forma aleatoria.
	 */
	private static InitializeData initializer = new InitializeData();

	/**
	 * Punto de entrada principal para la aplicación.
	 *
	 * @param args Los argumentos de la línea de comandos.
	 */
	public static void main(String[] args) {

		// Inicia el registro de la aplicación
		initLogging();

		// Genera estudiantes de forma aleatoria al iniciar la aplicación
		initializer.generateRandomStudents();

		// Gestiona las opciones del menú principal
		manageStudents();

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
	private static void manageStudents() {

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
					establishConnectionView();
					break;

				case 2:
					showStudentsView();
					break;
				case 3:
					createStudentView();
					break;
				case 4:
					updateStudentView();
					break;
				case 5:
					deleteStudentView();
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
	 * Método para establecer la conexión a la base de datos.
	 */
	private static void establishConnectionView() {
		Utils.log(Utils.INFO, "Estableciendo conexión a la base de datos...");
		ConnectionDB.establishConnection();
		Utils.log(Utils.INFO, "Conexión establecida con éxito.");
	}

	/**
	 * Método para mostrar todos los estudiantes de la base de datos.
	 */
	private static void showStudentsView() {
		Utils.log(Utils.INFO, "Mostrando todos los estudiantes...");
		service.getAllStudents().forEach(System.out::println);
		Utils.log(Utils.INFO, "Listado de estudiantes mostrado correctamente.");
	}

	/**
	 * Método para crear un nuevo estudiante.
	 */
	private static void createStudentView() {
		Utils.log(Utils.INFO, "Creando un nuevo estudiante...");

		// Se capturan los datos del nuevo estudiante
		String name = Utils.readString("Introduzca el nombre del Estudiante: ");
		String lastName = Utils.readString("Introduzca el apellido del Estudiante: ");
		String address = Utils.readString("Introduzca la dirección del Estudiante: ");
		String phone = Utils.readString("Introduzca el teléfono del Estudiante: ");
		String email = Utils.readString("Introduzca el correo electrónico del Estudiante: ");

		// Se crea una instancia de Student con los datos proporcionados
		Student newStudent = new Student(name, lastName, address, phone, email);

		if (service.createStudent(newStudent)) {
			Utils.log(Utils.INFO, "Estudiante creado exitosamente.");
		} else {
			Utils.log(Utils.ERROR, "Estudiante no creado.");
		}
	}

	/**
	 * Método para actualizar los datos de un estudiante existente.
	 */
	private static void updateStudentView() {
		Utils.log(Utils.INFO, "Actualizando datos de un estudiante...");

		// Se captura el ID del estudiante a modificar
		Long studentId = Utils.readLong("Ingrese el ID del estudiante a modificar: ");

		// Se verifica si el estudiante existe
		Student existingStudent = service.getStudentById(studentId);
		if (existingStudent == null) {
			Utils.log(Utils.ERROR, "Estudiante no encontrado con el ID proporcionado.");
			return;
		}

		// Se capturan los nuevos datos del estudiante
		String newName = Utils.readString("Ingrese el nuevo nombre: ");
		String newLastName = Utils.readString("Ingrese el nuevo apellido: ");
		String newAddress = Utils.readString("Ingrese la nueva dirección: ");
		String newPhone = Utils.readString("Ingrese el nuevo teléfono: ");
		String newEmail = Utils.readString("Ingrese el nuevo correo electrónico: ");

		// Se actualizan los datos del estudiante
		existingStudent.setName(newName);
		existingStudent.setLastName(newLastName);
		existingStudent.setAddress(newAddress);
		existingStudent.setPhone(newPhone);
		existingStudent.setEmail(newEmail);

		if (service.updateStudent(existingStudent)) {
			Utils.log(Utils.INFO, "Estudiante actualizado correctamente.");
		} else {
			Utils.log(Utils.ERROR, "Estudiante no actualizado.");
		}
	}

	/**
	 * Método para eliminar un estudiante existente.
	 */
	private static void deleteStudentView() {
		Utils.log(Utils.INFO, "Eliminando un estudiante...");

		// Se captura el ID del estudiante a eliminar
		Long studentId = Utils.readLong("Ingrese el ID del estudiante a eliminar: ");

		// Se verifica si el estudiante existe
		Student existingStudent = service.getStudentById(studentId);
		if (existingStudent == null) {
			Utils.log(Utils.ERROR, "Estudiante no encontrado con el ID proporcionado.");
			return;
		}

		// Se confirma la eliminación
		if (service.deleteStudent(studentId)) {
			Utils.log(Utils.INFO, "Estudiante eliminado correctamente.");
		} else {
			Utils.log(Utils.ERROR, "Estudiante no eliminado.");
		}
	}

	/**
	 * Muestra el menú principal de la aplicación.
	 */
	private static void showMainMenu() {
		Utils.write("\n---------- Menú Principal ----------");
		Utils.write("1. Establecer conexión a la base de datos");
		Utils.write("2. Mostrar todos los estudiantes");
		Utils.write("3. Crear un nuevo estudiante");
		Utils.write("4. Actualizar datos de un estudiante");
		Utils.write("5. Eliminar un estudiante");
		Utils.write("0. Salir");
		Utils.write("-----------------------------------");
	}

}
