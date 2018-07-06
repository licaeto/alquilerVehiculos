package persistencia;

import excepciones.DAOExcepcion;
import persistencia.dto.ClienteDTO;

public interface IClienteDAO {
	public ClienteDTO buscarCliente(String dni)throws DAOExcepcion; 
	public void crearCliente(ClienteDTO cliente) throws DAOExcepcion;
}
