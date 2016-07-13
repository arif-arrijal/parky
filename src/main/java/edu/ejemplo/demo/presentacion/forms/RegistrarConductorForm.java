//package edu.ejemplo.demo.presentacion.forms;
//
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotBlank;
//
//import edu.ejemplo.demo.model.GeneradorClaves;
//import edu.ejemplo.demo.model.User;
//import edu.ejemplo.demo.validators.FieldMatch;
//
//@FieldMatch(first = "email", second = "emailConfirmacion", message = "Los correos no coinciden")
//public class RegistrarConductorForm{
//
//	@NotBlank
//	@Email
//	private String email;
//	@NotBlank
//	@Email
//	private String emailConfirmacion;
//	@NotBlank
//	private String password;
//	@NotBlank
//	private String nombre;
//
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getEmailConfirmacion() {
//		return emailConfirmacion;
//	}
//	public void setEmailConfirmacion(String emailConfirmacion) {
//		this.emailConfirmacion = emailConfirmacion;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getNombre() {
//		return nombre;
//	}
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}
//
//	public User getUser() {
//		User user = new User();
//		user.setEmail(email);
//		user.setPassword(password);
////		user.setRol(User.ROLE_CONDUCTOR);
//		user.setEmailCode(new GeneradorClaves().generarApiKey(6));
//		user.setEmailVerificado(false);
//		return user;
//	}
//
//}
