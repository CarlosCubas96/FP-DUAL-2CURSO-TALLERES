package com.fpdual.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase de configuración
 * 
 * @author Carlos
 *
 */
public class HibernateUtil {

	/** Factoría de sesiones */
	private static final SessionFactory SESSION_FACTORY;
	
	/** Logger para la salida de registros */
	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

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
			logger.info("SessionFactory creado con éxito.");

		} catch (Exception ex) {

			// Error de inicialización.
			logger.error("Error en la Configuración de Hibernate");

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
