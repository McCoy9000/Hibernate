package pojos;

import interfaces.IArticulo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ArticuloCantidad extends Articulo implements Serializable, IArticulo {

	private static final long serialVersionUID = 4227855324423410643L;
	
	private BigInteger cantidad;

	public ArticuloCantidad() {
	}

	public ArticuloCantidad(long id, String codigoArticulo, String nombre, String descripcion, Imagen imagen, BigDecimal precio, BigInteger cantidad) {
		super(codigoArticulo, nombre, descripcion, imagen, precio);
		this.setId(id);
		this.cantidad = cantidad;
	}

	public BigInteger getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}
}
