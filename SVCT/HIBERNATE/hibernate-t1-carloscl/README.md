# Taller 1 Hibernate - Proyecto hibernate-t1-iniciales

## Descripción del Proyecto
Este proyecto tiene como objetivo implementar un sistema web utilizando Hibernate para gestionar la entidad "Cliente". Se utilizará el patrón DAO para realizar operaciones CRUD en la base de datos, y se incluirá un servicio de gestión de clientes que consumirá el DAO correspondiente. Además, se implementará un método de búsqueda por nombres y apellidos en la entidad Cliente.

## Evaluación

### Se evaluará:
- **Generación del Proyecto:**
  - Asegurar que el proyecto se genere correctamente con el nombre adecuado.

- **Uso de Dependencias y Librerías:**
  - Incluir las dependencias necesarias en el archivo `pom.xml` para Hibernate y otras funcionalidades.

- **Implementación del DAO:**
  - Crear un DAO para la entidad Cliente con métodos CRUD y un método de búsqueda por nombres y apellidos.

- **Servicio de Gestión de Clientes:**
  - Crear un servicio que consuma el DAO de la entidad Cliente y utilice todos los métodos implementados.

- **Iniciativa y Creatividad:**
  - Mostrar iniciativa y creatividad al incorporar funcionalidades no solicitadas que mejoren el proyecto.

- **Buenas Prácticas:**
  - Demostrar el uso de buenas prácticas en el desarrollo del proyecto.

## Instrucciones

### Ejecución de Scripts en el Gestor de Base de Datos
1. Ejecutar los scripts proporcionados por el formador/a en un gestor de base de datos como PHPMyAdmin, SQL Developer, MySQL Workbench, HeidiSQL, etc.

### Creación del Proyecto Maven
2. Crear un proyecto Apache Maven utilizando el nombre adecuado para el proyecto.

### Configuración de Hibernate
3. Configurar Hibernate mediante anotaciones JPA en la entidad Cliente. Puede realizarse mediante el uso de un archivo `hibernate.cfg.xml` o, en caso de utilizar Spring Boot, a través de `application.properties`.

### Implementación del DAO
4. Crear un DAO para la entidad Cliente con métodos CRUD y un método de búsqueda por nombres y apellidos.

### Servicio de Gestión de Clientes
5. Crear un servicio que consuma el DAO de la entidad Cliente y utilice todos los métodos implementados.

### Código Fuente
6. Implementar la clase principal (`App.java` o similar) para consumir el servicio de gestión de clientes y probar todas las operaciones.

## Ejecución

**1. Ejecución de Scripts en el Gestor de Base de Datos**
    
> [!NOTE]
> El Script se encuentra en la carpeta **SQL**

```sql

-- Autor       : Carlos Cubas Lorca 
-- Descripción : Taller 1 HIBERNATE BD MySQL - FP DUAL
------------------------------------------------------

-- Eliminación de la base de datos si existe.
DROP DATABASE IF EXISTS FPDUAL_HIBERNATE_MYSQL_T2;

-- Creación de la base de datos.
CREATE DATABASE FPDUAL_HIBERNATE_MYSQL_T2 DEFAULT CHARACTER SET UTF8MB4;

-- Uso de la base de datos.
USE FPDUAL_HIBERNATE_MYSQL_T2;

-- Creación de tabla para Clientes.
CREATE TABLE FPDUAL_HEX_CLIENT (
    CLIENT_ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID del CLiente',
    FIRST_NAME VARCHAR(100) COMMENT 'Nombre',
    LAST_NAME VARCHAR(100) COMMENT 'Primer apellido',
    SECOND_LAST_NAME VARCHAR(100) COMMENT 'Segundo apellido',
    IDENTITY_DOCUMENT VARCHAR(9) UNIQUE NOT NULL COMMENT 'Documento de identidad',
	AUDIT_UPDATED_USER VARCHAR(255) NOT NULL COMMENT 'Usuario que actualiza',
    AUDIT_UPDATED_DATE DATETIME NOT NULL COMMENT 'Día actualizado'
);

```

**2. Creación del Proyecto Maven**

Creamos un proyecto maven con estas características:

```xml

 	<!-- DETALLES DEL PROYECTO -->
	<groupId>com.fpdual</groupId>
	<artifactId>hibernate-t1-carloscl</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>hibernate-t1-carloscl</name>
	<description>PROYECTO - HIBERNATE - TALLER 1</description>

```

