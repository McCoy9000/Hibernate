package dataAccessLayer;

import java.math.BigDecimal;
import java.util.List;

import pojos.Factura;

public interface FacturaDAO extends IpartekDAO {

	public long insert(Factura factura);

	public void update(Factura factura);

	public void delete(Factura factura);

	public void delete(long id);

	public List<Factura> findAll();

	public Factura findById(long id);

	public BigDecimal getIvaTotal(long id);

	public BigDecimal getPrecioTotal(long id);

	public long getNextId();

}
