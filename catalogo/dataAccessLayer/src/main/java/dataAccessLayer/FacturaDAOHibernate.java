package dataAccessLayer;

import java.math.BigDecimal;
import java.util.List;

import pojos.ArticuloVendido;
import pojos.Factura;
import recursos.Constantes;

public class FacturaDAOHibernate extends IpartekDAOHibernate implements FacturaDAO {

	@Override
	public long insert(Factura factura) {
		man.persist(factura);
		long id = factura.getId();
		return id;
	}

	@Override
	public void update(Factura factura) {
		man.merge(factura);
	}

	@Override
	public void delete(Factura factura) {
		Factura fra = man.find(Factura.class, factura.getId());
		man.remove(fra);
	}

	@Override
	public void delete(long id) {
		Factura factura = man.find(Factura.class, id);
		man.remove(factura);
	}

	@Override
	public List<Factura> findAll() {
		@SuppressWarnings("unchecked")
		List<Factura> facturas = (List<Factura>) man.createQuery("FROM Factura").getResultList();
		return facturas;
	}

	@Override
	public Factura findById(long id) {
		Factura factura = man.find(Factura.class, id);
		return factura;
	}

	@Override
	public BigDecimal getIvaTotal(long id) {
		BigDecimal ivaTotal = BigDecimal.ZERO;
		for (ArticuloVendido a : this.findById(id).getArticulos()) {
			ivaTotal = ivaTotal.add(a.getPrecio().multiply(Constantes.IVA));

		}
		ivaTotal = ivaTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return ivaTotal;
	}

	@Override
	public BigDecimal getPrecioTotal(long id) {
		BigDecimal precioTotalSinIva = BigDecimal.ZERO;
		BigDecimal precioTotal = BigDecimal.ZERO;
		for (ArticuloVendido a : this.findById(id).getArticulos()) {
			precioTotalSinIva = precioTotalSinIva.add(a.getPrecio());
		}
		precioTotal = precioTotalSinIva.add(this.getIvaTotal(id));
		precioTotal = precioTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return precioTotal;
	}

	@Override
	public long getMaxId() {
		@SuppressWarnings("unchecked")
		List<Factura> maxFactura = (List<Factura>) man.createQuery("From Factura WHERE factura_id = (select max(id_factura) from Factura)").getResultList();
		return maxFactura.get(0).getId() + 1;
	}

}