**3. Configuración de Hibernate**

Configuramos Hibernate utilizando anotaciones JPA en la entidad Cliente y, si es necesario, mediante el archivo hibernate.cfg.xml:

- **Configuración de conexión a MySQL:**
Establece los parámetros de conexión a MySQL, incluyendo la URL, el controlador de la base de datos, y las credenciales del usuario.

```xml

<!-- Configuración de conexión a MySQL -->
		<property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
		<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/FPDUAL_HIBERNATE_MYSQL_T2?createDatabaseIfNotExist=true&amp;useSSL=false&amp;serverTimezone=UTC&amp;allowPublicKeyRetrieval=true</property>
		<property name="hibernate.connection.username">sqladmin</property>
		<property name="hibernate.connection.password">Elena12121234</property>
		<property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>

```

- **Configuración adicional:**
Incluye propiedades adicionales para Hibernate, como mostrar y formatear SQL.

```xml

		<!-- Configuración adicional -->
		<property name="hibernate.show_sql">true</property>
		<property name="hibernate.format_sql">true</property>

```

- **Configuración del pool de conexiones:**
Define parámetros para el pool de conexiones c3p0, especificando el tamaño mínimo y máximo, el tiempo de espera, el número máximo de declaraciones y el período de inactividad.

```xml
		<!-- Configuración del pool de conexiones (opcional) -->
		<property name="hibernate.c3p0.min_size">5</property>
		<property name="hibernate.c3p0.max_size">20</property>
		<property name="hibernate.c3p0.timeout">300</property>
		<property name="hibernate.c3p0.max_statements">50</property>
		<property name="hibernate.c3p0.idle_test_period">3000</property>
```

- **Mapeo de clases:**
Indica el mapeo de la clase Client en el archivo de configuración de Hibernate.


```xml
		<!-- Mapeos de clases -->
		<mapping class="com.fpdual.hibernate.persistence.Client" />

  ```


**4. Implementación del DAO**

La implementación del DAO se realiza mediante la clase `CommonDaoImpl`, que proporciona operaciones CRUD genéricas y comunes para todas las entidades. La clase específica `ClientDaoImpl` extiende esta implementación para adaptarse a la entidad Cliente.

```java

package com.fpdual.hibernate.persistence;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.fpdual.hibernate.Utils;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Implementación del DAO para la tabla FPDUAL_HEX_CLIENT. Extiende
 * CommonDaoImpl para heredar la funcionalidad común.
 * 
 * Esta clase proporciona métodos específicos para manipular la entidad Client
 * en la base de datos.
 * 
 * @author Carlos
 */
public class ClientDaoImpl extends CommonDaoImpl<Client> implements ClientDaoI {

	/** Sesión de conexión a BD */
	private Session session;

	/**
	 * Constructor que recibe la sesión de Hibernate.
	 * 
	 * @param session Sesión de conexión a BD.
	 */
	public ClientDaoImpl(Session session) {
		super(session);
		this.session = session;
	}

	/**
	 * Busca clientes por nombre y apellidos.
	 * 
	 * @param firstName      El nombre del cliente.
	 * @param lastName       El primer apellido del cliente.
	 * @param secondLastName El segundo apellido del cliente.
	 * @return Lista de clientes que coinciden con los parámetros dados.
	 */
	@Override
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName) {
		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Consulta HQL para buscar clientes por nombre y apellidos.
		String hql = "FROM Client c WHERE c.firstName = :firstName AND c.lastName = :lastName AND c.secondLastName = :secondLastName";
		Query<Client> query = session.createQuery(hql, Client.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		query.setParameter("secondLastName", secondLastName);

		// Agregar log para la consulta realizada
		Utils.log(Utils.INFO, "Realizando consulta para buscar clientes por nombre y apellidos.");

		// Ejecutar la consulta y devolver los resultados.
		List<Client> clients = query.list();

		// Agregar log para la cantidad de resultados encontrados
		Utils.log(Utils.INFO, "Número de clientes encontrados: " + clients.size());

		// Confirmar la transacción.
		session.getTransaction().commit();

		return clients;
	}
}

}

```

**5. Crear un servicio que consuma el DAO de la entidad Cliente y utilice todos los métodos implementados.**

Se ha creado el servicio `ClientManagementServiceImpl` que utiliza el DAO (`ClientDaoI`) para realizar operaciones CRUD y 
búsquedas de clientes en la base de datos. Este servicio se encarga de gestionar las operaciones relacionadas con los clientes.

