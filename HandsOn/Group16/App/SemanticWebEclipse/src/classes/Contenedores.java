package classes;

public class Contenedores {
	
	private String distrito;
	private String direccion;
	private String tipoResiduo;
	private double coordX;
	private double coordY;
	
	public Contenedores(String distrito, String direccion, String tipoResiduo, double coordX, double coordY) {
		this.distrito=distrito;
		this.direccion=direccion;
		this.tipoResiduo=tipoResiduo;
		this.coordX=coordX;
		this.coordY=coordY;
	}
}
