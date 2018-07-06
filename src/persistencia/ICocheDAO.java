package persistencia;

import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.CocheDTO;

public interface ICocheDAO {
	public List<CocheDTO> obtenerCochesPorSucursal(int idSucursal) throws DAOExcepcion;
}
