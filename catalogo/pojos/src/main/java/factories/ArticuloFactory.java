package factories;

import java.math.BigInteger;

import pojos.ArticuloCantidad;
import pojos.ArticuloStock;
import pojos.ArticuloFactura;
import pojos.Factura;
import pojos.Articulo;

public class ArticuloFactory {

	public static ArticuloCantidad getArticuloCantidad() {
		return new ArticuloCantidad();
	}
	
	public static ArticuloCantidad getArticuloCantidad(Articulo articulo, BigInteger cantidad) {
		return new ArticuloCantidad (articulo.getId(), articulo.getCodigoArticulo(), articulo.getNombre(), articulo.getDescripcion(), articulo.getImagen(), articulo.getPrecio(), cantidad);
	}
	
	public static ArticuloStock getArticuloStock() {
		return new ArticuloStock();
	}
	
	public static ArticuloStock getArticuloStock(Articulo articulo, BigInteger stock) {
		return new ArticuloStock (articulo.getCodigoArticulo(), articulo.getNombre(), articulo.getDescripcion(), articulo.getImagen(), articulo.getPrecio(), stock);
	}
	
	public static ArticuloFactura getArticuloVendido() {
		return new ArticuloFactura();
	}
	
	public static ArticuloFactura getArticuloVendido(Articulo articulo, BigInteger cantidad, Factura factura) {
		return new ArticuloFactura (articulo.getCodigoArticulo(), articulo.getNombre(), articulo.getDescripcion(), articulo.getImagen(), articulo.getPrecio(), cantidad, factura);
	}
}
