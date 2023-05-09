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

import javax.management.RuntimeErrorException;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class Mapa {

	private JFrame frame;
	private JPanel panelMapa;
	private JPanel panelControles;
	private JMapViewer miMapa;
	private ArrayList<Coordinate> coordenadas;
	private JButton btnEliminar;
	private MapPolygonImpl poligono;
	private JButton btnDibujarPolgono;
	
	private List<String> nombres;
	private List<Double> distancias;
	private boolean yaDibujado;
	
	private JLabel lblNewLabel;
	private JLabel lblElem;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	
	private JLabel lblDistancia;
	private JLabel lblDistancia_2;
	private JLabel lblDistancia_3;
	private JLabel lblDistancia_4;
	
	private ArbolGeneradoMinimo agm;
	private int maximoNombres = 5;

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
		
		lblNewLabel = new JLabel("-1");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 108, 100, 23);
		panelControles.add(lblNewLabel);
		
		lblElem = new JLabel("-2");
		lblElem.setHorizontalAlignment(SwingConstants.CENTER);
		lblElem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElem.setBounds(10, 142, 100, 23);
		panelControles.add(lblElem);
		
		lblNewLabel_2 = new JLabel("-3");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 176, 100, 23);
		panelControles.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("-4");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(10, 210, 100, 23);
		panelControles.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("-5");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(10, 244, 100, 23);
		panelControles.add(lblNewLabel_4);
		
		lblDistancia = new JLabel("dist1");
		lblDistancia.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistancia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDistancia.setBounds(85, 299, 120, 23);
		panelControles.add(lblDistancia);
		
		lblDistancia_2 = new JLabel("dist2");
		lblDistancia_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistancia_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDistancia_2.setBounds(85, 333, 120, 23);
		panelControles.add(lblDistancia_2);
		
		lblDistancia_3 = new JLabel("dist3");
		lblDistancia_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistancia_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDistancia_3.setBounds(85, 367, 120, 23);
		panelControles.add(lblDistancia_3);
		
		lblDistancia_4 = new JLabel("dist4");
		lblDistancia_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistancia_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDistancia_4.setBounds(85, 401, 120, 23);
		panelControles.add(lblDistancia_4);

		panelMapa.add(miMapa);
		
		limpiarDistancias();
		
		detectarCoordenadas();
		dibujarPoligono();
		eliminarPoligono();
	}

	private void detectarCoordenadas() {
		coordenadas = new ArrayList<Coordinate>();
		nombres = new ArrayList<String>();

		miMapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((e.getButton() == MouseEvent.BUTTON1) && (nombres.size() < maximoNombres)) {
					Coordinate markeradd = (Coordinate) miMapa.getPosition(e.getPoint());
					coordenadas.add(markeradd);
					System.out.println(markeradd);
					String nombre = JOptionPane.showInputDialog("Nombre: ");
					miMapa.addMapMarker(new MapMarkerDot(nombre, markeradd));
					
					System.out.println("nuevo nombre en la granja: "+ nombre);
					nombres.add(nombre);
					yaDibujado = true;
					System.out.println("ya dibujado:: " + yaDibujado);
					mostrarNombre();
				}
			}
		});
	}
	
	public void mostrarNombre() {
		switch (nombres.size()) {
		  case 1:
				lblNewLabel.setText(nombres.get(nombres.size() -1 ));
		    break;
		  case 2:
				lblElem.setText(nombres.get(nombres.size() -1 ));
		    break;
		  case 3:
				lblNewLabel_2.setText(nombres.get(nombres.size() -1 ));
		    break;
		  case 4:
				lblNewLabel_3.setText(nombres.get(nombres.size() -1 ));
		    break;
		  case 5:
				lblNewLabel_4.setText(nombres.get(nombres.size() -1 ));
		    break;
		}
	}

	private void dibujarPoligono() {
		btnDibujarPolgono = new JButton("Dibujar Pol\u00EDgono");
		btnDibujarPolgono.setBounds(10, 11, 195, 23);
		btnDibujarPolgono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agm = new ArbolGeneradoMinimo( obtenerCoordenadas() );
				mostrarDistancias();
				
				if ((coordenadas.size() == 0) || (coordenadas.size() == 1)) {
					throw new RuntimeException("No se puede dibujar el poligono. No tienes suficientes coordenadas");
				} else if(coordenadas.size() == 2) {
					poligono = new MapPolygonImpl( new ArrayList<Coordinate>(Arrays.asList(coordenadas.get(0), coordenadas.get(1), coordenadas.get(1)) ));
				} else {
					poligono = new MapPolygonImpl(coordenadas);
				}
				miMapa.addMapPolygon(poligono);
				yaDibujado = false;
			}
		});
	}
	
	public void mostrarDistancias() {
		List<String> distanciasMin = agm.obtenerCaminoCompleto(nombres);
		int tamanioDistancias = distanciasMin.size();
		for (int i = 0; i < tamanioDistancias; i++) {
			switch (i) {
			  case 0:
					lblDistancia.setText( distanciasMin.get(i) +"km");
			    break;
			  case 1:
					lblDistancia_2.setText( distanciasMin.get(i) +"km");
			    break;
			  case 2:
					lblDistancia_3.setText( distanciasMin.get(i) +"km");
			    break;
			  case 3:
					lblDistancia_4.setText( distanciasMin.get(i) +"km");
			    break;
			}
		}
	}
	

	private void eliminarPoligono() {
		btnEliminar = new JButton("Eliminar Polgono");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miMapa.removeMapPolygon(poligono);
				yaDibujado = true;
				limpiarDistancias();
			}
		});
		btnEliminar.setBounds(10, 64, 195, 23);
		panelControles.add(btnEliminar);
		panelControles.add(btnDibujarPolgono);
		
	}
	
	public void limpiarDistancias() {
		lblDistancia.setText( null );
		lblDistancia_2.setText( null );
		lblDistancia_3.setText( null );
		lblDistancia_4.setText( null );
	}

	private double[][] obtenerCoordenadas() {
		int cantCoordenadas = coordenadas.size();
		double[][] grafo = new double[cantCoordenadas][cantCoordenadas];
		distancias = new ArrayList<Double>();

		for (int i = 0; i < cantCoordenadas; i++) {
			for (int j = 0; j < cantCoordenadas; j++) {
				System.out.println("i: "+i+" --  j: "+j);
				if (i == j) {
					grafo[i][j] = 0; // La distancia de un nodo a si mismo es 0
				} else {
					// Lat y Long de cada coordenada
					double lat1 = coordenadas.get(i).getLat();
					double lng1 = coordenadas.get(i).getLon();
					double lat2 = coordenadas.get(j).getLat();
					double lng2 = coordenadas.get(j).getLon();
					double distancia = distanciaCoord(lat1, lng1, lat2, lng2); // Harvesine
					grafo[i][j] = distancia; // Parseo a entero y lo agrego al grafo
					// -------------------------------------------------------------------------- esto se hace x2 ojo
					System.out.println(":::::::::: "+nombres.get(i)+" :: "+nombres.get(j)+" ::::::::::");
					System.out.println("distancia::::: ");
					System.out.println(distancia); // BORRAR
					distancias.add(distancia);
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
