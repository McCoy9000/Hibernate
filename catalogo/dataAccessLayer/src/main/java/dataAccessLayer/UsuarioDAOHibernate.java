package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import pojos.Usuario;

public class UsuarioDAOHibernate extends IpartekDAOHibernate implements UsuarioDAO {

	private static Logger log = Logger.getLogger(UsuarioDAOHibernate.class);

	public UsuarioDAOHibernate() {

	}

	public UsuarioDAOHibernate(EntityManager man) {
		this.man = man;
	}

	@Override
	public long insert(Usuario usuario) {
		man.persist(usuario);
		long id = usuario.getId();
		return id;
	}

	@Override
	public void update(Usuario usuario) {
		man.merge(usuario);
	}

	@Override
	public void delete(Usuario usuario) {
		Usuario user = man.find(Usuario.class, usuario.getId());
		man.remove(user);
	}

	@Override
	public void delete(long id) {
		Usuario user = man.find(Usuario.class, id);
		man.remove(user);
	}

	@Override
	public List<Usuario> findAll() {
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) man.createQuery("FROM Usuario").getResultList();
		return usuarios;
	}

	@Override
	public Usuario findById(long id) {
		Usuario user = man.find(Usuario.class, id);
		return user;
	}

	@Override
	public Usuario findByName(String username) {
		Usuario usuario = null;
		try {
		usuario = (Usuario) man.createQuery("FROM Usuario WHERE username='" + username + "'").getSingleResult();
		} catch (NullPointerException npe) {
			log.info("usuario = null en Usuario.findByName()");
		}
		return usuario;
	}

	@Override
	public boolean validar(Usuario usuario) {
		Usuario user = null;
		if(usuario != null) {
		user = man.find(Usuario.class, usuario.getId());
		}
		if (user != null)
			return true;
		return false;
	}

	@Override
	public boolean validarNombre(Usuario usuario) {
		if (usuario != null)
			if (this.findByName(usuario.getUsername()) != null)
				return true;
		return false;
	}

}
