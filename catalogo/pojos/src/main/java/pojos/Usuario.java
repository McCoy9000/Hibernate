package pojos;

import interfaces.IPersona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIOS")
public class Usuario extends Persona implements IPersona {

	private static final long serialVersionUID = 2671695361728068311L;

	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_rol")
	private Rol rol;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST)
	private List<Factura> facturas = new ArrayList<Factura>();

	public Usuario() {
	}

	public Usuario(String nombre, String apellidos, String email, String username, String password, Rol rol) {
		super(nombre, apellidos, email);
		this.username = username;
		this.password = password;
		this.rol = rol;
	}

	public Usuario(String nombre, String apellidos, LocalDate fechaNacimiento, String documento, String telefono, String email, Direccion direccion, Empresa empresa, Imagen imagen) {
		super(nombre, apellidos, fechaNacimiento, documento, telefono, email, direccion, empresa, imagen);

	}

	public Usuario(String nombre, String apellidos, LocalDate fechaNacimiento, String documento, String telefono, String email, Direccion direccion, Empresa empresa, Imagen imagen, String username,
			String password) {
		super(nombre, apellidos, fechaNacimiento, documento, telefono, email, direccion, empresa, imagen);
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
