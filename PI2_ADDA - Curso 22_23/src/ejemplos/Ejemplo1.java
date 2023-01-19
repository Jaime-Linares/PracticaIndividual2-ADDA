package ejemplos;

public class Ejemplo1 {
	/*
	PI2 - Ejemplo 1

	Implementar de forma recursiva e iterativa un algoritmo para el calculo de la potencia a^n, 
	siendo a de tipo Double y n de tipo Integer. Analizar sus tiempos de ejecucion.
	*/
	
	// 1) Potencia recursiva (básica) -> lineal 
	public static Double potenciaR(Double a, Integer n) {
		Double res;
        if (n==0){ 
        	res = 1.;
        } 
        else{ 
        	res= a * potenciaR(a, n-1); 
        }
        return res;
	}
	
	// 2) Potencia iterativa (básica) -> lineal
	public static Double potenciaIter(Double a, Integer n) {
        Double res = 1.;
 
        for (int i = 0; i < n; i++) {
            res = res * a;
        }
 
        return res;
	}
	
	// 3) Potencia rec (eficiente) -> logaritmico  (repositorio)
	// 4) Potencia iterativa (eficiente) -> logaritmico (repositorio)
	
}
