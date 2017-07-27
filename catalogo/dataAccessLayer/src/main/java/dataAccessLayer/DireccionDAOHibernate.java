package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;

import pojos.Direccion;

public class DireccionDAOHibernate extends IpartekDAOHibernate implements DireccionDAO {

	public DireccionDAOHibernate() {
		
	}
	
	public DireccionDAOHibernate(EntityManager man) {
		this.man = man;
	}

	@Override
	public long insert(Direccion direccion) {
		man.persist(direccion);
		long id = direccion.getId();
		return id;
	}

	@Override
	public void update(Direccion direccion) {
		man.merge(direccion);
	}

	@Override
	public void delete(Direccion direccion) {
		Direccion dir = man.find(Direccion.class, direccion.getId());
		man.remove(dir);
	}

	@Override
	public void delete(long id) {
		Direccion direccion = man.find(Direccion.class, id);
		man.remove(direccion);
	}

	@Override
	public List<Direccion> findAll() {
		@SuppressWarnings("unchecked")
		List<Direccion> direcciones = (List<Direccion>) man.createQuery("FROM Direccion").getResultList();
		return direcciones;
	}

	@Override
	public Direccion findById(long id) {
		Direccion direccion = man.find(Direccion.class, id);
		return direccion;
	}

	@Override
	public boolean validar(Direccion direccion) {
		Direccion dir = man.find(Direccion.class, direccion.getId());
		if (dir == null)
			return false;
		return true;
	}

}
