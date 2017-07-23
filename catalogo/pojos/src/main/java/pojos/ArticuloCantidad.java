package pojos;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ArticuloCantidad extends Articulo {

	private BigInteger cantidad;
	
	public ArticuloCantidad() {
		// TODO Auto-generated constructor stub
	}

	public ArticuloCantidad(String codigoArticulo, String nombre,
			String descripcion, Imagen imagen, BigDecimal precio,
			BigInteger stock, BigInteger cantidad) {
		super(codigoArticulo, nombre, descripcion, imagen, precio, stock);
		this.cantidad = cantidad;
	}

}