```java

package com.fpdual.hibernate.services;

import java.util.List;

import org.hibernate.Session;

import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.persistence.ClientDaoI;
import com.fpdual.hibernate.persistence.ClientDaoImpl;

/**
 * FPDUAL - HIBERNATE - Taller1 Implementación del Servicio de Clientes.
 * Proporciona métodos para realizar operaciones CRUD y búsquedas de clientes en
 * la base de datos.
 * 
 * @author Carlos
 */
public class ClientManagementServiceImpl implements ClientManagementServiceI {

	/** DAO: FPDUAL_HEX_CLIENT */
	private ClientDaoI clientDao;

	/**
	 * Constructor de la clase ClientManagementServiceImpl.
	 * 
	 * @param session La sesión de Hibernate utilizada para la conexión a la base de
	 *                datos.
	 */
	public ClientManagementServiceImpl(Session session) {
		this.clientDao = new ClientDaoImpl(session);
	}

	/**
	 * Inserta un nuevo cliente en la base de datos.
	 * 
	 * @param newClient El nuevo cliente a insertar.
	 * @return true si la inserción fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean insertNewClient(Client newClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (newClient != null && newClient.getClientId() == null) {

			// Inserción del nuevo cliente.
			ok = clientDao.insert(newClient);

			// Log para la inserción de un nuevo cliente
			Utils.log(Utils.INFO, "Inserción exitosa de un nuevo cliente con ID: " + newClient.getClientId());
		}

		return ok;
	}

	/**
	 * Actualiza un cliente existente en la base de datos.
	 * 
	 * @param updatedClient El cliente actualizado.
	 * @return true si la actualización fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean updateClient(Client updatedClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (updatedClient != null && updatedClient.getClientId() != null) {

			// Actualización del Cliente.
			ok = clientDao.update(updatedClient);

			// Log para la actualización de un cliente
			Utils.log(Utils.INFO, "Actualización exitosa del cliente con ID: " + updatedClient.getClientId());
		}

		return ok;
	}

	/**
	 * Elimina un cliente de la base de datos.
	 * 
	 * @param deletedClient El cliente a eliminar.
	 * @return true si la eliminación fue exitosa, false de lo contrario.
	 */
	@Override
	public Boolean deleteClient(Client deletedClient) {

		Boolean ok = Boolean.FALSE;

		// Verificación del Cliente.
		if (deletedClient != null && deletedClient.getClientId() != null) {

			// Eliminación del Cliente.
			ok = clientDao.delete(deletedClient);

			// Log para la eliminación de un cliente
			Utils.log(Utils.INFO, "Eliminación exitosa del cliente con ID: " + deletedClient.getClientId());
		}

		return ok;
	}

	/**
	 * Busca un cliente por su ID en la base de datos.
	 * 
	 * @param clientId El ID del cliente a buscar.
	 * @return El cliente encontrado, o null si no se encuentra.
	 */
	@Override
	public Client searchById(Long clientId) {

		// Resultado.
		Client client = null;

		// Verificación del Cliente.
		if (clientId != null) {

			// Obtención del Cliente por ID.
			client = clientDao.searchById(clientId);

			// Log para la búsqueda de un cliente por ID
			if (client != null) {
				Utils.log(Utils.INFO, "Búsqueda exitosa del cliente con ID: " + clientId);
			} else {
				Utils.log(Utils.INFO, "Cliente no encontrado con ID: " + clientId);
			}
		}

		return client;
	}

	/**
	 * Obtiene una lista de todos los clientes en la base de datos.
	 * 
	 * @return Lista de clientes.
	 */
	@Override
	public List<Client> searchAll() {

		// Obtención de Clientes.
		List<Client> clients = clientDao.searchAll();

		// Log para la búsqueda de todos los clientes
		Utils.log(Utils.INFO, "Búsqueda exitosa de todos los clientes");

		return clients;
	}

	/**
	 * Busca clientes por nombre y apellidos en la base de datos.
	 * 
	 * @param firstName      El nombre del cliente.
	 * @param lastName       El primer apellido del cliente.
	 * @param secondLastName El segundo apellido del cliente.
	 * @return Lista de clientes que coinciden con los parámetros dados.
	 */
	@Override
	public List<Client> searchByNameAndLastName(String firstName, String lastName, String secondLastName) {

		// Obtención de Clientes.
		List<Client> clients = clientDao.searchByNameAndLastName(firstName, lastName, secondLastName);

		// Log para la búsqueda de clientes por nombre y apellidos
		Utils.log(Utils.INFO, "Búsqueda exitosa de clientes por nombre y apellidos");

		return clients;
	}
}

```

