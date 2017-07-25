package dataAccessLayer;

import java.util.List;

import pojos.Comprador;

public interface CompradorDAO {

	public long insert(Comprador comprador);

	public void update(Comprador comprador);

	public void delete(Comprador comprador);

	public void delete(long id);
	
	public List<Comprador> findAll();

	public Comprador findById(long id);

	public Comprador findByName(String username);

	public boolean validar(Comprador comprador);

	public boolean validarNombre(Comprador comprador);

}
