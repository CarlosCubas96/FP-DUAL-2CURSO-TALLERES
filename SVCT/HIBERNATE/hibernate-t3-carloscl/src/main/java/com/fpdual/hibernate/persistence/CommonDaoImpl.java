package com.fpdual.hibernate.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;

import com.fpdual.hibernate.Utils;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Implementación genérica de DAO para operaciones comunes de CRUD en la base de
 * datos.
 * 
 * @param <T> Tipo de entidad para la operación CRUD.
 * @author Carlos
 */
public abstract class CommonDaoImpl<T extends AbstractEntity> implements CommonDaoI<T> {

	/** Tipo de clase */
	private Class<T> entityClass;
	/** Sesión de conexión a BD */
	private Session session;

	/**
	 * Constructor de la clase CommonDaoImpl.
	 * 
	 * @param session Sesión de Hibernate para la conexión a la base de datos.
	 */
	@SuppressWarnings("unchecked")
	protected CommonDaoImpl(Session session) {
		setEntityClass(
				(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		this.session = session;
	}

	/**
	 * Inserta una entidad en la base de datos.
	 * 
	 * @param paramT La entidad a insertar.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	@Override
	public Boolean insert(T paramT) {
		Boolean ok = Boolean.FALSE;

		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		try {
			session.save(paramT);
			session.flush();
			session.getTransaction().commit();
			ok = Boolean.TRUE;

			// Log para la inserción exitosa
			Utils.log(Utils.INFO, "Inserción exitosa de la entidad con ID: " + paramT.getId());
		} catch (Exception e) {
			session.getTransaction().rollback();

			// Log para errores en la inserción
			Utils.log(Utils.ERROR, "Error al insertar la entidad: " + e.getMessage());
		}

		return ok;
	}

	/**
	 * Actualiza una entidad en la base de datos.
	 * 
	 * @param paramT La entidad a actualizar.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	@Override
	public Boolean update(T paramT) {
		Boolean ok = Boolean.FALSE;

		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		try {
			session.saveOrUpdate(paramT);
			session.getTransaction().commit();
			ok = Boolean.TRUE;

			// Log para la actualización exitosa
			Utils.log(Utils.INFO, "Actualización exitosa de la entidad con ID: " + paramT.getId());
		} catch (Exception e) {
			session.getTransaction().rollback();

			// Log para errores en la actualización
			Utils.log(Utils.ERROR, "Error al actualizar la entidad: " + e.getMessage());
		}

		return ok;
	}

	/**
	 * Elimina una entidad de la base de datos.
	 * 
	 * @param paramT La entidad a eliminar.
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	@Override
	public Boolean delete(T paramT) {
		Boolean ok = Boolean.FALSE;

		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		try {
			session.delete(paramT);
			session.getTransaction().commit();
			ok = Boolean.TRUE;

			// Log para la eliminación exitosa
			Utils.log(Utils.INFO, "Eliminación exitosa de la entidad con ID: " + paramT.getId());
		} catch (Exception e) {
			session.getTransaction().rollback();

			// Log para errores en la eliminación
			Utils.log(Utils.ERROR, "Error al eliminar la entidad: " + e.getMessage());
		}

		return ok;
	}

	/**
	 * Busca una entidad por su ID en la base de datos.
	 * 
	 * @param id El ID de la entidad a buscar.
	 * @return La entidad encontrada o null si no se encuentra.
	 */
	@Override
	public T searchById(Long id) {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		T result = null;

		try {
			result = session.get(this.entityClass, id);

			// Log para la búsqueda por ID
			Utils.log(Utils.INFO, "Búsqueda exitosa de la entidad con ID: " + id);
		} catch (Exception e) {
			// Log para errores en la búsqueda por ID
			Utils.log(Utils.ERROR, "Error al buscar la entidad por ID: " + e.getMessage());
		} finally {
			session.getTransaction().commit();
		}

		return result;
	}

	/**
	 * Busca todas las entidades de tipo T en la base de datos.
	 * 
	 * @return Lista de todas las entidades encontradas.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> searchAll() {
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		List<T> resultList = null;

		try {
			resultList = session.createQuery("FROM " + this.entityClass.getName()).list();

			// Log para la búsqueda de todos los registros
			Utils.log(Utils.INFO, "Búsqueda exitosa de todos los registros de la entidad: " + entityClass.getName());
		} catch (Exception e) {
			// Log para errores en la búsqueda de todos los registros
			Utils.log(Utils.ERROR, "Error al buscar todos los registros de la entidad: " + e.getMessage());
		} finally {
			session.getTransaction().commit();
		}

		return resultList;
	}

	/**
	 * Obtiene el tipo de clase asociado con la entidad.
	 * 
	 * @return El tipo de clase de la entidad.
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * Establece el tipo de clase asociado con la entidad.
	 * 
	 * @param entityClass El tipo de clase de la entidad.
	 */
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
}