**6. Implementar la clase principal (`App.java` o similar) para consumir el servicio de gestión de clientes y probar todas las operaciones.**

La clase principal (`App.java`), u otra designada para este propósito, se encargará de consumir el servicio de gestión de clientes y 
probará todas las operaciones disponibles. Es aquí donde se pueden realizar las pruebas y verificar el funcionamiento completo del sistema.

```java

package com.fpdual.hibernate;

import java.util.Date;

import org.hibernate.Session;

import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase principal.
 * 
 * @author Carlos
 *
 */
public class App {

	/** Sesión de Hibernate para la interacción con la base de datos */
	static Session session = HibernateUtil.getSessionFactory().openSession();

	/** Servicio para la gestión de clientes */
	static ClientManagementServiceImpl service = new ClientManagementServiceImpl(session);

	/**
	 * Objeto para inicializar datos de estudiantes de forma aleatoria.
	 */
	private static InitializeData initializer = new InitializeData(service);

	/**
	 * Método principal que inicia la aplicación.
	 * 
	 * @param args Argumentos de la línea de comandos (no utilizado)
	 */
	public static void main(String[] args) {

		// Inicia el registro de la aplicación
		initLogging();

		// Genera clientes de forma aleatoria al iniciar la aplicación
		initializer.generateRandomClients();

		// Gestiona las opciones del menú principal
		manageClients();

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
	private static void manageClients() {

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
					showClientsView();
					break;
				case 2:
					createClientView();
					break;
				case 3:
					updateClientView();
					break;
				case 4:
					deleteClientView();
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
	 * Método para mostrar todos los clientes de la base de datos.
	 */
	private static void showClientsView() {
		Utils.log(Utils.INFO, "Mostrando todos los clientes...");
		service.searchAll().forEach(System.out::println);
		Utils.log(Utils.INFO, "Listado de clientes mostrado correctamente.");
	}

	/**
	 * Método para crear un nuevo cliente.
	 */
	private static void createClientView() {
		Utils.log(Utils.INFO, "Creando un nuevo cliente...");

		// Se capturan los datos del nuevo cliente
		String firstName = Utils.readString("Introduzca el nombre del Cliente: ");
		String lastName = Utils.readString("Introduzca el apellido del Cliente: ");
		String secondLastName = Utils.readString("Introduzca el segundo apellido del Cliente: ");
		String identityDocument = Utils.readString("Introduzca el DNI del Cliente: ");

		// Se crea una instancia de Client con los datos proporcionados
		Client newClient = new Client();

		// Se agregan sus parametros
		newClient.setFirstName(firstName);
		newClient.setLastName(lastName);
		newClient.setSecondLastName(secondLastName);
		newClient.setIdentityDocument(identityDocument);
		newClient.setUpdatedDate(new Date());
		newClient.setUpdatedUser(Utils.getHostName());

		// Se confirma si se ha insertado el nuevo cliente
		if (Boolean.TRUE.equals(service.insertNewClient(newClient))) {
			Utils.log(Utils.INFO, "Cliente creado exitosamente.");
		} else {
			Utils.log(Utils.ERROR, "El cliente no ha sido creado.");
		}
	}

	/**
	 * Método para actualizar los datos de un cliente existente.
	 */
	private static void updateClientView() {
		Utils.log(Utils.INFO, "Actualizando datos de un cliente...");

		// Se captura el ID del cliente a modificar
		Long clientId = Utils.readLong("Ingrese el ID del cliente a modificar: ");

		// Se verifica si el cliente existe
		Client existingClient = service.searchById(clientId);
		if (existingClient == null) {
			Utils.log(Utils.ERROR, "Cliente no encontrado con el ID proporcionado.");
			return;
		}

		// Se capturan los nuevos datos del cliente
		String newFirstName = Utils.readString("Ingrese el nuevo nombre: ");
		String newLastName = Utils.readString("Ingrese el nuevo apellido: ");
		String newSecondLastName = Utils.readString("Ingrese el nuevo segundo apellido: ");
		String newIdentityDocument = Utils.readString("Ingrese el nuevo documento de identidad: ");

		// Se actualizan los datos del cliente
		existingClient.setFirstName(newFirstName);
		existingClient.setLastName(newLastName);
		existingClient.setSecondLastName(newSecondLastName);
		existingClient.setIdentityDocument(newIdentityDocument);
		existingClient.setUpdatedDate(new Date());

		// Se confirma si se ha actualizado el nuevo cliente
		if (Boolean.TRUE.equals(service.updateClient(existingClient))) {
			Utils.log(Utils.INFO, "Cliente actualizado correctamente.");
		} else {
			Utils.log(Utils.ERROR, "El cliente no ha sido actualizado.");
		}
	}

	/**
	 * Método para eliminar un cliente existente.
	 */
	private static void deleteClientView() {
		Utils.log(Utils.INFO, "Eliminando un cliente...");

		// Se captura el ID del cliente a eliminar
		Long clientId = Utils.readLong("Ingrese el ID del cliente a eliminar: ");

		// Se verifica si el cliente existe
		Client existingClient = service.searchById(clientId);
		if (existingClient == null) {
			Utils.log(Utils.ERROR, "Cliente no encontrado con el ID proporcionado.");
			return;
		}

		// Se confirma si se ha eliminado el nuevo cliente
		if (Boolean.TRUE.equals(service.deleteClient(existingClient))) {
			Utils.log(Utils.INFO, "Cliente eliminado correctamente.");
		} else {
			Utils.log(Utils.ERROR, "El cliente no ha sido eliminado.");
		}
	}

	/**
	 * Muestra el menú principal de la aplicación.
	 */
	private static void showMainMenu() {
		Utils.write("\n---------- Menú Principal ----------");
		Utils.write("1. Mostrar todos los clientes");
		Utils.write("2. Crear un nuevo cliente");
		Utils.write("3. Actualizar datos de un cliente");
		Utils.write("4. Eliminar un cliente");
		Utils.write("0. Salir");
		Utils.write("-----------------------------------");
	}
}

```

