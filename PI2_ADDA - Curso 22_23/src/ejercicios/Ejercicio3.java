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

public class Ejercicio3 {
	
	// Solucion para arboles binarios
	public static List<String> fEjercicio3Binary(BinaryTree<Character> tree, Character e) {
		return fEjercicio3Binary(tree, e, "", List2.of());
	}

	private static List<String> fEjercicio3Binary(BinaryTree<Character> tree, Character e, String camino, List<String> ac) {
		return switch (tree) {
		case BEmpty<Character> t -> ac;
		case BLeaf<Character> t -> {
			String posibleCamino = camino+t.label();
			if(!(t.label().equals(e))) {
				ac.add(posibleCamino);
			}	
			yield ac;
		}
		case BTree<Character> t -> {
			String posibleCamino = camino+t.label();
			if(!(t.label().equals(e))) {
				fEjercicio3Binary(t.left(), e, posibleCamino, ac);
				fEjercicio3Binary(t.right(), e, posibleCamino, ac);
			}
			yield ac;
		}
		};
	}
	
	
	// Solucion para arboles n-arios
	public static List<String> fEjercicio3Nary(Tree<Character> tree, Character e) {
		return fEjercicio3Nary(tree, e, "", List2.of());
	}

	private static List<String> fEjercicio3Nary(Tree<Character> tree, Character e, String camino, List<String> ac) {
		return switch (tree) {
		case TEmpty<Character> t -> ac;
		case TLeaf<Character> t -> {
			String posibleCamino = camino+t.label();
			if(!(t.label().equals(e))) {
				ac.add(posibleCamino);
			}
			yield ac;
		}
		case TNary<Character> t -> {
			String posibleCamino = camino+t.label();
			if(!(t.label().equals(e))) {
				for(Tree<Character> tr: t.elements()) {
					fEjercicio3Nary(tr, e, posibleCamino, ac);
				}
			}
			yield ac;
		}
		};
	}
	
	
}
