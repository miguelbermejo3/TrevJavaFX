package app.gui;

import app.model.Usuario;
import app.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class IniciarSesionController extends AppController {

	@FXML
	private Button btnIniciarSesion;

	@FXML
	private AnchorPane panel;

	@FXML
	private TextField txtFieldNombreUsuario;

	@FXML
	private TextField txtFieldPass;
	@FXML
	private Button btnRegistro;

	@FXML
	void irARegistro(ActionEvent event) {

		cambiarVista(FXML_REGISTRO);

	}

	@FXML
	void iniciarSesion(ActionEvent event) {

		LoginService service = new LoginService();
		Usuario user = service.iniciarSesion(txtFieldNombreUsuario.getText(), txtFieldPass.getText());

		if (user!=null) {
			putUserDataObject("usuarioConectado",user);

			// Cambiar a otra vista si el inicio de sesión es exitoso
			cambiarVista(FXML_HOME);
		} else {
			// Mostrar un error por pantalla
			mostrarError("Error de inicio de sesión");
		}

	}
	
	

}
