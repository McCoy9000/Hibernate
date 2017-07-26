package dataAccessLayer;

import java.util.List;

import pojos.Usuario;

public class UsuarioDAOHibernate extends IpartekDAOHibernate implements UsuarioDAO {

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
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) man.createQuery("FROM Usuario WHERE username='" + username + "'").getResultList();
		Usuario usuario = usuarios.get(0);
		return usuario;
	}

	@Override
	public boolean validar(Usuario usuario) {
		Usuario user = man.find(Usuario.class, usuario.getId());
		if (user == null)
			return false;
		return true;
	}

	@Override
	public boolean validarNombre(Usuario usuario) {
		if (this.findByName(usuario.getUsername()) != null)
			return true;
		return true;
	}

}
