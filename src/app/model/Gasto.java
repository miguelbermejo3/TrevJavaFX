package app.model;

public class Gasto {

	private Integer cantidad;
	private String categoria;
	private String fecha;
	
	
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Gasto [Cantidad=" + cantidad + ", categoria=" + categoria + ", fecha=" + fecha + "]";
	}
	
	
	
}
