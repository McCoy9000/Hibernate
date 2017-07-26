package dataAccessLayer;

import java.util.List;

import pojos.Rol;

public class RolDAOHibernate extends IpartekDAOHibernate implements RolDAO {

	@Override
	public long insert(Rol rol) {
		man.persist(rol);
		long id = rol.getId();
		return id;
	}

	@Override
	public void update(Rol rol) {
		man.merge(rol);
	}

	@Override
	public void delete(Rol rol) {
		Rol papel = man.find(Rol.class, rol.getId());
		man.remove(papel);
	}

	@Override
	public void delete(long id) {
		Rol papel = man.find(Rol.class, id);
		man.remove(papel);
	}

	@Override
	public List<Rol> findAll() {
		@SuppressWarnings("unchecked")
		List<Rol> roles = (List<Rol>) man.createQuery("FROM Rol").getResultList();
		return roles;
	}

	@Override
	public Rol findById(int id) {
		Rol rol = man.find(Rol.class, id);
		return rol;
	}

	@Override
	public Rol findByName(String nombre) {
		@SuppressWarnings("unchecked")
		List<Rol> roles = (List<Rol>) man.createQuery("FROM Rol WHERE nombre='" + nombre + "'").getResultList();
		Rol rol = roles.get(0);
		return rol;
	}

	@Override
	public boolean validar(Rol rol) {
		Rol papel = man.find(Rol.class, rol.getId());
		if (papel == null)
			return false;
		return true;
	}

	@Override
	public boolean validarNombre(Rol rol) {
		if (this.findByName(rol.getNombre()) != null)
			return true;
		return true;
	}

}
