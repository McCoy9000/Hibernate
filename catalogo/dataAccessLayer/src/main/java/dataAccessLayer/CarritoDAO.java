package dataAccessLayer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import pojos.ArticuloCantidad;

public interface CarritoDAO {

	public long insert(ArticuloCantidad articulo);

	public void update(ArticuloCantidad articulo);

	public void delete(ArticuloCantidad articulo);

	public void delete(long id);

	public List<ArticuloCantidad> findAll();

	public ArticuloCantidad findById(long id);

	public ArticuloCantidad findByCodigo(String codigo);

	public boolean validar(ArticuloCantidad articulo);

	public boolean validarCodigo(String codigo);

	public BigDecimal getPrecioTotal();

	public BigInteger getTotalArticulos();

}
