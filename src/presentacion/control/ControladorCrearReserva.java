package presentacion.control;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.AlquilerVehiculos;
import modelo.Categoria;
//import modelo.Cliente;
import modelo.Reserva;
import modelo.Sucursal;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
//import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControladorCrearReserva extends ControladorCasoDeUso {
	private static final Logger LOG = Logger.getLogger(ControladorCrearReserva.class.getName());
	Alert alert;
	List<Sucursal> sucursales;
	List<Categoria> categorias;
	List<String> modalidades;
	String dni;
	@FXML
	private DatePicker fechar;
	@FXML
	private DatePicker fechad;
	@FXML
	private ComboBox<Sucursal> sucursalr;
	@FXML
	private ComboBox<Sucursal> sucursald;
	@FXML
	private ComboBox<Categoria> categoria;
	@FXML
	private ComboBox<String> modalidad;
	@FXML
	private Button aceptar;
	@FXML
	private Button cancelar;

	/*
	 * Establece el DNI del cliente que va a realizar esta reserva.
	 * */

	public void setDNI(String dni) {
		this.dni = dni;
	}

	/*
	 * Rellena los campos de los ComboBox tanto para las Sucursales de recogida y devolución como para las categorias.
	 * */

	public void rellenar() {
		sucursales = AlquilerVehiculos.dameAlquilerVehiculos().getSucursales();
		categorias = AlquilerVehiculos.dameAlquilerVehiculos().getCategorias();
		sucursalr.getItems().addAll(sucursales);
		sucursald.getItems().addAll(sucursales);
		categoria.getItems().addAll(categorias);

		modalidades = new ArrayList<String>();
		modalidades.add("Kilometraje ilimitado");
		modalidades.add("Cantidad por kilometro recorrido.");
		modalidad.getItems().addAll(modalidades);
	}

	/*
	 * Genera un identificador de reserva a partir del anyo, el mes, el dia, las horas, los segundos y los minutos.
	 * */

	public int generarID() {
		Date d = new Date();
		@SuppressWarnings("deprecation")
		String s = ""+d.getYear()+d.getMonth()+d.getDay()+d.getHours()+d.getMinutes();
		return Integer.parseInt(s);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("CREAR RESERVA");
		stage.setResizable(false);

		cancelar.setOnAction(event -> stage.close());
		aceptar.setOnAction(event -> {
			try {
				Reserva res = new Reserva(generarID(),
						LocalDateTime.of(fechar.getValue(),LocalTime.now()),
						LocalDateTime.of(fechad.getValue(), LocalTime.now()),
						modalidad.getSelectionModel().getSelectedIndex(),
						dni,
						categoria.getSelectionModel().getSelectedItem().getNombre().trim(),
						sucursalr.getSelectionModel().getSelectedItem().getId(),
						sucursald.getSelectionModel().getSelectedItem().getId());

				if (res != null) {
					//Invocamos el servicio encargado de crear una nueva reserva
					AlquilerVehiculos.dameAlquilerVehiculos().crearReserva(res);
					LOG.log(Level.INFO, "Se ha creado una nueva Reserva: " +res);
				}/* else {
					LOG.log(Level.INFO, "No se ha podido crear un nuevo cliente.");
				}*/
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
