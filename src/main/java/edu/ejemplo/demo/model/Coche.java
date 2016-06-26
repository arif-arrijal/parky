package edu.ejemplo.demo.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

// hibernate validator es la librería encargada de validar los formularios (modelos)

@Entity
@Table(name="COCHE")
public class Coche {
	
	@Id
	@GeneratedValue
	private long idCoche;

	@ManyToOne //muchos coches pueden estar asociados a UN conductor
	private Conductor conductor;
	
	@ManyToOne //muchos coches pueden estar asociados a UNA tarjeta
	private TarjetaCredito tarjetaCredito;
	
	@NotBlank
	private String matricula;
	
	@NotBlank(message="Please upload a valid file")
	private String fotoPermiso; //we store the name of the picture of the driving permission of the car that matches the plate
	
	@NotBlank
	private String nombreCoche; //car name to remember by the user, ie. Blue Audi, Green Ford...
	
	private boolean activo=false; //this is whether the car is active or not
	private boolean matriculaValidada=false; //this will be true when the fotoPermiso has been validated by app admin aftere he sees that it matches the car
	
	


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
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean isMatriculaValidada() {
		return matriculaValidada;
	}
	public void setMatriculaValidada(boolean matriculaValidada) {
		this.matriculaValidada = matriculaValidada;
	}
	public String getNombreCoche() {
		return nombreCoche;
	}
	public void setNombreCoche(String nombreCoche) {
		this.nombreCoche = nombreCoche;
	}
	public long getIdCoche() {
		return idCoche;
	}
	public void setIdCoche(long idCoche) {
		this.idCoche = idCoche;
	}
	public Conductor getConductor() {
		return conductor;
	}
	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}
	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}
	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

}
