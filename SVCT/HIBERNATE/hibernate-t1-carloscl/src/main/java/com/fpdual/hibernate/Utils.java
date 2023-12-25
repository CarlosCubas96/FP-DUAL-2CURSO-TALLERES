package com.fpdual.hibernate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * Clase Personal que contiene diversos métodos de utilidad para trabajar con
 * java. Estos métodos incluyen la lectura y escritura de arreglos,
 * ordenamiento, inserción y eliminación de elementos, rotación de arreglos,
 * fusión de arreglos, cálculo de medianas y conteo de ceros etc...
 *
 * @author Carlos
 * @version 2.3
 */
public class Utils {

	/** Scanner para leer de consola */
	static Scanner read = new Scanner(System.in);

	/** String para confirmar continuación */
	static String proceed;

	/** LOGGER */
	private static final Logger LOG = (Logger) LoggerFactory.getLogger(App.class);

	/** Nivel Log Trace */
	public static final int TRACE = 0;

	/** Nivel Log DEBUG */
	public static final int DEBUG = 1;

	/** Nivel Log INFO */
	public static final int INFO = 2;

	/** Nivel Log WARN */
	public static final int WARN = 3;

	/** Nivel Log ERROR */
	public static final int ERROR = 4;

	/** Nº de Iteraciones */
	private static int numIterations = 3000;

	/**
	 * Constructor privado para evitar instanciación de la clase
	 */
	private Utils() {
	}

	// ===================== METHODS TRACEABILITY====================//

	/**
	 * 
	 * Registra un mensaje con el nivel de registro especificado si el registro está
	 * habilitado.
	 * 
	 * @param level   El nivel de registro del mensaje.
	 * @param message El mensaje que se registrará.
	 */
	public static void log(int level, String message) {
		switch (level) {
		case TRACE:
			if (LOG.isTraceEnabled()) {
				LOG.trace(message);
			}
			break;
		case DEBUG:
			if (LOG.isDebugEnabled()) {
				LOG.debug(message);
			}
			break;
		case INFO:
			if (LOG.isInfoEnabled()) {
				LOG.info(message);
			}
			break;
		case WARN:
			if (LOG.isWarnEnabled()) {
				LOG.warn(message);
			}
			break;
		case ERROR:
			if (LOG.isErrorEnabled()) {
				LOG.error(message);
			}
			break;
		default:
			throw new IllegalArgumentException("Nivel de log Inválido: " + level);
		}
	}

