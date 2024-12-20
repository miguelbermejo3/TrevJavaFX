package app.model;


import java.util.Objects;

public class Trabajo {

	private String horaEntrada;
	private String horaSalida;
	private Integer precioLaHora;
	private String fecha;
	private Double dineroGanado;
	private Double propina;
	
	
	
	public String getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public String getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}
	public Integer getPrecioLaHora() {
		return precioLaHora;
	}
	public void setPrecioLaHora(Integer precioLaHora) {
		this.precioLaHora = precioLaHora;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	@Override
	public int hashCode() {
		return Objects.hash(fecha);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trabajo other = (Trabajo) obj;
		return Objects.equals(fecha, other.fecha);
	}
	@Override
	public String toString() {
		return "Trabajo [horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", precioLaHora=" + precioLaHora
				+ ", fecha=" + fecha + "]";
	}
	public Double getDineroGanado() {
		return dineroGanado;
	}
	public void setDineroGanado(Double dineroGanado) {
		this.dineroGanado = dineroGanado;
	}
	public Double getPropina() {
		return propina;
	}
	public void setPropina(Double propina) {
		this.propina = propina;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
