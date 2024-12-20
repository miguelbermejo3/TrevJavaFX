package app.gui;

import java.time.LocalDate;

import app.model.Gasto;
import app.model.Usuario;
import app.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class GastosController extends AppController {

	@FXML
	private ComboBox<String> cboxCategoria;

	@FXML
	private TextField txtCantidad;

	@FXML
	void volverAHome(ActionEvent event) {
		cambiarVista(FXML_HOME);
	}

	@FXML
	public void initialize() {
		 // Añade las opciones al ComboBox
	    cboxCategoria.getItems().addAll("Ocio", "Alimentación", "Transporte", "Otros");
	    
	    // Establece un valor predeterminado
	    cboxCategoria.setValue("Ocio");

	}

	@FXML
	void anhadirGasto(ActionEvent event) {
		LoginService service = new LoginService();
		Usuario user = (Usuario) getUserDataObject("usuarioConectado");

		Integer cantidad = Integer.parseInt(txtCantidad.getText());
		String categoria = cboxCategoria.getValue();

		Gasto gasto = new Gasto();

		gasto.setCantidad(cantidad);
		gasto.setCategoria(categoria);
		String fecha=LocalDate.now().toString();
		gasto.setFecha(fecha);
		if(cantidad!=null && categoria!=null) {
			service.agregarGastoAUsuario(user.getCorreo(), gasto);
		}else {
			System.out.println("Error al rellenar el formulario.Rellene todos los datos");
		}

		

	}

}
