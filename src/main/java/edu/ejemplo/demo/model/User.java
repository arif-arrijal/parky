package edu.ejemplo.demo.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

// hibernate validator es la librería encargada de validar los formularios (modelos)
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.ejemplo.demo.validators.FieldMatch;

@Entity
@Table(name = "USER")
@FieldMatch.List({
    @FieldMatch(first = "email", second = "emailconfirm", message = "The email fields must match")
})
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

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
	
	private String role;
	
	private String emailCode; //the code to be sent to the user to validate the address

	private boolean emailVerificado; //will be false until email is verified from the activation code /link

 	
	public String getEmailconfirm(){
		return emailconfirm;
	}
	
	public void setEmailconfirm(String eml){
		this.emailconfirm = eml;
	}
	
	@Override
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

	public boolean getEmailVerificado() {
		return emailVerificado;
	}

	public void setEmailVerificado(boolean emailVerificado) {
		this.emailVerificado = emailVerificado;
	}

	public String getEmailCode() {
		return emailCode;
	}

	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return emailVerificado;
	}
	
	
}
