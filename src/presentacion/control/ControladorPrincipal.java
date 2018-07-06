package presentacion.control;

import java.util.List;
import java.util.NoSuchElementException;

import modelo.AlquilerVehiculos;
import modelo.Cliente;
import modelo.Sucursal;
import excepciones.LogicaExcepcion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class ControladorPrincipal {
	private static final String CREAR_CLIENTE = "../vista/crear-cliente.fxml";
	private static final String LISTAR_RESERVAS_SUCURSAL = "../vista/listar-reservas-sucursal.fxml";
	private static final String LISTAR_SUCURSALES = "../vista/listar-sucursales.fxml";
	private static final String CREAR_RESERVA = "../vista/crear-reserva.fxml";
	private static final String LISTAR_VEHICULOS = "../vista/listar-vehiculos.fxml";
	private static final String ENTREGAR_VEHICULO = "../vista/entregar_vehiculo.fxml";
	Sucursal sucursal;
	//TODO anyadir constantes de tipo String para la vistas correspondientes a los casos de uso Crear Reserva y Listar Reservas de una Sucursal
	private Stage primaryStage;

	/*
	 * Inicia el caso de uso para mostrar la lista de sucursales de la base de datos.
	 */

	@FXML
	void listarSucursales(ActionEvent event) throws LogicaExcepcion {
		initCasoDeUso(LISTAR_SUCURSALES, new ControladorListarSucursales()).show();
	}

	/*
	 * Inicia el caso de uso para crear un nuevo cliente
	 */
	@FXML
	void crearCliente(ActionEvent event) throws LogicaExcepcion {
		initCasoDeUso(CREAR_CLIENTE, new ControladorCrearCliente()).show();
	}

	/*
	 * Muestra un dialogo en el que el usuario debe introducir el dni del cliente que pretende realizar la reserva.
	 * Si el cliente esta en la base de datos se inicia el caso de uso para completar la reserva y si no se inicia
	 * el caso de uso para crear un cliente.
	 */

	@FXML
	void crearReserva(ActionEvent event) {
		Alert alert;
		String dni;

		try {
			TextInputDialog td = new TextInputDialog();
			td.setHeaderText("Introduzca el dni, por favor.");
			dni = td.showAndWait().get();

			Cliente c = AlquilerVehiculos.dameAlquilerVehiculos().buscarCliente(dni);

			if(!td.getResult().isEmpty() && c != null) {
				ControladorCrearReserva controlador = initCasoDeUso(CREAR_RESERVA, new ControladorCrearReserva());
				controlador.setDNI(dni);
				controlador.rellenar();
				controlador.show();
			}

			if(td.getResult().isEmpty()) {
				alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("No ha escrito nada.");
				alert.setContentText("Introduzca el dni del cliente.");
				alert.showAndWait();
				crearReserva(event);
			}
			else {
				if(c == null) {
					alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("El cliente no existe.");
					alert.setContentText("Debe registrarse primero.");
					alert.showAndWait();
					initCasoDeUso(CREAR_CLIENTE, new ControladorCrearCliente()).show();
				}
			}
		}
		catch (NoSuchElementException e) {}
	}

	/*
	 * Muestra un dialogo para elegir la sucursal de la que se quiere obtener las reservas e inicia el caso de uso
	 * para listar las reservas de dicha sucursal.
	 */

	@FXML
	void listarReservasSucursal(ActionEvent event) throws LogicaExcepcion {
		List<Sucursal> sucursales = AlquilerVehiculos.dameAlquilerVehiculos().getSucursales();
		ChoiceDialog<Sucursal> cd = new ChoiceDialog<Sucursal>(sucursales.get(0),sucursales);
		cd.setHeaderText("Elija la sucursal de recogida, por favor");

		try{
			sucursal = cd.showAndWait().get();
			ControladorReservasPorSucursal controlador = initCasoDeUso(LISTAR_RESERVAS_SUCURSAL, new ControladorReservasPorSucursal());
			controlador.setSucursal(sucursal);
			controlador.rellenarTabla();
			controlador.show();
		}
		catch (NoSuchElementException e) {}
	}

	/*
	 * Muestra un dialogo para elegir la sucursal de la que se quiere obtener los vehiculos disponibles e inicia el caso de uso
	 * para listar los vehiculos de dicha sucursal.
	 */

	@FXML
	void listarVehiculosPorSucursal(ActionEvent event) throws LogicaExcepcion {
		List<Sucursal> sucursales = AlquilerVehiculos.dameAlquilerVehiculos().getSucursales();
		ChoiceDialog<Sucursal> cd = new ChoiceDialog<Sucursal>(sucursales.get(0),sucursales);
		cd.setHeaderText("Elija la sucursal, por favor");

		try{
			sucursal = cd.showAndWait().get();
			ControladorListarVehiculos controlador = initCasoDeUso(LISTAR_VEHICULOS, new ControladorListarVehiculos());
			controlador.setSucursal(sucursal);
			controlador.rellenar();
			controlador.show();
		}
		catch (NoSuchElementException e) {}
	}

	@FXML
	void entregarVehiculo(ActionEvent event) throws LogicaExcepcion {
		try {
			ControladorEntregarVehiculo controlador = initCasoDeUso(ENTREGAR_VEHICULO, new ControladorEntregarVehiculo());
			controlador.rellenar();
			controlador.show();
		}
		catch(NoSuchElementException e) {}
	}
	
	@FXML
	void salir(ActionEvent event) {
		Platform.exit();
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	private <T extends ControladorCasoDeUso> T initCasoDeUso(String urlVista, ControladorCasoDeUso class1) {
		return ControladorCasoDeUso.initCasoDeUso(urlVista, class1, primaryStage, ControladorPrincipal.this);
	}
}


