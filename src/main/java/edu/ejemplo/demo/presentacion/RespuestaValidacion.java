//package edu.ejemplo.demo.presentacion;
//
//import java.util.List;
//
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
//
//public class RespuestaValidacion {
//
//	private static final String EXITO = "EXITO";
//	private static final String FRACASO = "FRACASO";
//
//	private String resultado;
//	private String mensajeExito;
//	private List<ObjectError> errores;
//
//	public RespuestaValidacion(BindingResult br, String mensajeExito) {
//		// Si el bindingResult continene errores, resultado será FRACASO, sino EXITO
//		this.resultado = br.hasErrors() ? FRACASO : EXITO;
//		// Añadimos los objetos fieldError del bindingResult
//		this.errores = br.getAllErrors();
//		this.mensajeExito = mensajeExito;
//	}
//
//	public RespuestaValidacion(boolean correcto) {
//		this.resultado = correcto ? FRACASO : EXITO;
//	}
//
//	public List<ObjectError> getErrores() {
//		return errores;
//	}
//
//	public String getResultado() {
//		return resultado;
//	}
//
//	public String getMensajeExito() {
//		return mensajeExito;
//	}
//}
