package pojos;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "FACTURAS")
public class Factura implements Serializable {

	private static final long serialVersionUID = -536150810066408871L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_factura", unique = true)
	private long id;
	@Column(name = "numero_factura", unique = true)
	private String numeroFactura;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "id")
	private Usuario usuario;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "id_comprador")
	private Comprador comprador;
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
	private List<ArticuloVendido> articulos = new ArrayList<ArticuloVendido>();
	@Column(name = "fecha")
	private LocalDate fecha;
	private static long siguienteFactura = 0L;

	public Factura() {
	}

	public Factura(String numeroFactura, Usuario usuario, LocalDate fecha) {
		super();
		this.numeroFactura = numeroFactura;
		this.usuario = usuario;
		this.fecha = fecha;
	}

	public Factura(int id, String numeroFactura, Usuario usuario, LocalDate fecha) {
		super();
		this.id = id;
		this.numeroFactura = numeroFactura;
		this.usuario = usuario;
		this.fecha = fecha;
	}

	public Factura(Usuario usuario, LocalDate fecha) {
		this.numeroFactura = String.format("DRV%09d", siguienteFactura);
		this.usuario = usuario;
		this.fecha = fecha;
		siguienteFactura++;
	}

	public Factura(Usuario usuario, Comprador comprador, LocalDate fecha) {
		this.numeroFactura = String.format("DRV%09d", siguienteFactura);
		this.usuario = usuario;
		this.comprador = comprador;
		this.fecha = fecha;
		siguienteFactura++;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Comprador getComprador() {
		return comprador;
	}

	public void setComprador(Comprador comprador) {
		this.comprador = comprador;
	}

	public List<ArticuloVendido> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<ArticuloVendido> articulos) {
		this.articulos = articulos;
		for(ArticuloVendido av : articulos) {
			av.setFactura(this);
		}
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public static long getSiguienteFactura() {
		return siguienteFactura;
	}

	public static void setSiguienteFactura(long siguienteFactura) {
		Factura.siguienteFactura = siguienteFactura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articulos == null) ? 0 : articulos.hashCode());
		result = prime * result + ((comprador == null) ? 0 : comprador.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((numeroFactura == null) ? 0 : numeroFactura.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Factura other = (Factura) obj;
		if (articulos == null) {
			if (other.articulos != null)
				return false;
		} else if (!articulos.equals(other.articulos))
			return false;
		if (comprador == null) {
			if (other.comprador != null)
				return false;
		} else if (!comprador.equals(other.comprador))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id != other.id)
			return false;
		if (numeroFactura == null) {
			if (other.numeroFactura != null)
				return false;
		} else if (!numeroFactura.equals(other.numeroFactura))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
