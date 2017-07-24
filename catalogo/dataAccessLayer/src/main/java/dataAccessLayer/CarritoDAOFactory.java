package dataAccessLayer;

public class CarritoDAOFactory {
	public static CarritoDAO getCarritoDAO() {
		return new CarritoDAOColeccion();
	}

}
