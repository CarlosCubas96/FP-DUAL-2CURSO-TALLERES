package com.fpdual.jdbc;

import com.fpdual.jdbc.model.Student;
import com.fpdual.jdbc.service.StudentServiceImpl;
import com.github.javafaker.Faker;

/**
 * FP-DUAL - JDBC - Taller1
 * 
 * Clase para inicializar datos de estudiantes.
 * 
 * @author Carlos
 *
 */
public class InitializeData {

	/** Servicio para realizar operaciones relacionadas con estudiantes. */
	private final StudentServiceImpl service;

	/** Objeto para generar datos ficticios de estudiantes de forma aleatoria. */
	private final Faker faker;

	/**
	 * Constructor de la clase InitializeData. Inicializa la instancia de
	 * StudentServiceImpl y Faker.
	 */
	public InitializeData() {
		service = new StudentServiceImpl();
		faker = new Faker();
	}

	/**
	 * Genera estudiantes de forma aleatoria y los inserta en la base de datos.
	 */
	public void generateRandomStudents() {

		// Limpia la base de datos antes de generar nuevos estudiantes
		service.clearDatabase();

		for (int i = 0; i < Constants.N_STUDENTS; i++) {
			Student student = new Student(
					// ID se generará automáticamente
					faker.name().firstName(), faker.name().lastName(), faker.address().fullAddress(),
					faker.phoneNumber().cellPhone(), faker.internet().emailAddress());

			// Se añade cada estudiante
			service.createStudent(student);
		}
	}
}
