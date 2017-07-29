package dataAccessLayer;

import javax.persistence.EntityManager;

public class ArticuloStockDAOFactory {
	
		public static ArticuloStockDAO getArticuloStockDAO(EntityManager man) {
			return new ArticuloStockDAOHibernate(man);
		}

}
