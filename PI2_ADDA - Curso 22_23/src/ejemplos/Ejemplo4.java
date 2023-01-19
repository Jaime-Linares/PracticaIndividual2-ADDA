package ejemplos;

import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejemplo4 {
	
	public static Boolean solucion_ejemplo4(BinaryTree<Character> tree, List<Character> chars) {
		return recursivo(tree, chars, 0);    // 0 es el indice del elemento por el q voy
	}

	private static Boolean recursivo(BinaryTree<Character> tree, List<Character> chars, Integer i) {
		Integer n = chars.size();
		return switch (tree) {
			case BEmpty<Character> t -> false;
			case BLeaf<Character> t -> n-i == 1 && chars.get(i).equals(t.label()); 
			case BTree<Character> t -> n-i > 1 && chars.get(i).equals(t.label()) 
				&& (recursivo(t.left(), chars, i+1) || recursivo(t.right(), chars, i+1));
		};
	}
	
	
	// HECHO POR MI: En caso de que fuese n-ario
	public static Boolean solNario(Tree<Character> tree, List<Character> chars) {
		return aux(tree, chars, 0);
	}

	private static Boolean aux(Tree<Character> tree, List<Character> chars, Integer i) {
		Integer n = chars.size();
		return switch(tree) {
		case TEmpty<Character> t -> false;
		case TLeaf<Character> t -> n-i==1 && t.label().equals(chars.get(i));
		case TNary<Character> t -> {
			Boolean a = n-i>1 && t.label().equals(chars.get(i));
			Boolean b = false;
			for(Tree<Character> tr: t.elements()) {
				b = b || aux(tr,chars,i+1);
			}
//			Boolean b = t.elements().stream()
//					.map(x -> aux(x, chars, i+1))
//					.anyMatch(x -> x==true);
			yield a && b;	
		}
		};
	}
	

}
