package conectandoLocalidades.mapa;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import conectandoLocalidades.agm.ArbolGeneradoMinimo;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Mapa {

	private JFrame frame;
	private JPanel panelMapa;
	private JPanel panelControles;
	private JMapViewer miMapa;
	private ArrayList<Coordinate> coordenadas;
	private JButton btnEliminar;
	private MapPolygonImpl poligono;
	private JButton btnDibujarPolgono;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mapa window = new Mapa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mapa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 725, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panelMapa = new JPanel();
		panelMapa.setBounds(10, 11, 437, 446);
		frame.getContentPane().add(panelMapa);

		panelControles = new JPanel();
		panelControles.setBounds(457, 11, 242, 446);
		frame.getContentPane().add(panelControles);
		panelControles.setLayout(null);

		miMapa = new JMapViewer();
		miMapa.setDisplayPosition(new Coordinate(-34.521, -58.7008), 15);

		panelMapa.add(miMapa);

		detectarCoordenadas();
		dibujarPoligono();
		eliminarPoligono();
	}

	private void detectarCoordenadas() {
		coordenadas = new ArrayList<Coordinate>();

		miMapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Coordinate markeradd = (Coordinate) miMapa.getPosition(e.getPoint());
					coordenadas.add(markeradd);
					System.out.println(markeradd);
					String nombre = JOptionPane.showInputDialog("Nombre: ");
					miMapa.addMapMarker(new MapMarkerDot(nombre, markeradd));
				}
			}
		});
	}

	private void dibujarPoligono() {
		btnDibujarPolgono = new JButton("Dibujar Pol\u00EDgono");
		btnDibujarPolgono.setBounds(10, 11, 195, 23);
		btnDibujarPolgono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArbolGeneradoMinimo.prim(obtenerCoordenadas());
				poligono = new MapPolygonImpl(coordenadas);
				miMapa.addMapPolygon(poligono);
			}
		});
	}

	private void eliminarPoligono() {
		btnEliminar = new JButton("Eliminar Polgono");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miMapa.removeMapPolygon(poligono);
			}
		});
		btnEliminar.setBounds(10, 64, 195, 23);
		panelControles.add(btnEliminar);
		panelControles.add(btnDibujarPolgono);
	}

	private int[][] obtenerCoordenadas() {
		int n = coordenadas.size();
		int[][] grafo = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					grafo[i][j] = 0; // La distancia de un nodo a si mismo es 0
				} else {
					// Lat y Long de cada coordenada
					double lat1 = coordenadas.get(i).getLat();
					double lng1 = coordenadas.get(i).getLon();
					double lat2 = coordenadas.get(j).getLat();
					double lng2 = coordenadas.get(j).getLon();
					double distancia = distanciaCoord(lat1, lng1, lat2, lng2); // Harvesine
					grafo[i][j] = (int) distancia; // Parseo a entero y lo agrego al grafo
					System.out.println(distancia); // BORRAR
				}
			}
		}
		return grafo;
	}

	// Fórmula de Haversine se utiliza para calcular la distancia entre dos puntos
	// en la superficie de la Tierra, y se basa en la latitud y longitud de los
	// puntos.
	public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
		double radioTierra = 6371; // Km

		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distancia = radioTierra * c; // Distancia en km

		return distancia;
	}
}
