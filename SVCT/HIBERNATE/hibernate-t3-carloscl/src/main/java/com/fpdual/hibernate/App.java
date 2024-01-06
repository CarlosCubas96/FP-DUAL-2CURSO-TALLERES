package com.fpdual.hibernate;

/**
 * FPDUAL - HIBERNATE - Taller3  
 * 
 * Clase principal de la aplicación. Puede eliminarse si no se utiliza para un
 * propósito específico, ya que la funcionalidad principal está en los servlets
 * y JSP. La aplicación utiliza Hibernate para la persistencia y JSP para la
 * interfaz, con los servlets manejando la lógica entre el frontend y el
 * backend.
 * 
 * <p>
 * Para ejecutarla, se usará los servlets con un contenedor como Apache Tomcat y se
 * configura el proyecto como una aplicación web.
 * </p>
 * 
 * <p>
 * Nota: Verificar la configuración de Hibernate y la conexión en
 * hibernate.cfg.xml.
 * </p>
 * 
 * @author Carlos
 * 
 */
public class App {

	/**
	 * Método principal de la aplicación. Puede ser eliminado si no se utiliza para
	 * ningún propósito específico.
	 * 
	 * @param args Los argumentos de la línea de comandos, que generalmente no se
	 *             utilizan.
	 */
	public static void main(String[] args) {
		Utils.log(Utils.TRACE, "Aplicación JSP, Hibernate y MYSQL");
	}

}
