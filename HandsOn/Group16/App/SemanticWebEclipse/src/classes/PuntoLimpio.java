package classes;

public class PuntoLimpio {
	
	private String distrito;
	private String horario;
	private String direccion;
	private String tipoResiduo;
	private double coordX;
	private double coordY;
	
	public PuntoLimpio(String distrito, String horario, String direccion, String tipoResiduo, double coordX, double coordY) {
		this.distrito=distrito;
		this.horario=horario;
		this.direccion=direccion;
		this.tipoResiduo=tipoResiduo;
		this.coordX=coordX;
		this.coordY=coordY;
	}
}
