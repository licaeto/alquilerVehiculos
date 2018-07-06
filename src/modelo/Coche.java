package modelo;

public class Coche {
	private String matricula;
	private double kmsActuales;
	private int idSucursal;
	private String nombreCategoria;

	public Coche(String matricula, double kmsActuales, int idSucursal, String nombreCategoria) {
		super();
		this.matricula = matricula;
		this.kmsActuales = kmsActuales;
		this.idSucursal = idSucursal;
		this.nombreCategoria = nombreCategoria;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public double getKmsActuales() {
		return kmsActuales;
	}
	public void setKmsActuales(double kmsActuales) {
		this.kmsActuales = kmsActuales;
	}
	public int getIdSucursal() {
		return idSucursal;
	}
	public void setIdSucursal(int idSucursal) {
		this.idSucursal = idSucursal;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
}
