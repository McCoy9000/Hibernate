package dataAccessLayer;

public class RolDAOFactory {
	
	public static RolDAO getRolDAO() {

		RolDAO rolDAO = new RolDAOHibernate();

		return rolDAO;

	}

}
