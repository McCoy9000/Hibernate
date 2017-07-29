package dataAccessLayer;

import java.math.BigInteger;
import java.util.List;

import pojos.ArticuloStock;

public interface ArticuloStockDAO extends IpartekDAO {

	public long insert(ArticuloStock articulo);

	public void update(ArticuloStock articulo);

	public void delete(ArticuloStock articulo);

	public void delete(long id);

	public List<ArticuloStock> findAll();

	public ArticuloStock findById(long id);

	public ArticuloStock findByCodigo(String codigo);

	public boolean validar(ArticuloStock articulo);

	public boolean validarCodigo(ArticuloStock articulo);

	public void restarCantidad(ArticuloStock articulo, BigInteger cantidad);

}
