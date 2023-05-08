package conectandoLocalidades.costos;

public class CostosConexion {

	private double costoPorKm;
	private double porcentajeDeAumento;
	private double costoFijo;

	public CostosConexion() {
		setCostoPorKm();
		setPorcentajeDeAumento();
		setCostoFijo();
	}

	private void setCostoPorKm() {
		this.costoPorKm = 100;
	}

	private void setCostoFijo() {
		this.costoFijo = 350;
	}

	private void setPorcentajeDeAumento() {
		this.porcentajeDeAumento = 0.2;
	}

	public double getCostoPorKm() {
		return costoPorKm;
	}

	public double getPorcentajeDeAumento() {
		return porcentajeDeAumento;
	}

	public double getCostoFijo() {
		return costoFijo;
	}

}
