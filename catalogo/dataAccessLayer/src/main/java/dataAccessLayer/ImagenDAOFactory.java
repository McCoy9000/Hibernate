package dataAccessLayer;

public class ImagenDAOFactory {

	public static ImagenDAO getImagenDAO() {

		ImagenDAO imagenDAO = new ImagenDAOHibernate();

		return imagenDAO;

	}

}
