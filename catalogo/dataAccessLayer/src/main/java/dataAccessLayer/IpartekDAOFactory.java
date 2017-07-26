package dataAccessLayer;

public class IpartekDAOFactory {

	public IpartekDAOHibernate getIpartekDAO() {
		return new IpartekDAOHibernate();
	}

}
