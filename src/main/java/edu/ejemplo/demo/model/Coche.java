package edu.ejemplo.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="coche")
public class Coche implements Serializable{

	private static final long serialVersionUID = 4666318272270682897L;

	private Long id;
	private User conductor;
	private TarjetaCredito tarjetaCredito;
	private String matricula;
	//we store the name of the picture of the driving permission of the car that matches the plate
	private String fotoPermiso;
	//car name to remember by the user, ie. Blue Audi, Green Ford...
	private String nombreCoche;
	//this is whether the car is active or not
	private Boolean activo;
	//this will be true when the fotoPermiso has been validated by app admin aftere he sees that it matches the car
	private Boolean matriculaValidada;
	private Integer version;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "coche_seq_gen")
	@SequenceGenerator(name = "coche_seq_gen", sequenceName = "coche_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "conductor_id", referencedColumnName = "id", nullable = false)
	public User getConductor() {
		return conductor;
	}

	public void setConductor(User conductor) {
		this.conductor = conductor;
	}

	@ManyToOne
	@JoinColumn(name = "tarjeta_credito_id", referencedColumnName = "id", nullable = false)
	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	@Column(name = "matricula", nullable = false)
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

    @Column(name = "foto_permiso", nullable = false)
	public String getFotoPermiso() {
		return fotoPermiso;
	}

	public void setFotoPermiso(String fotoPermiso) {
		this.fotoPermiso = fotoPermiso;
	}

    @Column(name = "nombre_coche", nullable = false)
	public String getNombreCoche() {
		return nombreCoche;
	}

	public void setNombreCoche(String nombreCoche) {
		this.nombreCoche = nombreCoche;
	}

    @Column(name = "activo", nullable = false)
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

    @Column(name = "matricula_validada", nullable = false)
	public Boolean getMatriculaValidada() {
		return matriculaValidada;
	}

	public void setMatriculaValidada(Boolean matriculaValidada) {
		this.matriculaValidada = matriculaValidada;
	}

    @Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
