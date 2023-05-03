package conectandoLocalidades.mapa;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Mapa 
{

	private JFrame frame;
	private JPanel panelMapa;
	private JPanel panelControles;
	private JMapViewer miMapa;
	private ArrayList<Coordinate> coordenadas;
	private JButton btnEliminar;
	private MapPolygonImpl poligono;
	private JButton btnDibujarPolgono ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
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
	public Mapa() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
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
	
	private void detectarCoordenadas() 
	{
		coordenadas = new ArrayList<Coordinate>();
				
		miMapa.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				Coordinate markeradd = (Coordinate)
				miMapa.getPosition(e.getPoint());
				coordenadas.add(markeradd);
				String nombre = JOptionPane.showInputDialog("Nombre: ");
				miMapa.addMapMarker(new MapMarkerDot(nombre, markeradd));
			}}
		});
	}

	private void dibujarPoligono()
	{
		btnDibujarPolgono = new JButton("Dibujar Pol\u00EDgono");
		btnDibujarPolgono.setBounds(10, 11, 195, 23);
		btnDibujarPolgono.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				poligono = new MapPolygonImpl(coordenadas);
				miMapa.addMapPolygon(poligono);
			}
		});
	}

	private void eliminarPoligono() 
	{
		btnEliminar = new JButton("Eliminar Polgono");
		btnEliminar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				 miMapa.removeMapPolygon(poligono);
			}
		});
		btnEliminar.setBounds(10, 64, 195, 23);
		panelControles.add(btnEliminar);
		panelControles.add(btnDibujarPolgono);
	}	
}