package dataAccessLayer;

import java.util.List;

import pojos.ArticuloVendido;

public class ArticuloVendidoDAOHibernate extends IpartekDAOHibernate implements ArticuloVendidoDAO {

	@Override
	public long insert(ArticuloVendido articulo) {
		man.persist(articulo);
		long id = articulo.getId();
		return id;
	}

	@Override
	public void update(ArticuloVendido articulo) {
		man.merge(articulo);
	}

	@Override
	public void delete(ArticuloVendido articulo) {
		ArticuloVendido art = man.find(ArticuloVendido.class, articulo.getId());
		man.remove(art);
	}

	@Override
	public void delete(long id) {
		ArticuloVendido art = man.find(ArticuloVendido.class, id);
		man.remove(art);
	}

	@Override
	public List<ArticuloVendido> findAll() {
		@SuppressWarnings("unchecked")
		List<ArticuloVendido> articulos = (List<ArticuloVendido>) man.createQuery("FROM ArticuloVendido").getResultList();
		return articulos;
	}

	@Override
	public ArticuloVendido findById(long id) {
		ArticuloVendido art = man.find(ArticuloVendido.class, id);
		return art;
	}

	@Override
	public ArticuloVendido findByCodigo(String codigo) {
		@SuppressWarnings("unchecked")
		List<ArticuloVendido> articulos = (List<ArticuloVendido>) man.createQuery("FROM ArticuloVendido WHERE codigoArticulo='" + codigo + "'").getResultList();
		ArticuloVendido articulo = articulos.get(0);
		return articulo;
	}

	@Override
	public boolean validar(ArticuloVendido articulo) {
		ArticuloVendido art = man.find(ArticuloVendido.class, articulo.getId());
		if (art == null)
			return false;
		return true;
	}

	@Override
	public boolean validarCodigo(ArticuloVendido articulo) {
		if (this.findByCodigo(articulo.getCodigoArticulo()) != null)
			return true;
		return true;
	}

}
