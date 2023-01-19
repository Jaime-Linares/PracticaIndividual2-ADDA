package ejerciciosTests;

import java.util.List;

import ejercicios.Ejercicio3;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjercicio3 {

	public static void main(String[] args) {
		
		// LECTURAS DE FICHERO
		String rutaFicheroBinarios = "ficheros/Ejercicio3DatosEntradaBinario.txt";
		String rutaFicheroNarios = "ficheros/Ejercicio3DatosEntradaNario.txt";
		
		List<Pair<BinaryTree<Character>, Character>> datosBinarios = 
				Files2.streamFromFile(rutaFicheroBinarios).map(linea -> {
					String[] partes = linea.split("#"); 
					BinaryTree<Character> tree = BinaryTree.parse(partes[0], s -> s.charAt(0));
					Character ch = partes[1].charAt(0);			
					return Pair.of(tree, ch);
					}						
				).toList();
		
		List<Pair<Tree<Character>, Character>> datosNarios =
				Files2.streamFromFile(rutaFicheroNarios).map(linea -> {
					String[] partes = linea.split("#");
					Tree<Character> tree = Tree.parse(partes[0], s -> s.charAt(0));
					Character ch = partes[1].charAt(0);
					return Pair.of(tree, ch);
					}	
				).toList();
		
		
		// TEST
		System.out.println("* TEST EJERCICIO 3*");
		
		System.out.println("\n*Arboles Binarios*");
		for(Pair<BinaryTree<Character>, Character> par: datosBinarios) {
			BinaryTree<Character> tree = par.first();
			Character ch = par.second();
			System.out.println("Arbol: " + tree + "\tCaracter: "+ ch + 
					"\t[" + Ejercicio3.fEjercicio3Binary(tree, ch) + "]");
		}
		
		System.out.println("\n*Arboles n-arios*");
		for(Pair<Tree<Character>, Character> par: datosNarios) {
			Tree<Character> tree = par.first();
			Character ch = par.second();
			System.out.println("Arbol: " + tree + "\tCaracter: "+ ch + 
					"\t[" + Ejercicio3.fEjercicio3Nary(tree, ch) + "]");
		}	

	}
	
	
}
