package pojos;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Producto implements Serializable {

	private static final long serialVersionUID = 2613954032931478117L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_producto", unique = true)
	private long id;
	@Column(name = "codigo_articulo")
	private String codigoArticulo;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "descripcion")
	private String descripcion;
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_imagen")
	private Imagen imagen;
	@Column(name = "precio")
	private BigDecimal precio;

	public Producto() {
	}

	public Producto(String codigoArticulo, String nombre, String descripcion,
			Imagen imagen, BigDecimal precio) {
		super();
		this.codigoArticulo = codigoArticulo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.precio = precio;
	}

	public Producto(long id, String codigoArticulo, String nombre,
			String descripcion, Imagen imagen, BigDecimal precio) {
		super();
		this.id = id;
		this.codigoArticulo = codigoArticulo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.precio = precio;
	}
	
	public Producto(String codigoArticulo, String nombre, String descripcion, BigDecimal precio) {
		this.codigoArticulo = codigoArticulo;
		this.nombre = nombre;
		this.imagen = new Imagen(codigoArticulo, File.separator + "img" + File.separator + codigoArticulo + ".jpg");
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoArticulo == null) ? 0 : codigoArticulo.hashCode());
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
		Producto other = (Producto) obj;
		if (codigoArticulo == null) {
			if (other.codigoArticulo != null)
				return false;
		} else if (!codigoArticulo.equals(other.codigoArticulo))
			return false;
		return true;
	}

	
}
