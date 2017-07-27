package dataAccessLayer;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import pojos.Articulo;

public class ArticuloDAOHibernate extends IpartekDAOHibernate implements ArticuloDAO {

	public ArticuloDAOHibernate() {

	}

	public ArticuloDAOHibernate(EntityManager man) {
		this.man = man;
	}

	@Override
	public long insert(Articulo articulo) {
		man.persist(articulo);
		long id = articulo.getId();
		return id;
	}

	@Override
	public void update(Articulo articulo) {
		man.merge(articulo);
	}

	@Override
	public void delete(Articulo articulo) {
		Articulo art = man.find(Articulo.class, articulo.getId());
		man.remove(art);
	}

	@Override
	public void delete(long id) {
		Articulo art = man.find(Articulo.class, id);
		man.remove(art);
	}

	@Override
	public List<Articulo> findAll() {
		@SuppressWarnings("unchecked")
		List<Articulo> articulos = (List<Articulo>) man.createQuery("FROM Articulo").getResultList();
		return articulos;
	}

	@Override
	public Articulo findById(long id) {
		Articulo art = man.find(Articulo.class, id);
		return art;
	}

	@Override
	public Articulo findByCodigo(String codigo) {
		Articulo articulo = (Articulo) man.createQuery("FROM Articulo WHERE codigoArticulo='" + codigo + "'").getSingleResult();
		return articulo;
	}

	@Override
	public boolean validar(Articulo articulo) {
		Articulo art = man.find(Articulo.class, articulo.getId());
		if (art == null)
			return false;
		return true;
	}

	@Override
	public boolean validarCodigo(Articulo articulo) {
		if (this.findByCodigo(articulo.getCodigoArticulo()) != null)
			return true;
		return true;
	}

	public void restarCantidad(Articulo articulo, BigInteger cantidad) {
		man.merge(articulo);
		articulo.setStock(articulo.getStock().subtract(cantidad));
	}
}
