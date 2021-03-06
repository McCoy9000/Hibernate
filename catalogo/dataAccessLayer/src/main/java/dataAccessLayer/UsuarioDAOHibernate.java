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
			usuario = (Usuario) man.createQuery("FROM Usuario u WHERE u.username='" + username + "'").getSingleResult();
		} catch (Exception e) {
			log.info("Cazada excepción por no encontrar ningún resultado en la Base de Datos al buscar con createQuery y"
					+ "pedir getSingleResult()");
		}
		return usuario;
	}

	@Override
	public boolean validar(Usuario usuario) {
		Usuario user = null;
		if (usuario != null) {
			user = this.findByName(usuario.getUsername());
			if (user != null)
				if (usuario.getUsername() != null && usuario.getPassword() != null)
					if (usuario.getUsername().equals(user.getUsername()) && usuario.getPassword().equals(user.getPassword()))
						return true;
		}
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
