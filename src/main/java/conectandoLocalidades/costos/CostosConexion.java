package conectandoLocalidades.costos;

public class CostosConexion {

	private double costoPorKm;
	private double porcentajeDeAumento; //  si la conexion tiene mas de 300 km
	private double costoFijo; // se agrega si la conexion involucra localidades de dos provincias distintas
	private int limiteLocalidad; // km de cada localidad
	private int limiteProvincia; // cant de localidades hasta pasar de provincia

	public CostosConexion() {
		this.costoPorKm = 100;
		this.porcentajeDeAumento = 0.2;
		this.costoFijo = 350;
		// el 50 y el 10, que aparecen luego, son solo para tener una idea
		limiteLocalidad = (int)(Math.random()*50+1);
		limiteProvincia = (int)(Math.random()*10+1);
		
	}
	
	public double calcularCosto(double distancia) {
		if(distancia==0) {
			throw new RuntimeException("la distancia ingresada no es valida");
		}
		
		double costo = distancia * costoPorKm;
		
		if( distancia > 300 ) {
			costo += costo * porcentajeDeAumento;
		}
		
		if( (distancia%limiteLocalidad) > limiteProvincia ) {
			costo += costoFijo * (distancia%limiteLocalidad);
		}
		
		return costo;
	}

}
