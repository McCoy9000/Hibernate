package dataAccessLayer;

import javax.persistence.EntityManager;

public class EmpresaDAOFactory {

	public static EmpresaDAO getEmpresaDAO(EntityManager man) {

		EmpresaDAO empresaDAO = new EmpresaDAOHibernate(man);

		return empresaDAO;

	}

}
