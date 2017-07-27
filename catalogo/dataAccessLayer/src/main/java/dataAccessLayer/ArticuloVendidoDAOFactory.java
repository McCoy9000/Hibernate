package dataAccessLayer;

import javax.persistence.EntityManager;

public class ArticuloVendidoDAOFactory {
	
	public static ArticuloVendidoDAO getArticuloVendidoDAO(EntityManager man) {
		return new ArticuloVendidoDAOHibernate(man);
	}


}
