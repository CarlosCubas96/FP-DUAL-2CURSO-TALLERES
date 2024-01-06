# Taller 3 Hibernate - Proyecto hibernate-t3-carloscl

## Descripción del Proyecto
Este proyecto web es una adaptación del taller 2 de Hibernate, incorporando dos consultas definidas e implementadas con JPA Criteria. Las entidades Cliente y Contrato han sido actualizadas en sus interfaces e implementaciones de DAO para incorporar estas consultas utilizando JPA Criteria.

## Evaluación

### Se evaluará:

- **Generación del Proyecto:**
  - Asegurar que el proyecto se genere correctamente con el nombre adecuado.

- **Adaptación de Interfaces e Implementaciones:**
  - Adecuada adaptación de las interfaces e implementaciones de DAO para incorporar las consultas con JPA Criteria en las entidades Cliente y Contrato.

- **Consultas JPA Criteria:**
  - Implementación correcta de las consultas definidas con JPA Criteria en los DAO de Cliente y Contrato.
  
- **Actualización de Servicios:**
  - Actualización de las interfaces e implementaciones de los servicios asociados para consumir los nuevos métodos en los DAO.

- **Consumo de Nuevos Métodos:**
  - Correcto consumo de los nuevos métodos en los servicios desde otras partes de la aplicación.

- **Iniciativa y Creatividad:**
  - Mostrar iniciativa y creatividad al incorporar funcionalidades no solicitadas que mejoren el proyecto.

- **Buenas Prácticas:**
  - Demostrar el uso de buenas prácticas en el desarrollo del proyecto.

## Instrucciones

### Ejecución de Scripts en el Gestor de Base de Datos
1. Ejecutar los scripts proporcionados por el formador/a en un gestor de base de datos como PHPMyAdmin, SQL Developer, MySQL Workbench, HeidiSQL, etc.

### Creación del Proyecto Maven
2. Crear un proyecto Apache Maven utilizando el nombre adecuado para el proyecto.

### Actualización de Interfaces e Implementaciones de DAO y Servicios
3. Actualizar la interfaces, implementaciones y servicios para incorporar los nuevos métodos definidos con JPA Criteria para las entidades.

### Actualización de JSP y Servlets
4. Actualizar el jsp para la comprobación de los metodos.

## Ejecución

**1. Ejecución de Scripts en el Gestor de Base de Datos**
    
> [!NOTE]
> El Script se encuentra en la carpeta **SQL**

Se añade la tabla de contratos

```sql
-- Autor       : Carlos Cubas Lorca
-- Descripción : Taller 3 HIBERNATE BD MySQL - FP DUAL
------------------------------------------------------

-- Eliminación de la base de datos si existe.
DROP DATABASE IF EXISTS FPDUAL_HIBERNATE_MYSQL_T3;

-- Creación de la base de datos.
CREATE DATABASE FPDUAL_HIBERNATE_MYSQL_T3 DEFAULT CHARACTER SET UTF8MB4;

-- Uso de la base de datos.
USE FPDUAL_HIBERNATE_MYSQL_T3;

-- Creación de tabla para Clientes.
CREATE TABLE FPDUAL_HEX_CLIENT (
    CLIENT_ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID del Cliente',
    FIRST_NAME VARCHAR(100) COMMENT 'Nombre',
    LAST_NAME VARCHAR(100) COMMENT 'Primer apellido',
    SECOND_LAST_NAME VARCHAR(100) COMMENT 'Segundo apellido',
    IDENTITY_DOCUMENT VARCHAR(9) UNIQUE NOT NULL COMMENT 'Documento de identidad',
    AUDIT_UPDATED_USER VARCHAR(255) NOT NULL COMMENT 'Usuario que actualiza',
    AUDIT_UPDATED_DATE DATETIME NOT NULL COMMENT 'Día actualizado'
);

-- Creación de tabla para Contratos.
CREATE TABLE FPDUAL_HEX_CONTRACT (
    CONTRACT_ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID del Contrato',
    FK_CLIENT_ID BIGINT COMMENT 'ID del Cliente asociado',
    CONSTRAINT FK_CLIENT_ID FOREIGN KEY (FK_CLIENT_ID) REFERENCES FPDUAL_HEX_CLIENT (CLIENT_ID),
    VIGENCY_DATE DATE COMMENT 'Fecha de vigencia',
    EXPIRATION_DATE DATE COMMENT 'Fecha de caducidad',
    MONTHLY_PRICE DECIMAL(10, 2) COMMENT 'Precio mensual',
    AUDIT_UPDATED_USER VARCHAR(255) NOT NULL COMMENT 'Usuario que actualiza',
    AUDIT_UPDATED_DATE DATETIME NOT NULL COMMENT 'Día actualizado'
);

```

**2. Creación del Proyecto Maven**

Creamos un proyecto maven con estas características:

```xml

 	<!-- DETALLES DEL PROYECTO -->
	<groupId>com.fpdual</groupId>
	<artifactId>hibernate-t3-carloscl</artifactId>
	<packaging>war</packaging>
	<version>0.0.1-SNAPSHOT</version>
	<name>hibernate-t3-carloscl</name>
	<description>PROYECTO - HIBERNATE - TALLER 3</description>

```

