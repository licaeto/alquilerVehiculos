package modelo;

import java.time.LocalDateTime;

public class Cliente {
	private String dni;

	private String nombreYApellidos;
	private String direccion;
	private String poblacion;
	private String codPostal;
	private LocalDateTime fechaCarnetConducir;
	private String digitosTC;
	private int mesTC;
	private int anyoTC;
	private int cvcTC;
	private String tipoTC;




	public Cliente(String dni, String nombreYApellidos, String direccion,
			String poblacion, String codPostal, LocalDateTime fechaCarnetConducir,
			String digitosTC, int mesTC, int anyoTC, int cvcTC, String tipoTC) {
		//super();
		this.dni = dni;
		this.nombreYApellidos = nombreYApellidos;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.codPostal = codPostal;
		this.fechaCarnetConducir = fechaCarnetConducir;
		this.digitosTC = digitosTC;
		this.mesTC = mesTC;
		this.anyoTC = anyoTC;
		this.cvcTC = cvcTC;
		this.tipoTC = tipoTC;
	}

	public String getDNI() {
		return dni;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public String getNombreYApellidos() {
		return nombreYApellidos;
	}
	public void setNombre(String nombre) {
		this.nombreYApellidos = nombre;
	}

	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public LocalDateTime getFechaCarnetConducir() {
		return fechaCarnetConducir;
	}
	public void setFechaCarnetConducir(LocalDateTime fechaCarnetConducir) {
		this.fechaCarnetConducir = fechaCarnetConducir;
	}
	public String getDigitosTC() {
		return digitosTC;
	}
	public void setDigitosTC(String digitosTC) {
		this.digitosTC = digitosTC;
	}
	public int getMesTC() {
		return mesTC;
	}
	public void setMesTC(int mesTC) {
		this.mesTC = mesTC;
	}
	public int getAnyoTC() {
		return anyoTC;
	}
	public void setAnyoTC(int anyoTC) {
		this.anyoTC = anyoTC;
	}
	public int getCvcTC() {
		return cvcTC;
	}
	public void setCvcTC(int cvcTC) {
		this.cvcTC = cvcTC;
	}
	public String getTipoTC() {
		return tipoTC;
	}
	public void setTipoTC(String tipoTC) {
		this.tipoTC = tipoTC;
	}

	@Override
	public String toString() {
		return "Cliente: "+nombreYApellidos;
	}
}
