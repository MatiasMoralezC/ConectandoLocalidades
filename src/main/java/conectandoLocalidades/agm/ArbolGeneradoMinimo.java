package conectandoLocalidades.agm;

import java.util.Arrays;

public class ArbolGeneradoMinimo {

	public static void prim(int[][] grafo) {
        int n = grafo.length;

        int[] padre = new int[n];
        int[] clave = new int[n];
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

        imprimirAGM(padre, grafo);
    }

	public static int minClave(int[] clave, boolean[] visitado) {
        int min = Integer.MAX_VALUE;
        int minIndice = -1;

        for (int i = 0; i < clave.length; i++) {
            if (!visitado[i] && clave[i] < min) {
                min = clave[i];
                minIndice = i;
            }
        }

        return minIndice;
    }

    //Muestra la distancia en km entre cada coordenada. Estaria bueno mostrarlo en la ventana.
    public static void imprimirAGM(int[] padre, int[][] grafo) {
        System.out.println("Arista \tPeso");
        for (int i = 1; i < grafo.length; i++) {
            System.out.println(padre[i] + " - " + i + "\t" + grafo[i][padre[i]]);
        }
    }
    
//    public static void imprimirAGM(int[] padre, ArrayList<MapMarkerDot> marcadores) {
//	    System.out.println("Arista \tDistancia");
//	    for (int i = 1; i < padre.length; i++) {
//	        GeoPosition pos1 = marcadores.get(padre[i]).getCoordinate();
//	        GeoPosition pos2 = marcadores.get(i).getCoordinate();
//	        double distancia = pos1.distance(pos2);
//	        System.out.println(padre[i] + " - " + i + "\t" + distancia);
//	    }
//	} BORRAR - NO NOS SIRVE
    
}
