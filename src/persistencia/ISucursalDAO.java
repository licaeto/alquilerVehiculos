package persistencia;
import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.SucursalDTO;

public interface ISucursalDAO {
	public List<SucursalDTO> obtenerSucursales() throws DAOExcepcion;
}
