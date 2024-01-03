# Taller 2 Hibernate - Proyecto hibernate-t2-carloscl

## Descripción del Proyecto
Este proyecto web es una adaptación del ejercicio del taller 1, incorporando una nueva entidad llamada Contrato. La relación entre Cliente y Contrato es de 1:N, lo que significa que un cliente puede tener de cero a varios contratos, y un contrato está asociado a un único cliente. Los atributos de la entidad Contrato incluyen un identificador numérico, fecha de vigencia, fecha de caducidad, precio mensual y la relación con Cliente. Además, se implementa el patrón DAO para gestionar las operaciones CRUD y un método de búsqueda por identificador del Cliente en la entidad Contrato.

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
    
- **Servicio de Gestión de Contratos:**
  - Crear un servicio que consuma el DAO de la entidad Contrato y utilice todos los métodos implementados.

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

### Implementación de JSP y Servlets
4. Implementa páginas JSP y Servlets para interactuar con los servicios de gestión de Clientes y Contratos. Puedes utilizar estas páginas para probar todas las operaciones.

### Ejecución del Proyecto
5. Despliega tu proyecto en un contenedor web como Apache Tomcat.

## Ejecución

**1. Ejecución de Scripts en el Gestor de Base de Datos**
    
> [!NOTE]
> El Script se encuentra en la carpeta **SQL**

Se añade la tabla de contratos

```sql
-- Autor       : Carlos Cubas Lorca
-- Descripción : Taller 2 HIBERNATE BD MySQL - FP DUAL
------------------------------------------------------

-- Eliminación de la base de datos si existe.
DROP DATABASE IF EXISTS FPDUAL_HIBERNATE_MYSQL_T2;

-- Creación de la base de datos.
CREATE DATABASE FPDUAL_HIBERNATE_MYSQL_T2 DEFAULT CHARACTER SET UTF8MB4;

-- Uso de la base de datos.
USE FPDUAL_HIBERNATE_MYSQL_T2;

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
	<artifactId>hibernate-t2-carloscl</artifactId>
	<packaging>war</packaging>
	<version>0.0.1-SNAPSHOT</version>
	<name>hibernate-t2-carloscl Maven Webapp</name>
	<description>PROYECTO - HIBERNATE - TALLER 2</description>

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
		<mapping class="com.fpdual.hibernate.persistence.Contract" />

  ```

**4. Implementación de JSP y Servlets**

En este proyecto, se han implementado varios Servlets para gestionar las operaciones relacionadas con las entidades Cliente y Contrato. A continuación, se proporciona una breve descripción de cada Servlet:

### Servlets

## EditClientServlet
- **Clase:** `com.fpdual.hibernate.servlets.EditClientServlet`
- **URL:** `/EditClientServlet`
- **Descripción:** Este servlet maneja las solicitudes para editar la información de un cliente. Al acceder a la URL `/EditClientServlet`, el usuario puede actualizar los datos de un cliente existente.

## DeleteClientServlet
- **Clase:** `com.fpdual.hibernate.servlets.DeleteClientServlet`
- **URL:** `/DeleteClientServlet`
- **Descripción:** Este servlet gestiona las solicitudes para eliminar un cliente de la base de datos. Al acceder a la URL `/DeleteClientServlet`, se elimina el cliente seleccionado.

## ListClientServlet
- **Clase:** `com.fpdual.hibernate.servlets.ListClientServlet`
- **URL:** `/ListClientServlet`
- **Descripción:** Este servlet maneja las solicitudes para obtener la lista de todos los clientes almacenados en la base de datos. Al acceder a la URL `/ListClientServlet`, se muestra una lista de clientes.

## CreateClientServlet
- **Clase:** `com.fpdual.hibernate.servlets.CreateClientServlet`
- **URL:** `/CreateClientServlet`
- **Descripción:** Este servlet gestiona las solicitudes para crear un nuevo cliente en la base de datos. Al acceder a la URL `/CreateClientServlet`, se presenta un formulario para ingresar los detalles del nuevo cliente.

## ViewClientServlet
- **Clase:** `com.fpdual.hibernate.servlets.ViewClientServlet`
- **URL:** `/ViewClientServlet`
- **Descripción:** Este servlet maneja las solicitudes para ver los detalles de un cliente específico. Al acceder a la URL `/ViewClientServlet`, se muestra la información detallada de un cliente.

## CreateContractServlet
- **Clase:** `com.fpdual.hibernate.servlets.CreateContractServlet`
- **URL:** `/CreateContractServlet`
- **Descripción:** Este servlet gestiona las solicitudes para crear un nuevo contrato asociado a un cliente. Al acceder a la URL `/CreateContractServlet`, se presenta un formulario para ingresar los detalles del nuevo contrato.

## EditContractServlet
- **Clase:** `com.fpdual.hibernate.servlets.EditContractServlet`
- **URL:** `/EditContractServlet`
- **Descripción:** Este servlet maneja las solicitudes para editar la información de un contrato existente. Al acceder a la URL `/EditContractServlet`, el usuario puede actualizar los datos de un contrato.