**3. Actualización de Interfaces e Implementaciones de DAO y Servicios**

Se ha realizado una adecuada adaptación en la interfaz e implementación del DAO de la entidad Cliente para incorporar una nueva consulta utilizando JPA Criteria. A continuación, se presenta el código correspondiente:

## Cliente  
Se ha realizado una adecuada adaptación en la interfaz e implementación del DAO de la entidad Cliente para incorporar una nueva consulta utilizando JPA Criteria. A continuación, se presenta el código correspondiente:


```java

/**
	 * Busca clientes por nombre.
	 * 
	 * @param firstName El nombre del cliente.
	 * @return Lista de clientes que coinciden con el nombre dado.
	 */
	@Override
	public List<Client> searchByName(String firstName) {

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Creación del CriteriaBuilder
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		// Creación del CriteriaQuery para Client
		CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
		Root<Client> clientRoot = criteriaQuery.from(Client.class);

		// Agregar condición para hacer coincidir el primer nombre
		Predicate condition = criteriaBuilder.equal(clientRoot.get("firstName"), firstName);
		criteriaQuery.where(condition);

		// Realizar la consulta
		List<Client> clients = session.createQuery(criteriaQuery).getResultList();

		// Registro
		Utils.log(Utils.INFO, "Realizando consulta para buscar clientes por nombre.");
		Utils.log(Utils.INFO, "Número de clientes encontrados: " + clients.size());

		// Confirmar la transacción
		session.getTransaction().commit();

		return clients;
	}
```

### Contrato
Se ha implementado una consulta adicional en la interfaz e implementación del DAO de la entidad Contrato, utilizando JPA Criteria para buscar contratos por el DNI del cliente asociado. A continuación, se presenta el código correspondiente:

  ```java
  /**
	 * Busca contratos por el DNI del cliente asociado utilizando JPA Criteria.
	 *
	 * @param identityDocument El DNI del cliente.
	 * @return Una lista de contratos asociados con el DNI de cliente especificado.
	 */
	@Override
	public List<Contract> searchContractByClientIdentityDocument(String identityDocument) {

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		// Creación del CriteriaBuilder para Contract
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		// Creación del CriteriaQuery
		CriteriaQuery<Contract> criteriaQuery = criteriaBuilder.createQuery(Contract.class);
		Root<Contract> contractRoot = criteriaQuery.from(Contract.class);

		// Unión con la entidad Client
		Join<Contract, Client> clientJoin = contractRoot.join("client");

		// Agregar condición para hacer coincidir el DNI del cliente
		Predicate condition = criteriaBuilder.equal(clientJoin.get("identityDocument"), identityDocument);
		criteriaQuery.where(condition);

		// Realizar la consulta
		List<Contract> contracts = session.createQuery(criteriaQuery).getResultList();

		// Registros
		Utils.log(Utils.INFO, "Realizando consulta para buscar contratos por DNI del cliente.");
		Utils.log(Utils.INFO, "Número de contratos encontrados: " + contracts.size());

		// Confirmar la transacción
		session.getTransaction().commit();

		return contracts;
	}

  ```


**4. Actualización de JSP y Servlets**

Se ha llevado a cabo una actualización en las páginas JSP y Servlets para reflejar los cambios realizados en las interfaces e implementaciones de los DAO y servicios. Estas modificaciones permitirán una integración fluida con las nuevas consultas JPA Criteria implementadas en las entidades Cliente y Contrato.

### Cambios en Servlets
Se han actualizado los Servlets para incorporar llamadas a los nuevos métodos de los servicios que consumen las consultas JPA Criteria. A continuación, se presenta de cómo se realiza esta integración en los Servlet:

- **SearchClientServlet**

```java
package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fpdual.hibernate.Constants;
import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.persistence.Client;
import com.fpdual.hibernate.services.ClientManagementServiceImpl;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

/**
 * 
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para buscar clientes. Este servlet maneja las solicitudes para buscar
 * clientes por nombre. Utiliza el método doGet para procesar las solicitudes
 * GET.
 * 
 * @author Carlos
 */
public class SearchClientServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public SearchClientServlet() {
		super();
	}

	/**
	 * Maneja las solicitudes GET para buscar clientes por nombre.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP que se enviará.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida durante el
	 *                          procesamiento de la solicitud.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Obtener el parámetro de búsqueda del formulario
			String searchKeyword = request.getParameter("searchKeyword");

			// Realizar la búsqueda en el servicio de gestión de clientes
			ClientManagementServiceImpl clientService = new ClientManagementServiceImpl(session);
			List<Client> searchResults = clientService.searchByName(searchKeyword);

			// Guardar los resultados en el request
			request.setAttribute("clients", searchResults);

			// Log para registrar la búsqueda realizada
			Utils.log(Utils.INFO, "Búsqueda realizada por nombre: " + searchKeyword);

			// Redirigir a la página de lista de clientes con los resultados de la búsqueda
			request.getRequestDispatcher("JSP/listClients.jsp").forward(request, response);

		} catch (Exception e) {
			// Log para registrar errores en la búsqueda
			Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en SearchClientServlet: " + e);
			response.sendRedirect(Constants.JSP_ERROR_JSP);
		}
	}
}

```

