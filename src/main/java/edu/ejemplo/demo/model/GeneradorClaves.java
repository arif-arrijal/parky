package edu.ejemplo.demo.model;

import java.util.Random;

public class GeneradorClaves {
	
	private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final Random RANDOM = new Random();

	public String generarApiKey(int tamanio) {
		StringBuilder sb = new StringBuilder(tamanio);
		for (int i = 0; i < tamanio; i++) {
			sb.append(CARACTERES.charAt(RANDOM.nextInt(CARACTERES.length())));
		}
		return sb.toString();
	}
}
