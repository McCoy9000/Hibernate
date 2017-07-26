package dataAccessLayer;

import java.util.List;

import pojos.Comprador;

public interface CompradorDAO extends IpartekDAO {

	public long insert(Comprador comprador);

	public void update(Comprador comprador);

	public void delete(Comprador comprador);

	public void delete(long id);

	public List<Comprador> findAll();

	public Comprador findById(long id);

	public List<Comprador> findByIdUsuario(long idUsuario);

	public boolean validar(Comprador comprador);

}
