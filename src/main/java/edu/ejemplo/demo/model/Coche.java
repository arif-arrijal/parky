package edu.ejemplo.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


// hibernate validator es la librería encargada de validar los formularios (modelos)
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import edu.ejemplo.demo.validators.FieldMatch;

@Entity
@Table(name="COCHE")
public class Coche {
	
	@Id
	@GeneratedValue
	private long idCoche;
	
	
	private long idUsuario;
	private long idTarjeta;
	
	@NotBlank
	private String matricula;
	
	@NotBlank(message="Please upload a valid file")
	private String fotoPermiso; //we store the name of the picture of the driving permission of the car that matches the plate
	
	@NotBlank
	private String nombreCoche; //car name to remember by the user, ie. Blue Audi, Green Ford...
	
	private Integer activo=0; //this is whether the car is active or not
	private Integer matriculaValidada=0; //this will be true when the fotoPermiso has been validated by app admin aftere he sees that it matches the car
	
	


	public String getFotoPermiso() {
		return fotoPermiso;
	}
	public void setFotoPermiso(String fotoPermiso) {
		this.fotoPermiso = fotoPermiso;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Integer isActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public Integer isMatriculaValidada() {
		return matriculaValidada;
	}
	public void setMatriculaValidada(Integer matriculaValidada) {
		this.matriculaValidada = matriculaValidada;
	}
	public String getNombreCoche() {
		return nombreCoche;
	}
	public void setNombreCoche(String nombreCoche) {
		this.nombreCoche = nombreCoche;
	}
	public long getIdTarjeta() {
		return idTarjeta;
	}
	public void setIdTarjeta(long idTarjeta) {
		this.idTarjeta = idTarjeta;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdCoche() {
		return idCoche;
	}
	public void setIdCoche(long idCoche) {
		this.idCoche = idCoche;
	}
	
	

}
