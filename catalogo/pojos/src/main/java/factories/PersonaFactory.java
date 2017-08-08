package factories;

import java.time.LocalDate;

import pojos.Direccion;
import pojos.Empresa;
import pojos.IPersona;
import pojos.Imagen;
import pojos.Persona;
import pojos.Rol;
import pojos.Usuario;

public class PersonaFactory {

	public PersonaFactory() {
	}

	public IPersona getPersona() {
		return new Persona();
	}
	
	public IPersona getUsuario() {
		return new Usuario();
	}
	
	public IPersona getUsuario(String nombre, String apellidos, String email, String username, String password, Rol rol) {
		return new Usuario(nombre, apellidos, email, username, password, rol);
	}
	
	public IPersona getUsuario(String nombre, String apellidos, LocalDate fechaNacimiento, String documento, String telefono, String email, Direccion direccion, Empresa empresa, Imagen imagen, String username,
			String password) {
		return new Usuario(nombre, apellidos, fechaNacimiento, documento, telefono, email, direccion, empresa, imagen, username, password);
	}
}
