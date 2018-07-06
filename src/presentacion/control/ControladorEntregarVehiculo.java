package presentacion.control;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import modelo.AlquilerVehiculos;
import modelo.Coche;
import modelo.Empleado;
import modelo.Entrega;
import modelo.Reserva;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorEntregarVehiculo extends ControladorCasoDeUso{
	List<Reserva> listaReservas;
	List<Coche> listaCoches;
	Alert alert;
    @FXML
    private TableView<Reserva> reservas;
    
    @FXML
    private TableColumn<Reserva, Integer> id;
    
    @FXML
    private TableColumn<Reserva, LocalDate> recogida;
    
    @FXML
    private TableColumn<Reserva, LocalDate> devolucion;
    
    @FXML
    private TableColumn<Reserva, String> categoria;

    @FXML
    private TableColumn<Reserva, Integer> modalidad;
    
    @FXML
    private TableColumn<Reserva, String> dni;
    
    @FXML
    private TableColumn<Reserva, String> nombre;
    
    @FXML
    private TableView<Coche> vehiculos;
    
    @FXML
    private TableColumn<Coche, String> matricula;

    @FXML
    private TableColumn<Coche, Double> kilometros;
    
    @FXML
    private TableColumn<Coche, String> categoriaCoche;

    @FXML
    private Button siguiente;
    
    @FXML
    private Button anterior;
    
    @FXML
    private Button aceptar;
    
    @FXML
    private Button cancelar;

    @FXML
    private ComboBox<String> seguros;
    
    @FXML
    private ComboBox<Empleado> empleados;
    
    /*
     * Rellena la tabla reservas con las reservas pendientes de entregar (si hay) y muestra un mensaje de aviso en caso contrario.
     */
    public void rellenar() {
    	seguros.getItems().setAll("todoriesgo","terceros");
    	
    	listaReservas = AlquilerVehiculos.dameAlquilerVehiculos().obtenerReservasPendientes();
    	if(listaReservas != null && listaReservas.size() > 0) {
    		reservas.getItems().setAll(listaReservas);
    	}
    	else {
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("No hay reservas disponibles.");
			alert.showAndWait();
		}
    }
    
    /*
     * Cuando el usuario selecciona una reserva y pulsa el boton "siguiente" se rellena la tabla de vehiculos 
     * con los de la sucursal de recogida asociada a dicha reserva. Si no hay vehiculos de la categoria solicitada o alguna superior 
     * el sistema muestra un aviso.
     */
    @FXML
    void rellenarVehiculos(ActionEvent event) {
    	try {
    		empleados.getItems().setAll(AlquilerVehiculos.dameAlquilerVehiculos().obtenerEmpleadosPorSucursal(reservas.getSelectionModel().getSelectedItem().getIdSucursalRecogida()));
    		
    		reservas.setDisable(true);
        	vehiculos.setDisable(false);
        	anterior.setDisable(false);
        	siguiente.setDisable(true);
    		
        	vehiculos.getItems().clear();
    		Reserva seleccion = reservas.getSelectionModel().getSelectedItem();
    		listaCoches = AlquilerVehiculos.dameAlquilerVehiculos().obtenerVehiculosPorCategoria(seleccion.getIdSucursalRecogida(), seleccion.getNombreCategoria().trim());
    		if(listaCoches != null && listaCoches.size() > 0) { 		  		
    			vehiculos.getItems().setAll(listaCoches);
    		}
    		else {
    			alert = new Alert(AlertType.WARNING);
    			alert.setHeaderText("No hay vehiculos disponibles.");
    			alert.showAndWait();
    		}
    	}
    	catch(NullPointerException e) {
    		alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("No ha seleccionado ninguna reserva.");
			alert.setContentText("Por favor, seleccione la reserva.");
			alert.showAndWait();
    	}
    }
    
    /*
     * Cuando el usuario pulsa el boton "aceptar" se crea una entrega con la informacion de los campos seleccionados y se añade tanto
     * a la base de datos como a la lista de entregas. 
     */
    @FXML
    void crearEntrega(ActionEvent event) {
    	try {
    		Reserva r = reservas.getSelectionModel().getSelectedItem();
    		Coche c = vehiculos.getSelectionModel().getSelectedItem();
    		String s = seguros.getSelectionModel().getSelectedItem();
    		Empleado e = empleados.getSelectionModel().getSelectedItem();

    		Entrega entrega = new Entrega(r.getId(),r.getFechaRecogida(),s,c.getKmsActuales(),0.0,c.getMatricula(),e.getDni());
    		if(entrega != null) {
    			AlquilerVehiculos.dameAlquilerVehiculos().crearEntrega(entrega);
    		}
    		stage.close();
    	}
    	catch(NullPointerException e) {
    		alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Faltan campos por rellenar.");
			alert.setContentText("Por favor, rellene todos los campos");
			alert.showAndWait();
    	}
    	
    }
    
    /*
     * Cuando el usuario selecciona un vehiculo se activa el boton "aceptar" 
     */
    @FXML
    void desbloquear(MouseEvent event) {
    	aceptar.setDisable(false);
    }
    
    /*
     * Cuando el usuario pulsa el boton "anterior" se activan y desactivan los elementos adecuados.
     */
    @FXML
    void bloquear(ActionEvent event) {
    	reservas.setDisable(false);
    	siguiente.setDisable(false);
    	aceptar.setDisable(true);
		vehiculos.setDisable(true);
		anterior.setDisable(true);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("ENTREGA DE VEHICULO");
		stage.setResizable(false);
		
		aceptar.setDisable(true);
		vehiculos.setDisable(true);
		anterior.setDisable(true);
		
		id.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getId()));
		recogida.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getFechaRecogida().toLocalDate()));
		devolucion.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getFechaDevolucion().toLocalDate()));
		categoria.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getNombreCategoria()));
		modalidad.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getModalidadAlquiler()));
		dni.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getDniCliente()));
		nombre.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getNombreCliente()));
		
		matricula.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getMatricula()));
		kilometros.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKmsActuales()));
		categoriaCoche.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getNombreCategoria()));
		
		cancelar.setOnAction(event -> stage.close());
		
	}

}
