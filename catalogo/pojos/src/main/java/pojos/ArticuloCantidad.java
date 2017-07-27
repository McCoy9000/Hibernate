package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ArticuloCantidad extends Articulo implements Serializable {

	private static final long serialVersionUID = 4227855324423410643L;
	
	private BigInteger cantidad;

	public ArticuloCantidad() {
		// TODO Auto-generated constructor stub
	}

	public ArticuloCantidad(String codigoArticulo, String nombre, String descripcion, Imagen imagen, BigDecimal precio, BigInteger stock, BigInteger cantidad) {
		super(codigoArticulo, nombre, descripcion, imagen, precio, stock);
		this.cantidad = cantidad;
	}

	public BigInteger getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cantidad == null) ? 0 : cantidad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticuloCantidad other = (ArticuloCantidad) obj;
		if (cantidad == null) {
			if (other.cantidad != null)
				return false;
		} else if (!cantidad.equals(other.cantidad))
			return false;
		return true;
	}

}
