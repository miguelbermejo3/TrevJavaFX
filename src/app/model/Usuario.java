package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

	private String nombreUsuario;
	private String password;
	private String correo;
	private int numTelefono;
	private List<Trabajo> trabajos;
	private List<Gasto> gastos;

	public Usuario() {

	}

	public Usuario(String nombreUsuario, String password, String correo, int numTelefono) {
		this.correo = correo;
		this.nombreUsuario = nombreUsuario;
		this.numTelefono = numTelefono;
		this.password = password;
		this.trabajos = new ArrayList<Trabajo>();
		this.gastos = new ArrayList<Gasto>();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(int numTelefono) {
		this.numTelefono = numTelefono;
	}

	public List<Trabajo> getTrabajos() {
		return trabajos;
	}

	public void setTrabajos(List<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}

	public void addTrabajo(Trabajo trabajo) {
		this.trabajos.add(trabajo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(correo, nombreUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(correo, other.correo) && Objects.equals(nombreUsuario, other.nombreUsuario);
	}

	@Override
	public String toString() {
		return "Usuario [nombreUsuario=" + nombreUsuario + ", password=" + password + ", correo=" + correo
				+ ", numTelefono=" + numTelefono + "]";
	}

	public List<Gasto> getGastos() {
		return gastos;
	}

	public void setGastos(List<Gasto> gastos) {
		this.gastos = gastos;
	}
	
	

}
