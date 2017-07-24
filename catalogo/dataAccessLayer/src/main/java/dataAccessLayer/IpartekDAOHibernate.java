package dataAccessLayer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class IpartekDAOHibernate {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
	
	public IpartekDAOHibernate() {
		// TODO Auto-generated constructor stub
	}

}
