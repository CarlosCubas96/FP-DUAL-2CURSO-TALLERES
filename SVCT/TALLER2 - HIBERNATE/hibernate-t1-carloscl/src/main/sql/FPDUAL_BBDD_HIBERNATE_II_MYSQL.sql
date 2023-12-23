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
    CLIENT_ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID del CLiente',
    FIRST_NAME VARCHAR(100) COMMENT 'Nombre',
    LAST_NAME VARCHAR(100) COMMENT 'Primer apellido',
    SECOND_LAST_NAME VARCHAR(100) COMMENT 'Segundo apellido',
    IDENTITY_DOCUMENT VARCHAR(9) UNIQUE NOT NULL COMMENT 'Documento de identidad',
	AUDIT_UPDATED_USER VARCHAR(255) NOT NULL COMMENT 'Usuario que actualiza',
    AUDIT_UPDATED_DATE DATETIME NOT NULL COMMENT 'Día actualizado'
);
