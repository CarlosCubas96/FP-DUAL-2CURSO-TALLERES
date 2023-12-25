package com.fpdual.jdbc.dbm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import com.fpdual.jdbc.Constants;
import com.fpdual.jdbc.Utils;

/**
 * FP-DUAL - JDBC - Taller1
 * 
 * Esta clase gestiona la conexión a la base de datos y proporciona métodos de
 * utilidad para operaciones en la base de datos.
 * 
 * @author Carlos
 *
 */
public class ConnectionDB {

	/**
	 * Origen de datos utilizado para gestionar las conexiones a la base de datos.
	 */
	private static DataSource dataSource;

	/**
	 * Conexión estática utilizada para almacenar la conexión actual.
	 */
	private static Connection connection;

	/**
	 * Constructor privado
	 */
	private ConnectionDB() {
	}

	/**
	 * Bloque estático que se ejecuta durante la carga de la clase para inicializar
	 * el DataSource.
	 */
	static {
		// Este bloque estático se ejecuta durante la carga de la clase
		initializeDataSource();
	}

	/**
	 * Inicializa el DataSource con los parámetros necesarios para la conexión a la
	 * base de datos.
	 */
	private static void initializeDataSource() {
		// Se crea una instancia de BasicDataSource
		BasicDataSource basicDataSource = new BasicDataSource();

		// Se configuran los parámetros del DataSource
		basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		basicDataSource.setUrl(Constants.NAME_DB);
		basicDataSource.setUsername(Constants.USER_DB);
		basicDataSource.setPassword(Constants.PASSWORD_DB);

		// Establece la cantidad mínima de conexiones inactivas en el pool
		basicDataSource.setMinIdle(1);

		// Establece la cantidad máxima de conexiones inactivas en el pool
		basicDataSource.setMaxIdle(10);

		// Establece la cantidad máxima de PreparedStatement que pueden mantenerse
		// abiertos
		basicDataSource.setMaxOpenPreparedStatements(100);

		basicDataSource.setRemoveAbandonedOnBorrow(true);
		basicDataSource.setRemoveAbandonedOnMaintenance(true);
		basicDataSource.setRemoveAbandonedTimeout(60);

		// Se asigna el DataSource a la variable estática
		dataSource = basicDataSource;
	}

	/**
	 * Verifica la conexión a la base de datos
	 */
	public static void establishConnection() {
		Utils.log(Utils.INFO, "Abriendo la conexión e inicializando el DataSource...");
		if (connection != null) {
			Utils.log(Utils.INFO, "Conexión exitosa: " + connection);
		} else {
			Utils.log(Utils.ERROR, "No se pudo establecer la conexión a la base de datos.");
		}
	}

	/**
	 * Abre una conexión a la base de datos utilizando el DataSource.
	 *
	 * @return La conexión a la base de datos o null en caso de error.
	 */
	public static Connection openConnection() {
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR: No se puede establecer una conexión a la base de datos. " + e.getMessage());
		}
		return connection;
	}

	/**
	 * Cierra la conexión existente a la base de datos.
	 *
	 * Utiliza la conexión almacenada en la clase para cerrarla.
	 */
	public static void closeConnection() {
		if (dataSource != null && connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				Utils.log(Utils.ERROR, "ERROR: No se puede cerrar la conexión a la base de datos. " + e.getMessage());
			}
		}
	}

	/**
	 * Ejecuta una consulta SQL y devuelve el conjunto de resultados.
	 *
	 * @param sql La consulta SQL a ejecutar.
	 * @return El ResultSet que contiene los resultados de la consulta.
	 */
	public static ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR: No se puede ejecutar la consulta SQL. " + e.getMessage());
		}
		return rs;
	}

	/**
	 * Prepara una declaración SQL para su ejecución y devuelve el objeto
	 * PreparedStatement.
	 *
	 * @param sql La declaración SQL a preparar.
	 * @return El objeto PreparedStatement.
	 */
	public static PreparedStatement prepareState(String sql) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR: No se puede preparar la declaración SQL. " + e.getMessage());
		}
		return ps;
	}

	/**
	 * Confirma la transacción actual en la base de datos.
	 */
	public static void commit() {
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
				connection.commit();
			}
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR: No se puede confirmar la transacción. " + e.getMessage());
		}
	}

	/**
	 * Deshace la transacción actual en la base de datos.
	 */
	public static void rollback() {
		try {
			if (connection != null) {
				connection.rollback();
			}
		} catch (SQLException e) {
			Utils.log(Utils.ERROR, "ERROR: No se puede realizar un rollback. " + e.getMessage());
		}
	}
}
