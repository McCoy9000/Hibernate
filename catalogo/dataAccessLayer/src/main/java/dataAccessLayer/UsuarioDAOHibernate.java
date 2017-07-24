package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.Usuario;

public class UsuarioDAOHibernate extends IpartekDAOHibernate implements UsuarioDAO {

	
	@Override
	public long insert(Usuario usuario) {

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
	public void update(Usuario usuario) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(usuario);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Usuario usuario) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Usuario user = man.find(Usuario.class, usuario.getId());
		transaction.begin();
		man.remove(user);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Usuario user = man.find(Usuario.class, id);
		transaction.begin();
		man.remove(user);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Usuario> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>)man.createQuery("FROM Usuario");
		man.close();
		return usuarios;
	}

	@Override
	public Usuario findById(int id) {
		EntityManager man = emf.createEntityManager();
		Usuario user = man.find(Usuario.class, id);
		man.close();
		return user;
	}

	@Override
	public Usuario findByName(String username) {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>)man.createQuery("FROM Usuario WHERE username=" + username);
		Usuario usuario = usuarios.get(0);
		man.close();
		return usuario;
	}

	@Override
	public boolean validar(Usuario usuario) {
		EntityManager man = emf.createEntityManager();
		Usuario user = man.find(Usuario.class, usuario.getId());
		man.close();
		if (user == null)
		return false;
		return true;
	}

	@Override
	public boolean validarNombre(Usuario usuario) {
		if(this.findByName(usuario.getUsername()) != null)
		return true;
		return true;
	}

}
