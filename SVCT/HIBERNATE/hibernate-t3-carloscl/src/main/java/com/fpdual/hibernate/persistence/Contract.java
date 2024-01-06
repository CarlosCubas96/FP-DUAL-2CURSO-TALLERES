package com.fpdual.hibernate.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * FPDUAL - HIBERNATE - Taller3
 * 
 * Clase que representa un Contrato. Extiende la clase AbstractEntity para
 * heredar propiedades de auditoría.
 * 
 * La relación con Cliente es de 1:1, un contrato solo puede estar asociado a un cliente.
 * 
 * @author Carlos
 *
 */
@Entity
@Table(name = "FPDUAL_HEX_CONTRACT")
public class Contract extends AbstractEntity implements Serializable {

    /** Serial Version */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTRACT_ID")
    /** Identificador único del contrato. */
    private Long contractId;

    @ManyToOne
    @JoinColumn(name = "FK_CLIENT_ID")
    /** Cliente asociado al contrato. */
    private Client client;

    @Column(name = "VIGENCY_DATE")
    /** Fecha de inicio del contrato. */
    private Date startDate;

    @Column(name = "EXPIRATION_DATE")
    /** Fecha de vencimiento del contrato. */
    private Date endDate;

    @Column(name = "MONTHLY_PRICE")
    /** Precio mensual del contrato. */
    private BigDecimal monthlyPrice;

    /**
     * Obtiene el identificador del contrato.
     * 
     * @return El identificador del contrato.
     */
    public Long getContractId() {
        return contractId;
    }

    /**
     * Establece el identificador del contrato.
     * 
     * @param contractId El identificador del contrato.
     */
    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    /**
     * Obtiene el cliente asociado al contrato.
     * 
     * @return El cliente asociado al contrato.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Establece el cliente asociado al contrato.
     * 
     * @param client El cliente asociado al contrato.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Obtiene la fecha de inicio del contrato.
     * 
     * @return La fecha de inicio del contrato.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Establece la fecha de inicio del contrato.
     * 
     * @param startDate La fecha de inicio del contrato.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Obtiene la fecha de vencimiento del contrato.
     * 
     * @return La fecha de vencimiento del contrato.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Establece la fecha de vencimiento del contrato.
     * 
     * @param endDate La fecha de vencimiento del contrato.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Obtiene el precio mensual del contrato.
     * 
     * @return El precio mensual del contrato.
     */
    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    /**
     * Establece el precio mensual del contrato.
     * 
     * @param monthlyPrice El precio mensual del contrato.
     */
    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    /**
     * Método que devuelve la clase del contrato.
     * 
     * @return La clase del contrato.
     */
    @Transient
    public Class<?> getClase() {
        return Contract.class;
    }

    /**
     * Obtiene el identificador de la entidad (contrato).
     * 
     * @return El identificador de la entidad.
     */
    @Override
    public Long getId() {
        return this.contractId;
    }

    /**
     * Establece el identificador de la entidad (contrato).
     * 
     * @param id El identificador de la entidad.
     */
    @Override
    public void setId(Long id) {
        this.contractId = id;
    }

    /**
     * Genera una representación de cadena del objeto Contract.
     *
     * @return Una cadena que representa el objeto Contract.
     */
    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", client=" + client +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", monthlyPrice=" + monthlyPrice +
                '}';
    }
}
