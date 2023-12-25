package com.fpdual.jdbc.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fpdual.jdbc.Utils;
import com.fpdual.jdbc.dbm.ConnectionDB;
import com.fpdual.jdbc.model.Student;

/**
 * FP-DUAL - JDBC - Taller1
 * 
 * Implementación de la interfaz StudentServiceI que proporciona operaciones
 * CRUD para la entidad Student en la base de datos.
 * 
 * @author Carlos
 *
 */
public class StudentServiceImpl implements StudentServiceI {

	/**
	 * Obtiene todos los estudiantes de la base de datos.
	 *
	 * @return Lista de estudiantes.
	 */
	@Override
	public List<Student> getAllStudents() {
		List<Student> students = new ArrayList<>();

		try {
			// Abre la conexión a la base de datos
			ConnectionDB.openConnection();

			// Ejecuta la consulta SQL para obtener todos los estudiantes
			try (ResultSet rs = ConnectionDB.query("SELECT * FROM FPD_STUDENT")) {
				while (rs.next()) {
					Student student = new Student();
					student.setId(rs.getLong("ID"));
					student.setName(rs.getString("NAME"));
					student.setLastName(rs.getString("LAST_NAME"));
					student.setAddress(rs.getString("ADDRESS"));
					student.setPhone(rs.getString("PHONE"));
					student.setEmail(rs.getString("EMAIL"));
					students.add(student);
				}
				Utils.log(Utils.INFO, "Listado de Estudiantes");
			}
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR AL OBTENER ESTUDIANTES " + e.getMessage());
		} finally {
			// Cierra la conexión a la base de datos
			ConnectionDB.closeConnection();
		}

		return students;
	}

	/**
	 * Obtiene un estudiante por su ID.
	 *
	 * @param id ID del estudiante.
	 * @return Estudiante correspondiente al ID o null si no se encuentra.
	 */
	@Override
	public Student getStudentById(Long id) {
		ConnectionDB.openConnection();
		ResultSet rs = null;
		Student student = null;

		String selectQuery = "SELECT * FROM FPD_STUDENT WHERE ID = ?";
		PreparedStatement ps = ConnectionDB.prepareState(selectQuery);

		try {
			// Establece el parámetro en la consulta SQL
			ps.setLong(1, id);
			rs = ps.executeQuery();

			// Si se encuentra un estudiante, crea un objeto Student y lo devuelve
			if (rs.next()) {
				student = new Student();
				student.setId(rs.getLong("ID"));
				student.setName(rs.getString("NAME"));
				student.setLastName(rs.getString("LAST_NAME"));
				student.setAddress(rs.getString("ADDRESS"));
				student.setPhone(rs.getString("PHONE"));
				student.setEmail(rs.getString("EMAIL"));
			}
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR AL OBTENER ESTUDIANTE POR ID " + e.getMessage());
		} finally {
			// Cierra la conexión a la base de datos
			ConnectionDB.closeConnection();
		}
		return student;
	}

