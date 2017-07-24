package pojos;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIOS")
public class Usuario extends Persona {

	private static final long serialVersionUID = 2671695361728068311L;

	@Column(name = "username", nullable = false)
	String username;
	@Column(name = "password", nullable = false)
	String password;
	@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rol")
	String rol;
	
	
	public Usuario() {
	}

	public Usuario (String nombre, String apellidos, String email, String username, String password, String rol) {
		super(nombre, apellidos, email);
		this.username = username;
		this.password = password;
		this.rol = rol;
	}
	
	public Usuario(String nombre, String apellidos, LocalDate fechaNacimiento,
			String documento, String telefono, String email, Direccion direccion,
			Empresa empresa, Imagen imagen) {
		super(nombre, apellidos, fechaNacimiento, documento, telefono, email,
				direccion, empresa, imagen);
		
	}

	public Usuario(String nombre, String apellidos, LocalDate fechaNacimiento,
			String documento, String telefono, String email, Direccion direccion,
			Empresa empresa, Imagen imagen, String username, String password) {
		super(nombre, apellidos, fechaNacimiento, documento, telefono, email,
				direccion, empresa, imagen);
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
