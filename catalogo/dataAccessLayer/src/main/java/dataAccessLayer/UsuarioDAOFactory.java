package dataAccessLayer;

import javax.persistence.EntityManager;

public class UsuarioDAOFactory {

	public static UsuarioDAO getUsuarioDAO(EntityManager man) {

		UsuarioDAO usuarios = new UsuarioDAOHibernate(man);

		return usuarios;

	}

}
