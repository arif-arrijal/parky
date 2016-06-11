package edu.ejemplo.demo.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Estancia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //id of the stay of the car
	
	@ManyToOne
	private User usuario; //id of the user that owns the car
	@ManyToOne
	private Parking parking; //id of the Parking where the car stays
	@ManyToOne
	private TarjetaCredito tarjeta; //id of the card where the stay will be billed. Will be set after the car leaves the stay and the charge is made, as card associated to a car could change during the stay
	@ManyToOne
	private Coche coche; //id of the car that stays
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaEntrada; //time the car enters the parking
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaSalida; //time the car leaves the parking, upon that we will calculate the 
	private Long importeEstancia; //the cost of the stay, calculated upon exit, based on the parking fee per minute
	private String fotoEntrada; //we will store a picture of the car taken by the OCR upon entering the parking, we store the location of the picture
	

	public Date getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public Date getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}
	public Long getImporteEstancia() {
		return importeEstancia;
	}
	public void setImporteEstancia(Long importeEstancia) {
		this.importeEstancia = importeEstancia;
	}

	/* devuelve los minutos de estancia de sacar la diferencia entre horas de entrda y salida en milisengundos, por eso dividimios entre 6000
	 * */	
	public long getMinutos() {		
		return (horaSalida.getTime() - horaEntrada.getTime()) / 60000;
		
	}
	public String getFotoEntrada() {
		return fotoEntrada;
	}
	public void setFotoEntrada(String fotoEntrada) {
		this.fotoEntrada = fotoEntrada;
	}

}
