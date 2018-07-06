package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import persistencia.dto.CategoriaDTO;
//import persistencia.dto.ClienteDTO;
import persistencia.dto.ReservaDTO;
import excepciones.DAOExcepcion;

public class ReservaDAOImp implements IReservaDAO{
	private ConnectionManager connManager;
	public ReservaDAOImp() throws DAOExcepcion{
		super();
		try{
		connManager= new ConnectionManager("alquilervehiculosBD");
		}
		catch (ClassNotFoundException e){
			throw new DAOExcepcion(e);
			}
	}

	public ReservaDTO buscarReserva(int identificador) throws DAOExcepcion {
		try {
			connManager.connect();
			ResultSet rs=connManager.queryDB("select * from RESERVA where ID= '"+identificador+"'");

			connManager.close();

			if(rs.next()) {
				return new ReservaDTO(
						rs.getInt("ID"),
						LocalDateTime.of(rs.getDate("FECHARECOGIDA").toLocalDate(),rs.getTime("FECHARECOGIDA").toLocalTime()),
						LocalDateTime.of(rs.getDate("FECHADEVOLUCION").toLocalDate(),rs.getTime("FECHADEVOLUCION").toLocalTime()),
						rs.getInt("MODALIDADALQUILER"),
						rs.getString("CATEGORIA"),
						rs.getString("CLIENTEREALIZA"),
						rs.getInt("SUCURSALRECOGIDA"),
						rs.getInt("SUCURSALDEVOLUCION"));
			}
			else return null;
			}
			catch (SQLException e){	throw new DAOExcepcion(e);}
	}

	public List<ReservaDTO> obtenerReservasPorSucursalOrigen(int idSucursal) throws DAOExcepcion {
		try{
			connManager.connect();
			ResultSet rs=connManager.queryDB("select * from RESERVA where SUCURSALRECOGIDA= '"+idSucursal+"'");
			connManager.close();

			List<ReservaDTO> listaReservaDTO = new ArrayList<ReservaDTO>();
			try{
				while (rs.next()){
					ReservaDTO resDTO = new ReservaDTO(
							rs.getInt("ID"),
							LocalDateTime.of(rs.getDate("FECHARECOGIDA").toLocalDate(),rs.getTime("FECHARECOGIDA").toLocalTime()),
							LocalDateTime.of(rs.getDate("FECHADEVOLUCION").toLocalDate(),rs.getTime("FECHADEVOLUCION").toLocalTime()),
							rs.getInt("MODALIDADALQUILER"),
							rs.getString("CLIENTEREALIZA"),
							rs.getString("CATEGORIA"),
							rs.getInt("SUCURSALRECOGIDA"),
							rs.getInt("SUCURSALDEVOLUCION"));
					listaReservaDTO.add(resDTO);
				}
				return listaReservaDTO;
			}
			catch (Exception e){	throw new DAOExcepcion(e);}
		}
		catch (SQLException e){	throw new DAOExcepcion(e);}
		catch (DAOExcepcion e){		throw e;}
	}

	public void crearReserva(ReservaDTO reserva) throws DAOExcepcion {
		try{
			connManager.connect();
			connManager.updateDB("INSERT INTO RESERVA VALUES('"+reserva.getId()+"',"
					  									  + "'"+reserva.getFechaRecogida().toLocalDate()+" "+reserva.getFechaRecogida().toLocalTime()+"',"
					  									  + "'"+reserva.getFechaDevolucion().toLocalDate()+" "+reserva.getFechaDevolucion().toLocalTime()+"',"
					  									  + "'"+reserva.getModalidadAlquiler()+"',"
					  									  + "'"+reserva.getNombreCategoria()+"',"
					  									  + "'"+reserva.getDniCliente()+"',"
					  									  + "'"+reserva.getIdSucursalRecogida()+"',"
					  									  + "'"+reserva.getIdSucursalDevolucion()+"')");

			connManager.close();
		}
		catch(SQLException e){	throw new DAOExcepcion(e);}
	}

}
