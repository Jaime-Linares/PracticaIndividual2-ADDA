package tests;

import java.util.List;
import java.util.function.Predicate;

import ejemplos.Ejemplo5;
import us.lsi.common.Files2;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.Tree;

public class TestEjemplo5 {
	
	private static final Predicate<Integer> PAR = x -> x%2 == 0;
	private static final Predicate<Integer> PRIMO = x -> Math2.esPrimo(x);

	public static void main(String[] args) {
		
		String file = "ficheros/Ejemplo5DatosEntrada.txt";
		
		List<Tree<Integer>> inputs = Files2
				.streamFromFile(file)
				.map(linea -> Tree.parse(linea, s-> Integer.parseInt(s)))
				.toList();
		
		System.out.println("* TEST EJEMPLO 5*");
		
		for(Tree<Integer> t: inputs) {
			System.out.println("\n" +t);
			
			System.out.println("\nSOLUCION RECURSIVA-PARES: " + Ejemplo5.solucion_ejemplo5(t, PAR));
			System.out.println("\nSOLUCION RECURSIVA-PRIMOS: " + Ejemplo5.solucion_ejemplo5(t, PRIMO));
		}
		
		

	}

}
