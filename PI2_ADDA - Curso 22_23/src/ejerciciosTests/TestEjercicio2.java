package ejerciciosTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import ejercicios.Ejercicio2;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEjercicio2 {

	public static void main(String[] args) {
		System.out.println("* TEST EJERCICIO 2 *");

		// TEST (correcto funcionamiento de las funciones)
		System.out.println("\n*Analizamos correcto funcionamiento*");
		List<Integer> ls1 = List2.of(23,5,56,67,57,32,21,8,24,11,1,44,89,2);
		List<Integer> ls2 = List2.ofCollection(ls1);
		System.out.println("- Lista original: " + ls1);
		Ejercicio2.sort(ls1, 4);
		System.out.println("- Lista ordenada con umbral pequeño (4): " + ls1);
		Ejercicio2.sort(ls2, 25);
		System.out.println("- Lista ordenada con umbral grande (20): " + ls2);

		// TEST (analizamos tiempo de ejecucion con graficas)
		System.out.println("\n*Analizamos tiempo de ejecucion con graficas*\n");
		// generaFicheroListasEnteros("ficheros/2Lista1AlgoritmoSort.txt");
		// generaFicherosTiempoEjecucion();
		muestraGraficas();
	}


	// Parametros de generacion de las listas
	private static Integer sizeMin = 50; // tamano minimo de lista
	private static Integer sizeMax = 20000; // tamano maximo de lista
	private static Integer numListas = 40; // numero de de listas
	// para inicializarlo una sola vez y compartirlo con los metodos que lo requieran
	private static Random rr = new Random(System.nanoTime());

	// Parametros de medicion
	private static Integer numMediciones = 2; // numero de mediciones de tiempo de cada caso (numero de experimentos)
	private static Integer numIter = 5; // numero de iteraciones para cada medicion de tiempo
	private static Integer numIterWarmup = 100; // numero de iteraciones para warmup

	// Umbral fijo para analisis de complejidad
	private static Integer umbralFijo = 4;
		
	// Lista umbrales que vamos a tomar
	private static List<Integer> umbrales = List2.of(4, 25, 100, 500); 
	
	// Fichero entrada
	private static String ficheroListaEntrada = "ficheros/2Lista1AlgoritmoSort.txt";
	
	// Para el analisis de la complejidad con umbral fijo y listas de distinto tamano
	private static List<Trio<BiConsumer<List<Integer>, Integer>, TipoAjuste, String>> metodosComplejidadUmbralFijoTamVariable = 
			List.of(
				Trio.of(Ejercicio2::sort, TipoAjuste.NLOGN_0, "QuickSort(Complejidad)")	
			);
	
	// Para el analisis de la complejidad con distintos umbral 
	private static List<Trio<BiConsumer<List<Integer>, Integer>, Integer, String>> metodosUmbralVariable = 
			List.of(
				Trio.of(Ejercicio2::sort, umbrales.get(0), "QuickSort(umbral4)"),
				Trio.of(Ejercicio2::sort, umbrales.get(1), "QuickSort(umbral25)"),	
				Trio.of(Ejercicio2::sort, umbrales.get(2), "QuickSort(umbral100)"),	
				Trio.of(Ejercicio2::sort, umbrales.get(3), "QuickSort(umbral500)")	
			);
	
	
	// Genera fichero de listas de enteros con tamano variable
	public static void generaFicheroListasEnteros(String fichero) {
		Resultados.cleanFile(fichero);
		for(int i=0; i<numListas; i++) {
			int div = numListas<2? 1:(numListas-1);
			int tam = sizeMin + i*(sizeMax-sizeMin)/div;
			List<Integer> ls = generaListaEnteros(tam);
			String sls = ls.stream().map(x -> x.toString()).collect(Collectors.joining(","));
			Resultados.toFile(String.format("%d#%s", tam,sls), fichero, false);
		}
	}
	
	
	private static List<Integer> generaListaEnteros(Integer tamLista) {
		List<Integer> res = List2.of();
		for(int j=0; j<tamLista;j++) {
			res.add(0+rr.nextInt(1000000-0));
		}
		return res;
	}
		
	
	// Generando ficheros con tiempo de ejecucion
	private static void generaFicherosTiempoEjecucionComplejidad(
			List<Trio<BiConsumer<List<Integer>, Integer>, TipoAjuste, String>> metodos) {
		for (int i=0; i<metodos.size(); i++) { 
			String ficheroSalida = String.format("ficheros/2Tiempos1%s.csv",
					metodos.get(i).third());
			System.out.println(ficheroSalida);
			testTiemposEjecucionComplejidad(metodos.get(i).first(), ficheroSalida);
			}
	}

	private static void generaFicherosTiempoEjecucionUmbral(
			 List<Trio<BiConsumer<List<Integer>, Integer>, Integer, String>> metodos) {
		for (int i=0; i<metodos.size(); i++) { 
			String ficheroSalida = String.format("ficheros/2Tiempos1%s.csv",
					metodos.get(i).third());
			System.out.println(ficheroSalida);
			testTiemposEjecucionUmbral(metodos.get(i).first(), ficheroSalida, metodos.get(i).second());
		}
	}
	
	public static void generaFicherosTiempoEjecucion() {
		generaFicherosTiempoEjecucionComplejidad(metodosComplejidadUmbralFijoTamVariable);
		generaFicherosTiempoEjecucionUmbral(metodosUmbralVariable);
	}
	
	
	public static void testTiemposEjecucionComplejidad(BiConsumer<List<Integer>, Integer> funcion,
			String ficheroTiempo) {
		Map<Problema, Double> tiempos = new HashMap<>();
		Map<Integer, Double> tiemposMedios; // tiempos medios por tamano

		List<String> lineasListas = Files2.linesFromFile(ficheroListaEntrada);

		Integer nMed = numMediciones;
		for(int iter=0; iter<nMed; iter++) {
			for (int i=0; i<lineasListas.size(); i++) {
				String lineaLista = lineasListas.get(i);
				List<String> ls = List2.parse(lineaLista, "#", Function.identity());
				Integer tamLista = Integer.parseInt(ls.get(0));
				List<Integer> le = List2.parse(ls.get(1), ",", Integer::parseInt);

				Problema p = Problema.of(tamLista, i, tamLista);
				warmup(funcion, le, 4);

				Integer nIter = numIter;
				Long t0 = System.nanoTime();
				for (int z=0; z<nIter; z++) {
					funcion.accept(le, umbralFijo); // 4 
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);
			}
		}

		tiemposMedios = tiempos.entrySet().stream()
				.collect(Collectors.groupingBy(x -> x.getKey().tam(),
						Collectors.averagingDouble(x -> x.getValue())));

		Resultados.toFile(tiemposMedios.entrySet().stream()
				.map(x -> TResultD.of(x.getKey(), x.getValue()).toString()), 
				ficheroTiempo, true);
	}
	
	
	public static void testTiemposEjecucionUmbral(BiConsumer<List<Integer>, Integer> funcion, 
			String ficheroTiempos, Integer umbral) {
		Map<Problema, Double> tiempos = new HashMap<>();
		Map<Integer, Double> tiemposMedios;

		List<String> lineasListas = Files2.linesFromFile(ficheroListaEntrada);

		Integer nMed = numMediciones;

		for (int iter=0; iter<nMed; iter++) {
			for (int j=0; j<lineasListas.size(); j++) {
				String lineaLista = lineasListas.get(j);
				List<String> ls = List2.parse(lineaLista, "#", Function.identity());
				Integer tamLista = Integer.parseInt(ls.get(0));
				List<Integer> le = List2.parse(ls.get(1), ",", Integer::parseInt);

				Problema p = Problema.of(tamLista, j, tamLista);
				warmup(funcion, le, umbral);

				Integer nIter = numIter;
				Long t0 = System.nanoTime();
				for (int z=0; z<nIter; z++) {
					funcion.accept(le, umbral); 
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);
			}
		}

		
		tiemposMedios = tiempos.entrySet().stream()
				.collect((Collectors.groupingBy(x -> x.getKey().tam(),
						Collectors.averagingDouble(x -> x.getValue()))));
		
		Resultados.toFile(tiemposMedios.entrySet().stream()
				.map(x->TResultD.of(x.getKey(), x.getValue()).toString()),
			ficheroTiempos, true);
		
	}

	
	private static void warmup(BiConsumer<List<Integer>, Integer> func, List<Integer> ls, Integer  umbral) {
		for(int i=0; i<numIterWarmup; i++) {
			func.accept(ls, umbral);
		}
	}

	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
		if (!tiempos.containsKey(p)) {
			tiempos.put(p, d);
		} else if (tiempos.get(p) > d) {
			tiempos.put(p, d);
		}
	}

	
	// Generando graficas
	public static void muestraGraficas() {
		muestraGraficasMetodos(metodosComplejidadUmbralFijoTamVariable);
		muestraGraficasUmbral(metodosUmbralVariable);
		
	}
	
	public static void muestraGraficasMetodos(List<Trio<BiConsumer<List<Integer>, Integer>, 
			TipoAjuste, String>> metodosComplejidad) {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		for (int i=0; i<metodosComplejidadUmbralFijoTamVariable.size(); i++) {
			String ficheroSalida = String.format("ficheros/2Tiempos1%s.csv",
					metodosComplejidad.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodosComplejidad.get(i).third();
			System.out.println(label);

			TipoAjuste tipoAjuste = metodosComplejidad.get(i).second();
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);	

			// Obtener ajusteString para mostrarlo en grafica combinada
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste.fitCurve(
					DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));
		}
	}


	public static void muestraGraficasUmbral(List<Trio<BiConsumer<List<Integer>, Integer>, 
			Integer, String>> metodosUmbral) {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();

		for(int i=0; i<metodosUmbral.size(); i++) {
			String ficheroSalida = String.format("ficheros/2Tiempos1%s.csv", metodosUmbral.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodosUmbral.get(i).third();
			System.out.println(label);
			TipoAjuste tipoAjuste = TipoAjuste.NLOGN_0;
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);
			
			Pair<Function<Double, Double>, String> parCurva = GraficosAjuste.fitCurve(DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurva.second();
			labels.add(String.format("%s     %s", label, ajusteString));
		}

		GraficosAjuste.showCombined("Comparativa complejidad distintos umbrales", ficherosSalida, labels);
	}
	
	
	// Tipos (records) auxiliares
	record TResultD(Integer tam, Double t) {

		public static TResultD of(Integer tam, Double t){
			return new TResultD(tam, t);
		}

		public String toString() {
			return String.format("%d,%.0f", tam, t);
		}

	}

	record Problema(Integer tam, Integer numList, Integer numCase) {
		
		public static Problema of(Integer tam, Integer numList, Integer numCase){
			return new Problema(tam, numList, numCase);
		}

	}
}
