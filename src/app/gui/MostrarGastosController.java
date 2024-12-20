package app.gui;

import java.util.ArrayList;
import java.util.List;

import app.model.Gasto;
import app.model.Usuario;
import app.services.LoginService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MostrarGastosController extends AppController {

	@FXML
	private TableColumn<Gasto, String> columnaCategoria;

	@FXML
	private TableColumn<Gasto, String> columnaFecha;

	@FXML
	private TableColumn<Gasto, Integer> columnaImporte;

	@FXML
	private TableView<Gasto> table;

	private ObservableList<Gasto> datos;
	
	  @FXML
	    private TextField txtFiltro;
  

	@FXML
	void initialize() {

		PropertyValueFactory<Gasto, String> factoryValueFecha = new PropertyValueFactory<>("fecha");
		PropertyValueFactory<Gasto, String> factoryValueCategoria = new PropertyValueFactory<>("categoria");
		PropertyValueFactory<Gasto, Integer> factoryValueImporte = new PropertyValueFactory<>("cantidad");

		columnaFecha.setCellValueFactory(factoryValueFecha);
		columnaCategoria.setCellValueFactory(factoryValueCategoria);
		columnaImporte.setCellValueFactory(factoryValueImporte);

		datos = FXCollections.observableArrayList();
		table.setItems(datos);

		cargarGastos();

		table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null) {
				mostrarDetallesGastos(newValue);
			}

		});

	}

	public void cargarGastos() {
		Usuario user = (Usuario) getUserDataObject("usuarioConectado");
		// Verifica si el usuario está correctamente cargado
	    if (user == null) {
	        System.out.println("Usuario no encontrado.");
	        return;
	    }

		LoginService service = new LoginService();
		Task<Void> task = new Task<Void>() {

			List<Gasto> gastos;

			@Override
			protected Void call() throws Exception {
				System.out.println("ejecutando tarea en segundo plano");
				// lo que tengo que hacer en otro hilo
				gastos = service.consultarGastosDeUsuario(user.getCorreo());
				System.out.println(gastos+"gastos en la tabla");
				return null;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				table.setEffect(null);
				datos.setAll(gastos); // cuando va bien
				updateProgress(100, 100);
			}

			@Override
			protected void failed() {
				super.failed();
				table.setEffect(null);
				datos.clear(); // cuando falla
				mostrarError("No hay registros en la bbdd con ese filtro");
				updateProgress(100, 100);
			}

		};
		
		 // Inicia el Task en un nuevo hilo
	    System.out.println("Iniciando tarea...");
	    new Thread(task).start();  // Esta línea asegura que el Task se ejecute en otro hilo
		
		/*
		if (user != null && user.getGastos() != null) {
			datos.setAll(user.getGastos());
		} else {
			System.out.println("El usuario no tiene gastos");
		}
		
		*/

	}

	public void mostrarDetallesGastos(Gasto gastoSeleccionado) {

		putUserDataObject("gasto", gastoSeleccionado);
		System.out.println(gastoSeleccionado);
		cambiarVista(FXML_DETALLES_GASTOS);

	}
	

    @FXML
    void buscarPorFiltro(ActionEvent event) {

    	  // Obtener el texto del filtro desde el TextField
        String filtro = txtFiltro.getText().toLowerCase(); // Convertir a minúsculas para hacer la búsqueda no sensible a mayúsculas/minúsculas

        // Si el filtro no está vacío, filtrar los gastos
        if (!filtro.isEmpty()) {
            // Filtrar los gastos basados en la categoría
            List<Gasto> gastosFiltrados = new ArrayList<>();

            for (Gasto gasto : datos) {
                // Verificar si la categoría contiene el texto del filtro
                if (gasto.getCategoria().toLowerCase().contains(filtro)) {
                    gastosFiltrados.add(gasto);
                }
            }
         // Verifica que los gastos filtrados contienen los resultados esperados
            System.out.println("Gastos filtrados: " + gastosFiltrados);


            // Actualizar la tabla con los gastos filtrados
            datos.setAll(gastosFiltrados);
        } else {
            // Si el filtro está vacío, cargar todos los gastos
            cargarGastos();
        }
        // Forzar la actualización de la tabla
        table.refresh();
    	
    }
	
	  @FXML
	    void volverAHome(ActionEvent event) {
		  cambiarVista(FXML_HOME);
	    }

}
