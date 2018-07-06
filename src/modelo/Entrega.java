package modelo;

import java.time.LocalDateTime;
//import java.util.List;

public class Entrega {
	private int idReserva;
	private LocalDateTime fecha;
	private String tipoSeguro;
	private double kms;
	private double combustible;
	private String matriculaCoche;
	private String DniEmpleado;

	public Entrega(int idReserva, LocalDateTime fecha, String tipoSeguro, double kms, double combustible,
			String matriculaCoche, String dniEmpleado) {
		super();
		this.idReserva = idReserva;
		this.fecha = fecha;
		this.tipoSeguro = tipoSeguro;
		this.kms = kms;
		this.combustible = combustible;
		this.matriculaCoche = matriculaCoche;
		DniEmpleado = dniEmpleado;
	}

	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public String getTipoSeguro() {
		return tipoSeguro;
	}
	public void setTipoSeguro(String tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
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
	public String getMatriculaCoche() {
		return matriculaCoche;
	}
	public void setMatriculaCoche(String matriculaCoche) {
		this.matriculaCoche = matriculaCoche;
	}
	public String getDniEmpleado() {
		return DniEmpleado;
	}
	public void setDniEmpleado(String dniEmpleado) {
		DniEmpleado = dniEmpleado;
	}


}