## DeleteContractServlet
- **Clase:** `com.fpdual.hibernate.servlets.DeleteContractServlet`
- **URL:** `/DeleteContractServlet`
- **Descripción:** Este servlet gestiona las solicitudes para eliminar un contrato asociado a un cliente. Al acceder a la URL `/DeleteContractServlet`, se elimina el contrato seleccionado.

## SearchClientServlet
- **Clase:** `com.fpdual.hibernate.servlets.SearchClientServlet`
- **URL:** `/SearchClientServlet`
- **Descripción:** Este servlet permite buscar clientes por nombre y apellidos. Al acceder a la URL `/SearchClientServlet`, se presenta un formulario de búsqueda y se muestran los resultados correspondientes.


### Páginas JSP Implementadas

## createClient.jsp
- **Ubicación:** `/webapp/WEB-INF/views/createClient.jsp`
- **Descripción:** Página que contiene un formulario para crear un nuevo cliente. Se accede mediante la acción del Servlet `CreateClientServlet`.

## createContract.jsp
- **Ubicación:** `/webapp/WEB-INF/views/createContract.jsp`
- **Descripción:** Página que presenta un formulario para crear un nuevo contrato asociado a un cliente. Se accede mediante la acción del Servlet `CreateContractServlet`.

## listClients.jsp
- **Ubicación:** `/webapp/WEB-INF/views/listClients.jsp`
- **Descripción:** Página que muestra una lista de todos los clientes almacenados en la base de datos. Se accede mediante la acción del Servlet `ListClientServlet`.

## updateClient.jsp
- **Ubicación:** `/webapp/WEB-INF/views/updateClient.jsp`
- **Descripción:** Página que permite actualizar la información de un cliente existente. Se accede mediante la acción del Servlet `EditClientServlet`.

### updateContract.jsp
- **Ubicación:** `/webapp/WEB-INF/views/updateContract.jsp`
- **Descripción:** Página que permite actualizar la información de un contrato existente. Se accede mediante la acción del Servlet `EditContractServlet`.

### viewClient.jsp
- **Ubicación:** `/webapp/WEB-INF/views/viewClient.jsp`
- **Descripción:** Página que muestra los detalles de un cliente específico. Se accede mediante la acción del Servlet `ViewClientServlet`.
  
### error.jsp
- **Ubicación:** `/webapp/WEB-INF/view/error/error.jsp`
- **Descripción:** Página de manejo de errores que se utiliza para redirigir en caso de fallos en las operaciones.


### 5. Ejecución del Proyecto

Asegúrate de seguir estos pasos para desplegar el proyecto en un contenedor web como Apache Tomcat:

1. **Configuración de Apache Tomcat:**
   - Asegúrarse de tener Apache Tomcat instalado y configurado correctamente en el entorno de desarrollo. Descargar la última versión de Apache Tomcat desde [el sitio oficial de Apache Tomcat](https://tomcat.apache.org/).

2. **Configuración del Proyecto:**
   - Verificar las dependencias y configuraciones del proyecto, Hibernate y las dependencias en el archivo `pom.xml`.

3. **Compilación y Empaquetado:**
   - Ejecutar el siguiente comando Maven para compilar y empaquetar el proyecto:
     ```
     mvn clean package
     ```
     Este comando compilará el proyecto y generará un archivo WAR en el directorio `target` del proyecto.

4. **Despliegue en Apache Tomcat:**
   - Copiar el archivo WAR desde el directorio `target` al directorio `webapps` de la instalación de Apache Tomcat.

5. **Inicio de Apache Tomcat:**
   - Iniciar Apache Tomcat.

6. **Acceso a la Aplicación:**
   - Una vez iniciado Apache Tomcat, abre tu navegador web y accede a la aplicación utilizando la URL correspondiente. Por ejemplo, `http://localhost:8080/mi-proyecto`.

7. **Prueba de Funcionalidades:**
   - Interactúa con las páginas JSP y Servlets implementadas para gestionar clientes y contratos. Asegúrarse de probar todas las operaciones para garantizar el correcto funcionamiento de la aplicación.


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

Eerrores encontrados durante la implementación de Hibernate y JSP en la aplicación, así como las soluciones correspondientes.

## Error 1: Configuración Incorrecta de Hibernate
Durante la configuración de Hibernate, se encontraron problemas. Se corrigió la configuración ajustando las propiedades en el archivo `hibernate.cfg.xml`. Asegurándose de que...

## Error 2: Problema con la Etiqueta `<c:forEach>` en JSP
Al utilizar la etiqueta `<c:forEach>` en las páginas JSP, se encontró un error. La solución consistió en ajustar la sintaxis o importar las bibliotecas adecuadas para la etiqueta `<c:forEach>`.

## Error 3: Mensajes de Error en Servlets
Los servlets presentaron mensajes de error específicos. Se identificaron y corrigieron los problemas en los servlets, incluyendo ajustes en la lógica de negocio, manejo de excepciones y redirecciones adecuadas.

## Error 4: Interacción entre JSP y Servlets
Se encontraron problemas al pasar datos entre las páginas JSP y los servlets. Se ajustaron los métodos de envío y recepción entre JSP y servlets, asegurándose de utilizar los atributos correctos y manejar adecuadamente los parámetros.

 
