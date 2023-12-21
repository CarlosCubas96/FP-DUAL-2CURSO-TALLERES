# Taller 1 JDBC - Proyecto jdbc-t1-carlocl

## Descripción del Proyecto
Este proyecto tiene como objetivo establecer una conexión a una base de datos (ya sea SQL u Oracle) mediante JDBC. Se realizará una operación de consulta a la base de datos y se mostrará la información resultante por pantalla.

## Evaluación

### Se evaluará:
- **Generación del Proyecto:**
  - Asegurar que el proyecto se genere correctamente con el nombre adecuado.

- **Uso de Dependencias y Librerías:**
  - Incluir las dependencias necesarias en el archivo `pom.xml` para establecer la conexión con la base de datos.

- **Iniciativa y Creatividad:**
  - Mostrar iniciativa y creatividad al incorporar funcionalidades no solicitadas que mejoren el proyecto.

- **Buenas Prácticas:**
  - Demostrar el uso de buenas prácticas en el desarrollo del proyecto.

## Instrucciones

### Ejecución de Scripts en el Gestor de Base de Datos
1. Ejecutar los scripts proporcionados por el formador/a en un gestor de base de datos como PHPMyAdmin, SQL Developer, MySQL Workbench, HeidiSQL, etc.

### Creación del Proyecto Maven
2. Crear un proyecto Apache Maven básico utilizando el arquetipo quickstart.

### Código Fuente
3. La clase App.java en su método main() debe contener una llamada a método privado que establezca la conexión con BBDD.
4. Establecida la conexión se debe realizar una operación de consulta contra la base de datos para, posteriormente, mostrar la información retornada por pantalla.

## Ejecución

 **1. Ejecución de Scripts en el Gestor de Base de Datos**
    
> [!NOTE]
> Se utiliza una BD en la carpeta **SQL**

```sql

-- Autor       : Carlos Cubas Lorca 
-- Descripción : Taller 1 BD MySQL - FP DUAL
------------------------------------------------------

-- Eliminación de la base de datos si existe.
DROP DATABASE IF EXISTS FPDUAL_MYSQL_T4;

-- Creación de la base de datos.
CREATE DATABASE FPDUAL_MYSQL_T4 DEFAULT CHARACTER SET UTF8MB4;

-- Uso de la base de datos.
USE FPDUAL_MYSQL_T4;

-- Creación de tabla para alumnos.
CREATE TABLE FPD_STUDENT (
    ID INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID del Alumno',
    NAME VARCHAR(100) COMMENT 'Nombre del Alumno',
    LAST_NAME VARCHAR(100) COMMENT 'Apellido del Alumno',
    ADDRESS VARCHAR(200) COMMENT 'Dirección del Alumno',
    PHONE VARCHAR(20) COMMENT 'Número de teléfono del Alumno',
    EMAIL VARCHAR(100) COMMENT 'Correo electrónico del Alumno'
);

-- Modificación del valor del contador AUTO_INCREMENT.
ALTER TABLE FPD_STUDENT AUTO_INCREMENT = 1;

```

**2. Creación del Proyecto Maven**

Creamos un proyecto maven con estas caracteristicas:

```xml
  <!-- DETALLES DEL PROYECTO -->
	<groupId>com.fpdual</groupId>
	<artifactId>jdbc-t1-carloscl</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>jdbc-t1-carloscl</name>
	<description>PROYECTO - JDBC - TALLER 1</description>
```


**3. Código Fuente**

La clase ConnectionDB es responsable de administrar la conexión a una base de datos mediante JDBC y un DataSource. Sus funciones principales incluyen la inicialización del DataSource, la gestión de la conexión y transacciones, la ejecución de consultas SQL y la preparación de declaraciones. Utiliza la librería **org.apache.commons.dbcp2** para la administración eficiente de conexiones.
En resumen, la clase facilita el acceso y manipulación de la base de datos a través de métodos encapsulados y reutilizables.

```java

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
```

Primero se ha creado una clase Conexion.db donde obtiene los metodos.
Uno de los metodos requeridos es un método privado que llamado desde app hacia ConnectionDB:

> [!NOTE]
> Donde pregunta si la conexion es nula y manda un mensaje al log escribiendo por consola el nombre del **pool**.

```xml
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
```




## Depedencias del proyecto 

Dependencias utilizadas para el proyecto

- *Dependencias del log SLF4J Y LOGBACK*
- *Depedencia de Apache Commons para utilidades básicas*
- *Dependecia de MYSQL  JDBC CONNECTOR y realizar la conexión con una base de datos envebida*
- *Dependencia de Faker para introducir en la Aplicación datos ficticios*

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

		<!-- Dependencia de Apache Commons DBCP2 -->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-dbcp2</artifactId>
			<version>2.9.0</version>
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

	</dependencies>
```



