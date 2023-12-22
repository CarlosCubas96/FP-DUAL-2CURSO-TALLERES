package com.fpdual.hibernate.persistence;

import java.util.List;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * DAO genérico
 * 
 * @author Carlos
 *
 */
public interface CommonDaoI<T> {

	public void insert(T paramT);

	public void update(T paramT);

	public void delete(T paramT);

	public T searchById(Long id);

	public List<T> searchAll();

}
