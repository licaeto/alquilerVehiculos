package persistencia;

import java.util.List;

import persistencia.dto.EmpleadoDTO;
import persistencia.dto.CategoriaDTO;
import persistencia.dto.ClienteDTO;
import persistencia.dto.CocheDTO;
import persistencia.dto.EntregaDTO;
import persistencia.dto.ReservaDTO;
import persistencia.dto.SucursalDTO;
import excepciones.DAOExcepcion;

public class DAL {
	private ICategoriaDAO catDAO;
	private IClienteDAO clienteDAO;
	private IReservaDAO reservaDAO;
	private ISucursalDAO sucursalDAO;
	private IEntregaDAO entregaDAO;
	private ICocheDAO cocheDAO;
	private IEmpleadoDAO empleadoDAO;

	public DAL() throws DAOExcepcion {
		catDAO = new CategoriaDAOImp();
		clienteDAO = new ClienteDAOImp();
		reservaDAO = new ReservaDAOImp();
		sucursalDAO = new SucursalDAOImp();
		entregaDAO = new EntregaDAOImp();
		cocheDAO = new CocheDAOImp();
		empleadoDAO = new EmpleadoDAOImp();
	}

	public static DAL dameDAL() {
		try {
			return new DAL();
		} catch (DAOExcepcion e) {
			return null;
		}
	}

	public List<CategoriaDTO> obtenerCategorias() {
		try {
			return catDAO.obtenerCategorias();
		} catch (DAOExcepcion e) {
			return null;
		}
	}

	public ClienteDTO buscarCliente(String dni) {
		try {
			return clienteDAO.buscarCliente(dni);
		} catch (DAOExcepcion e) {
			return null;
		}
	}

	public void crearCliente(ClienteDTO cliente) {
		try {
			clienteDAO.crearCliente(cliente);
		} catch (DAOExcepcion e) {}
	}

	public ReservaDTO buscarReserva(int identificador) {
		try {
			return reservaDAO.buscarReserva(identificador);
		}
		catch (DAOExcepcion e) {
			return null;
		}
	}

	public List<ReservaDTO> obtenerReservasPorSucursalOrigen(int idSucursal) {
		try {
			return reservaDAO.obtenerReservasPorSucursalOrigen(idSucursal);
		} catch (DAOExcepcion e) {
			return null;
		}
	}

	public void crearReserva(ReservaDTO reserva) {
		try {
			reservaDAO.crearReserva(reserva);
		} catch (DAOExcepcion e) {}
	}

	public List<SucursalDTO> obtenerSucursales() {
		try {
			return sucursalDAO.obtenerSucursales();
		} catch (DAOExcepcion e) {
			return null;
		}
	}

	public void crearEntrega(EntregaDTO entrega) {
		try{
			entregaDAO.crearEntrega(entrega);
		}
		catch (DAOExcepcion e) {}
	}

	public List<CocheDTO> obtenerVehiculosPorSucursal(int idSucursal) {
		try {
			return cocheDAO.obtenerCochesPorSucursal(idSucursal);
		} catch (DAOExcepcion e) {
			return null;
		}
	}

	public List<EntregaDTO> obtenerEntregas() {
		try {
			return entregaDAO.obtenerEntregas();
		}
		catch (DAOExcepcion e) {
			return null;
		}
	}

	public List<EmpleadoDTO> obtenerEmpleadosPorSucursal(int idSucursal) {
		try {
			return empleadoDAO.obtenerEmpleadosPorSucursal(idSucursal);
		} catch (DAOExcepcion e) {
			return null;
		}
	}
}
