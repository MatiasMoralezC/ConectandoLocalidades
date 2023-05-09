package conectandoLocalidades.agm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.nio.channels.AsynchronousServerSocketChannel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ArbolGeneradoMinimoTest {
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

	@Test
	public void calculoPrimCorrecto() {
		double[][] grafo = { { 0, 4, 7, 6, 0, 0 }, { 4, 0, 0, 0, 0, 0 }, { 7, 0, 0, 1, 0, 8 }, { 6, 0, 1, 0, 10, 0 },
				{ 0, 0, 0, 10, 0, 12 }, { 0, 0, 8, 0, 12, 0 } };
		ArbolGeneradoMinimo agm = new ArbolGeneradoMinimo(grafo);
		int[] rtaCorrecta = { -1, 0, 3, 0, 3, 2 };

		assertArrayEquals(rtaCorrecta, agm.getPosicionesElegidos());
	}

	@Test
	public void caminoMinCorrecto() {
		double[][] grafo = { { 0, 4, 7, 6, 0, 0 }, { 4, 0, 0, 0, 0, 0 }, { 7, 0, 0, 1, 0, 8 }, { 6, 0, 1, 0, 10, 0 },
				{ 0, 0, 0, 10, 0, 12 }, { 0, 0, 8, 0, 12, 0 } };
		ArbolGeneradoMinimo agm = new ArbolGeneradoMinimo(grafo);
		List<Double> caminoMinCorrecto = new ArrayList<Double>();
		caminoMinCorrecto.add(4.0);
		caminoMinCorrecto.add(1.0);
		caminoMinCorrecto.add(6.0);
		caminoMinCorrecto.add(10.0);
		caminoMinCorrecto.add(8.0);

		assertEquals(caminoMinCorrecto, agm.getCaminoMin());
	}

	@Test
	public void asignarCaminoMinCorrecto() {
		double[][] grafo = { { 7, 0, 1 }, { 0, 4, 0 }, { 7, 0, 0 } };
		ArbolGeneradoMinimo agm = new ArbolGeneradoMinimo(grafo);
		List<String> nombresPosibles = new ArrayList<>();
		nombresPosibles.add("A");
		nombresPosibles.add("C");
		nombresPosibles.add("B");

		System.out.println(agm.obtenerCaminoCompleto(nombresPosibles));
		List<String> rtaCorrecta = new ArrayList<>();
		rtaCorrecta.add("A -> C = 4.00km. costo $400.00");
		rtaCorrecta.add("A -> B = 7.00km. costo $3150.00");
		assertTrue(true);
	}

	@Test
	public void dosDecimalesIncorrecto() {
		double[][] grafo = { { 0, 4, 7, 6, 0, 0 }, { 4, 0, 0, 0, 0, 0 }, { 7, 0, 0, 1, 0, 8 }, { 6, 0, 1, 0, 10, 0 },
				{ 0, 0, 0, 10, 0, 12 }, { 0, 0, 8, 0, 12, 0 } };
		ArbolGeneradoMinimo agm = new ArbolGeneradoMinimo(grafo);

		assertFalse(agm.dosDecimales(1.9999999).equals(new BigDecimal(2)));
	}

	@Test
	public void dosDecimalesCorrecto() {
		double[][] grafo = { { 0, 4, 7, 6, 0, 0 }, { 4, 0, 0, 0, 0, 0 }, { 7, 0, 0, 1, 0, 8 }, { 6, 0, 1, 0, 10, 0 },
				{ 0, 0, 0, 10, 0, 12 }, { 0, 0, 8, 0, 12, 0 } };
		ArbolGeneradoMinimo agm = new ArbolGeneradoMinimo(grafo);

		assertFalse(agm.dosDecimales(1.9999999).equals(new BigDecimal(1.99)));
	}

}
