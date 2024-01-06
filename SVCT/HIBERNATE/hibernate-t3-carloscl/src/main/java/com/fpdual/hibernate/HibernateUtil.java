package com.fpdual.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Clase de configuración
 * 
 * @author Carlos
 *
 */
public class HibernateUtil {

	/** Factoría de sesiones */
	private static final SessionFactory SESSION_FACTORY;

	/**
	 * Constructor privado.
	 */
	private HibernateUtil() {

	}

	/**
	 * Generación de factoría de sesiones.
	 */
	static {

		try {

			// Generación de configuración.
			SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
			Utils.log(2, "SessionFactory creado con éxito.");

		} catch (Exception ex) {

			// Error de inicialización.
			Utils.log(4, "Error en la Configuración de Hibernate");

			throw new ExceptionInInitializerError();
		}

	}

	/**
	 * Retorna la factoría de sesiones.
	 * 
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}

}
