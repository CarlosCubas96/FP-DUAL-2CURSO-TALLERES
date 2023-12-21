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
