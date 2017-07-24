package dataAccessLayer;

public class EmpresaDAOFactory {

	public static EmpresaDAO getEmpresaDAO() {

		EmpresaDAO empresaDAO = new EmpresaDAOHibernate();

		return empresaDAO;

	}

}
