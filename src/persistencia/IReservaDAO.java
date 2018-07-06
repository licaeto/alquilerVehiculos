package persistencia;

import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.ReservaDTO;

public interface IReservaDAO {
	public ReservaDTO buscarReserva(int identificador) throws DAOExcepcion;
	public List<ReservaDTO> obtenerReservasPorSucursalOrigen(int idSucursal) throws DAOExcepcion;
	public void crearReserva(ReservaDTO reserva) throws DAOExcepcion;
}
