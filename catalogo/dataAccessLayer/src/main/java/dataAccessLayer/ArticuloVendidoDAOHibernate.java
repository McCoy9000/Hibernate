package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.ArticuloVendido;

public class ArticuloVendidoDAOHibernate extends IpartekDAOHibernate implements ArticuloVendidoDAO  {

	@Override
	public long insert(ArticuloVendido articulo) {
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
	public void update(ArticuloVendido articulo) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(articulo);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(ArticuloVendido articulo) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		ArticuloVendido art= man.find(ArticuloVendido.class, articulo.getId());
		transaction.begin();
		man.remove(art);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		ArticuloVendido art = man.find(ArticuloVendido.class, id);
		transaction.begin();
		man.remove(art);
		transaction.commit();
		man.close();
	}

	@Override
	public List<ArticuloVendido> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<ArticuloVendido> articulos = (List<ArticuloVendido>)man.createQuery("FROM ArticuloVendido").getResultList();
		man.close();
		return articulos;
	}

	@Override
	public ArticuloVendido findById(long id) {
		EntityManager man = emf.createEntityManager();
		ArticuloVendido art = man.find(ArticuloVendido.class, id);
		man.close();
		return art;
	}

	@Override
	public ArticuloVendido findByCodigo(String codigo) {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<ArticuloVendido> articulos = (List<ArticuloVendido>)man.createQuery("FROM ArticuloVendido WHERE codigoArticulo='" + codigo + "'").getResultList();
		ArticuloVendido articulo = articulos.get(0);
		man.close();
		return articulo;
	}

	@Override
	public boolean validar(ArticuloVendido articulo) {
		EntityManager man = emf.createEntityManager();
		ArticuloVendido art = man.find(ArticuloVendido.class, articulo.getId());
		man.close();
		if (art == null)
		return false;
		return true;
	}

	@Override
	public boolean validarCodigo(ArticuloVendido articulo) {
		if(this.findByCodigo(articulo.getCodigoArticulo()) != null)
		return true;
		return true;
	}


}
