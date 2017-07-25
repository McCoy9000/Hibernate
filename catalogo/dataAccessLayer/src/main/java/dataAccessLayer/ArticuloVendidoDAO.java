package dataAccessLayer;

import java.util.List;

import pojos.ArticuloVendido;

public interface ArticuloVendidoDAO {
	
	public long insert(ArticuloVendido articulo);

	public void update(ArticuloVendido articulo);

	public void delete(ArticuloVendido articulo);

	public void delete(long id);

	public List<ArticuloVendido> findAll();

	public ArticuloVendido findById(long id);

	public ArticuloVendido findByCodigo(String codigo);

	public boolean validar(ArticuloVendido articulo);

	public boolean validarCodigo(ArticuloVendido articulo);
	
}
