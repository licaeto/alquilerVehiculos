package presentacion.control;

import java.net.URL;
//import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
//import java.util.logging.Logger;

import modelo.AlquilerVehiculos;
import modelo.Reserva;
import modelo.Sucursal;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorReservasPorSucursal extends ControladorCasoDeUso{
//	private static final Logger LOG = Logger.getLogger(ControladorCrearCliente.class.getName());
	Sucursal sucursal;
	List<Reserva> reservas;
	@FXML
	private TableView<Reserva> tabla;
	@FXML
	private TableColumn<Reserva, Integer> id;
	@FXML
	private TableColumn<Reserva,String> fRecogida;
	@FXML
	private TableColumn<Reserva,String> lRecogida;
	@FXML 
	private TableColumn<Reserva,String> fDevolucion;
	@FXML
	private TableColumn<Reserva,String> lDevolucion;
	@FXML
	private TableColumn<Reserva,String> modalidad;
	@FXML 
	private TableColumn<Reserva,String> dni;
	@FXML
	private TableColumn<Reserva,String> categoria;
	
	/*
	 * Establece la sucursal para la que queremos obtener la lista de reservas.
	 */
	
	public void setSucursal(Sucursal s) {
		sucursal = s;
	}
	
	/*
	 * Rellena la tabla con la lista de reservas cuya sucursal de recogida es this.sucursal.
	 */
	
	public void rellenarTabla() {
		reservas = (AlquilerVehiculos.dameAlquilerVehiculos()).obtenerReservasPorSucursalOrigen(sucursal.getId());
		if(reservas != null && reservas.size()>0){
			tabla.getItems().addAll(reservas);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("LISTADO DE RESERVAS");
		
		//Asocia un atributo a cada columna de la tabla.
		id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(new Integer(param.getValue().getId())));
		fRecogida.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getFechaRecogida().toLocalDate().toString()));
		lRecogida.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(new Integer(param.getValue().getIdSucursalRecogida()).toString()));
		fDevolucion.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getFechaDevolucion().toLocalDate().toString()));
		lDevolucion.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(new Integer(param.getValue().getIdSucursalDevolucion()).toString()));
		modalidad.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(new Integer(param.getValue().getModalidadAlquiler()).toString()));
		dni.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getDniCliente()));
		categoria.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getNombreCategoria()));
	}
	
}
