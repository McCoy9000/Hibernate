package pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Rol {
	
	@Id
	@Column(name = "id_rol")
	long id;
	@Column(name = "nombre")
	String nombre;
	@Column(name = "descripcion")
	String descripcion;
	@OneToMany (mappedBy = "empresa", cascade = CascadeType.PERSIST)
	private List<Usuario> personas = new ArrayList<Usuario>();
	
	public Rol () {
		
	}
	
	public Rol (long id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	

}
