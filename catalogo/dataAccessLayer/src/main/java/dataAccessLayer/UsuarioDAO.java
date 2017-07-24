package dataAccessLayer;

import java.util.List;

import pojos.Usuario;

public interface UsuarioDAO {

	public long insert(Usuario usuario);

	public void update(Usuario usuario);

	public void delete(Usuario usuario);

	public void delete(long id);
	
	public List<Usuario> findAll();

	public Usuario findById(long id);

	public Usuario findByName(String username);

	public boolean validar(Usuario usuario);

	public boolean validarNombre(Usuario usuario);

}
