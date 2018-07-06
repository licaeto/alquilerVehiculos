package presentacion.control;

import java.io.IOException;

//import modelo.Sucursal;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class ControladorCasoDeUso implements Initializable {
	protected Stage stage; 
	protected ControladorPrincipal controladorPrincipal;
	public void setControladorPrincipal(ControladorPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void show() {
		stage.show();
	}
	
	/*
	 * Carga la vista correspondiente a la url de un caso de uso y devuelve el controlador de la misma.
	 */
	
	public static <T extends ControladorCasoDeUso> T initCasoDeUso(String urlVista, ControladorCasoDeUso controlClass, Stage owner, ControladorPrincipal controladorPrincipal) {
		FXMLLoader fxmlLoader = new FXMLLoader(ControladorCasoDeUso.class.getResource(urlVista));
		T controlador = null;
		try {
			Parent parent = fxmlLoader.load();
			controlador = fxmlLoader.getController();
			controlador.stage.setScene(new Scene(parent));
			controlador.stage.initOwner(owner);
			controlador.setControladorPrincipal(controladorPrincipal);
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
		}
		return controlador;
	}
}
