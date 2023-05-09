package conectandoLocalidades.agm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArbolGeneradoMinimo {
	private static double[][] grafo;
	private static int[] posicionesElegidos;
	private static List<Double> caminoMin;

	public ArbolGeneradoMinimo(double[][] grafo) {
		this.grafo = grafo;
		prim();
	}

	public static double[][] prim() {
		imprimirGrafo();
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
        
        imprimirGrafo();
        imprimirAGM();
        asignarCaminoMin();

        return grafo;
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
            caminoMin.add( grafo[i][posicionesElegidos[i]]  );
        }
    }
    
    public List<String> obtenerCaminoCompleto(List<String> nombres) {
    	List<String> camino = new ArrayList<>();
    	BigDecimal bd;
        for (int i = 1; i < grafo.length; i++) {
        	// me quedo con 2 decimales
        	bd = new BigDecimal(grafo[i][posicionesElegidos[i]]);
        	bd = bd.setScale(2, RoundingMode.HALF_UP);
        	
            camino.add(nombres.get(posicionesElegidos[i]) + " -> " + nombres.get(i) + " = " + bd);
        }

    	return camino;
    }
    
    //Muestra la distancia en km entre cada coordenada. Estaria bueno mostrarlo en la ventana.
    public static void imprimirAGM() {
        System.out.println("Arista \tPeso");
        for (int i = 1; i < grafo.length; i++) {
            System.out.println(posicionesElegidos[i] + " - " + i + "\t" + grafo[i][posicionesElegidos[i]]);
        }
    }
    
    public static void imprimirGrafo() {
        System.out.println(":::::::::::::::::::::::::::\nGrafo:::\n");
        for (int i = 0; i < grafo.length; i++) {
        	for (int j = 0; j < grafo[i].length; j++) {
				System.out.print(grafo[i][j] + " - ");
			}
        	System.out.println();
        }
        System.out.println(":::::::::::::::::::::::::::");
    }

	public List<Double> getCaminoMin() {
		return caminoMin;
	}

	public int[] getPosicionesElegidos() {
		return posicionesElegidos;
	}
	
	

}
