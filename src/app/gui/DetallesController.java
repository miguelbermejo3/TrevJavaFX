package app.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import app.model.Gasto;
import app.model.Trabajo;
import app.model.Usuario;
import app.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DetallesController extends AppController {

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnVolver;

	@FXML
	private DatePicker pickerFecha;

	@FXML
	private TextField txtEntrada;

	@FXML
	private TextField txtGanado;

	@FXML
	private TextField txtPrecio;

	@FXML
	private TextField txtSalida;
	
    @FXML
    private TextField txtPropina;

	@FXML
	public void initialize() {

		Trabajo trabajo = (Trabajo) getUserDataObject("trabajo");
		txtEntrada.setText(trabajo.getHoraEntrada());
		txtGanado.setText(trabajo.getDineroGanado().toString());
		txtPrecio.setText(trabajo.getPrecioLaHora().toString());
		txtSalida.setText(trabajo.getHoraSalida());

		// Definir el formato esperado de la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Convertir el String en LocalDate
		LocalDate fecha = LocalDate.parse(trabajo.getFecha(), formatter);

		pickerFecha.setValue(fecha);
		
		txtPropina.setText(trabajo.getPropina().toString());

	}

	@FXML
	void editarTrabajo(ActionEvent event) {
		 Usuario usuario = (Usuario) getUserDataObject("usuarioConectado");
		Trabajo trabajo = (Trabajo) getUserDataObject("trabajo");
		
		trabajo.setFecha(pickerFecha.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        trabajo.setHoraEntrada(txtEntrada.getText());
        trabajo.setHoraSalida(txtSalida.getText());
        trabajo.setPrecioLaHora(Integer.parseInt(txtPrecio.getText()));
        trabajo.setDineroGanado(Double.valueOf(txtGanado.getText()));
		trabajo.setPropina(Double.valueOf(txtPropina.getText()));
        LoginService loginService = new LoginService();
        boolean actualizado = loginService.actualizarTrabajo(usuario,trabajo);
        
        if (actualizado) {
           System.out.println( ("Trabajo actualizado correctamente."));
        } else {
            mostrarError("No se pudo actualizar el trabajo.");
        }
		
		
	}
	
	 @FXML
	    void eliminarTrabajo(ActionEvent event) {

		 LoginService service= new LoginService();
		 Usuario user= (Usuario) getUserDataObject("usuarioConectado");
		 Trabajo trabajo = (Trabajo) getUserDataObject("trabajo");
		 
		 service.borrarTrabajoDeUsuario(user.getCorreo(), trabajo);
		 
		 System.out.println("Trabajo eliminado exitosamente");
		 
		
		
		 
		 cambiarVista(FXML_HOME);
		 
		 
		 
	    }

	@FXML
	void irAHome(ActionEvent event) {
		cambiarVista(FXML_HOME);
		
	}
}
