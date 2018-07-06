package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.CocheDTO;

public class CocheDAOImp implements ICocheDAO{
	private ConnectionManager connManager;
	public CocheDAOImp() throws DAOExcepcion{
		super();
		try{
			connManager= new ConnectionManager("alquilervehiculosBD");
		}
		catch (ClassNotFoundException e){
			throw new DAOExcepcion(e);
		}
	}

	@Override
	public List<CocheDTO> obtenerCochesPorSucursal(int idSucursal) throws DAOExcepcion {
		try{
			connManager.connect();
			ResultSet rs=connManager.queryDB("select * from COCHE where SUCURSAL= '"+idSucursal+"'");
			connManager.close();

			List<CocheDTO> listaCocheDTO = new ArrayList<CocheDTO>();
			try{
				while (rs.next()){
					CocheDTO resDTO = new CocheDTO(
							rs.getString("MATRICULA"),
							rs.getDouble("KMSACTUALES"),
							rs.getInt("SUCURSAL"),
							rs.getString("NOMBRE"));
					listaCocheDTO.add(resDTO);
				}
				return listaCocheDTO;
			}
			catch (Exception e){	throw new DAOExcepcion(e);}
		}
		catch (SQLException e){	throw new DAOExcepcion(e);}
		catch (DAOExcepcion e){		throw e;}
	}

}
