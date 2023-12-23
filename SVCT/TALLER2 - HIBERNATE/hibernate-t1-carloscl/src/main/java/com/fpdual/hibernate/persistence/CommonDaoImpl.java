package com.fpdual.hibernate.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Implementación genérica de DAO para operaciones comunes de CRUD en la base de datos.
 * 
 * @param <T> Tipo de entidad para la operación CRUD.
 * @author Carlos
 *
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

	@Override
	public Boolean insert(final T paramT) {

		// Inicialización de la bandera.
		Boolean ok = Boolean.FALSE;

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		try {
			// Inserción.
			session.save(paramT);
			session.flush();

			// Commit.
			session.getTransaction().commit();
			ok = Boolean.TRUE;
		} catch (Exception e) {
			// Rollback en caso de error.
			session.getTransaction().rollback();
		}

		return ok;
	}

	@Override
	public Boolean update(final T paramT) {

		// Inicialización de la bandera.
		Boolean ok = Boolean.FALSE;

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		try {
			// Actualización.
			session.saveOrUpdate(paramT);

			// Commit.
			session.getTransaction().commit();
			ok = Boolean.TRUE;
		} catch (Exception e) {
			// Rollback en caso de error.
			session.getTransaction().rollback();
		}

		return ok;
	}

	@Override
	public Boolean delete(final T paramT) {

		// Inicialización de la bandera.
		Boolean ok = Boolean.FALSE;

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		try {
			// Eliminación.
			session.delete(paramT);

			// Commit.
			session.getTransaction().commit();
			ok = Boolean.TRUE;
		} catch (Exception e) {
			// Rollback en caso de error.
			session.getTransaction().rollback();
		}

		return ok;
	}

	@Override
	public T searchById(final Long id) {

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		T result = null;

		try {
			// Búsqueda por PK.
			result = session.get(this.entityClass, id);

		} finally {
			// Commit.
			session.getTransaction().commit();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> searchAll() {

		// Verificación de sesión abierta.
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}

		List<T> resultList = null;

		try {
			// Búsqueda de todos los registros.
			resultList = session.createQuery("FROM " + this.entityClass.getName()).list();

		} finally {
			// Commit.
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
