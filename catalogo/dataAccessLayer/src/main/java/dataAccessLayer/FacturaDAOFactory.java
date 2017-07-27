package dataAccessLayer;

import javax.persistence.EntityManager;

public class FacturaDAOFactory {
	
	public static FacturaDAO getFacturaDAO(EntityManager man) {

		FacturaDAO facturaDAO = new FacturaDAOHibernate(man);

		return facturaDAO;

	}

}
