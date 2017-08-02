package dataAccessLayer;

public class DAOManagerFactory {

	public static DAOManager getDAOManager() {
		return new DAOManagerHibernate();
	}
}
