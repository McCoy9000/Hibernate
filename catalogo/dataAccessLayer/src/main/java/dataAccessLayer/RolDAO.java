package dataAccessLayer;

import java.util.List;

import pojos.Rol;

public interface RolDAO {
	
	public long insert(Rol rol);

	public void update(Rol rol);

	public void delete(Rol rol);

	public void delete(long id);
	
	public List<Rol> findAll();

	public Rol findById(int id);

	public Rol findByName(String nombre);

	public boolean validar(Rol rol);

	public boolean validarNombre(Rol rol);


}
