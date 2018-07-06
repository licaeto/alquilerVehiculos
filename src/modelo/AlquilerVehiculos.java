package modelo;

import java.util.ArrayList;
//import java.time.LocalDateTime;
//import java.util.Date;
import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.DAL;
import persistencia.dto.CategoriaDTO;
import persistencia.dto.ClienteDTO;
import persistencia.dto.CocheDTO;
import persistencia.dto.EmpleadoDTO;
import persistencia.dto.EntregaDTO;
import persistencia.dto.ReservaDTO;
import persistencia.dto.SucursalDTO;

public class AlquilerVehiculos {
	private DAL dal;

	private List<Sucursal> sucursales = new ArrayList<Sucursal>();
	private List<Categoria> categorias = new ArrayList<Categoria>();
	private List<Reserva> reservas = new ArrayList<Reserva>();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private List<Entrega> entregas = new ArrayList<Entrega>();
	private List<Coche> coches = new ArrayList<Coche>();

	public AlquilerVehiculos() throws DAOExcepcion{
		dal = DAL.dameDAL();
		cargaSistema();
	}

	public static AlquilerVehiculos dameAlquilerVehiculos() {
		try {
			return new AlquilerVehiculos();
		} catch (DAOExcepcion e) {
			return null;
		}
	}

	private void cargaSistema() {
		cargaCategorias();
		cargaSucursales();
	}

	private void cargaCategorias() {
		List<CategoriaDTO> listacatDTO = dal.obtenerCategorias();
		for(CategoriaDTO catDTO : listacatDTO) {
			anyadirCategoria(new Categoria(catDTO.getNombre(), catDTO.getPrecioModIlimitada(), catDTO.getPrecioModKms(),
							catDTO.getPrecioKMModKms(), catDTO.getPrecioSeguroTRiesgo(),catDTO.getPrecioSeguroTerceros(),null));
		}
		for (CategoriaDTO catDTO : listacatDTO)
			if (catDTO.getNombreCategoriaSuperior() != null)
			buscarCategoria(catDTO.getNombre()).setNombreCategoriaSuperior(catDTO.getNombreCategoriaSuperior());
	}

	private void cargaSucursales() {
		List<SucursalDTO> listasucDTO = dal.obtenerSucursales();
		for(SucursalDTO sucDTO : listasucDTO) {
			anyadirSucursal(new Sucursal(sucDTO.getId(),sucDTO.getDireccion()));
		}
	}

	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	public boolean anyadirSucursal(Sucursal s) {
		return sucursales.add(s);
	}

	public Sucursal buscarSucursal(int id){
		for(Sucursal s : this.sucursales){
			if(s.getId() == id) return s;
		}
		return null;
	}

