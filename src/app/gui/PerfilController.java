package app.gui;

import app.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PerfilController extends AppController{

	
	@FXML
    private Label lblCorreo;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    void volverAHome(ActionEvent event) {
    	cambiarVista(FXML_HOME);
    }

	@FXML
	void initialize() {
		
		Usuario user= (Usuario) getUserDataObject("usuarioConectado");
		
		lblCorreo.setText(user.getCorreo());
		lblNombre.setText(user.getNombreUsuario());
		int num=user.getNumTelefono();
		lblTelefono.setText(String.valueOf(num));
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
