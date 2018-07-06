package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import persistencia.dto.ClienteDTO;
import excepciones.DAOExcepcion;

public class ClienteDAOImp implements IClienteDAO {
	protected ConnectionManager connManager;

	public ClienteDAOImp() throws DAOExcepcion {
		super();
		try{
		connManager= new ConnectionManager("alquilervehiculosBD");
		}
		catch (ClassNotFoundException e){
			throw new DAOExcepcion(e);
			}
	}

	public ClienteDTO buscarCliente(String dni) throws DAOExcepcion {
		try {
		connManager.connect();
		ResultSet rs=connManager.queryDB("select * from CLIENTE where DNI= '"+dni+"'");

		connManager.close();

		if(rs.next()) {
			return new ClienteDTO(
					rs.getString("DNI"),
					rs.getString("NOMBREAPELLIDOS"),
					rs.getString("DIRECCION"),
					rs.getString("POBLACION"),
					rs.getString("CODPOSTAL"),
					LocalDateTime.of(rs.getDate("FECHACARNETCONDUCIR").toLocalDate(),rs.getTime("FECHACARNETCONDUCIR").toLocalTime()),
					rs.getString("DIGITOSTC"),
					rs.getInt("MESTC"),
					rs.getInt("ANYOTC"),
					rs.getInt("CVCTC"),
					rs.getString("TIPOTC"));
		}
		else return null;
		}
		catch (SQLException e){	throw new DAOExcepcion(e);}
	}

	public void crearCliente(ClienteDTO cliente) throws DAOExcepcion {
		try{
			connManager.connect();
			connManager.updateDB("INSERT INTO CLIENTE VALUES('"+cliente.getDni()+"',"
					  									  + "'"+cliente.getNombreyApellidos()+"',"
					  									  + "'"+cliente.getDireccion()+"',"
					  									  + "'"+cliente.getPoblacion()+"',"
					  									  + "'"+cliente.getCodPostal()+"',"
					  									  + "'"+cliente.getFechaCanetConducir().toLocalDate()+" "+cliente.getFechaCanetConducir().toLocalTime()+"',"
					  									  + "'"+cliente.getDigitosTC()+"',"
					  									  + "'"+cliente.getMesTC()+"',"
					  									  + "'"+cliente.getAnyoTC()+"',"
					  									  + "'"+cliente.getCvcTC()+"',"
					  									  + "'"+cliente.getTipoTC()+"')");

			connManager.close();
		}
		catch(SQLException e){	throw new DAOExcepcion(e);}
	}


}
