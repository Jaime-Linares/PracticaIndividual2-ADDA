package ejemplos;

import java.util.List;
import java.util.function.Predicate;

import us.lsi.common.List2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejemplo5 {
	
	public static <E> List<Boolean> solucion_ejemplo5(Tree<E> tree, Predicate<E> pred) {
		return recursivo(tree, pred, 0, List2.empty());
	}

	private static <E> List<Boolean> recursivo(Tree<E> tree, Predicate<E> pred, Integer nivel, List<Boolean> res) {
		if(res.size() <= nivel) {
			res.add(true);
		}
		return switch (tree) {
			case TEmpty<E> t -> res;
			case TLeaf<E> t -> {
				Boolean cumple = pred.test(t.label());
				res.set(nivel, res.get(nivel) && cumple);
				yield res;
			}
			case TNary<E> t -> {
				Boolean cumple = pred.test(t.label());
				res.set(nivel, res.get(nivel) && cumple);
				// t.elements().forEach(tc -> recursivo(tc, pred, nivel+1, res));
				for(Tree<E> tc: t.elements()) {
					recursivo(tc, pred, nivel+1, res);
				}
				yield res;
			}
		};
	}

}
