package app.gui;

import app.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class HomeController extends AppController {

	@FXML
	private Label lblNombre;

	@FXML
	private BorderPane borderPane;

	@FXML
	private MenuItem itemCrearTrabajo;

	@FXML
	void irACrearTrabajo(ActionEvent event) {

		cambiarVista(FXML_CREAR_TRABAJO);

	}

	@FXML
	private void initialize() {
		Usuario usuario = (Usuario) getUserDataObject("usuarioConectado");
		System.out.println(usuario);
		if (usuario != null) {
			lblNombre.setText("Bienvenido: " + usuario.getNombreUsuario());
		} else {
			lblNombre.setText("Bienvenido");
		}

	}

	@FXML
	void irATrabajados(ActionEvent event) {
		cambiarVista(FXML_TRABAJADOS);
	}

	@FXML
	void irAEditar(ActionEvent event) {
		// cambiarVista(FXML_DETALLES_TRABAJO);
	}

	@FXML
	void irAGraficos(ActionEvent event) {
		cambiarVista(FXML_GRAFICOS);
	}

	@FXML
	void irAGastos(ActionEvent event) {
		cambiarVista(FXML_GASTOS);
	}

	@FXML
	void mostrarGastos(ActionEvent event) {
		cambiarVista(FXML_MOSTRAR_GASTOS);
	}
	
    @FXML
    void cerrarSesion(ActionEvent event) {
    	
    	Usuario user= new Usuario();
    	putUserDataObject("usuarioConectado",user);
    	cambiarVista(FXML_INICIO_SESION);
    	
    	
    }
    
    @FXML
    void irAPerfil(ActionEvent event) {
    	cambiarVista(FXML_PERFIL);
    }

}
