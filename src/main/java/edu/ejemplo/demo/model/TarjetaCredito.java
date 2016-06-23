package edu.ejemplo.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.CreditCardNumber;
// hibernate validator es la librería encargada de validar los formularios (modelos)
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class TarjetaCredito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // card id
	
	@ManyToOne
	private User usuario; //user id that owns the card
	
	@NotBlank
	private String nombreTitular; //holder name
	
	@CreditCardNumber(message="Please enter a valid card number")
	private String numeroTarjeta; 
	
	@NotBlank
	private String fechaCaducidad; //this is credit card Expiration date, im not sure if should be an int, or even if it could be stored in the upper attribute the whole thing
		
	public Long getId() {
	return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUsuario() {
		return usuario;
	}
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public String getNombreTitular() {
		return nombreTitular;
	}
	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}	
}
