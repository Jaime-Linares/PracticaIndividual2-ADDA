package ejerciciosTests;

import java.util.List;

import ejercicios.Ejercicio4;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio4 {

	public static void main(String[] args) {
		
		// LECTURAS DE FICHERO
		String rutaFicheroBinarios = "ficheros/Ejercicio4DatosEntradaBinario.txt";
		String rutaFicheroNarios = "ficheros/Ejercicio4DatosEntradaNario.txt";
		
		List<BinaryTree<String>> datosBinarios = Files2.streamFromFile(rutaFicheroBinarios)
				.map(linea -> BinaryTree.parse(linea))
				.toList();
		
		List<Tree<String>> datosNarios = Files2.streamFromFile(rutaFicheroNarios)
				.map(linea -> Tree.parse(linea))
				.toList();
		
		
		// TEST
		System.out.println("* TEST EJERCICIO 4 *");
		
		System.out.println("\n*Arboles binarios*");
		for(BinaryTree<String> tree: datosBinarios) {
			System.out.println(tree + ": " + Ejercicio4.fEjercicio4Binary(tree));
		}
		
		System.out.println("\n*Arboles n-arios*");
		for(Tree<String> tree: datosNarios) {
			System.out.println(tree + ": " + Ejercicio4.fEjercicio4Nary(tree));
		}
		
	}
	

}
