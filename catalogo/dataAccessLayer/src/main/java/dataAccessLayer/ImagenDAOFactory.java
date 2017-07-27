package dataAccessLayer;

import javax.persistence.EntityManager;

public class ImagenDAOFactory {

	public static ImagenDAO getImagenDAO(EntityManager man) {

		ImagenDAO imagenDAO = new ImagenDAOHibernate(man);

		return imagenDAO;

	}

}
