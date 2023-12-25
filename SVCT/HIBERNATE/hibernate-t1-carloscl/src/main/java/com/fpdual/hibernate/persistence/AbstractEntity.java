package com.fpdual.hibernate.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fpdual.hibernate.Utils;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase abstracta que sirve como base para las entidades. Contiene información
 * de auditoría como el usuario que actualiza y la fecha de actualización.
 * 
 * Esta clase proporciona campos y métodos comunes para la auditoría de
 * entidades.
 * 
 * @author Carlos
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	/** Número de versión para la serialización. */
	private static final long serialVersionUID = 1L;

	/** Auditoría | Usuario que actualiza */
	@Column(name = "AUDIT_UPDATED_USER", nullable = false, length = 255)
	private String updatedUser;

	/** Auditoría | Día actualizado */
	@Column(name = "AUDIT_UPDATED_DATE", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private Date updatedDate;

	/**
	 * Método abstracto para obtener el identificador de la entidad.
	 * 
	 * @return El identificador de la entidad.
	 */
	public abstract Long getId();

	/**
	 * Método abstracto para establecer el identificador de la entidad.
	 * 
	 * @param id El identificador a establecer.
	 */
	public abstract void setId(final Long id);

	/**
	 * Obtiene el usuario que actualiza la entidad.
	 * 
	 * @return El usuario que actualiza la entidad.
	 */
	@Column(name = "AUDIT_UPDATED_USER", nullable = false)
	public String getUpdatedUser() {
		return updatedUser;
	}

	/**
	 * Establece el usuario que actualiza la entidad.
	 * 
	 * @param updatedUser El usuario que actualiza la entidad.
	 */
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;

		// Agrega log al establecer el usuario actualizado
		Utils.log(Utils.INFO, "Usuario actualizado: " + updatedDate);
	}

	/**
	 * Obtiene la fecha de actualización de la entidad.
	 * 
	 * @return La fecha de actualización de la entidad.
	 */
	@Column(name = "AUDIT_UPDATED_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * Establece la fecha de actualización de la entidad.
	 * 
	 * @param updatedDate La fecha de actualización de la entidad.
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;

		// Agrega log al establecer la fecha de actualización
		Utils.log(Utils.INFO, "Fecha actualizada: " + updatedDate);
	}
}