- **SearchContractServlet**

```java

package com.fpdual.hibernate.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.fpdual.hibernate.Constants;
import com.fpdual.hibernate.HibernateUtil;
import com.fpdual.hibernate.Utils;
import com.fpdual.hibernate.services.ContractManagementServiceImpl;
import com.fpdual.hibernate.persistence.Contract;

import java.io.IOException;
import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Servlet para buscar contratos por DNI del cliente. Maneja solicitudes GET.
 * 
 * @author Carlos
 */
public class SearchContractServlet extends HttpServlet {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto.
	 */
	public SearchContractServlet() {
		super();
	}

	/**
	 * Maneja solicitudes GET para buscar contratos por DNI del cliente.
	 * 
	 * @param request  La solicitud HTTP recibida.
	 * @param response La respuesta HTTP que se enviará.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 * @throws IOException      Si ocurre un error de entrada/salida durante el
	 *                          procesamiento de la solicitud.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtener la sesión
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			// Crear una instancia del servicio de contratos
			ContractManagementServiceImpl contractService = new ContractManagementServiceImpl(session);

			// Obtener el parámetro DNI de la solicitud
			String searchDNI = request.getParameter("searchDNI");

			// Buscar contratos por DNI
			List<Contract> contracts = contractService.searchContractByClientIdentityDocument(searchDNI);

			// Establecer los contratos como atributo en la solicitud
			request.setAttribute("contracts", contracts);

			// Redirigir a la página JSP de contratos
			request.getRequestDispatcher("JSP/listContracts.jsp").forward(request, response);

		} catch (Exception e) {
			Utils.log(Utils.ERROR, "Error al procesar la solicitud GET en SearchContractServlet: " + e);
			response.sendRedirect(Constants.JSP_ERROR_JSP);
		}
	}
}

```


### Cambios en JSP
Las páginas JSP también han sido actualizadas para mostrar los resultados de las nuevas consultas JPA Criteria. A continuación, se muestran  los fragmentos de código que presenta las listas obtenidas:

Se ha añadido el formulario de busqueda para lisClients para obtener los clientes por basados por nombres retornando la lista actualizada

```jsp

	<!-- Formulario de búsqueda -->
		<form action="SearchClientServlet" method="get" class="mb-3">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search by name"
					name="searchKeyword">
				<div class="input-group-append">
					<button class="btn btn-outline-secondary" type="submit">Search</button>
				</div>
			</div>
		</form>

```

Se ha añadido el formulario de busqueda para lisContracts para obtener los controtatos por DNI del cliente obtenidos

```jsp

<!-- Formulario de búsqueda por DNI -->
		<form action="SearchContractServlet" method="get" class="mb-3">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search by DNI"
					name="searchDNI">
				<div class="input-group-append">
					<button class="btn btn-outline-secondary" type="submit">Search</button>
				</div>
			</div>
		</form>

```

### Dependencias del Proyecto

En este proyecto se han utilizado las siguientes dependencias:

- **Log SLF4J y Logback**: Para el registro de eventos y mensajes en la aplicación.
- **Apache Commons Lang**: Ofrece utilidades básicas para manipulación de cadenas, fechas, números, etc.
- **MySQL JDBC Connector**: Proporciona la conexión con una base de datos MySQL.
- **JavaFaker**: Utilizada para generar datos ficticios de forma realista.
- **Hibernate Core**: El núcleo de Hibernate, una herramienta ORM para Java.
- **Java Validation API**: Utilizada para la validación de objetos Java.
- **C3P0**: Proveedor de servicios JDBC para la administración de conexiones.
- **JSP API**: Proporciona las clases y métodos necesarios para trabajar con JavaServer Pages (JSP).
- **Servlet API**: Proporciona las clases y métodos necesarios para trabajar con Servlets.

> [!NOTE]
> Se han añadido manualmente las librerías de JSTL a la carpeta `lib` debido a problemas con Maven WebApp. Puedes encontrar las librerías en la carpeta `lib` de este proyecto.

```xml
	<!-- DEPENDENCIAS -->
	<dependencies>

		<!-- Dependencias del Log -->
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

		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-core</artifactId>
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

		<!-- Dependencia para Hibernate Core, el núcleo de Hibernate herramienta
		ORM para Java -->
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

		<!-- Dependencia de JSP -->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>4.0.1</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>jakarta.servlet</groupId>
			<artifactId>jakarta.servlet-api</artifactId>
			<version>6.0.0</version>
			<scope>provided</scope>
		</dependency>

	</dependencies>
```

### Errores y Soluciones con Hibernate y JSP

Errores encontrados durante la implementación de Hibernate y JSP en la aplicación, así como las soluciones correspondientes.

## Error 1: Interacción entre JSP y Servlets
Se encontraron problemas al pasar datos entre las páginas JSP y los servlets. Se ajustaron los métodos de envío y recepción entre JSP y servlets, asegurándose de utilizar los atributos correctos y manejar adecuadamente los parámetros.

 
