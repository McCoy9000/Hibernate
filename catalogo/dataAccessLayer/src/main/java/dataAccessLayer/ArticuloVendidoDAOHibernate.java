package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;

import pojos.ArticuloFactura;

public class ArticuloVendidoDAOHibernate extends IpartekDAOHibernate implements ArticuloVendidoDAO {

	public ArticuloVendidoDAOHibernate() {
		
	}
	
	public ArticuloVendidoDAOHibernate(EntityManager man) {
		this.man = man;
	}

	
	@Override
	public long insert(ArticuloFactura articulo) {
		man.persist(articulo);
		long id = articulo.getId();
		return id;
	}

	@Override
	public void update(ArticuloFactura articulo) {
		man.merge(articulo);
	}

	@Override
	public void delete(ArticuloFactura articulo) {
		ArticuloFactura art = man.find(ArticuloFactura.class, articulo.getId());
		man.remove(art);
	}

	@Override
	public void delete(long id) {
		ArticuloFactura art = man.find(ArticuloFactura.class, id);
		man.remove(art);
	}

	@Override
	public List<ArticuloFactura> findAll() {
		@SuppressWarnings("unchecked")
		List<ArticuloFactura> articulos = (List<ArticuloFactura>) man.createQuery("FROM ArticuloFactura").getResultList();
		return articulos;
	}

	@Override
	public ArticuloFactura findById(long id) {
		ArticuloFactura art = man.find(ArticuloFactura.class, id);
		return art;
	}

	@Override
	public List<ArticuloFactura> findByCodigo(String codigo) {
		@SuppressWarnings("unchecked")
		List<ArticuloFactura> articulos = (List<ArticuloFactura>) man.createQuery("FROM ArticuloFactura WHERE codigoArticulo='" + codigo + "'").getResultList();
		return articulos;
	}

	@Override
	public boolean validar(ArticuloFactura articulo) {
		ArticuloFactura art = man.find(ArticuloFactura.class, articulo.getId());
		if (art == null)
			return false;
		return true;
	}

	@Override
	public boolean validarCodigo(ArticuloFactura articulo) {
		if (this.findByCodigo(articulo.getCodigoArticulo()) != null)
			return true;
		return true;
	}

}
