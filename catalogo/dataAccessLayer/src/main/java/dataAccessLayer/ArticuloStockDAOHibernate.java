package dataAccessLayer;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import pojos.ArticuloStock;

public class ArticuloStockDAOHibernate extends IpartekDAOHibernate implements ArticuloStockDAO {

	public ArticuloStockDAOHibernate() {

	}

	public ArticuloStockDAOHibernate(EntityManager man) {
		this.man = man;
	}

	@Override
	public long insert(ArticuloStock articulo) {
		man.persist(articulo);
		long id = articulo.getId();
		return id;
	}

	@Override
	public void update(ArticuloStock articulo) {
		man.merge(articulo);
	}

	@Override
	public void delete(ArticuloStock articulo) {
		man.remove(articulo);
	}

	@Override
	public void delete(long id) {
		ArticuloStock art = man.find(ArticuloStock.class, id);
		man.remove(art);
	}

	@Override
	public List<ArticuloStock> findAll() {
		@SuppressWarnings("unchecked")
		List<ArticuloStock> articulos = (List<ArticuloStock>) man.createQuery("FROM ArticuloStock").getResultList();
		return articulos;
	}

	@Override
	public ArticuloStock findById(long id) {
		ArticuloStock art = man.find(ArticuloStock.class, id);
		return art;
	}

	@Override
	public ArticuloStock findByCodigo(String codigo) {
		ArticuloStock articulo = (ArticuloStock) man.createQuery("FROM ArticuloStock WHERE codigoArticulo='" + codigo + "'").getSingleResult();
		return articulo;
	}

	@Override
	public boolean validar(ArticuloStock articulo) {
		ArticuloStock art = man.find(ArticuloStock.class, articulo.getId());
		if (art == null)
			return false;
		return true;
	}

	@Override
	public boolean validarCodigo(ArticuloStock articulo) {
		if (this.findByCodigo(articulo.getCodigoArticulo()) != null)
			return true;
		return true;
	}

	public void restarCantidad(ArticuloStock articulo, BigInteger cantidad) {
		man.merge(articulo);
		articulo.setStock(articulo.getStock().subtract(cantidad));
	}
}
