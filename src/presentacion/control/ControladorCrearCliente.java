package presentacion.control;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.logging.Logger;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;

import modelo.AlquilerVehiculos;
import modelo.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ControladorCrearCliente extends ControladorCasoDeUso {
	private static final Logger LOG = Logger.getLogger(ControladorCrearCliente.class.getName());
	Alert alert;
	@FXML
	private TextField dni;
	@FXML
	private TextField nombreApellidos;
	@FXML
	private TextField direccion;
	@FXML
	private TextField anyoTC;
	@FXML
	private TextField mesTC;
	@FXML
	private TextField codigoPostal;
	@FXML
	private TextField poblacion;
	@FXML
	private DatePicker fechaCarnet;
	@FXML
	private TextField cvc;
	@FXML
	private TextField tipoTarjeta;
	@FXML
	private TextField digitosTC;
	@FXML
	private Button aceptar;
	@FXML
	private Button cancelar;

	private Cliente nuevoCliente;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("CREAR CLIENTE");
		stage.setResizable(false);
		
		cancelar.setOnAction(event -> stage.close());
		aceptar.setOnAction(event -> {
			try {
				nuevoCliente = new Cliente(dni.getText(), nombreApellidos.getText(),direccion.getText(),poblacion.getText(), codigoPostal.getText(), LocalDateTime.of(fechaCarnet.getValue(),LocalTime.now()), digitosTC.getText(),Integer.parseInt(mesTC.getText()),Integer.parseInt(anyoTC.getText()),Integer.parseInt(cvc.getText()), tipoTarjeta.getText());
				if (nuevoCliente != null) {
					//Invocamos el servicio encargado de crear un nuevo cliente
					AlquilerVehiculos.dameAlquilerVehiculos().crearCliente(nuevoCliente);
					LOG.log(Level.INFO, "Se ha creado un nuevo Cliente: " +nuevoCliente);
				} else {
					LOG.log(Level.INFO, "No se ha podido crear un nuevo cliente.");
				}
				stage.close();
			}
			catch (NullPointerException e) {
				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Faltan campos por completar.");
				alert.setContentText("Rellene todos los campos, por favor.");
				alert.showAndWait();
			}
			catch (NumberFormatException e) {
				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Formato de campo incorrecto.");
				alert.setContentText("Asegurese de respetar el formato de todos los campos.");
				alert.showAndWait();
			}

		});
	}
}
