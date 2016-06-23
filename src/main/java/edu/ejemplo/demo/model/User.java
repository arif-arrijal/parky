package edu.ejemplo.demo.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER")
public class User implements UserDetails {

	//todos los objetos serializables deben de llevar un identificador de version
	//nos permite hacer evolutivos y diferenciar versiones para evitar problemas.
	private static final long serialVersionUID = 1L;

	public static final String ROLE_CONDUCTOR = "ROLE_CONDUCTOR"; 
	public static final String ROLE_PARKING = "ROLE_PARKING"; 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String password;
	
	private String rol;
	
	private String emailCode; //the code to be sent to the user to validate the address

	private boolean emailVerificado; //will be false until email is verified from the activation code /link

	public User() {
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(rol));
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
		return true;
	}
	
	@Override
	public String toString() {
		return "Parking [id=" + id  + ", email=" + email + "]";
	}
	
}
