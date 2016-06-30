package edu.ejemplo.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// hibernate validator es la librería encargada de validar los formularios (modelos)
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "parking")
@PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
public class Parking extends User{

	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private String provincia;
	private String cif;
	private String telefono;
	//tarifa por minuto de estancia -> fee
	private Long tarifa;
	//se deberia de autogenerar desde alguna funcion si el usuario decide cambiarla
	//clave para autorizar el acceso del lector de OCR para cada estancia
	//there will be an api key, so we can send petitions for cars trying to access a parking
	private String claveAPI;

	@Column(name = "direccion", nullable = false)
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "ciudad", nullable = false)
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@Column(name = "codigo_pastal", nullable = false)
	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Column(name = "provincia", nullable = false)
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Column(name = "cif", nullable = false)
	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	@Column(name = "telefono", nullable = false)
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

    @Column(name = "tarifa", nullable = false)
	public Long getTarifa() {
		return tarifa;
	}

	public void setTarifa(Long tarifa) {
		this.tarifa = tarifa;
	}

    @Column(name = "clave_api")
	public String getClaveAPI() {
		return claveAPI;
	}

	public void setClaveAPI(String claveAPI) {
		this.claveAPI = claveAPI;
	}
}