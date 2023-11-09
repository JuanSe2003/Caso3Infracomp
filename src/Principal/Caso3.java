package Principal;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Caso3 {

	public static volatile boolean parar;

	public static void main(String[] args) throws NoSuchAlgorithmException {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Ingrese el algoritmo (1: SHA-256 o 2: SHA-512):");
		int algoritmoHash = Integer.valueOf(scanner.nextLine());

		System.out.println("Ingrese la cadena C:");
		String cadenaC = scanner.nextLine();

		System.out.println("Ingrese el numero entero buscado de bits en cero:");
		int Numero_Ceros = Integer.valueOf(scanner.nextInt());

		System.out.println("Ingrese si quiere correr 1 o 2 threads:");
		int numeroThreads = Integer.valueOf(scanner.nextInt());

		scanner.close();
		parar = false;

		if (numeroThreads == 1) {
			Calculadora calculadora = new Calculadora(1, algoritmoHash, cadenaC, Numero_Ceros, numeroThreads);
			calculadora.start();
		} else {
			Calculadora c1 = new Calculadora(1, algoritmoHash, cadenaC, Numero_Ceros, numeroThreads);
			Calculadora c2 = new Calculadora(2, algoritmoHash, cadenaC, Numero_Ceros, numeroThreads);
			c1.start();
			c2.start();
		}

		// System.out.println(calculadora.mostrarHash("hola mundognjdw"));
	}

}
