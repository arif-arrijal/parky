package edu.ejemplo.demo.model;

import javax.persistence.*;

import org.hibernate.validator.constraints.CreditCardNumber;
// hibernate validator es la librería encargada de validar los formularios (modelos)
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tarjeta_credito")
public class TarjetaCredito implements Serializable{
	
	private Long id;
	private User usuario; //user id that owns the card
	private String nombreTitular; //holder name
	private String numeroTarjeta;
	private Date fechaCaducidad;
	private Integer version;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tarjeta_credito_seq_gen")
	@SequenceGenerator(name = "tarjeta_credito_seq_gen", sequenceName = "tarjeta_credito_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

    @Column(name = "nombre_titular", nullable = false)
	public String getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}

    @Column(name = "numero_tarjeta", unique = true, nullable = false)
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_caducidad")
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

    @Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}