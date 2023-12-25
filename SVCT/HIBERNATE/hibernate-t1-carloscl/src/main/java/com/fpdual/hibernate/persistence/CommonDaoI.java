package com.fpdual.hibernate.persistence;

import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Interfaz genérica para operaciones CRUD en la base de datos.
 * Define métodos comunes para insertar, actualizar, eliminar y buscar por ID o todos los registros.
 * 
 * @param <T> Tipo de entidad para la operación CRUD.
 * @author Carlos
 *
 */
public interface CommonDaoI<T> {

	/**
	 * Inserta un nuevo registro en la base de datos.
	 * 
	 * @param paramT La entidad a ser insertada.
	 * @return `true` si la operación fue exitosa, `false` en caso contrario.
	 */
	public Boolean insert(T paramT);

	/**
	 * Actualiza un registro existente en la base de datos.
	 * 
	 * @param paramT La entidad con los datos actualizados.
	 * @return `true` si la operación fue exitosa, `false` en caso contrario.
	 */
	public Boolean update(T paramT);

	/**
	 * Elimina un registro de la base de datos.
	 * 
	 * @param paramT La entidad a ser eliminada.
	 * @return `true` si la operación fue exitosa, `false` en caso contrario.
	 */
	public Boolean delete(T paramT);

	/**
	 * Busca un registro por su ID en la base de datos.
	 * 
	 * @param id El ID del registro a buscar.
	 * @return La entidad encontrada o `null` si no se encuentra.
	 */
	public T searchById(Long id);

	/**
	 * Busca todos los registros de la entidad en la base de datos.
	 * 
	 * @return Lista de todas las entidades encontradas.
	 */
	public List<T> searchAll();

}
