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
@Table(name = "USER")
@FieldMatch.List({
    @FieldMatch(first = "email", second = "emailconfirm", message = "The email fields must match")
})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nombre;

	@NotBlank
	@Email
	private String email;
	
	@Email
	@NotBlank
	@Transient // This will make sure it doesnt get saved in database.
	private String emailconfirm;
	
	@NotBlank
	private String password;
	
	private String emailCode; //the code to be sent to the user to validate the address

	private Integer emailVerificado; //will be false until email is verified from the activation code /link

 	
	public String getEmailconfirm(){
		return emailconfirm;
	}
	
	public void setEmailconfirm(String eml){
		this.emailconfirm = eml;
	}
	
	public String getPassword(){
		return this.password;
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Parking [id=" + id + ", nombre=" + nombre  + ", email=" + email + "]";
	}

	public Integer getEmailVerificado() {
		return emailVerificado;
	}

	public void setEmailVerificado(Integer emailVerificado) {
		this.emailVerificado = emailVerificado;
	}

	public String getEmailCode() {
		return emailCode;
	}

	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
	
	
}
