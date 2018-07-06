package persistencia;

import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.EntregaDTO;
//import persistencia.dto.ReservaDTO;

public interface IEntregaDAO {
	public void crearEntrega(EntregaDTO entrega) throws DAOExcepcion;

	public List<EntregaDTO> obtenerEntregas() throws DAOExcepcion;
}
