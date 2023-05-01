package conectandoLocalidades.main;

import conectandoLocalidades.agm.ArbolGeneradoMinimo;

public class Main {

	public static void main(String[] args) {
		int[][] grafo = { { 0, 2, 0, 6, 0 },
                { 2, 0, 3, 8, 5 },
                { 0, 3, 0, 0, 7 },
                { 6, 8, 0, 0, 9 },
                { 0, 5, 7, 9, 0 } };

		
		ArbolGeneradoMinimo agm = new ArbolGeneradoMinimo();
		
		agm.prim(grafo);

	}

}
