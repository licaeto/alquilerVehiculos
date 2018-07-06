package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencia.dto.SucursalDTO;
import excepciones.DAOExcepcion;

public class SucursalDAOImp implements ISucursalDAO{
	protected ConnectionManager connManager;
	
	public SucursalDAOImp() throws DAOExcepcion {
		super();
		try{
			connManager= new ConnectionManager("alquilervehiculosBD");
		}
		catch (ClassNotFoundException e){	
			throw new DAOExcepcion(e);
			}
	}
	
	@Override
	public List<SucursalDTO> obtenerSucursales() throws DAOExcepcion {
		try{
			connManager.connect();
			ResultSet rs=connManager.queryDB("select * from SUCURSAL");						
			connManager.close();
	  	  
			List<SucursalDTO> listaSucursalDTO = new ArrayList<SucursalDTO>();
				
			try{				
				while (rs.next()){

					SucursalDTO sucDTO = new SucursalDTO(
							rs.getInt("ID"),
							rs.getString("DIRECCION"));	 
					listaSucursalDTO.add(sucDTO);
				}
				return listaSucursalDTO;
			}
			catch (Exception e){	throw new DAOExcepcion(e);}
		}
		catch (SQLException e){	throw new DAOExcepcion(e);}	
		catch (DAOExcepcion e){		throw e;}

	}

}
