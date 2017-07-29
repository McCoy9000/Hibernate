package factories;

import java.math.BigInteger;

import pojos.ArticuloCantidad;
import pojos.ArticuloStock;
import pojos.ArticuloVendido;
import pojos.Factura;
import pojos.Producto;

public class ProductoFactory {

	public static ArticuloCantidad getArticuloCantidad() {
		return new ArticuloCantidad();
	}
	
	public static ArticuloCantidad getArticuloCantidad(Producto producto, BigInteger cantidad) {
		return new ArticuloCantidad (producto.getId(), producto.getCodigoArticulo(), producto.getNombre(), producto.getDescripcion(), producto.getImagen(), producto.getPrecio(), cantidad);
	}
	
	public static ArticuloStock getArticuloStock() {
		return new ArticuloStock();
	}
	
	public static ArticuloStock getArticuloStock(Producto producto, BigInteger stock) {
		return new ArticuloStock (producto.getCodigoArticulo(), producto.getNombre(), producto.getDescripcion(), producto.getImagen(), producto.getPrecio(), stock);
	}
	
	public static ArticuloVendido getArticuloVendido() {
		return new ArticuloVendido();
	}
	
	public static ArticuloVendido getArticuloVendido(Producto producto, BigInteger cantidad, Factura factura) {
		return new ArticuloVendido (producto.getCodigoArticulo(), producto.getNombre(), producto.getDescripcion(), producto.getImagen(), producto.getPrecio(), cantidad, factura);
	}
}
