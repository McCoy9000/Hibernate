package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICULOS_STOCK")
public class ArticuloStock extends Producto implements Serializable {

	private static final long serialVersionUID = 2702580752722330560L;

	@Column(name = "stock")
	private BigInteger stock;

	public ArticuloStock() {
	}

	public ArticuloStock(String codigoArticulo, String nombre, String descripcion, Imagen imagen, BigDecimal precio, BigInteger stock) {
		super(codigoArticulo, nombre, descripcion, imagen, precio);
		this.stock = stock;
	}

	public ArticuloStock(String codigoArticulo, String nombre, String descripcion, BigDecimal precio, BigInteger stock) {
		super(codigoArticulo, nombre, descripcion, precio);
		this.stock = stock;
	}

	public BigInteger getStock() {
		return stock;
	}

	public void setStock(BigInteger stock) {
		this.stock = stock;
	}
}
