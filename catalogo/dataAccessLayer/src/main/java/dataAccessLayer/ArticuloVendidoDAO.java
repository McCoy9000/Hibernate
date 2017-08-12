package dataAccessLayer;

import java.util.List;

import pojos.ArticuloFactura;

public interface ArticuloVendidoDAO extends IpartekDAO {

	public long insert(ArticuloFactura articulo);

	public void update(ArticuloFactura articulo);

	public void delete(ArticuloFactura articulo);

	public void delete(long id);

	public List<ArticuloFactura> findAll();

	public ArticuloFactura findById(long id);

	public List<ArticuloFactura> findByCodigo(String codigo);

	public boolean validar(ArticuloFactura articulo);

	public boolean validarCodigo(ArticuloFactura articulo);

}
