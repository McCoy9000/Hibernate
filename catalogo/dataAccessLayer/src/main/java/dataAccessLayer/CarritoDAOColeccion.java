package dataAccessLayer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pojos.ArticuloCantidad;
import pojos.Carrito;
import recursos.Constantes;

public class CarritoDAOColeccion implements CarritoDAO {

	private static Logger log = Logger.getLogger(CarritoDAOColeccion.class);

	Carrito carrito;

	public CarritoDAOColeccion() {
		this.carrito = new Carrito();
	}

	public CarritoDAOColeccion(HashMap<Long, ArticuloCantidad> productosCarrito) {
		this.carrito = new Carrito(productosCarrito);
	}

	@Override
	public long insert(ArticuloCantidad articulo) {
		carrito.getListaArticulos().put(articulo.getId(), articulo);
		return articulo.getId();
	}

	@Override
	public void update(ArticuloCantidad articulo) {
	}

	@Override
	public void delete(ArticuloCantidad articulo) {
		long id = articulo.getId();
		carrito.getListaArticulos().remove(id);
	}

	@Override
	public void delete(long id) {
		carrito.getListaArticulos().remove(id);

	}

	@Override
	public List<ArticuloCantidad> findAll() {
		return new ArrayList<ArticuloCantidad>(carrito.getListaArticulos().values());
	}

	@Override
	public ArticuloCantidad findById(long id) {
		return carrito.getListaArticulos().get(id);
	}

	@Override
	public ArticuloCantidad findByCodigo(String codigo) {
		for (ArticuloCantidad articulo : carrito.getListaArticulos().values()) {
			if (articulo.getCodigoArticulo() == codigo)
				return articulo;
		}
		return null;
	}

	@Override
	public boolean validar(ArticuloCantidad articulo) {
		if (carrito.getListaArticulos().values().contains(articulo))
			return true;
		return false;
	}

	@Override
	public boolean validarCodigo(String codigo) {
		for (ArticuloCantidad articulo : new ArrayList<ArticuloCantidad>(carrito.getListaArticulos().values())) {
			if (articulo.getCodigoArticulo() == codigo)
				return true;
		}
		return false;
	}

	@Override
	public BigDecimal getPrecioTotal() {
		BigDecimal precioTotal = BigDecimal.ZERO;
		BigDecimal precioTotalMasIva = BigDecimal.ZERO;
		for (ArticuloCantidad a : this.findAll()) {

			precioTotal = precioTotal.add(a.getPrecio().multiply(new BigDecimal(a.getCantidad())));
		}

		precioTotalMasIva = precioTotal.multiply(Constantes.IVA.add(BigDecimal.ONE)).setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return precioTotalMasIva;
	}

	public BigInteger getTotalArticulos() {
		BigInteger totalArticulos = BigInteger.ZERO;
		for (ArticuloCantidad ac : this.findAll()) {
			totalArticulos = totalArticulos.add(ac.getCantidad());
		}

		return totalArticulos;
	}

}
