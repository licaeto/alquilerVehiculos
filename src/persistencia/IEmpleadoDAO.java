package persistencia;

import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.EmpleadoDTO;

public interface IEmpleadoDAO {
	public List<EmpleadoDTO> obtenerEmpleadosPorSucursal(int idSucursal) throws DAOExcepcion;
}
