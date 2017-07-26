package dataAccessLayer;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.Articulo;

public class ArticuloDAOHibernate extends IpartekDAOHibernate implements ArticuloDAO {

	@Override
	public long insert(Articulo articulo) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.persist(articulo);
		transaction.commit();
		long id = articulo.getId();
		man.close();	
		return id;
	}

	@Override
	public void update(Articulo articulo) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(articulo);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Articulo articulo) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Articulo art= man.find(Articulo.class, articulo.getId());
		transaction.begin();
		man.remove(art);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Articulo art = man.find(Articulo.class, id);
		transaction.begin();
		man.remove(art);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Articulo> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Articulo> articulos = (List<Articulo>)man.createQuery("FROM Articulo").getResultList();
		man.close();
		return articulos;
	}

	@Override
	public Articulo findById(long id) {
		EntityManager man = emf.createEntityManager();
		Articulo art = man.find(Articulo.class, id);
		man.close();
		return art;
	}

	@Override
	public Articulo findByCodigo(String codigo) {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Articulo> articulos = (List<Articulo>)man.createQuery("FROM Articulo WHERE codigoArticulo='" + codigo + "'").getResultList();
		Articulo articulo = articulos.get(0);
		man.close();
		return articulo;
	}

	@Override
	public boolean validar(Articulo articulo) {
		EntityManager man = emf.createEntityManager();
		Articulo art = man.find(Articulo.class, articulo.getId());
		man.close();
		if (art == null)
		return false;
		return true;
	}

	@Override
	public boolean validarCodigo(Articulo articulo) {
		if(this.findByCodigo(articulo.getCodigoArticulo()) != null)
		return true;
		return true;
	}

	public void restarCantidad(Articulo articulo, BigInteger cantidad) {
		EntityManager man = emf.createEntityManager();
		man.merge(articulo);
		articulo.setStock(articulo.getStock().subtract(cantidad));
		man.close();
	}
}
