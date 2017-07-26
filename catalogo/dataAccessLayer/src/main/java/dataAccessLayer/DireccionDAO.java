package dataAccessLayer;

import java.util.List;

import pojos.Direccion;

public interface DireccionDAO extends IpartekDAO {

	public long insert(Direccion direccion);

	public void update(Direccion direccion);

	public void delete(Direccion direccion);

	public void delete(long id);

	public List<Direccion> findAll();

	public Direccion findById(long id);

	public boolean validar(Direccion direccion);

}
