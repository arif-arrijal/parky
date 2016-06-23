package edu.ejemplo.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// hibernate validator es la librería encargada de validar los formularios (modelos)
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "PARKING")
public class Parking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nombre;
	@NotBlank
	private String direccion;
	@NotBlank 
	private String ciudad;
	@NotBlank
	private String cp;
	@NotBlank
	private String provincia;
	@NotBlank
	private String cif;
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	
	//long best option?
	@NotNull
	private float tarifa; //tarifa por minuto de estancia -> fee
	@NotBlank
	private String telefono; 
	
	//se deberia de autogenerar desde alguna funcion si el usuario decide cambiarla
	private String claveAPI; //clave para autorizar el acceso del lector de OCR para cada estancia
	//there will be an api key, so we can send petitions for cars trying to access a parking
	
	
	public Parking() {
		GeneradorClaves clave =  new GeneradorClaves();
		this.claveAPI = clave.generarApiKey(128);
		
	}
	
	public Parking(String nombre, String direccion) {
		super();
		GeneradorClaves clave =  new GeneradorClaves();
		this.nombre = nombre;
		this.direccion = direccion;
		this.claveAPI = clave.generarApiKey(128);
	}
	
	
	public String getPassword(){
		return this.password;
	}
	
	public String getTelefono(){
		return this.telefono;
	}
	
	public void setTelefono(String tel){
		this.telefono = tel;		
	}
	
	public void setPassword(String pwd){
		this.password= pwd;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setTarifa(float cents){
		this.tarifa = cents;
	}
	
	public float getTarifa(){
		return tarifa;
	}
	
	public void setClaveAPI(String key){
		this.claveAPI = key;
	}
	
	public String getClaveAPI(){
		return claveAPI;
	}

	@Override
	public String toString() {
		return "Parking [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", email=" + email + "]";
	}
	

}
