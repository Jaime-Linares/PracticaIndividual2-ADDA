package ejerciciosTests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ejercicios.Ejercicio1;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEjercicio1 {

	public static void main(String[] args) {
		 System.out.println("* TEST EJERCICIO 1 *");
		 
		 // TEST (correcto funcionamiento de las funciones)
		 System.out.println("\n*Analizamos correcto funcionamiento*");
		 System.out.println("Factorial de 5(Double, Iterativo): " + Ejercicio1.fEjercicio1DoubleIter(5));
		 System.out.println("Factorial de 5(Double, Recursivo): " + Ejercicio1.fEjercicio1DoubleRec(5));
		 System.out.println("Factorial de 5(BigInteger, Iterativo): " + Ejercicio1.fEjercicio1BigIntegerIter(5));
		 System.out.println("Factorial de 5(BigInteger, Recursivo): " + Ejercicio1.fEjercicio1BigIntegerRec(5));
		 
		 // TEST (analizamos tiempo de ejecucion con graficas)
		 System.out.println("\n*Analizamos tiempo de ejecucion con graficas*\n");
		 // generaFicherosTiempoEjecucion();
		 muestraGraficas();
	}
	
	
	// Parametros para analizar los tiempos de ejecucion
	private static Integer nMin = 25; // n minimo para el calculo del factorial
	private static Integer nMaxIter = 20000; // n maximo para el calculo del factorial en el caso iterativo
	private static Integer nMaxRec = 20000; // n maximo para el calculo del factorial en el caso recursivo
	private static Integer numSizes = 50; // numero de problemas (numero de factoriales distintas a calcular)
	private static Integer numMediciones = 10; // numero de mediciones de tiempo de cada caso (numero de experimentos)
	private static Integer numIter = 50; // numero de iteraciones para cada medicion de tiempo
	private static Integer numIterWarmup = 20000; // numero de iteraciones para warmup
	
	
	// Trios de metodos a probar con su tipo de ajuste y etiqueta para el nombre de los ficheros
	private static List<Trio<Function<Integer, Number>, TipoAjuste, String>> metodosFactorialDouble = 
			List.of(
				// Trio.of(Ejercicio1::fEjercicio1DoubleRec, TipoAjuste.POWERANB, "FactorialDoubleRecursiva1"),
				Trio.of(Ejercicio1::fEjercicio1DoubleRec,TipoAjuste.POWERANB, "FactorialDoubleRecursiva"),
				Trio.of(Ejercicio1::fEjercicio1DoubleIter, TipoAjuste.POWERANB, "FactorialDoubleIterativa")
			);
	
	private static List<Trio<Function<Integer, Number>, TipoAjuste, String>> metodosFactorialBigInteger = 
			List.of(
				Trio.of(Ejercicio1::fEjercicio1BigIntegerRec, TipoAjuste.POWERANB, "FactorialBigIntegerRecursiva"),
				Trio.of(Ejercicio1::fEjercicio1BigIntegerIter, TipoAjuste.POWERANB, "FactorialBigIntegerIterativa")
			);
	
	// Generando ficheros con tiempo de ejecucion
	private static <E> void generaFicherosTiempoEjecucionMetodos(List<Trio<Function<E, Number>, TipoAjuste, String>> metodos) {
		for (int i=0; i<metodos.size(); i++) { 
			int numMax = i==0 ? nMaxRec : nMaxIter; 
			Boolean flagExp = i==0 ? true : false;

			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodos.get(i).third());

			testTiemposEjecucion(nMin, numMax, 
					metodos.get(i).first(),
					ficheroSalida,
					flagExp);
		}
	}
	

	public static void generaFicherosTiempoEjecucion() {
		generaFicherosTiempoEjecucionMetodos(metodosFactorialDouble);
		generaFicherosTiempoEjecucionMetodos(metodosFactorialBigInteger);
	}
	
	
	@SuppressWarnings("unchecked")
	public static <E> void testTiemposEjecucion(Integer nMin, Integer nMax,
			Function<E, Number> funcionFactorial,
			String ficheroTiempos,
			Boolean flagExp) {
		Map<Problema, Double> tiempos = new HashMap<Problema,Double>();
		Integer nMed = flagExp ? 1 : numMediciones; 
		for (int iter=0; iter<nMed; iter++) {
			for (int i=0; i<numSizes; i++) {
				Double r = Double.valueOf(nMax-nMin)/(numSizes-1);
				Integer tam = (Integer.MAX_VALUE/nMax > i) 
						? nMin + i*(nMax-nMin)/(numSizes-1)
						: nMin + (int) (r*i) ;
				Problema p = Problema.of(tam);
				System.out.println(tam);
				warmup(funcionFactorial, 10);
				Integer nIter = flagExp ? numIter/(i+1) : numIter;
				Number[] res = new Number[nIter];
				Long t0 = System.nanoTime();
				for (int z=0; z<nIter; z++) {
					res[z] = funcionFactorial.apply((E) tam);
				}
				Long t1 = System.nanoTime();
				actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);
			}
			
		}
		
		Resultados.toFile(tiempos.entrySet().stream()
				.map(x->TResultD.of(x.getKey().tam(), 
									x.getValue()))
				.map(TResultD::toString),
			ficheroTiempos, true);
	}
		
	
	private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
		if (!tiempos.containsKey(p)) {
			tiempos.put(p, d);
		} else if (tiempos.get(p) > d) {
				tiempos.put(p, d);
		}
	}
	
	
	private static <E> BigInteger warmup(Function<E, Number> factorial, Integer n) {
		BigInteger res = BigInteger.ZERO;
		BigInteger z = BigInteger.ZERO; 
		for (int i=0; i<numIterWarmup; i++) {
			if (factorial.apply((E) n).equals(z)) z.add(BigInteger.ONE);
		}
		res = z.equals(BigInteger.ONE)? z.add(BigInteger.ONE):z;
		return res;
	}
	
	
	// Generando graficas
	public static <E> void muestraGraficasMetodos(List<Trio<Function<E, Number>, TipoAjuste, String>> metodos, 
			List<String> ficherosSalida, List<String> labels) {
		for (int i=0; i<metodos.size(); i++) { 
	
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodos.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodos.get(i).third();
			System.out.println(label);

			TipoAjuste tipoAjuste = metodos.get(i).second();
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);	
			
			// Obtener ajusteString para mostrarlo en grafica combinada
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste.fitCurve(
					DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));
		}
	}
	
	
	public static void muestraGraficas() {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		muestraGraficasMetodos(metodosFactorialDouble, ficherosSalida, labels);
		muestraGraficasMetodos(metodosFactorialBigInteger, ficherosSalida, labels);
		
		GraficosAjuste.showCombined("Calculo Factorial", ficherosSalida, labels);
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
	

	record Problema(Integer tam) {
		
		public static Problema of(Integer tam){
			return new Problema(tam);
		}
		
	}
	

}
