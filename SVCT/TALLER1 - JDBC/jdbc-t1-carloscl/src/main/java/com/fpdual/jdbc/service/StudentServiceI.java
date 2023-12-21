package com.fpdual.jdbc.service;

import java.util.List;

import com.fpdual.jdbc.model.Student;

/**
 * FP-DUAL - JDBC - Taller1
 * 
 * Interfaz que define operaciones CRUD para la entidad Estudiante.
 * 
 * @author Carlos
 *
 */
public interface StudentServiceI {

	/**
	 * Recupera una lista de todos los estudiantes.
	 *
	 * @return Lista de estudiantes.
	 */
	List<Student> getAllStudents();

	/**
	 * Recupera un estudiante por su ID.
	 *
	 * @param id El ID del estudiante.
	 * @return El estudiante encontrado o null si no existe.
	 */
	Student getStudentById(Long id);

	/**
	 * Crea un nuevo estudiante.
	 *
	 * @param student El estudiante que se va a crear.
	 * @return true si se crea exitosamente, false en caso contrario.
	 */
	boolean createStudent(Student student);

	/**
	 * Actualiza la información de un estudiante.
	 *
	 * @param student El estudiante con la información actualizada.
	 * @return true si se actualiza exitosamente, false en caso contrario.
	 */
	boolean updateStudent(Student student);

	/**
	 * Elimina un estudiante por su ID.
	 *
	 * @param id El ID del estudiante a eliminar.
	 * @return true si se elimina exitosamente, false en caso contrario.
	 */
	boolean deleteStudent(Long id);

	/**
	 * Elimina todos los estudiantes existentes en la base de datos.
	 */
	void clearDatabase();
}
