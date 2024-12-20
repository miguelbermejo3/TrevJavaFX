package app.gui;

import java.util.List;

import app.model.Gasto;
import app.model.Trabajo;
import app.model.Usuario;
import app.services.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class GraficasController extends AppController{

	   @FXML
	    private PieChart pieChart;

	    @FXML
	    private BarChart<String,Number> barChart;
	    
	    @FXML
	    private CategoryAxis xAxis;

	    @FXML
	    private NumberAxis yAxis;


	    public void initialize() {
	        Usuario usuario = (Usuario) getUserDataObject("usuarioConectado");

	        // Obtener los trabajos del usuario
	        List<Trabajo> trabajos = usuario.getTrabajos();

	        // Crear una instancia de LoginService
	        LoginService trabajoService = new LoginService();

	        // Obtener el dinero ganado en el mes anterior y en el mes actual
	        Double dineroMesAnterior = trabajoService.obtenerDineroGanadoMesAnterior(trabajos);
	        Double dineroMesActual = trabajoService.obtenerDineroGanadoMesActual(trabajos);

	        // Crear las partes del gráfico
	        PieChart.Data mesAnterior = new PieChart.Data("Mes Anterior", dineroMesAnterior);
	        PieChart.Data mesActual = new PieChart.Data("Mes Actual", dineroMesActual);

	        // Añadir los datos al gráfico
	        pieChart.getData().addAll(mesAnterior, mesActual);

	        // Añadir las etiquetas con los valores sobre las porciones del gráfico
	        for (PieChart.Data data : pieChart.getData()) {
	            String value = String.format("%.2f", data.getPieValue());  // Formatear el valor con dos decimales

	            // Crear un texto con el valor y añadirlo sobre la porción
	            Text valueText = new Text(value);

	            // Calcular la posición del texto basado en la porción
	            double angle = data.getPieValue() / pieChart.getData().stream().mapToDouble(PieChart.Data::getPieValue).sum() * 360;
	            double xPos = Math.cos(Math.toRadians(angle)) * 100 + pieChart.getWidth() / 2;
	            double yPos = Math.sin(Math.toRadians(angle)) * 100 + pieChart.getHeight() / 2;

	            valueText.setX(xPos);
	            valueText.setY(yPos);

	            // Hacer que el texto sea visible
	            valueText.setVisible(true);

	            // Agregar el texto al contenedor principal del gráfico
	            Pane pane = (Pane) pieChart.getParent();
	            pane.getChildren().add(valueText);  // Añadir el texto al contenedor principal
	        }
	        
	     // Obtener los gastos del usuario
	        List<Gasto> gastos = usuario.getGastos();
	        Double gastoMesActual = trabajoService.obtenerGastoMesActual(gastos);  // Método similar a obtenerDineroGanadoMesActual()

	        // Crear el gráfico de barras
	        XYChart.Series<String, Number> series = new XYChart.Series<>();
	        series.setName("Ingresos vs Gastos");

	        // Añadir los datos al gráfico de barras
	        series.getData().add(new XYChart.Data<>("Ingresos", dineroMesActual));
	        series.getData().add(new XYChart.Data<>("Gastos", gastoMesActual));

	        // Añadir la serie al gráfico
	        barChart.getData().clear();  // Limpiar cualquier dato anterior
	        barChart.getData().add(series);
	        
	        
	        
	        
	        
	        
	        
	        
	    }

	    @FXML
	    void volverAHome(ActionEvent event) {
	    	cambiarVista(FXML_HOME);
	    }
    }
    

