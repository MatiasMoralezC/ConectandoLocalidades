package conectandoLocalidades.agm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ArbolGeneradoMinimoTest {
	double[][] grafo;
	ArbolGeneradoMinimo agm;

	@Before
	public void inicializar() {
		double[][] grafo1 = { { 0, 4, 7, 6, 0, 0 }, { 4, 0, 0, 0, 0, 0 }, { 7, 0, 0, 1, 0, 8 }, { 6, 0, 1, 0, 10, 0 },
				{ 0, 0, 0, 10, 0, 12 }, { 0, 0, 8, 0, 12, 0 } };
		grafo = grafo1;
		agm = new ArbolGeneradoMinimo(grafo);
	}

	// --> calculoPrim
	@Test
	public void calculoPrimCorrecto() {
		int[] rtaCorrecta = { -1, 0, 3, 0, 3, 2 };
		assertArrayEquals(rtaCorrecta, agm.getPosicionesElegidos());
	}

	// --> asignarCaminoMin
	@Test
	public void caminoMinCorrecto() {
		List<Double> caminoMinCorrecto = new ArrayList<Double>();
		caminoMinCorrecto.add(4.0);
		caminoMinCorrecto.add(1.0);
		caminoMinCorrecto.add(6.0);
		caminoMinCorrecto.add(10.0);
		caminoMinCorrecto.add(8.0);

		assertEquals(caminoMinCorrecto, agm.getCaminoMin());
	}

	@Test(expected = Exception.class)
	public void asignarCaminoMinErrorDistancias() {
		double[][] grafo2 = { { -7, 0, -1 }, { 0, -4, 0 }, { -7, 0, 0 } };
		ArbolGeneradoMinimo agm2 = new ArbolGeneradoMinimo(grafo2);
		List<String> nombresPosibles = new ArrayList<>();
		nombresPosibles.add("A");
		nombresPosibles.add("C");
		nombresPosibles.add("B");
		agm2.obtenerCaminoCompleto(nombresPosibles);
	}

	@Test(expected = Exception.class)
	public void asignarCaminoMinErrorNombres() {
		double[][] grafo3 = { { -7, 0, -1 }, { 0, 4, 0 }, { 7, 0, 0 } };
		ArbolGeneradoMinimo agm3 = new ArbolGeneradoMinimo(grafo3);
		List<String> nombresPosibles = new ArrayList<>();
		nombresPosibles.add("A");
		nombresPosibles.add("C");
		agm3.obtenerCaminoCompleto(nombresPosibles);
	}

	@Test
	public void asignarCaminoMinCorrecto() {
		double[][] grafo4 = { { 0, 1, 0 }, { 1, 0, 2 }, { 0, 2, 0 } };
		ArbolGeneradoMinimo agm4 = new ArbolGeneradoMinimo(grafo4);
		List<String> nombresPosibles = new ArrayList<>();
		nombresPosibles.add("A");
		nombresPosibles.add("B");
		nombresPosibles.add("C");

		List<String> rtaCorrecta = new ArrayList<String>();
		rtaCorrecta.add("A -> B = 1.00km. costo $100.00");
		rtaCorrecta.add("B -> C = 2.00km. costo $200.00");
		assertEquals(rtaCorrecta, agm4.obtenerCaminoCompleto(nombresPosibles));
	}

	// --> dosDecimales
	@Test
	public void dosDecimalesIncorrecto() {
		assertFalse(agm.dosDecimales(1.9999999).equals(new BigDecimal(2)));
	}

	@Test
	public void dosDecimalesCorrecto() {
		assertFalse(agm.dosDecimales(1.9999999).equals(new BigDecimal(1.99)));
	}

}
