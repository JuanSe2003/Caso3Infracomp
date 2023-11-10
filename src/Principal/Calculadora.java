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

	private String cadena;
	private String v;
	private String cadenaFinal = "";
	private Object lock;

	// public static double tiempoTotal = 0;

	public Calculadora(int id, Integer algoritmo, String cadena, int numeroCeros, int nThreads, Object lock) {
		this.id = id;
		this.algoritmo = algoritmo;
		this.cadena = cadena;
		this.numeroCeros = numeroCeros;
		this.nthreads = nThreads;
		this.lock = lock;
	}

	public void run() {
		String respuesta = "";
		long startTime = System.currentTimeMillis();
		try {
			respuesta = buscar();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(respuesta);
		long endTime = System.currentTimeMillis();
		long timeElapsed = endTime - startTime;
		System.out.println("Tiempo de ejecucion en milisegundos: " + timeElapsed);
	}

	public String buscar() throws NoSuchAlgorithmException {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String alphabet2 = "zyxwvutsrqponmlkjihgfedcba";
		int i;

		if (id == 1) {
			// THREAD 1
			// Longitud mínima 1
			i = 0;
			while (i < alphabet.length() && !getParar()) {
				v = "" + alphabet.charAt(i);
				cadenaFinal = cadena + v;
				if (verificarHash(mostrarHash(cadenaFinal), numeroCeros)) {
					setParar(true);
					return ">>>v:" + v + " Thread 1";
				}
				i++;
			}

			// Longitud 2 a 7
			i = 2;
			while (i <= 7 && !getParar()) {
				// Generar permutaciones de longitud i
				String resultado = permutations(alphabet, "", i);
				if (resultado != null) {
					setParar(true);
					return resultado + " Thread 1";
				}
				i++;
			}
		} else {
			// THREAD 2
			// Longitud 2 a 7
			i = 7;
			while (i >= 2 && !getParar()) {
				// Generar permutaciones de longitud i
				String resultado = permutations(alphabet2, "", i);
				if (resultado != null) {
					setParar(true);
					return resultado + " Thread 2";
				}
				i--;
			}

			// Longitud mínima 1
			i = 0;
			while (i < alphabet2.length() && !getParar()) {
				v = "" + alphabet2.charAt(i);
				cadenaFinal = cadena + v;
				if (verificarHash(mostrarHash(cadenaFinal), numeroCeros)) {

					return ">>>v:" + v + " Thread 2";
				}
				i++;
			}
		}
		return ">>>! si no hay nada hasta ahora, no hay respuesta para espacio de busqueda";
	}

	private boolean getParar() {
		synchronized (lock) {
			return Caso3.parar;
		}
	}

	private void setParar(boolean valor) {
		synchronized (lock) {
			Caso3.parar = valor;
		}
	}

	public String permutations(String alphabet, String prefix, int length) throws NoSuchAlgorithmException {
		// Verificar la condición de parada al inicio de la función
		if (getParar()) {
			return null; // Si se debe parar, termina la ejecución inmediatamente.
		}

		if (prefix.length() == length) {
			v = prefix;
			cadenaFinal = cadena + v;
			if (verificarHash(mostrarHash(cadenaFinal), numeroCeros)) {
				setParar(true); // Indica a otros hilos que detengan su ejecución.
				return ">>>v:" + v;
			}
			return null;
		}

		for (int i = 0; i < alphabet.length(); i++) {
			// Aquí también se puede verificar la condición de parada antes de la recursión.
			if (getParar()) {
				return null; // De nuevo, si se debe parar, termina la ejecución inmediatamente.
			}

			String result = permutations(alphabet, prefix + alphabet.charAt(i), length);
			if (result != null) {
				// No es necesario llamar a setParar(true) aquí porque
				// ya se habría llamado dentro de la instancia de la función que encontró el
				// resultado.
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
		long startTime = System.currentTimeMillis();
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
		long endTime = System.currentTimeMillis();
		long timeElapsed = endTime - startTime;
		System.out.println("Tiempo de ejecucion en milisegundos: " + timeElapsed);
		return nBits >= nBitsCero; // Retorna verdadero si hemos encontrado suficientes bits cero
	}

}