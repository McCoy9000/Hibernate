package pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@OneToOne(mappedBy = "imagen", fetch = FetchType.LAZY)
	private Comprador comprador;
	@OneToOne(mappedBy = "imagen", fetch = FetchType.LAZY)
	private Articulo articulo;
	@OneToOne(mappedBy = "imagen", fetch = FetchType.LAZY)
	private ArticuloVendido articuloVendido;

	public Imagen() {

	}

	public Imagen(String nombre) {
		this.nombre = nombre;
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
		result = prime * result + ((articulo == null) ? 0 : articulo.hashCode());
		result = prime * result + ((articuloVendido == null) ? 0 : articuloVendido.hashCode());
		result = prime * result + ((comprador == null) ? 0 : comprador.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
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
		if (articulo == null) {
			if (other.articulo != null)
				return false;
		} else if (!articulo.equals(other.articulo))
			return false;
		if (articuloVendido == null) {
			if (other.articuloVendido != null)
				return false;
		} else if (!articuloVendido.equals(other.articuloVendido))
			return false;
		if (comprador == null) {
			if (other.comprador != null)
				return false;
		} else if (!comprador.equals(other.comprador))
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
