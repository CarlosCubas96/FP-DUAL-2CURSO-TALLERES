package com.fpdual.hibernate;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Clase que contiene constantes utilizadas en la aplicación.
 * 
 * @author Carlos
 *
 */
public class Constants {

    /**
     * Constructor privado para evitar instanciación de la clase.
     */
    private Constants() {
    }

    /**
     * Número de estudiantes deseado.
     * 
     * Esta constante representa el número de estudiantes que se desea gestionar en la aplicación.
     */
    public static final int N_CLIENTS = 3;

    /**
     * Ruta al archivo JSP de error.
     * 
     * Esta constante proporciona la ruta al archivo JSP que se mostrará en caso de error.
     */
    public static final String JSP_ERROR_JSP = "JSP/error/error.jsp";

    /**
     * Nombre del parámetro "clientId".
     * 
     * Esta constante representa el nombre del parámetro utilizado para identificar el clientId en las solicitudes.
     */
    public static final String PARAM_CLIENT_ID = "clientId";
}
