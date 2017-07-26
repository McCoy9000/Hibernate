package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.Comprador;

public class CompradorDAOHibernate extends IpartekDAOHibernate implements CompradorDAO {

	
	@Override
	public long insert(Comprador usuario) {

		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.persist(usuario);
		transaction.commit();
		long id = usuario.getId();
		man.close();	
		return id;
	}

	@Override
	public void update(Comprador usuario) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(usuario);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Comprador usuario) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Comprador user = man.find(Comprador.class, usuario.getId());
		transaction.begin();
		man.remove(user);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Comprador user = man.find(Comprador.class, id);
		transaction.begin();
		man.remove(user);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Comprador> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Comprador> usuarios = (List<Comprador>)man.createQuery("FROM Comprador").getResultList();
		man.close();
		return usuarios;
	}

	@Override
	public Comprador findById(long id) {
		EntityManager man = emf.createEntityManager();
		Comprador user = man.find(Comprador.class, id);
		man.close();
		return user;
	}

	@Override
	public List<Comprador> findByIdUsuario(long idUsuario) {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Comprador> usuarios = (List<Comprador>)man.createQuery("FROM Comprador WHERE id_usuario ='" + idUsuario + "'").getResultList();
		man.close();
		return usuarios;
	}

	@Override
	public boolean validar(Comprador usuario) {
		EntityManager man = emf.createEntityManager();
		Comprador user = man.find(Comprador.class, usuario.getId());
		man.close();
		if (user == null)
		return false;
		return true;
	}
}
