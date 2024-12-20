package app.gui;

import java.util.List;

import app.model.Trabajo;
import app.model.Usuario;
import app.services.LoginService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrabajadosController extends AppController {

	@FXML
	private TableView<Trabajo> tabla;

	@FXML
	private TableColumn<Trabajo, String> columnaEntrada;

	@FXML
	private TableColumn<Trabajo, String> columnaFecha;

	@FXML
	private TableColumn<Trabajo, Integer> columnaPrecio;

	@FXML
	private TableColumn<Trabajo, String> columnaSalida;
	
    @FXML
    private TableColumn<Trabajo, Integer> columnaGanado;
    

    @FXML
    private TableColumn<Trabajo, Integer> columnaPropina;

	private ObservableList<Trabajo> datos;
	 @FXML
	    private Button btnVolver;

	@FXML
	public void initialize() {
		PropertyValueFactory<Trabajo, String> factoryValueFecha = new PropertyValueFactory<>("fecha");
		PropertyValueFactory<Trabajo, String> factoryValueHoraEntrada = new PropertyValueFactory<>("horaEntrada");
		PropertyValueFactory<Trabajo, String> factoryValueHoraSalida = new PropertyValueFactory<>("horaSalida");
		PropertyValueFactory<Trabajo, Integer> factoryValuePrecioLaHora = new PropertyValueFactory<>("precioLaHora");
		PropertyValueFactory<Trabajo, Integer> factoryValueDineroGanado= new PropertyValueFactory<>("dineroGanado");
		PropertyValueFactory<Trabajo, Integer> factoryValuePropina= new PropertyValueFactory<>("propina");
		
		columnaFecha.setCellValueFactory(factoryValueFecha);
		columnaEntrada.setCellValueFactory(factoryValueHoraEntrada);
		columnaSalida.setCellValueFactory(factoryValueHoraSalida);
		columnaPrecio.setCellValueFactory(factoryValuePrecioLaHora);
		columnaGanado.setCellValueFactory(factoryValueDineroGanado);
		columnaPropina.setCellValueFactory(factoryValuePropina);
		datos = FXCollections.observableArrayList();
		tabla.setItems(datos);

		actualizarTrabajosBbdd();
		cargarTrabajos();
		
		tabla.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
			
			
			if(newValue!=null) {
				mostrarDetallesTrabajo(newValue);
			}
			
			
			
		});

	}

	private void cargarTrabajos() {

		Usuario usuario = (Usuario) getUserDataObject("usuarioConectado");
		LoginService service= new LoginService();
		Task<Void> task = new Task<Void>() {

			List<Trabajo> trabajos;

			@Override
			protected Void call() throws Exception {
				// lo que tengo que hacer en otro hilo
				trabajos = service.consultarTrabajosDeUsuario(usuario.getCorreo());
				return null;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				tabla.setEffect(null);
				datos.setAll(trabajos); // cuando va bien
				updateProgress(100, 100);
			}

			@Override
			protected void failed() {
				super.failed();
				tabla.setEffect(null);
				datos.clear(); // cuando falla
				mostrarError("No hay registros en la bbdd con ese filtro");
				updateProgress(100, 100);
			}

		};

		if (usuario != null && usuario.getTrabajos() != null) {
			datos.setAll(usuario.getTrabajos());
		} else {
			System.out.println("El usuario no tiene trabajos");
		}

	}
	
	public void mostrarDetallesTrabajo(Trabajo trabajoSeleccionado) {
		
		
		
		
		putUserDataObject("trabajo",trabajoSeleccionado);
		System.out.println(trabajoSeleccionado);
		
		cambiarVista(FXML_DETALLES_TRABAJO);
		
		
		
		
	}
	
    @FXML
    void volverAHome(ActionEvent event) {

    	cambiarVista(FXML_HOME);
    }

}
