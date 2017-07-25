package pojos;

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
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_factura", unique = true)
	private long id;
	@Column(name = "numero_factura", unique = true)
	private String numeroFactura;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id")
	private Usuario usuario;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_comprador")
	private Comprador comprador;
	@OneToMany (mappedBy = "factura", cascade = CascadeType.PERSIST)
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
		siguienteFactura++;
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

	

}