	/**
	 * Obtiene el nombre del equipo.
	 *
	 * @return El nombre del equipo, o null si no se puede obtener.
	 */
	public static String getHostName() {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			return localHost.getHostName();
		} catch (UnknownHostException e) {
			// Manejo de la excepción: imprimir el mensaje y devolver null
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Genera un bucle que se ejecuta el número de veces especificado en la variable
	 * 'numIteraciones'. En cada iteración, se registra un mensaje de depuración que
	 * indica el número de iteración actual, si el registro de depuración está
	 * habilitado.
	 */
	static void generateIterations() {
		for (int i = 0; i < numIterations; i++) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Iteration Nº {}", i);
			}
		}
	}

	// ===================== METHODS WRITE ====================//

	/**
	 * Método para escribir texto en la consola con un salto de línea.
	 *
	 * @param text El texto a escribir
	 */
	public static void write(String text) {
		System.out.println(text);
	}

	/**
	 * Método para escribir texto en la consola sin un salto de línea.
	 *
	 * @param text El texto a escribir
	 */
	public static void writeInline(String text) {
		System.out.print(text);
	}

	/**
	 * Método para escribir un texto de error en la consola con un salto de línea.
	 *
	 * @param text El texto de error a escribir
	 */
	public static void writeError(String text) {
		System.err.println(text);
	}

	/**
	 * Método para mostrar el valor de un objeto en la consola.
	 *
	 * @param obj El objeto a mostrar
	 */
	public static void displayObj(Object obj) {
		System.out.print(obj);
	}

	/**
	 * Método para escribir un número entero en la consola con un salto de línea.
	 *
	 * @param number El número entero a escribir
	 */
	public static void write(int number) {
		System.out.println("" + number);
	}

	/**
	 * Método para escribir un número decimal en la consola con un salto de línea.
	 *
	 * @param number El número decimal a escribir
	 */
	public static void write(double number) {
		System.out.println("" + number);
	}

	/**
	 * Método para escribir el contenido de un StringBuilder en la consola con un
	 * salto de línea.
	 *
	 * @param append El StringBuilder a escribir
	 */
	public static void writeBuilder(StringBuilder append) {
		System.out.println("" + append);
	}

	// ===================== METHODS READ ====================//

	/**
	 * Método para leer un valor de tipo float desde la consola con una pregunta.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor float leído desde la consola
	 */
	public static float readFloat(String question) {
		float response;
		writeInline(question);
		response = Float.parseFloat(read.nextLine());
		return response;
	}

	/**
	 * Método para leer un valor de tipo int desde la consola con una pregunta.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor int leído desde la consola
	 */
	public static int readInt(String question) {
		int response;
		write(question);
		response = Integer.parseInt(read.nextLine());
		return response;
	}

	/**
	 * Método para leer un valor de tipo Long desde la consola con una pregunta.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor Long leído desde la consola
	 */
	public static Long readLong(String question) {
		int response;
		write(question);
		response = (int) Long.parseLong(read.nextLine());
		return (long) response;
	}

	/**
	 * Método para leer un valor de tipo int desde la consola con una pregunta con
	 * salto de línea.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor int leído desde la consola
	 */
	public static int readIntln(String question) {
		int response;
		writeInline(question);
		response = Integer.parseInt(read.nextLine());
		return response;
	}

	/**
	 * Método para leer un valor de tipo Integer desde la consola con una pregunta.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor Integer leído desde la consola
	 */
	public static int readInteger(String question) {
		int response;
		write(question);
		response = read.nextInt();
		read.nextLine(); // Consume the line break after the integer
		read.close(); // Close the input resource
		return response;
	}

	/**
	 * Método para leer un valor de tipo Integer desde la consola con una pregunta
	 * con salto de línea.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor Integer
	 */
	public static Integer readIntegerln(String question) {
		Integer response = null;
		boolean ok = false;
		do {
			writeInline(question);
			try {
				response = Integer.parseInt(read.nextLine());
				ok = true;
			} catch (NumberFormatException e) {
				writeError("Invalid format");
			}
		} while (!ok);
		return response;
	}

	/**
	 * Método para leer un valor de tipo String desde la consola con una pregunta.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor String leído desde la consola
	 */
	public static String readString(String question) {
		String response;
		write(question);
		response = read.nextLine();
		return response;
	}

	/**
	 * Método para leer un valor de tipo String desde la consola con una pregunta
	 * con salto de línea.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor String leído desde la consola
	 */
	public static String readStringln(String question) {
		String response = null;
		writeInline(question);
		response = read.nextLine();
		return response;
	}

	/**
	 * Método para leer un valor de tipo char desde la consola con una pregunta.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor char leído desde la consola
	 */
	public static char readChar(String question) {
		int response;
		write(question);
		response = read.nextLine().charAt(0);
		return (char) response;
	}

	/**
	 * Método para leer un valor de tipo char desde la consola con una pregunta con
	 * salto de línea.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor char leído desde la consola
	 */
	public static char readCharln(String question) {
		int response;
		writeInline(question);
		response = read.nextLine().charAt(0);
		return (char) response;
	}

	/**
	 * Método para leer un valor de tipo double desde la consola con una pregunta.
	 *
	 * @param question La pregunta a mostrar en la consola
	 * @return El valor double leído desde la consola
	 */
	public static double readDouble(String question) {
		double response;
		writeInline(question);
		response = Double.parseDouble(read.nextLine());
		return response;
	}

	// =====================OTHERS====================//

	// ========LINE_BREAKS========/

	/**
	 * Imprime una línea vacía en la consola.
	 */
	public static void lineBreak() {
		System.out.println("");
	}

	/**
	 * Imprime un número específico de líneas vacías en la consola.
	 * 
	 * @param lineBreaks El número de saltos de línea a imprimir.
	 */
	public static void lineBreak(int lineBreaks) {
		for (int i = 0; i < lineBreaks; i++) {
			lineBreak();
		}
	}

	// ===================== METHODS OPC====================//
	/**
	 * 
	 * Este método obtiene el nombre del proyecto actual a partir de la ruta del
	 * directorio raíz del proyecto.
	 * 
	 * @return el nombre del proyecto actual.
	 */
	public static String getProjectName() {
		String projectPath = System.getProperty("user.dir");
		String[] pathSplit = projectPath.split("\\\\");
		return pathSplit[pathSplit.length - 1];
	}

	/**
	 * Crea una instancia de la clase Date en Java utilizando los parámetros
	 * proporcionados para el año, mes y día.
	 *
	 * @param year  el año de la fecha a crear
	 * @param month el mes de la fecha a crear (1 = enero, 2 = febrero, ..., 12 =
	 *              diciembre)
	 * @param day   el día del mes de la fecha a crear
	 * @return una instancia de la clase Date que representa la fecha especificada
	 *         por los parámetros proporcionados.
	 */
	public static Date createDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}

	/**
	 * Verifica si un número dado es primo.
	 * 
	 * @param num El número a verificar.
	 * @return true si el número es primo, false en caso contrario.
	 */
	public static boolean isPrime(int num) {
		boolean prime = true;
		for (int i = 2; i < num; i++) {
			if (num % i == 0) {
				prime = false;
			}
		}
		return prime;
	}

	/**
	 * Espera a que el usuario presione Enter para continuar.
	 */
	public static void waitForEnter() {
		proceed = readStringln("\n\t\t PRESIONA ENTER PARA CONTINUAR...");
	}

	/**
	 * Muestra un mensaje de confirmación para salir y espera la respuesta del
	 * usuario.
	 * 
	 * @return true si el usuario confirma la salida, false en caso contrario.
	 */
	public static boolean confirmExit() {
		int option;
		boolean confirm = false;
		option = Utils.readIntln("¿Estás seguro de que quieres salir? [1-SI||2-NO]: ");
		Utils.lineBreak();
		if (option == 1) {
			confirm = true;
		}
		return confirm;
	}

	/**
	 * Muestra un mensaje de confirmación para regresar y espera la respuesta del
	 * usuario.
	 * 
	 * @return true si el usuario confirma el regreso, false en caso contrario.
	 */
	public static boolean goBack() {

		int option;
		boolean confirm = false;
		option = Utils.readIntln("¿Estás seguro de que quieres regresar? [1-SI||2-NO]: ");
		Utils.lineBreak();
		if (option == 1) {
			confirm = true;
		}
		return confirm;
	}

	// ==========================STRING_UTILS===========================//

	/**
	 * Compara dos palabras y muestra la más corta.
	 *
	 * @param word1 La primera palabra a comparar.
	 * @param word2 La segunda palabra a comparar.
	 */
	static void shorterWord(String word1, String word2) {
		String result;
		result = (word1.length() < word2.length()) ? word1 : word2;
		System.out.println(result);
	}

	/**
	 * Cuenta el número de espacios en una oración.
	 *
	 * @param sentence La oración en la que se contarán los espacios.
	 */
	static void countSpaces(String sentence) {
		int counter = 0;
		for (int i = 0; i < sentence.length(); i++) {
			if (sentence.charAt(i) == ' ') {
				counter++;
			}
		}
		System.out.println(counter);
	}

	/**
	 * Comprueba si el carácter en la posición central de una oración es un espacio
	 * o un carácter.
	 *
	 * @param sentence La oración en la cual se verificará el carácter central.
	 */
	static void centralSpaceOrCharacter(String sentence) {
		String message = "";
		for (int i = 0; i < sentence.length() / 2; i++) {
			message = (sentence.charAt(i) == ' ') ? "Es un espacio" : "Es un carácter";
		}
		System.out.println(message);
	}

	/**
	 * Solicita al usuario una serie de números enteros y los almacena en un
	 * arreglo.
	 *
	 * @param numbers El arreglo en el cual se almacenarán los números.
	 */
	static void getNumbers(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = Utils.readIntln("Ingresa un número " + (i + 1) + "/" + numbers.length + ": ");
		}
	}

	/**
	 * Muestra los números en un arreglo en orden inverso.
	 *
	 * @param numbers El arreglo cuyos números se mostrarán en orden inverso.
	 */
	static void showNumbers(int[] numbers) {
		for (int i = numbers.length - 1; i >= 0; i--) {
			Utils.write(i + numbers[i]);
		}
	}

	/**
	 * Solicita al usuario una serie de números enteros y los almacena en un
	 * arreglo.
	 *
	 * @param numbers El arreglo en el cual se almacenarán los números.
	 */
	public static void getIntegerArrayNumbers(Integer[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = Utils.readIntln("Ingresa el valor " + (i + 1) + ": ");
		}
	}

	/**
	 * Método que lee valores enteros del usuario y los almacena en un arreglo
	 * bidimensional.
	 *
	 * @param numbers El arreglo bidimensional en el cual se almacenarán los
	 *                valores.
	 */
	public static void getIntegerTableArrayNumbers(int[][] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i][i] = Utils.readIntln("Ingresa el valor " + (i + 1) + ": ");
		}
	}

	/**
	 * Método que muestra los valores de un arreglo de objetos Integer.
	 *
	 * @param n Arreglo de objetos Integer.
	 */
	public static void displayIntegerNumbers(Integer[] n) {
		for (int i = 0; i < n.length; i++) {
			Utils.writeInline("En la posición " + (i + 1) + " hay " + n[i]);
		}
	}

	/**
	 * Método que muestra los valores de un arreglo de enteros en una sola línea.
	 *
	 * @param n Arreglo de enteros.
	 */
	public static void displayVector(int[] n) {
		for (int i = 0; i < n.length; i++) {
			Utils.writeInline(n[i] + " ");
		}
	}

	/**
	 * Método que muestra los valores de un arreglo de objetos Integer en una sola
	 * línea.
	 *
	 * @param n Arreglo de objetos Integer.
	 */
	public static void displayIntegerVector(Integer[] n) {
		for (int i = 0; i < n.length; i++) {
			Utils.writeInline(n[i] + " ");
		}
	}

	/**
	 * Método que ordena un arreglo de objetos Integer en orden ascendente.
	 *
	 * @param n Arreglo de objetos Integer.
	 */
	public static void sortIntegerVector(Integer[] n) {
		Integer temp;
		for (int i = 0; i < n.length; i++) {
			for (int j = i + 1; j < n.length; j++) {
				if (n[i] > n[j]) {
					temp = n[i];
					n[i] = n[j];
					n[j] = temp;
				}
			}
		}
	}

	/**
	 * Método que ordena un arreglo de enteros en orden ascendente.
	 *
	 * @param n Arreglo de enteros.
	 */
	public static void sortVector(int[] n) {
		int temp;
		for (int i = 0; i < n.length; i++) {
			for (int j = i + 1; j < n.length; j++) {
				if (n[i] > n[j]) {
					temp = n[i];
					n[i] = n[j];
					n[j] = temp;
				}
			}
		}
	}

	/**
	 * Método que inserta un valor en una posición específica en un arreglo de
	 * objetos Integer.
	 *
	 * @param v     Arreglo de objetos Integer.
	 * @param pos   Posición en la que se insertará el valor.
	 * @param value Valor que se insertará en el arreglo.
	 */
	static void insertVector(Integer[] v, int pos, Integer value) {
		for (int i = v.length - 1; i > pos; i--) {
			v[i] = v[i - 1];
		}
		v[pos] = value;
	}

	/**
	 * Método que elimina un valor de una posición específica en un arreglo de
	 * objetos Integer.
	 *
	 * @param v   Arreglo de objetos Integer.
	 * @param pos Posición del valor que se eliminará.
	 */
	static void removeVector(Integer[] v, int pos) {
		for (int i = pos; i < v.length - 1; i++) {
			v[i] = v[i + 1];
		}
		v[v.length - 1] = null;
	}

	/**
	 * Método que rota los valores de un arreglo de objetos Integer hacia la
	 * derecha.
	 *
	 * @param v      Arreglo de objetos Integer.
	 * @param cycles Número de ciclos de rotación.
	 */
	static void rotateRight(Integer[] v, int cycles) {
		for (int i = 0; i < v.length - 1; i++) {
			v[i] = v[i + cycles];
		}
	}

	/**
	 * Fusiona dos vectores de enteros en uno, manteniendo el orden ascendente.
	 *
	 * @param a      Vector de enteros a fusionar
	 * @param b      Vector de enteros a fusionar
	 * @param fusion Vector de enteros resultante de la fusión
	 */
	static void mergeArrays(Integer[] a, Integer[] b, Integer[] fusion) {
		int posA = 0;
		int posB = 0;
		int posF = 0;

		while (posA < a.length && posB < b.length) {
			fusion[posF++] = (a[posA] - a.length < b[posB]) ? a[posA++] : b[posB++];
		}
		while (posA < a.length)
			fusion[posF++] = a[posA++];
		while (posB < b.length)
			fusion[posF++] = b[posB++];
	}

	/**
	 * Calcula la media de los números positivos en un arreglo de números decimales.
	 * 
	 * @param n Arreglo de números decimales
	 * @return La media de los números positivos en el arreglo
	 */
	static double calcularMediaNumerosPositivos(double[] n) {
		double media;
		double suma = 0; // Cambiar el tipo de dato de suma a double para evitar pérdida de precisión
		int contador = 0;
		for (int i = 0; i < n.length; i++) {
			if (n[i] > 0) {
				suma += n[i];
				contador++;
			}
		}
		if (contador > 0) { // Agregar validación para evitar posible división por cero
			media = suma / contador;
		} else {
			media = 0; // Asignar un valor predeterminado en caso de no haber números positivos
		}
		return media;
	}

	/**
	 * 
	 * Calcula la media de los números negativos en un arreglo de números decimales.
	 * 
	 * @param n Arreglo de números decimales
	 * @return La media de los números negativos en el arreglo
	 */
	static double calcularMediaNumerosNegativos(double[] n) {
		double media;
		double suma = 0; // Cambiar el tipo de dato de suma a double para evitar pérdida de precisión
		int contador = 0;
		for (int i = 0; i < n.length; i++) {
			if (n[i] < 0) {
				suma += n[i];
				contador++;
			}
		}
		if (contador > 0) { // Agregar validación para evitar posible división por cero
			media = suma / contador;
		} else {
			media = 0; // Asignar un valor predeterminado en caso de no haber números negativos
		}
		return media;
	}

	/**
	 * 
	 * Cuenta el número de ceros en un arreglo de números decimales.
	 * 
	 * @param n Arreglo de números decimales
	 * @return El número de ceros en el arreglo
	 */
	static int contarCeros(double[] n) {
		int contador = 0;
		for (int i = 0; i < n.length; i++) {
			if (n[i] == 0) {
				contador++;
			}
		}
		return contador;
	}
// ==========================MeTODOS UTILITARIOS===========================//

	/**
	 * 
	 * Muestra los elementos de un arreglo de objetos en la consola.
	 * 
	 * @param cc Arreglo de objetos a mostrar
	 */
	public static void showArray(Object[] cc) {
		for (int i = 0; i < cc.length; i++) {
			System.out.println(cc[i]);
		}
	}

	/**
	 * 
	 * Termina el programa con un mensaje de despedida y cierra la aplicación.
	 */
	public static void endProgram() {
		Utils.write("\nFin del Programa."); // Llamar a un método "write" de la clase "Utils" para mostrar el mensaje
		System.exit(0); // Cerrar la aplicación con código de salida 0 (terminación normal)
		//
	}

}
