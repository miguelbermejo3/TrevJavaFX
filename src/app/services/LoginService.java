package app.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import app.model.Gasto;
import app.model.Trabajo;
import app.model.Usuario;
import app.mongodb.MongoSession;

public class LoginService {

	public String insertarUsuario(Usuario usuario) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);
		InsertOneResult result = c.insertOne(usuario);
		return result.getInsertedId().toString();
	}

	public Usuario consultarUsuario(String id) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);
		Bson filter = Filters.eq("_id", new ObjectId(id));
		FindIterable<Usuario> result = c.find(filter);
		return result.first();
	}

	public void borrarUsuario(String id) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);
		Bson filter = Filters.eq("_id", new ObjectId(id));
		c.deleteOne(filter);
	}

	public List<Usuario> consultarTodasPersonas() {
		List<Usuario> personas = new ArrayList<Usuario>();
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);
		FindIterable<Usuario> result = c.find();
		MongoCursor<Usuario> cursor = result.cursor();
		while (cursor.hasNext()) {
			personas.add(cursor.next());
		}
		return personas;
	}

	// Método para verificar las credenciales al iniciar sesión
	public Usuario iniciarSesion(String nombreUsuario, String contraseña) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		// Crear el filtro para buscar el nombre de usuario y la contraseña
		Bson filter = Filters.and(Filters.eq("nombreUsuario", nombreUsuario), Filters.eq("password", contraseña));

		// Realizar la consulta para encontrar el usuario
		Usuario usuario = c.find(filter).first();

		if (usuario != null) {
			// Usuario encontrado
			return usuario;
		}
		return usuario;
	}

	public boolean agregarTrabajoAUsuario(String correo, Trabajo trabajo) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		Bson filter = Filters.eq("correo", correo);

		Bson update = com.mongodb.client.model.Updates.push("trabajos", trabajo);

		try {

			var resultado = c.updateOne(filter, update);
			return resultado.getModifiedCount() > 0;

		} catch (Exception e) {
			System.out.println("Error al agregar el trabajo");
			return false;
		}

	}

	public List<Trabajo> consultarTrabajosDeUsuario(String correo) {
		List<Trabajo> trabajos = new ArrayList<>();
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		// Filtro para buscar al usuario por su correo
		Bson filter = Filters.eq("correo", correo);

		// Buscar el usuario en la colección
		Usuario usuario = c.find(filter).first();

		if (usuario != null) {
			// Si el usuario existe, obtener la lista de trabajos
			trabajos = usuario.getTrabajos(); // Obtener la lista de trabajos del objeto Usuario
		}

		return trabajos;
	}

	public boolean actualizarTrabajo(Usuario usuario, Trabajo trabajo) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		// Filtro para encontrar al usuario por su correo
		Bson filter = Filters.eq("correo", usuario.getCorreo());

		// Buscar el usuario en la colección
		Usuario usuarioExistente = c.find(filter).first();

		if (usuarioExistente != null) {
			// Encontrar el índice del trabajo en la lista de trabajos del usuario
			List<Trabajo> trabajos = usuarioExistente.getTrabajos();

			for (int i = 0; i < trabajos.size(); i++) {
				Trabajo trabajoExistente = trabajos.get(i);

				// Si el trabajo coincide por fecha y hora de entrada, lo actualizamos
				if (trabajoExistente.getFecha().equals(trabajo.getFecha())
						&& trabajoExistente.getHoraEntrada().equals(trabajo.getHoraEntrada())) {

					// Actualizamos los campos del trabajo
					trabajos.set(i, trabajo); // Reemplazamos el trabajo existente con el actualizado

					// Creamos la actualización en MongoDB
					Bson update = com.mongodb.client.model.Updates.set("trabajos", trabajos);
					c.updateOne(filter, update);
					return true; // Trabajo actualizado exitosamente
				}
			}
		}

		return false; // No se encontró el trabajo
	}

	// Método para obtener el dinero ganado en un mes específico (mes y año)
	public double obtenerDineroGanadoMes(List<Trabajo> trabajos, int mes, int anio) {
		// Filtrar los trabajos del mes y año
		return trabajos.stream().filter(trabajo -> {
			LocalDate fechaTrabajo = LocalDate.parse(trabajo.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			return fechaTrabajo.getMonthValue() == mes && fechaTrabajo.getYear() == anio;
		}).mapToDouble(trabajo -> trabajo.getDineroGanado() != null ? trabajo.getDineroGanado() : 0.0) // Convertir
																										// Double a
																										// double
				.sum(); // Sumar los valores
	}

	// Obtener el dinero ganado en el mes anterior
	public double obtenerDineroGanadoMesAnterior(List<Trabajo> trabajos) {
		LocalDate fechaActual = LocalDate.now();
		int mesAnterior = fechaActual.minusMonths(1).getMonthValue();
		int anioAnterior = fechaActual.minusMonths(1).getYear();
		return obtenerDineroGanadoMes(trabajos, mesAnterior, anioAnterior);
	}

	// Obtener el dinero ganado en el mes actual
	public double obtenerDineroGanadoMesActual(List<Trabajo> trabajos) {
		LocalDate fechaActual = LocalDate.now();
		int mesActual = fechaActual.getMonthValue();
		int anioActual = fechaActual.getYear();
		return obtenerDineroGanadoMes(trabajos, mesActual, anioActual);
	}

	// Método para obtener el gasto total en un mes específico (mes y año)
	public double obtenerGastoMes(List<Gasto> gastos, int mes, int anio) {
		// Filtrar los gastos del mes y año
		return gastos.stream().filter(gasto -> {
			LocalDate fechaGasto = LocalDate.parse(gasto.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			return fechaGasto.getMonthValue() == mes && fechaGasto.getYear() == anio;
		}).mapToDouble(gasto -> gasto.getCantidad() != null ? gasto.getCantidad() : 0.0) // Convertir Double a double
				.sum(); // Sumar los valores
	}

	// Método para obtener el gasto total en el mes actual
	public double obtenerGastoMesActual(List<Gasto> gastos) {
		LocalDate fechaActual = LocalDate.now();
		int mesActual = fechaActual.getMonthValue();
		int anioActual = fechaActual.getYear();
		return obtenerGastoMes(gastos, mesActual, anioActual);
	}

	public boolean agregarGastoAUsuario(String correo, Gasto gasto) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		Bson filter = Filters.eq("correo", correo);

		Bson update = com.mongodb.client.model.Updates.push("gastos", gasto);

		try {

			var resultado = c.updateOne(filter, update);
			return resultado.getModifiedCount() > 0;

		} catch (Exception e) {
			System.out.println("Error al agregar el trabajo");
			return false;
		}

	}

	public List<Gasto> consultarGastosDeUsuario(String correo) {
		List<Gasto> gastos = new ArrayList<>();
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		// Filtro para buscar al usuario por su correo
		Bson filter = Filters.eq("correo", correo);

		// Buscar el usuario en la colección
		Usuario usuario = c.find(filter).first();

		if (usuario != null) {
			// Si el usuario existe, obtener la lista de trabajos
			gastos = usuario.getGastos(); // Obtener la lista de gastos del objeto Usuario
			System.out.println(gastos);
		}

		return gastos;
	}

	public boolean actualizarGasto(Usuario usuario, Gasto gasto) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		Bson filter = Filters.eq("correo", usuario.getCorreo());
		Usuario usuarioExistente = c.find(filter).first();

		if (usuarioExistente != null) {
			List<Gasto> gastos = usuarioExistente.getGastos();

			for (int i = 0; i < gastos.size(); i++) {
				Gasto gastoExistente = gastos.get(i);

				// Validar que los campos no sean nulos antes de comparar
				if (gastoExistente.getFecha() != null && gastoExistente.getFecha().equals(gasto.getFecha())
						&& gastoExistente.getCategoria() != null
						&& gastoExistente.getCategoria().equals(gasto.getCategoria())
						&& gastoExistente.getCantidad() != null
						&& gastoExistente.getCantidad().equals(gasto.getCantidad())) {

					gastos.set(i, gasto); // Actualizar gasto
					Bson update = com.mongodb.client.model.Updates.set("gastos", gastos);
					c.updateOne(filter, update);
					return true;
				}
			}
		}

		return false; // Si no se encontró el gasto
	}

	public boolean borrarTrabajoDeUsuario(String correo, Trabajo trabajo) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		// Filtro para encontrar al usuario por correo
		Bson filter = Filters.eq("correo", correo);

		// Operación para eliminar un trabajo específico de la lista de trabajos
		Bson update = com.mongodb.client.model.Updates.pull("trabajos", trabajo);

		try {
			var resultado = c.updateOne(filter, update);
			return resultado.getModifiedCount() > 0;
		} catch (Exception e) {
			System.out.println("Error al borrar el trabajo: " + e.getMessage());
			return false;
		}
	}

	public boolean borrarGastoDeUsuario(String correo, Gasto gasto) {
		MongoDatabase db = MongoSession.getDatabase();
		MongoCollection<Usuario> c = db.getCollection("usuarios", Usuario.class);

		// Filtro para encontrar al usuario por correo
		Bson filter = Filters.eq("correo", correo);

		// Operación para eliminar un gasto específico de la lista de gastos
		Bson update = com.mongodb.client.model.Updates.pull("gastos", gasto);

		try {
			var resultado = c.updateOne(filter, update);
			return resultado.getModifiedCount() > 0;
		} catch (Exception e) {
			System.out.println("Error al borrar el gasto: " + e.getMessage());
			return false;
		}
	}

}
