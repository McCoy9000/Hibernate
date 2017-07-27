package dataAccessLayer;

import javax.persistence.EntityManager;

public class RolDAOFactory {
	
	public static RolDAO getRolDAO(EntityManager man) {

		RolDAO rolDAO = new RolDAOHibernate(man);

		return rolDAO;

	}

}
