package dataAccessLayer;

public class FacturaDAOFactory {
	
	public static FacturaDAO getFacturaDAO() {

		FacturaDAO facturaDAO = new FacturaDAOHibernate();

		return facturaDAO;

	}

}
