package app.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import app.model.Trabajo;
import app.model.Usuario;
import app.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CrearTrabajoController extends AppController {

	@FXML
	private Button btnCrear;

	@FXML
	private Button btnVolver;

	@FXML
	private DatePicker dateFecha;

	@FXML
	private TextField txtFieldEntrada;

	@FXML
	private TextField txtFieldPrecio;

	@FXML
	private TextField txtFieldSalida;
	

    @FXML
    private TextField txtPropina;

	@FXML
	void crearTrabajo(ActionEvent event) {

		Usuario user = (Usuario) getUserDataObject("usuarioConectado");
		Trabajo trabajo = new Trabajo();
		LocalDate date = dateFecha.getValue();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		trabajo.setFecha(date.format(formatter));
		trabajo.setHoraEntrada(txtFieldEntrada.getText());
		trabajo.setHoraSalida(txtFieldSalida.getText());
		trabajo.setPrecioLaHora(Integer.parseInt(txtFieldPrecio.getText()));
			
		trabajo.setPropina(Double.valueOf(txtPropina.getText()));
		trabajo.setDineroGanado(calcularPrecioTotal(txtFieldEntrada.getText(),txtFieldSalida.getText(),txtFieldPrecio.getText(),txtPropina.getText()));	
		System.out.println(trabajo.getDineroGanado());
		LoginService service = new LoginService();

		boolean actualizado = service.agregarTrabajoAUsuario(user.getCorreo(), trabajo);
		if (actualizado) {
			System.out.println("trabajo añadido correctamente");
			user.addTrabajo(trabajo);
		} else {
			System.out.println("Error al añadir el trabajo");
		}

	}

	public Double calcularPrecioTotal(String horaEntrada, String horaSalida, String precioLaHora,String propina) {

		Integer horaEntradaInteger = Integer.parseInt(horaEntrada);
		Integer horaSalidaInteger = Integer.parseInt(horaSalida);
		Integer horasTotales = horaSalidaInteger - horaEntradaInteger;
		Integer precioTotal = horasTotales *Integer.parseInt(precioLaHora) ;
		Double precioFinal=precioTotal+Double.valueOf(propina);
		return precioFinal;
	}

	@FXML
	void volverHome(ActionEvent event) {

		cambiarVista(FXML_HOME);
	}

}
