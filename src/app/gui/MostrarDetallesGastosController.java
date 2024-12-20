package app.gui;

import app.model.Gasto;
import app.model.Usuario;
import app.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MostrarDetallesGastosController extends AppController{

	
	  @FXML
	    private TextField txtCategoria;

	    @FXML
	    private TextField txtFecha;

	    @FXML
	    private TextField txtImporte;

	    @FXML
	    void editarGasto(ActionEvent event) {
	    	 if (txtImporte.getText().isEmpty() || txtCategoria.getText().isEmpty() || txtFecha.getText().isEmpty()) {
	    	        System.out.println("Por favor, complete todos los campos.");
	    	        return;
	    	    }

	    	    try {
	    	        Usuario user = (Usuario) getUserDataObject("usuarioConectado");
	    	        LoginService service = new LoginService();
	    	        Gasto gasto = new Gasto();
	    	        gasto.setCantidad(Integer.parseInt(txtImporte.getText()));
	    	        gasto.setCategoria(txtCategoria.getText());
	    	        gasto.setFecha(txtFecha.getText());

	    	        if (user != null) {
	    	            boolean actualizado = service.actualizarGasto(user, gasto);
	    	            if (actualizado) {
	    	                System.out.println("Gasto actualizado correctamente.");
	    	            } else {
	    	                System.out.println("No se pudo actualizar el gasto.");
	    	            }
	    	        }
	    	    } catch (NumberFormatException e) {
	    	        System.out.println("El importe debe ser un número válido.");
	    	    }
	    	
	    }

	    @FXML
	    void volverAHome(ActionEvent event) {
	    	cambiarVista(FXML_HOME);
	    }
	    @FXML
	    public void initialize() {
	    Gasto gasto=(Gasto) getUserDataObject("gasto");
	    	
	    txtCategoria.setText(gasto.getCategoria());
	    txtFecha.setText(gasto.getFecha());
	    txtImporte.setText(gasto.getCantidad().toString());
	    	
	    	
	    }
	
	
	
	
	
	
	
	
	
	
	
}
