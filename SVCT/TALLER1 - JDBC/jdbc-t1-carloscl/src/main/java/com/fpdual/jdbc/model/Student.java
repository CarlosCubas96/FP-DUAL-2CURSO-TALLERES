package com.fpdual.jdbc.model;

/**
 * FP-DUAL - JDBC - Taller1
 * 
 * Clase que representa la entidad Student en la base de datos.
 * 
 * @author Carlos
 *
 */
public class Student {

	/** Identificador único del estudiante. */
	private Long id;

	/** Nombre del estudiante. */
	private String name;

	/** Apellido del estudiante. */
	private String lastName;

	/** Dirección del estudiante. */
	private String address;

	/** Número de teléfono del estudiante. */
	private String phone;

	/** Correo electrónico del estudiante. */
	private String email;

	/**
	 * Constructor sin parámetros.
	 */
	public Student() {
	}

	/**
	 * Constructor con parámetros.
	 * 
	 * @param name     El nombre del estudiante.
	 * @param lastName El apellido del estudiante.
	 * @param address  La dirección del estudiante.
	 * @param phone    El número de teléfono del estudiante.
	 * @param email    El correo electrónico del estudiante.
	 */
	public Student(String name, String lastName, String address, String phone, String email) {
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	/**
	 * Obtiene el ID del estudiante.
	 *
	 * @return El ID del estudiante.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el ID del estudiante.
	 *
	 * @param id El ID del estudiante.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del estudiante.
	 *
	 * @return El nombre del estudiante.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del estudiante.
	 *
	 * @param name El nombre del estudiante.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el apellido del estudiante.
	 *
	 * @return El apellido del estudiante.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Establece el apellido del estudiante.
	 *
	 * @param lastName El apellido del estudiante.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Obtiene la dirección del estudiante.
	 *
	 * @return La dirección del estudiante.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Establece la dirección del estudiante.
	 *
	 * @param address La dirección del estudiante.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Obtiene el número de teléfono del estudiante.
	 *
	 * @return El número de teléfono del estudiante.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Establece el número de teléfono del estudiante.
	 *
	 * @param phone El número de teléfono del estudiante.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Obtiene el correo electrónico del estudiante.
	 *
	 * @return El correo electrónico del estudiante.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el correo electrónico del estudiante.
	 *
	 * @param email El correo electrónico del estudiante.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Devuelve una representación en formato de cadena de la entidad Student.
	 *
	 * @return Una cadena que representa la entidad Student.
	 */
	@Override
	public String toString() {
		return "Student [getId()=" + getId() + ", getName()=" + getName() + ", getLastName()=" + getLastName()
				+ ", getAddress()=" + getAddress() + ", getPhone()=" + getPhone() + ", getEmail()=" + getEmail() + "]";
	}

}
