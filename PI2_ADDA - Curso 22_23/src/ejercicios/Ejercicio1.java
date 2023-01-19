package ejercicios;

import java.math.BigInteger;

public class Ejercicio1 {
	
	// Factorial: Double - Iterativo
	public static Double fEjercicio1DoubleIter(Integer n) {
		Double res = 1.;
		while(n>0) {
			res = res * n;
			n -= 1;
		}
		return res;
	}
	
	
	// Factorial: Double - Recursivo
	public static Double fEjercicio1DoubleRec(Integer n) {
		Double res;
		if(n==0) {
			res = 1.;
		} else {
			res = n * fEjercicio1DoubleIter(n-1);
		}
		return res;
	}
	
	
	// Factorial: BigInteger - Iterativo
	public static BigInteger fEjercicio1BigIntegerIter(Integer n) {
		BigInteger res = BigInteger.ONE;
		while(n>0) {
			res = res.multiply(BigInteger.valueOf(n));
			n -= 1;
		}
		return res;
	}
	
	
	// Factorial: BigInteger - Recursivo
	public static BigInteger fEjercicio1BigIntegerRec(Integer n) {
		BigInteger res;
		if(n==0) {
			res = BigInteger.ONE;
		} else {
			res = BigInteger.valueOf(n).multiply(fEjercicio1BigIntegerRec(n-1));
		}
		return res;
	}
	

}
