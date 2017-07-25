package dataAccessLayer;

public class CompradorDAOFactory {

	public static CompradorDAO getCompradorDAO() {

		CompradorDAO compradores = new CompradorDAOHibernate();

		return compradores;

	}

}
