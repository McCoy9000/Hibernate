package dataAccessLayer;

import javax.persistence.EntityManager;

public class ArticuloDAOFactory {
	
		public static ArticuloDAO getArticuloDAO(EntityManager man) {
			return new ArticuloDAOHibernate(man);
		}

}
