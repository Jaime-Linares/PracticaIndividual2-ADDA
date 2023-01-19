package tests;

import java.util.ArrayList;
import java.util.List;

import ejemplos.Ejemplo4;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestEjemplo4 {

	public static void main(String[] args) {
		
		String file = "ficheros/Ejemplo4DatosEntrada.txt";
		
		List<Pair<BinaryTree<Character>, List<Character>>> inputs = 
				Files2.streamFromFile(file).map(linea -> {
					String[] aux = linea.split("#");
					BinaryTree<Character> a = BinaryTree.parse(aux[0], s-> s.charAt(0));  
					List<Character> l = stringListToCharList(aux[1]);
					return Pair.of(a, l);
					}
				).toList();
		
		System.out.println("*TEST EJEMPLO 4*");
		
		for(Pair<BinaryTree<Character>, List<Character>> par: inputs) {
			BinaryTree<Character> tree = par.first();
			List<Character> chars = par.second();
			
			System.out.println("Arbol: " + tree +
					"\tSecuencia: "+ chars + "\t[" + 
					Ejemplo4.solucion_ejemplo4(tree, chars) + "]");
		}
		
		// test pa lo q me he inventado
		System.out.println("\n *PRUEBA MIA:* ");
		Tree<Character> tr = Tree.parse("A(B(C),C)", x -> x.charAt(0));
		System.out.println("Cumple lo d la lista?: " + Ejemplo4.solNario(tr, List2.of('A','B','C')));
	}

	private static List<Character> stringListToCharList(String s) {
		String letras = s.replace(",", "").replace("[", "").replace("]", "");
		List<Character> res = new ArrayList<>(letras.length());
		for(int i=0; i<letras.length(); i++) {
			res.add(letras.charAt(i));
		}
		return res;
	}
	
	

}
