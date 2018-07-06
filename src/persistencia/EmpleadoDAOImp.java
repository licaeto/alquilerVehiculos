package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.EmpleadoDTO;

public class EmpleadoDAOImp implements IEmpleadoDAO {
	private ConnectionManager connManager;
	public EmpleadoDAOImp() throws DAOExcepcion{
		super();
		try{
		connManager= new ConnectionManager("alquilervehiculosBD");
		}
		catch (ClassNotFoundException e){
			throw new DAOExcepcion(e);
			}
	}
	
	@Override
	public List<EmpleadoDTO> obtenerEmpleadosPorSucursal(int idSucursal) throws DAOExcepcion {
		try{
			connManager.connect();
			ResultSet rs=connManager.queryDB("select * from EMPLEADO where SUCURSAL= '"+idSucursal+"'");
			connManager.close();

			List<EmpleadoDTO> listaEmpleadoDTO = new ArrayList<EmpleadoDTO>();
			try{
				while (rs.next()){
					EmpleadoDTO eDTO = new EmpleadoDTO(
							rs.getString("DNI"),
							rs.getString("NOMBRE"),
							rs.getBoolean("ADMINISTRADOR"),
							rs.getInt("SUCURSAL"));
					listaEmpleadoDTO.add(eDTO);
				}
				return listaEmpleadoDTO;
			}
			catch (Exception e){	throw new DAOExcepcion(e);}
		}
		catch (SQLException e){	throw new DAOExcepcion(e);}
		catch (DAOExcepcion e){		throw e;}
	}

}