## Dependencias del Proyecto

En este proyecto se han utilizado las siguientes dependencias:

- **Log SLF4J y Logback**: Para el registro de eventos y mensajes en la aplicación.
- **Apache Commons Lang**: Ofrece utilidades básicas para manipulación de cadenas, fechas, números, etc.
- **MySQL JDBC Connector**: Proporciona la conexión con una base de datos MySQL.
- **JavaFaker**: Utilizada para generar datos ficticios de forma realista.
- **Hibernate Core**: El núcleo de Hibernate, una herramienta ORM para Java.
- **Java Validation API**: Utilizada para la validación de objetos Java.
- **C3P0**: Proveedor de servicios JDBC para la administración de conexiones.

```xml
<!-- Fragmento del POM.xml con las dependencias -->
<dependencies>
    <!-- ... Otras dependencias ... -->

    <!-- Dependencias del log -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>${logback.version}</version>
    </dependency>

    <!-- Dependencia de MySQL JDBC -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.23</version>
    </dependency>

    <!-- Dependencia de Faker -->
    <dependency>
        <groupId>com.github.javafaker</groupId>
        <artifactId>javafaker</artifactId>
        <version>1.0.2</version>
    </dependency>

    <!-- Dependencia de utilidades de Java -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.12.0</version>
    </dependency>

    <!-- Dependencia para Hibernate Core -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.5.3.Final</version>
    </dependency>

    <!-- Dependencia para la API de validación de Java -->
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
        <version>2.0.1.Final</version>
    </dependency>

    <!-- Dependencia de c3p0 -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-c3p0</artifactId>
        <version>5.5.3.Final</version>
    </dependency>
</dependencies>
```

## Errores encontrados

### Solución al Error "Public Key Retrieval is not allowed"

Al intentar conectar Hibernate a MySQL, puedes encontrarte con el error "Public Key Retrieval is not allowed".
Este problema suele ocurrir cuando el conector JDBC está configurado para realizar una autenticación basada en SHA-256. Se ha modificado la URL de Conexión JDBC
Editar la URL de conexión JDBC en el archivo de configuración de Hibernate para incluir la propiedad `allowPublicKeyRetrieval=true`.

```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/FPDUAL_HIBERNATE_MYSQL_T2 createDatabaseIfNotExist=true&amp;useSSL=false&amp;serverTimezone=UTC&amp;allowPublicKeyRetrieval=true</property>
```




