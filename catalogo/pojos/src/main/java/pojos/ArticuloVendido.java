package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICULOS_VENDIDOS")
public class ArticuloVendido extends Producto implements Serializable {

	private static final long serialVersionUID = -8894436731693409575L;
	
	@Column(name = "cantidad")
	private BigInteger cantidad;
	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_factura")
	private Factura factura;

	public ArticuloVendido() {

	}

	public ArticuloVendido(String codigoArticulo, String nombre, String descripcion, Imagen imagen, BigDecimal precio, BigInteger cantidad, Factura factura) {
		super();
		this.cantidad = cantidad;
		this.factura = factura;
	}

	public BigInteger getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}


}
