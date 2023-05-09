package conectandoLocalidades.agm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import conectandoLocalidades.costos.CostosConexion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArbolGeneradoMinimo {
	private CostosConexion costosConexion = new CostosConexion();

	private static double[][] grafo;
	private static int[] posicionesElegidos;
	private static List<Double> caminoMin;

	public ArbolGeneradoMinimo(double[][] grafo) {
		this.grafo = grafo;
		prim();
	}

	public static void prim() {
		int n = grafo.length;

		int[] padre = new int[n];
		double[] clave = new double[n];
		boolean[] visitado = new boolean[n];

		Arrays.fill(clave, Integer.MAX_VALUE);
		Arrays.fill(visitado, false);

		clave[0] = 0;
		padre[0] = -1;

		for (int i = 0; i < n - 1; i++) {
			int u = minClave(clave, visitado);
			visitado[u] = true;

			for (int v = 0; v < n; v++) {
				if (grafo[u][v] != 0 && !visitado[v] && grafo[u][v] < clave[v]) {
					padre[v] = u;
					clave[v] = grafo[u][v];
				}
			}
		}

		posicionesElegidos = padre;
		asignarCaminoMin();
	}

	public static int minClave(double[] clave, boolean[] visitado) {
		double min = Integer.MAX_VALUE;
		int minIndice = 0;

		for (int i = 0; i < clave.length; i++) {
			if (!visitado[i] && clave[i] < min) {
				min = clave[i];
				minIndice = i;
			}
		}

		return minIndice;
	}

	public static void asignarCaminoMin() {
		caminoMin = new ArrayList<Double>();
		for (int i = 1; i < grafo.length; i++) {
			caminoMin.add(grafo[i][posicionesElegidos[i]]);
		}
	}

	public List<String> obtenerCaminoCompleto(List<String> nombres) {
		if (nombres.size() != grafo.length) {
			throw new RuntimeException("el nombre ingresado no es del mismo tamanio que el grafo");
		}
		List<String> camino = new ArrayList<>();
		double distancia;
		for (int i = 1; i < grafo.length; i++) {
			distancia = grafo[i][posicionesElegidos[i]];
			camino.add(nombres.get(posicionesElegidos[i]) + " -> " + nombres.get(i) + " = " + dosDecimales(distancia)
					+ "km. costo $" + dosDecimales(costosConexion.calcularCosto(distancia)));
		}

		return camino;
	}

	public BigDecimal dosDecimales(double num) {
		// me quedo con 2 decimales
		BigDecimal bd;

		bd = new BigDecimal(num);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;
	}

	public List<Double> getCaminoMin() {
		return caminoMin;
	}

	public int[] getPosicionesElegidos() {
		return posicionesElegidos;
	}

}
