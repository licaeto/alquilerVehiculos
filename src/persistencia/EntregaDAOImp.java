package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import excepciones.DAOExcepcion;
import persistencia.dto.EntregaDTO;

public class EntregaDAOImp implements IEntregaDAO {
	private ConnectionManager connManager;
	public EntregaDAOImp() throws DAOExcepcion{
		super();
		try{
			connManager= new ConnectionManager("alquilervehiculosBD");
		}
		catch (ClassNotFoundException e){
			throw new DAOExcepcion(e);
		}
	}

	@Override
	public void crearEntrega(EntregaDTO entrega) throws DAOExcepcion {
		try{
			connManager.connect();
			String update = "INSERT INTO ENTREGA VALUES('"+entrega.getIdReserva()+"',"
					+"'"+entrega.getFecha().toLocalDate()+" "+LocalTime.now()+"',"
					+"'"+entrega.getTipoSeguro()+"',"
					+"'"+entrega.getKms()+"',"
					+"'"+entrega.getCombustible()+"',"
					+"'"+entrega.getMatriculaCoche()+"',"
					+"'"+entrega.getDniEmpleado()+"')";
			connManager.updateDB(update);

			connManager.close();
		}
		catch(SQLException e){	throw new DAOExcepcion(e);}
	}

	@Override
	public List<EntregaDTO> obtenerEntregas() throws DAOExcepcion {
		try {
			connManager.connect();
			ResultSet rs=connManager.queryDB("select * from ENTREGA");
			connManager.close();

			List<EntregaDTO> listaEntregaDTO = new ArrayList<EntregaDTO>();

			try{
				while (rs.next()){

					EntregaDTO eDTO = new EntregaDTO(
							rs.getInt("ID"),
							LocalDateTime.of(rs.getDate("FECHA").toLocalDate(),rs.getTime("FECHA").toLocalTime()),
							rs.getString("TIPOSEGURO"),
							rs.getDouble("KMS"),
							rs.getDouble("COMBUSTIBLE"),
							rs.getString("COCHEASIGNADO"),
							rs.getString("EMPLEADOREALIZA")
							);
					listaEntregaDTO.add(eDTO);
				}
				return listaEntregaDTO;
			}
			catch (Exception e){	throw new DAOExcepcion(e);}
		}
		catch (SQLException e){	throw new DAOExcepcion(e);}
		catch (DAOExcepcion e){		throw e;}
	}

}
