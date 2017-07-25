package dataAccessLayer;

public class ArticuloVendidoDAOFactory {
	
	public static ArticuloVendidoDAO getArticuloVendidoDAO() {
		return new ArticuloVendidoDAOHibernate();
	}


}
