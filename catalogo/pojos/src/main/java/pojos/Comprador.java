package pojos;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMPRADORES")
public class Comprador{

	private static final long serialVersionUID = -4479395202727906913L;

	@Id
	@Column(name = "id")
	private long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellidos")
	private String apellidos;
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	@Column (name = "documento")
	private String documento;
	@Column (name = "telefono")
	private String telefono;
	@Column (name = "email")
	private String email;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_direccion")
	private Direccion direccion;
	@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_imagen")
	private Imagen imagen;
	

	public Comprador() {
		
	}
	
	public Comprador(long id, String nombre, String apellidos, LocalDate fechaNacimiento,
			String documento, String telefono, String email, Direccion direccion,
			Empresa empresa, Imagen imagen) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.documento = documento;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
		this.empresa = empresa;
		this.imagen = imagen;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
	
}