	public boolean eliminarSucursal(Sucursal s) {
		return sucursales.remove(s);
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Categoria buscarCategoria(String n) {
		for(Categoria c: this.categorias) {
			if(c.getNombre().equals(n)) return c;
		}
		return null;
	}

	public boolean anyadirCategoria(Categoria c) {
		return categorias.add(c);
	}

	public boolean eliminarCategoria(Categoria c) {
		return categorias.remove(c);
	}

	public List<Reserva> getReservas() {
		return reservas;
	}
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public void crearReserva(Reserva res) {
		ReservaDTO r = new ReservaDTO(res.getId(),
				res.getFechaRecogida(),
				res.getFechaDevolucion(),
				res.getModalidadAlquiler(),
				res.getDniCliente(),
				res.getNombreCategoria(),
				res.getIdSucursalRecogida(),
				res.getIdSucursalDevolucion());

		anyadirReserva(res);
		dal.crearReserva(r);
	}

	public boolean anyadirReserva(Reserva r) {
		return reservas.add(r);
	}

	public boolean eliminarReserva(Reserva r) {
		return reservas.add(r);
	}

	public List<Reserva> obtenerReservasPorSucursalOrigen(int idSucursal) {
		List<Reserva> res = new ArrayList<Reserva>();
		List<ReservaDTO> rdto = dal.obtenerReservasPorSucursalOrigen(idSucursal);
		for(ReservaDTO r : rdto) {
			Reserva x = new Reserva(r.getId(),
					r.getFechaRecogida(),
					r.getFechaDevolucion(),
					r.getModalidadAlquiler(),
					r.getDniCliente(),
					r.getNombreCategoria(),
					r.getIdSucursalRecogida(),
					r.getIdSucursalDevolucion());
			res.add(x);
			reservas.add(x);
		}
		return res;
	}

	/*
	 * Devuelve una lista de las reservas cuyo identificador no está entre los identificadores de reservas asociados a alguna
	 * entrega, o null si no hay reservas que cumplan la condición.
	 */
	public List<Reserva> obtenerReservasPendientes() {
		List<Reserva> res = new ArrayList<Reserva>();
		List<Reserva> reservas = new ArrayList<Reserva>();
		List<Entrega> entregas = obtenerEntregas();
		boolean encontrado = false;
		
		for(int s = 0; s < sucursales.size(); s++) {
			reservas.addAll(obtenerReservasPorSucursalOrigen(sucursales.get(s).getId()));
		}

		if(entregas != null) {
			for(int r = 0; r < reservas.size(); r++) {
				encontrado = false;
				int e = 0;
				while(!encontrado && e < entregas.size()) {
					if(reservas.get(r).getId() == entregas.get(e).getIdReserva()) encontrado = true;
					else e++;
				}
				if(!encontrado) {
					res.add(reservas.get(r));
				}
			}
		}
		else return reservas;

		if(res.size() > 0) return res;
		else return null;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public boolean anyadirCliente(Cliente c) {
		return clientes.add(c);
	}

	public boolean eliminarCliente(Cliente c) {
		return clientes.remove(c);
	}

	public Cliente buscarCliente(String dni) {
		ClienteDTO cdto = dal.buscarCliente(dni);
		if(cdto != null)
		return new Cliente(cdto.getDni(),
						   cdto.getNombreyApellidos(),
						   cdto.getDireccion(),
						   cdto.getPoblacion(),
						   cdto.getCodPostal(),
						   cdto.getFechaCanetConducir(),
						   cdto.getDigitosTC(),
						   cdto.getMesTC(),
						   cdto.getAnyoTC(),
						   cdto.getCvcTC(),
						   cdto.getTipoTC());
		else return null;
	}

	public void crearCliente(Cliente cliente) {
		ClienteDTO clienteDTO = new ClienteDTO(cliente.getDNI(),
				cliente.getNombreYApellidos(), cliente.getDireccion(),
				cliente.getPoblacion(), cliente.getCodPostal(),
				cliente.getFechaCarnetConducir(), cliente.getDigitosTC(),
				cliente.getMesTC(), cliente.getAnyoTC(), cliente.getCvcTC(),
				cliente.getTipoTC());
		// Lo a�ade al a memoria
		anyadirCliente(cliente);
		// Y le pide a dal que lo persista
		dal.crearCliente(clienteDTO);
	}

	public List<Entrega> getEntregas() {
		return entregas;
	}

	private List<Entrega> obtenerEntregas() {
		List<Entrega> res = new ArrayList<Entrega>();
		List<EntregaDTO> edto = dal.obtenerEntregas();
		for(EntregaDTO e : edto) {
			res.add(new Entrega(e.getIdReserva(),
								e.getFecha(),
								e.getTipoSeguro(),
								e.getKms(),
								e.getCombustible(),
								e.getMatriculaCoche(),
								e.getDniEmpleado()));
		}
		if(res.size() > 0) return res;
		else return null;
	}

	public void setEntregas(List<Entrega> entregas) {
		this.entregas = entregas;
	}

	public void crearEntrega(Entrega entrega) {
		EntregaDTO entregaDTO = new EntregaDTO(
				entrega.getIdReserva(),
				entrega.getFecha(),
				entrega.getTipoSeguro(),
				entrega.getKms(),
				entrega.getCombustible(),
				entrega.getMatriculaCoche(),
				entrega.getDniEmpleado()
				);
		anyadirEntrega(entrega);
		dal.crearEntrega(entregaDTO);
	}

	public boolean anyadirEntrega(Entrega e) {
		return entregas.add(e);
	}

	public boolean eliminarEntrega(Entrega e) {
		return entregas.remove(e);
	}

	public List<Coche> obtenerVehiculosPorSucursal(int idSucursal) {
		List<Coche> res = new ArrayList<Coche>();
		List<CocheDTO> listaDTO = dal.obtenerVehiculosPorSucursal(idSucursal);
		for(CocheDTO c : listaDTO) {
			Coche x = new Coche(c.getMatricula(),c.getKmsActuales(),c.getIdSucursal(),c.getNombreCategoria());
			res.add(x);
			coches.add(x);

		}
		return res;
	}

	/*
	 * Devuelve la lista de vehiculos con categoria nombreCategoria (o superior) de la sucursal idSucursalRecogida
	 */
	public List<Coche> obtenerVehiculosPorCategoria(int idSucursalRecogida,	String nombreCategoria) {
		List<Coche> res = new ArrayList<Coche>();
		List<Coche> aux = obtenerVehiculosPorSucursal(idSucursalRecogida);
		int c;
		String sup = null;
		if(aux != null && aux.size() > 0) {
			for(c = 0; c < aux.size(); c++) {
				if(aux.get(c).getNombreCategoria().equals(nombreCategoria)) {
					res.add(aux.get(c));
				}
			}
			if(res.size() > 0) return res;
			else {
				for(Categoria cat : categorias) {
					if(cat.getNombre().equals(nombreCategoria)) {
						sup = cat.getNombreCategoriaSuperior();
						break;
					}
				}
				if(sup != null) return obtenerVehiculosPorCategoria(idSucursalRecogida,sup);
				else return null;
			}
		}
		else return null;
	}

	public List<Empleado> obtenerEmpleadosPorSucursal(int idSucursal) {
		List<Empleado> res = new ArrayList<Empleado>();
		List<EmpleadoDTO> eDTO = dal.obtenerEmpleadosPorSucursal(idSucursal);
		for(EmpleadoDTO e : eDTO) {
			res.add(new Empleado(e.getDni(),
								 e.getNombre(),
								 e.isAdministrador(),
								 e.getSucursal()));
		}
		return res;
	}

	/*
	 * Dado el DNI de un Cliente, devuelve el nombre del mismo.
	 */
	public String obtenerNombreConDNI(String dniCliente) {
		return dal.buscarCliente(dniCliente).getNombreyApellidos();
	}
}

