package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;

import pojos.Comprador;

public class CompradorDAOHibernate extends IpartekDAOHibernate implements CompradorDAO {

	public CompradorDAOHibernate() {
		
	}
	
	public CompradorDAOHibernate(EntityManager man) {
		this.man = man;
	}

	
	@Override
	public long insert(Comprador usuario) {
		man.persist(usuario);
		long id = usuario.getId();
		return id;
	}

	@Override
	public void update(Comprador usuario) {
		man.merge(usuario);
	}

	@Override
	public void delete(Comprador usuario) {
		Comprador user = man.find(Comprador.class, usuario.getId());
		man.remove(user);
	}

	@Override
	public void delete(long id) {
		Comprador user = man.find(Comprador.class, id);
		man.remove(user);
	}

	@Override
	public List<Comprador> findAll() {
		@SuppressWarnings("unchecked")
		List<Comprador> usuarios = (List<Comprador>) man.createQuery("FROM Comprador").getResultList();
		return usuarios;
	}

	@Override
	public Comprador findById(long id) {
		Comprador user = man.find(Comprador.class, id);
		return user;
	}

	@Override
	public List<Comprador> findByIdUsuario(long idUsuario) {
		@SuppressWarnings("unchecked")
		List<Comprador> usuarios = (List<Comprador>) man.createQuery("FROM Comprador WHERE id_usuario ='" + idUsuario + "'").getResultList();
		return usuarios;
	}

	@Override
	public boolean validar(Comprador usuario) {
		Comprador user = man.find(Comprador.class, usuario.getId());
		if (user == null)
			return false;
		return true;
	}
}
