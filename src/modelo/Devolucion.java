package modelo;

import java.util.List;

public class Devolucion {
	private String fecha;
	private double totalACobrar;
	private boolean cobrado;
	private double kms;
	private double combustible;
	private Entrega entrega;
	private List<Danyo> danyos;
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getTotalACobrar() {
		return totalACobrar;
	}
	public void setTotalACobrar(double totalACobrar) {
		this.totalACobrar = totalACobrar;
	}
	public boolean isCobrado() {
		return cobrado;
	}
	public void setCobrado(boolean cobrado) {
		this.cobrado = cobrado;
	}
	public double getKms() {
		return kms;
	}
	public void setKms(double kms) {
		this.kms = kms;
	}
	public double getCombustible() {
		return combustible;
	}
	public void setCombustible(double combustible) {
		this.combustible = combustible;
	}
	public Entrega getEntrega() {
		return entrega;
	}
	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}
	public List<Danyo> getDanyos() {
		return danyos;
	}
	public void setDanyos(List<Danyo> danyos) {
		this.danyos = danyos;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	private Empleado empleado;
}
