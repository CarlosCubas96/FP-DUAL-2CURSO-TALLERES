# Taller 2 Hibernate - Proyecto nttdatacenters-hibernate-t1-iniciales

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
-- Descripción : Taller 2 BD MySQL - FP DUAL
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
    IDENTITY_DOCUMENT VARCHAR(9) UNIQUE NOT NULL COMMENT 'Documento de identidad'
);

```

**2. Creación del Proyecto Maven**

Creamos un proyecto maven con estas características:

```xml

  <!-- DETALLES DEL PROYECTO -->
	<groupId>com.nttdatacenters</groupId>
	<artifactId>nttdatacenters-hibernate-t1-iniciales</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>nttdatacenters-hibernate-t1-iniciales</name>
	<description>PROYECTO - Hibernate - Taller 2</description>

```

**3. Configuración de Hibernate**

Configuramos Hibernate utilizando anotaciones JPA en la entidad Cliente y, si es necesario, mediante el archivo hibernate.cfg.xml:


**4. Implementación del DAO**

**5. Crear un servicio que consuma el DAO de la entidad Cliente y utilice todos los métodos implementados.**

**6. Implementar la clase principal (`App.java` o similar) para consumir el servicio de gestión de clientes y probar todas las operaciones.**





## Depedencias del proyecto 

Dependencias utilizadas para el proyecto

- *Dependencias del log SLF4J Y LOGBACK*
- *Depedencia de Apache Commons para utilidades básicas*
- *Dependecia de MYSQL  JDBC CONNECTOR y realizar la conexión con una base de datos envebida*
- *Dependencia de Faker para introducir en la Aplicación datos ficticios*



## Errores encontrado

### Solución al Error "Public Key Retrieval is not allowed"

Al intentar conectar Hibernate a MySQL, puedes encontrarte con el error "Public Key Retrieval is not allowed".
Este problema suele ocurrir cuando el conector JDBC está configurado para realizar una autenticación basada en SHA-256. Se ha modificado la URL de Conexión JDBC
Editar la URL de conexión JDBC en el archivo de configuración de Hibernate para incluir la propiedad `allowPublicKeyRetrieval=true`.

```xml
<property name="connection.url">jdbc:mysql://localhost:3306/your_database?allowPublicKeyRetrieval=true</property>
```




