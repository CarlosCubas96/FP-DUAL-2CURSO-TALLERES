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
