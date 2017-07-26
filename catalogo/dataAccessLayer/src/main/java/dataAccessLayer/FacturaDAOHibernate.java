package dataAccessLayer;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.ArticuloVendido;
import pojos.Factura;
import recursos.Constantes;

public class FacturaDAOHibernate extends IpartekDAOHibernate implements FacturaDAO{

	@Override
	public long insert(Factura factura) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.persist(factura);
		transaction.commit();
		long id = factura.getId();
		man.close();	
		return id;
	}

	@Override
	public void update(Factura factura) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(factura);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Factura factura) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Factura fra = man.find(Factura.class, factura.getId());
		transaction.begin();
		man.remove(fra);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Factura factura = man.find(Factura.class, id);
		transaction.begin();
		man.remove(factura);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Factura> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Factura> facturas = (List<Factura>)man.createQuery("FROM Factura").getResultList();
		man.close();
		return facturas;
	}

	@Override
	public Factura findById(long id) {
		EntityManager man = emf.createEntityManager();
		Factura factura = man.find(Factura.class, id);
		man.close();
		return factura;
	}

	@Override
	public BigDecimal getIvaTotal(long id) {
		BigDecimal ivaTotal = BigDecimal.ZERO;
		for(ArticuloVendido a: this.findById(id).getArticulos()) {
			ivaTotal = ivaTotal.add(a.getPrecio().multiply(Constantes.IVA));

		}
		ivaTotal = ivaTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return ivaTotal;
	}

	@Override
	public BigDecimal getPrecioTotal(long id) {
		BigDecimal precioTotalSinIva = BigDecimal.ZERO;
		BigDecimal precioTotal = BigDecimal.ZERO;
		for(ArticuloVendido a: this.findById(id).getArticulos()) {
			precioTotalSinIva = precioTotalSinIva.add(a.getPrecio());
		}
		precioTotal = precioTotalSinIva.add(this.getIvaTotal(id));
		precioTotal = precioTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return precioTotal;
	}

	@Override
	public long getMaxId() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Factura> maxFactura = (List<Factura>) man.createQuery("From Factura WHERE factura_id = (select max(id_factura) from Factura)").getResultList();
		return maxFactura.get(0).getId() + 1;
	}

}