	/**
	 * Crea un nuevo estudiante en la base de datos.
	 *
	 * @param student Estudiante a crear.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	@Override
	public boolean createStudent(Student student) {
		Boolean ok = Boolean.FALSE;

		ConnectionDB.openConnection();
		String insertQuery = "INSERT INTO FPD_STUDENT (NAME, LAST_NAME, ADDRESS, PHONE, EMAIL) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = ConnectionDB.prepareState(insertQuery);

		try {
			// Establece los parámetros en la consulta SQL
			ps.setString(1, student.getName());
			ps.setString(2, student.getLastName());
			ps.setString(3, student.getAddress());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getEmail());

			// Ejecuta la actualización y verifica si tuvo éxito
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ok = Boolean.TRUE;
			}
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR AL CREAR ESTUDIANTE " + e.getMessage());
		} finally {
			// Cierra la conexión a la base de datos
			ConnectionDB.closeConnection();
		}
		return ok;
	}

	/**
	 * Actualiza los datos de un estudiante en la base de datos.
	 *
	 * @param student Estudiante con los nuevos datos.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	@Override
	public boolean updateStudent(Student student) {
		Boolean ok = Boolean.FALSE;

		ConnectionDB.openConnection();
		String updateQuery = "UPDATE FPD_STUDENT SET NAME = ?, LAST_NAME = ?, ADDRESS = ?, PHONE = ?, EMAIL = ? WHERE ID = ?";
		PreparedStatement ps = ConnectionDB.prepareState(updateQuery);

		try {
			// Establece los parámetros en la consulta SQL
			ps.setString(1, student.getName());
			ps.setString(2, student.getLastName());
			ps.setString(3, student.getAddress());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getEmail());
			ps.setLong(6, student.getId());

			// Ejecuta la actualización y verifica si tuvo éxito
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ok = Boolean.TRUE;
			}
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR AL ACTUALIZAR ESTUDIANTE " + e.getMessage());
		} finally {
			// Cierra la conexión a la base de datos
			ConnectionDB.closeConnection();
		}
		return ok;
	}

	/**
	 * Elimina un estudiante por su ID de la base de datos.
	 *
	 * @param id ID del estudiante a eliminar.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	@Override
	public boolean deleteStudent(Long id) {
		Boolean ok = Boolean.FALSE;

		ConnectionDB.openConnection();
		String deleteQuery = "DELETE FROM FPD_STUDENT WHERE ID = ?";
		PreparedStatement ps = ConnectionDB.prepareState(deleteQuery);

		try {
			// Establece el parámetro en la consulta SQL
			ps.setLong(1, id);

			// Ejecuta la eliminación y verifica si tuvo éxito
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ok = Boolean.TRUE;
			}
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR AL ELIMINAR ESTUDIANTE " + e.getMessage());
		} finally {
			// Cierra la conexión a la base de datos
			ConnectionDB.closeConnection();
		}
		return ok;
	}

	/**
	 * Elimina todos los estudiantes existentes en la base de datos y reinicia el
	 * valor AUTO_INCREMENT a 1. También confirma la transacción y maneja los
	 * errores con rollback.
	 */
	@Override
	public void clearDatabase() {
		try {
			// Abre la conexión a la base de datos
			ConnectionDB.openConnection();

			// Elimina todos los estudiantes existentes en la base de datos.
			String deleteSql = "DELETE FROM FPD_STUDENT";
			PreparedStatement deleteStatement = ConnectionDB.prepareState(deleteSql);

			// Verifica si deleteStatement no es nulo antes de ejecutar la eliminación
			if (deleteStatement != null) {
				deleteStatement.executeUpdate();

				// Reinicia el valor AUTO_INCREMENT a 1.
				String resetAutoIncrementSql = "ALTER TABLE FPD_STUDENT AUTO_INCREMENT = 1";
				PreparedStatement resetAutoIncrementStatement = ConnectionDB.prepareState(resetAutoIncrementSql);

				// Verifica si resetAutoIncrementStatement no es nulo antes de ejecutar la
				// actualización
				if (resetAutoIncrementStatement != null) {
					resetAutoIncrementStatement.executeUpdate();
				} else {
					Utils.log(Utils.ERROR, "ERROR: No se pudo crear la declaración SQL para reiniciar AUTO_INCREMENT.");
				}
			} else {
				Utils.log(Utils.ERROR, "ERROR: No se pudo crear la declaración SQL para eliminar estudiantes.");
			}

			Utils.log(Utils.INFO, "Limpiando Base de datos");

			// Confirma la transacción
			ConnectionDB.commit();
		
		} catch (SQLException e) {
			// En caso de error, realiza un rollback
			ConnectionDB.rollback();
			Utils.log(Utils.ERROR,
					"ERROR: No se pudo eliminar todos los estudiantes de la base de datos. " + e.getMessage());
		} finally {
			// Cierra la conexión
			ConnectionDB.closeConnection();
		}
	}
}
