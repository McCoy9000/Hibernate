package dataAccessLayer;

import javax.persistence.EntityManager;

public class CompradorDAOFactory {

	public static CompradorDAO getCompradorDAO(EntityManager man) {

		CompradorDAO compradores = new CompradorDAOHibernate(man);

		return compradores;

	}

}
