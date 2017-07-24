package dataAccessLayer;

import java.math.BigInteger;
import java.util.List;

import pojos.Articulo;

public interface ArticuloDAO {
	
	public long insert(Articulo articulo);

	public void update(Articulo articulo);

	public void delete(Articulo articulo);

	public void delete(long id);

	public List<Articulo> findAll();

	public Articulo findById(long id);

	public Articulo findByCodigo(String codigo);

	public boolean validar(Articulo articulo);

	public boolean validarCodigo(Articulo articulo);
	
	public void restarCantidad(Articulo articulo, BigInteger cantidad);

}
