package edu.ejemplo.demo.presentacion.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import edu.ejemplo.demo.model.Parking;
import edu.ejemplo.demo.model.User;

public class RegistrarParkingForm {
	
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;
	@NotBlank
	private String nombre;
	@NotBlank
	private String direccion;
	@NotBlank
	private String ciudad;
	@NotBlank
	private String provincia;
	@NotBlank
	private String codigoPostal;
	@NotBlank
	private String telefono;
	
	//this should be a float and cant be zero
	private float tarifa;
	@NotBlank
	private String cif;
	@NotBlank
	private String claveAPI;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public float getTarifa() {
		return tarifa;
	}
	public void setTarifa(float tarifa) {
		this.tarifa = tarifa;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getClaveAPI() {
		return claveAPI;
	}
	public void setClaveAPI(String claveAPI) {
		this.claveAPI = claveAPI;
	}	
	
	public User getUser() {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
//		user.setRol(User.ROLE_PARKING);
		return user;
	}
	public Parking getParking() {
		Parking parking = new Parking();
		parking.setNombre(nombre);
		parking.setDireccion(direccion);
//		parking.setTarifa(tarifa);
		//La clave API ya se inicializado directamente en el constructor
		parking.setTelefono(telefono);
		parking.setCif(cif);
		parking.setCiudad(ciudad);
		parking.setProvincia(provincia);
		return parking;
	}

}
