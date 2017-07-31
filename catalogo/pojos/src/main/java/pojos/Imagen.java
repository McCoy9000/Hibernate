package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGENES")
public class Imagen implements Serializable {

	private static final long serialVersionUID = -7360393809608650040L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_imagen", unique = true)
	private long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "url")
	private String url;
	@OneToOne(mappedBy = "imagen", fetch = FetchType.LAZY)
	private Persona persona;
//	@OneToOne(mappedBy = "imagen", fetch = FetchType.LAZY)
//	private Comprador comprador;
	@OneToMany(mappedBy = "imagen", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Producto> productos = new ArrayList<Producto>();


	public Imagen() {

	}

	public Imagen(String url) {
		this.url = url;
	}
	
	public Imagen(String nombre, String url){
		this.nombre = nombre;
		this.url = url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Imagen other = (Imagen) obj;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
