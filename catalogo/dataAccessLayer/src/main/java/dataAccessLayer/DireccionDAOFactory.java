package dataAccessLayer;

public class DireccionDAOFactory {

	public static DireccionDAO getDireccionDAO() {

		DireccionDAO direccionDAO = new DireccionDAOHibernate();

		return direccionDAO;

	}

}
