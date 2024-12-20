package app.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.App;
import app.model.Trabajo;
import app.model.Usuario;
import app.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppController {

	private static final String PATH_BASE_LOGIN = "/app/gui/login/";
	private static final String PATH_BASE_PANTALLA = "/app/gui/pantalla/";

	public static String FXML_INICIO_SESION = PATH_BASE_LOGIN + "login.fxml";
	public static String FXML_REGISTRO = PATH_BASE_LOGIN + "registro.fxml";
	public static String FXML_HOME = PATH_BASE_PANTALLA + "home.fxml";
	public static String FXML_CREAR_TRABAJO = PATH_BASE_PANTALLA + "crearTrabajo.fxml";
	public static String FXML_TRABAJADOS=PATH_BASE_PANTALLA+"trabajados.fxml";
	public static String FXML_DETALLES_TRABAJO=PATH_BASE_PANTALLA+"detalleTrabajo.fxml";
	public static String FXML_GRAFICOS=PATH_BASE_PANTALLA+"graficas.fxml";
	public static String CSS_DARK_THEME = PATH_BASE_LOGIN + "dark-theme.css";
	public static String FXML_GASTOS=PATH_BASE_PANTALLA+"gastos.fxml";
	public static String FXML_MOSTRAR_GASTOS=PATH_BASE_PANTALLA+"mostrarGastos.fxml";
	public static String FXML_DETALLES_GASTOS=PATH_BASE_PANTALLA+"detalleGastos.fxml";
	public static String FXML_PERFIL=PATH_BASE_PANTALLA+"perfil.fxml";
	public static final String MODO_OSCURO = "MODO_OSCURO";

	private static Stage primaryStage;

	@FXML
	private AnchorPane panel;

	public AppController() {
	}

	public AppController(Stage stage) {
		primaryStage = stage;
		stage.setUserData(new HashMap<String, Object>());
	}

	public Object getUserDataObject(String key) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) primaryStage.getUserData();
		return map.get(key);
	}

	public void putUserDataObject(String key, Object data) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) primaryStage.getUserData();
		map.put(key, data);
	}

	public AppController cambiarVista(String fxml) {
		try {
			URL url = App.class.getResource(fxml);
			FXMLLoader loader = new FXMLLoader(url);
			Scene scene = new Scene(loader.load());
			primaryStage.setScene(scene);
			return loader.getController();
		} catch (IOException e) {
			throw new RuntimeException("No se ha podido cargar fxml con ruta " + fxml, e);
		}
	}

	public void cargarVista(String fxml) {
		try {
			URL url = App.class.getResource(fxml);
			FXMLLoader loader = new FXMLLoader(url);
			Parent nuevaVista = loader.load();
			// Limpiar el contenido actual del contenedor dinámico
			panel.getChildren().clear();
			panel.getChildren().add(nuevaVista);

		} catch (IOException e) {
			throw new RuntimeException("No se ha podido cargar fxml con ruta " + fxml, e);
		}
	}

	public void mostrarError(String mensaje) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Error");
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public void cargarModoOscuro() {
		Boolean modoOscuro = (Boolean) getUserDataObject(MODO_OSCURO);
		if (modoOscuro) {
			panel.getStylesheets().add(CSS_DARK_THEME); // añade la hoja de estilos
		} else {
			panel.getStylesheets().clear(); // borramos las hojas de estilos añadidas
		}
	}
	
	public void actualizarTrabajosBbdd() {
	    // Obtener los datos actuales del usuario
	    Usuario usuario = (Usuario) getUserDataObject("usuarioConectado");
	    LoginService service = new LoginService();

	    // Llamar a la función que recarga los trabajos desde la base de datos
	    if (usuario != null) {
	        // Cargar los trabajos desde la base de datos
	        List<Trabajo> trabajosActualizados = service.consultarTrabajosDeUsuario(usuario.getCorreo());

	        // Actualizar los trabajos en el usuario
	        usuario.setTrabajos(trabajosActualizados);

	        // Guardar la lista de trabajos actualizada
	        putUserDataObject("usuarioConectado", usuario);
	    }
	}

}
