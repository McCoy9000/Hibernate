package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.Rol;

public class RolDAOHibernate extends IpartekDAOHibernate implements RolDAO {

	@Override
	public long insert(Rol rol) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.persist(rol);
		transaction.commit();
		long id = rol.getId();
		man.close();	
		return id;
	}

	@Override
	public void update(Rol rol) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(rol);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Rol rol) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Rol papel = man.find(Rol.class, rol.getId());
		transaction.begin();
		man.remove(papel);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Rol papel = man.find(Rol.class, id);
		transaction.begin();
		man.remove(papel);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Rol> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Rol> roles = (List<Rol>)man.createQuery("FROM Rol").getResultList();
		man.close();
		return roles;
	}

	@Override
	public Rol findById(int id) {
		EntityManager man = emf.createEntityManager();
		Rol rol = man.find(Rol.class, id);
		man.close();
		return rol;
	}

	@Override
	public Rol findByName(String nombre) {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Rol> roles = (List<Rol>)man.createQuery("FROM Rol WHERE nombre='" + nombre + "'").getResultList();
		Rol rol = roles.get(0);
		man.close();
		return rol;
	}

	@Override
	public boolean validar(Rol rol) {
		EntityManager man = emf.createEntityManager();
		Rol papel = man.find(Rol.class, rol.getId());
		man.close();
		if (papel == null)
		return false;
		return true;
	}

	@Override
	public boolean validarNombre(Rol rol) {
		if(this.findByName(rol.getNombre()) != null)
		return true;
		return true;
	}

}
