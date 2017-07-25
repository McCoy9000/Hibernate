package pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPRESAS")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_empresa", unique = true)
	private long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "documento")
	private String documento;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id")
	private Direccion direccion;
	@OneToMany (mappedBy = "empresa", cascade = CascadeType.PERSIST)
	private List<Persona> personas = new ArrayList<Persona>();
	@OneToMany (mappedBy = "empresa", cascade = CascadeType.PERSIST)
	private List<Comprador> compradores = new ArrayList<Comprador>();
	

	public Empresa() {
	}


	public Empresa(String nombre, String documento, Direccion direccion) {
		super();
		this.nombre = nombre;
		this.documento = documento;
		this.direccion = direccion;
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


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}


	public Direccion getDireccion() {
		return direccion;
	}


	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}


	public List<Persona> getPersonas() {
		return personas;
	}


	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

}
