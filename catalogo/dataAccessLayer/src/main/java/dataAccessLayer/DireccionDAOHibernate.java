package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.Direccion;

public class DireccionDAOHibernate extends IpartekDAOHibernate implements DireccionDAO {

	
	@Override
	public long insert(Direccion direccion) {

		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.persist(direccion);
		transaction.commit();
		long id = direccion.getId();
		man.close();	
		return id;
	}

	@Override
	public void update(Direccion direccion) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(direccion);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Direccion direccion) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Direccion dir = man.find(Direccion.class, direccion.getId());
		transaction.begin();
		man.remove(dir);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Direccion direccion = man.find(Direccion.class, id);
		transaction.begin();
		man.remove(direccion);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Direccion> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Direccion> direcciones = (List<Direccion>)man.createQuery("FROM Direccion").getResultList();
		man.close();
		return direcciones;
	}

	@Override
	public Direccion findById(long id) {
		EntityManager man = emf.createEntityManager();
		Direccion direccion = man.find(Direccion.class, id);
		man.close();
		return direccion;
	}

	@Override
	public boolean validar(Direccion direccion) {
		EntityManager man = emf.createEntityManager();
		Direccion dir = man.find(Direccion.class, direccion.getId());
		man.close();
		if (dir == null)
		return false;
		return true;
	}

}
