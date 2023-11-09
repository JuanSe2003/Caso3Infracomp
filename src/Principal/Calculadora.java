package Principal;

import java.security.NoSuchAlgorithmException;

import Principal.Caso3;
import AlgoritmosHash.SHA256;
import AlgoritmosHash.SHA512;

public class Calculadora extends Thread {
	private Integer id;
	private Integer algoritmo;
	private Integer numeroCeros;
	private Integer nthreads;
	private boolean parar = false;

	private String cadena;
	private String v;
	private String cadenaFinal = "";

	public Calculadora(int id, Integer algoritmo, String cadena, int numeroCeros, int nThreads) {
		this.id = id;
		this.algoritmo = algoritmo;
		this.cadena = cadena;
		this.numeroCeros = numeroCeros;
		this.nthreads = nThreads;
	}

	public void run() {
		long startTime = System.currentTimeMillis();
		while (parar == false) {
			try {
				parar = buscar().contains(">>>v:");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		long timeElapsed = endTime - startTime;
		System.out.println("Tiempo de ejecucion en milisegundos: " + timeElapsed);
	}

	public String buscar() throws NoSuchAlgorithmException {

		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		if (id == 1) {// THREAD 1
			// Longitud mínima 1
			for (int i = 0; i < alphabet.length(); i++) {
				v = "" + alphabet.charAt(i);
				cadenaFinal = cadena + v;
				if (verificarHash(mostrarHash(cadenaFinal), numeroCeros)) {
					parar = true;
					return ">>>v:" + v + "Thread 1";

				}
				;
			}
			// Longitud 2 a 7
			for (int i = 2; i <= 7; i++) {
				// Generar permutaciones de longitud i
				String resultado = permutations(alphabet, "", i);
				if (resultado != null) {
					parar = true;
					return resultado + "Thread 1";
				}
			}

		} else {// THREAD 2
			// Longitud 2 a 7
			for (int i = 7; i >= 2; i--) {
				// Generar permutaciones de longitud i

				String resultado = permutations(alphabet, "", i);
				if (resultado != null) {
					parar = true;
					return resultado + "Thread 2";
				}
			}
			// Longitud mínima 1
			for (int i = 0; i < alphabet.length(); i++) {
				v = "" + alphabet.charAt(i);
				cadenaFinal = cadena + v;
				if (verificarHash(mostrarHash(cadenaFinal), numeroCeros)) {
					parar = true;
					return ">>>v:" + v + "Thread 2";
				}
				;
			}
		}
		return ">>>! si no hay nada hasta ahora, no hay respuesta para espacio de busqueda";
	}

	public String permutations(String alphabet, String prefix, int lenght) throws NoSuchAlgorithmException {

		if (prefix.length() == lenght) {
			v = prefix;
			cadenaFinal = cadena + v;
			if (verificarHash(mostrarHash(cadenaFinal), numeroCeros)) {
				return ">>>v:" + v;
			}
			return null;
		}

		for (int i = 0; i < alphabet.length(); i++) {
			String result = permutations(alphabet, prefix + alphabet.charAt(i), lenght);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public String mostrarHash(String input) throws NoSuchAlgorithmException {
		String solucion = "SS";
		if (this.algoritmo == 2) {
			SHA512 class1 = new SHA512();
			solucion = class1.getSHA512(input);
		} else if (this.algoritmo == 1) {
			SHA256 class2 = new SHA256();
			solucion = class2.getSHA256(input);
		}

		else {
			System.out.println("El algoritmo es invalido");
		}

		return solucion;

	}

	public boolean verificarHash(String hash, int nBitsCero) {
		int nBits = 0;

		for (char c : hash.toCharArray()) {
			if (c == '0') {
				nBits += 4;
			} else {
				int value = Character.digit(c, 16);
				for (int i = 3; i >= 0; i--) {
					if ((value & (1 << i)) == 0) {
						nBits++;
					} else {
						return false; // Retorna falso tan pronto como encuentres un bit que no es cero
					}
					if (nBits >= nBitsCero) { // Si hemos encontrado suficientes bits cero, retorna verdadero
						return true;
					}
				}
			}
		}

		return nBits >= nBitsCero; // Retorna verdadero si hemos encontrado suficientes bits cero
	}

}