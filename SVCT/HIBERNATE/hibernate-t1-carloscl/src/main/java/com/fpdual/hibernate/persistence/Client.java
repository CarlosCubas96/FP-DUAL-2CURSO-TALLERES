package com.fpdual.hibernate.persistence;

import java.io.Serializable;

import javax.persistence.*;

/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase que representa un Cliente.
 * Extiende la clase AbstractEntity para heredar propiedades de auditoría.
 * 
 * @author Carlos
 *
 */
@Entity
@Table(name = "FPDUAL_HEX_CLIENT")
public class Client extends AbstractEntity implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** Identificador único del cliente */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENT_ID")
	private Long clientId;

	/** Nombre del cliente */
	@Column(name = "FIRST_NAME")
	private String firstName;

	/** Apellido del cliente */
	@Column(name = "LAST_NAME")
	private String lastName;

	/** Segundo apellido del cliente */
	@Column(name = "SECOND_LAST_NAME")
	private String secondLastName;

	/** Documento de identidad del cliente */
	@Column(name = "IDENTITY_DOCUMENT", unique = true, nullable = false, length = 9)
	private String identityDocument;

	/**
	 * Obtiene el identificador único del cliente.
	 * 
	 * @return El identificador único del cliente.
	 */
	public Long getClientId() {
		return clientId;
	}

	/**
	 * Establece el identificador único del cliente.
	 * 
	 * @param clientId El identificador único del cliente.
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * Obtiene el nombre del cliente.
	 * 
	 * @return El nombre del cliente.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Establece el nombre del cliente.
	 * 
	 * @param firstName El nombre del cliente.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Obtiene el apellido del cliente.
	 * 
	 * @return El apellido del cliente.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Establece el apellido del cliente.
	 * 
	 * @param lastName El apellido del cliente.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Obtiene el segundo apellido del cliente.
	 * 
	 * @return El segundo apellido del cliente.
	 */
	public String getSecondLastName() {
		return secondLastName;
	}

	/**
	 * Establece el segundo apellido del cliente.
	 * 
	 * @param secondLastName El segundo apellido del cliente.
	 */
	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	/**
	 * Obtiene el documento de identidad del cliente.
	 * 
	 * @return El documento de identidad del cliente.
	 */
	public String getIdentityDocument() {
		return identityDocument;
	}

	/**
	 * Establece el documento de identidad del cliente.
	 * 
	 * @param identityDocument El documento de identidad del cliente.
	 */
	public void setIdentityDocument(String identityDocument) {
		this.identityDocument = identityDocument;
	}

	/**
	 * Método que devuelve la clase del cliente.
	 * 
	 * @return La clase del cliente.
	 */
	@Transient
	public Class<?> getClase() {
		return Client.class;
	}

	/**
	 * Obtiene el identificador de la entidad (cliente).
	 * 
	 * @return El identificador de la entidad.
	 */
	@Override
	public Long getId() {
		return this.clientId;
	}

	/**
	 * Establece el identificador de la entidad (cliente).
	 * 
	 * @param id El identificador de la entidad.
	 */
	@Override
	public void setId(Long id) {
		this.clientId = id;
	}

	/**
	 * Genera una representación en cadena del objeto cliente.
	 * 
	 * @return Una cadena que representa al cliente.
	 */
	@Override
	public String toString() {
		return "Client [getClientId()=" + getClientId() + ", getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + ", getSecondLastName()=" + getSecondLastName() + ", getIdentityDocument()="
				+ getIdentityDocument() + ", getUpdatedUser()=" + getUpdatedUser() + "]";
	}

}
