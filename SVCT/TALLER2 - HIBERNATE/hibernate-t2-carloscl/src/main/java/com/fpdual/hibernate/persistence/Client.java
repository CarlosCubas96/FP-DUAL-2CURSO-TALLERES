package com.fpdual.hibernate.persistence;

import java.io.Serializable;

import javax.persistence.*;


/**
 * FPDUAL - HIBERNATE - Taller1
 * 
 * Clase Cliente
 * 
 * @author Carlos
 *
 */
@Entity
@Table(name = "FPDUAL_HEX_CLIENT")
public class Client extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLIENT_ID")
	private Long clientId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "SECOND_LAST_NAME")
	private String secondLastName;

	@Column(name = "IDENTITY_DOCUMENT", unique = true, nullable = false, length = 9)
	private String identityDocument;

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondLastName() {
		return secondLastName;
	}

	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	public String getIdentityDocument() {
		return identityDocument;
	}

	public void setIdentityDocument(String identityDocument) {
		this.identityDocument = identityDocument;
	}

	@Transient
	public Class<?> getClase() {
		return Client.class;
	}

	@Override
	public Long getId() {
		return this.clientId;
	}

	@Override
	public void setId(Long id) {
		this.clientId = id;
	}

}
