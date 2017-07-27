package dataAccessLayer;

import javax.persistence.EntityManager;

public class DireccionDAOFactory {

	public static DireccionDAO getDireccionDAO(EntityManager man) {

		DireccionDAO direccionDAO = new DireccionDAOHibernate(man);

		return direccionDAO;

	}

}
