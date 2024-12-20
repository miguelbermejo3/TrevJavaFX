package app.gui;

import app.model.Usuario;
import app.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController extends AppController {

	
	

    @FXML
    private Button btnVolver;

    @FXML
    private PasswordField passField;

    @FXML
    private PasswordField passField2;

    @FXML
    private TextField txtFieldCorreo;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldTelefono;

    @FXML
    void volverALogin(ActionEvent event) {

    	cambiarVista(FXML_INICIO_SESION);
    }
    
    @FXML
    void registrarUsuario(ActionEvent event) {
    	
    	Usuario user= new Usuario();
    	user.setCorreo(txtFieldCorreo.getText());
    	user.setNombreUsuario(txtFieldNombre.getText());
    	user.setNumTelefono(Integer.parseInt( txtFieldTelefono.getText()));
    	user.setPassword(passField.getText());
    	
    	LoginService service= new LoginService();
    	service.insertarUsuario(user);

    }
	
	
	
}
