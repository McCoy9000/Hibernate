package dataAccessLayer;

public class ArticuloDAOFactory {
	
		public static ArticuloDAO getArticuloDAO() {
			return new ArticuloDAOHibernate();
		}

}
