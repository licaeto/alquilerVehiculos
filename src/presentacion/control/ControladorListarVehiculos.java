package presentacion.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.AlquilerVehiculos;
import modelo.Coche;
import modelo.Sucursal;

public class ControladorListarVehiculos extends ControladorCasoDeUso {
	Alert alert;
	Sucursal sucursal;
	List<Coche> coches;

	@FXML
	TableView<Coche> tabla;
	@FXML
	TableColumn<Coche,String> matriculas;
	@FXML
	TableColumn<Coche,Double> kilometros;
	@FXML
	TableColumn<Coche,String> categorias;

	/*
	 * Establece la sucursal de la que queremos obtener la lista de vehículos.
	 */
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	/*
	 * Rellena la tabla con los coches de la sucursal establecida. Si no hay coches disponibles devuelve una alerta que informa al usuario de la situacion.
	 */
	public void rellenar() {
		coches = AlquilerVehiculos.dameAlquilerVehiculos().obtenerVehiculosPorSucursal(sucursal.getId());
		if(coches != null && coches.size() > 0) {
			tabla.getItems().addAll(coches);
		}
		else {
			alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("No hay vehiculos disponibles en la sucursal elegida.");
			alert.showAndWait();
		}
	}

	/*
	 * Vincula cada columna a un atributo de Coche.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("LISTADO DE VEHICULOS");

		matriculas.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getMatricula()));
		kilometros.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getKmsActuales()));
		categorias.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getNombreCategoria()));
	}

}
