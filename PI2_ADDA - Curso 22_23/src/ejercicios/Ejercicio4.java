package ejercicios;

import java.util.List;

import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio4 {
	
	// Solucion para arboles binarios
	public static Boolean fEjercicio4Binary(BinaryTree<String> tree) {
		return fEjercicio4BinaryAux(tree, TuplaEj4.of(0, true)).b();
	}
	
	private static TuplaEj4 fEjercicio4BinaryAux(BinaryTree<String> tree, TuplaEj4 ac) {
		return switch (tree) {
		case BEmpty<String> t -> ac;
		case BLeaf<String> t -> TuplaEj4.of(ac.a()+cuentaVocales(t.label()), ac.b());
		case BTree<String> t -> {
			TuplaEj4 resLeft = fEjercicio4BinaryAux(t.left(), TuplaEj4.of(ac.a(), ac.b()));
			TuplaEj4 resRight = fEjercicio4BinaryAux(t.right(), TuplaEj4.of(ac.a(), ac.b()));
			yield TuplaEj4.of(cuentaVocales(t.label())+resLeft.a()+resRight.a(), 
					(resLeft.a()==resRight.a()) && resLeft.b() && resRight.b());
		}
		};
	}
	
	
	// Solucion para arboles n-arios
	public static Boolean fEjercicio4Nary(Tree<String> tree) {
		return fEjercicio4NaryAux(tree, TuplaEj4.of(0, true)).b();
	}

	private static TuplaEj4 fEjercicio4NaryAux(Tree<String> tree, TuplaEj4 ac) {
		return switch (tree) {
		case TEmpty<String> t -> ac;
		case TLeaf<String> t -> TuplaEj4.of(ac.a()+cuentaVocales(t.label()), ac.b());
		case TNary<String> t -> {
			List<TuplaEj4> res = List2.of();
			for(int i=0; i<t.elements().size(); i++) {
				TuplaEj4 resArbol = fEjercicio4NaryAux(t.elements().get(i), TuplaEj4.of(ac.a(), ac.b()));
				res.add(resArbol);
			}
			yield TuplaEj4.of(cuentaVocales(t.label())+res.stream().mapToInt(x -> x.a()).sum(),
					(res.stream().mapToInt(x -> x.a()).distinct().count()==1) && 
					res.stream().map(x -> x.b()).allMatch(x -> x==true));
		}
		};
	}
	
	
	// Funcion auxiliar que se encarga de contar las vocales de una cadena
	private static Integer cuentaVocales(String cadena) {
		Integer res = 0;
		for(int i=0; i<cadena.length(); i++) {
			if(cadena.charAt(i)=='a' || cadena.charAt(i)=='e' || cadena.charAt(i)=='i' || 
					cadena.charAt(i)== 'o' || cadena.charAt(i)=='u') {
				res++;
			}
		}
		return res;
	}
	
	
	// Tupla que hemos usado para que se vayan actualizando los valores
	private static record TuplaEj4(Integer a, Boolean b) {
		
		public static TuplaEj4 of(Integer a, Boolean b) {
			return new TuplaEj4(a, b);
		}
		
	}
		
	
}
