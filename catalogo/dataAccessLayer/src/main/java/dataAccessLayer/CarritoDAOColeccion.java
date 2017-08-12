package dataAccessLayer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pojos.ArticuloCantidad;
import pojos.CarritoHashMap;
import recursos.Constantes;

public class CarritoDAOColeccion implements CarritoDAO {

	CarritoHashMap carritoHashMap;

	public CarritoDAOColeccion() {
		this.carritoHashMap = new CarritoHashMap();
	}

	public CarritoDAOColeccion(HashMap<Long, ArticuloCantidad> productosCarrito) {
		this.carritoHashMap = new CarritoHashMap(productosCarrito);
	}

	@Override
	public long insert(ArticuloCantidad articulo) {
		
		
		if(carritoHashMap.getListaArticulos().containsKey(articulo.getId())) {
			carritoHashMap.getListaArticulos().get(articulo.getId()).setCantidad(carritoHashMap.getListaArticulos().get(articulo.getId()).getCantidad().add(articulo.getCantidad()));
		} else {
			carritoHashMap.getListaArticulos().put(articulo.getId(), articulo);
		}
		return articulo.getId();
	}

	@Override
	public void update(ArticuloCantidad articulo) {
	}

	@Override
	public void delete(ArticuloCantidad articulo, BigInteger cantidad) {
		long id = articulo.getId();
		this.delete(id, cantidad);
	}

	@Override
	public void delete(long id, BigInteger cantidad) {
		ArticuloCantidad articulo = carritoHashMap.getListaArticulos().get(id);
		articulo.setCantidad(articulo.getCantidad().subtract(cantidad));;
		if (articulo.getCantidad().compareTo(BigInteger.ZERO) <= 0)
			carritoHashMap.getListaArticulos().remove(id);
	}

	@Override
	public List<ArticuloCantidad> findAll() {
		return new ArrayList<ArticuloCantidad>(carritoHashMap.getListaArticulos().values());
	}

	@Override
	public ArticuloCantidad findById(long id) {
		return carritoHashMap.getListaArticulos().get(id);
	}

	@Override
	public ArticuloCantidad findByCodigo(String codigo) {
		for (ArticuloCantidad articulo : carritoHashMap.getListaArticulos().values()) {
			if (articulo.getCodigoArticulo() == codigo)
				return articulo;
		}
		return null;
	}

	@Override
	public boolean validar(ArticuloCantidad articulo) {
		if (carritoHashMap.getListaArticulos().values().contains(articulo))
			return true;
		return false;
	}

	@Override
	public boolean validarCodigo(String codigo) {
		for (ArticuloCantidad articulo : new ArrayList<ArticuloCantidad>(carritoHashMap.getListaArticulos().values())) {
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
		if(carritoHashMap.getListaArticulos().keySet().size() > 0)
		for (ArticuloCantidad ac : this.findAll()) {
			totalArticulos = totalArticulos.add(ac.getCantidad());
		}
		return totalArticulos;
	}

}
