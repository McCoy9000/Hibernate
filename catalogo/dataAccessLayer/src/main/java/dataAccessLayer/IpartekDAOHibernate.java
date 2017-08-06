package dataAccessLayer;

import javax.persistence.EntityManager;

public class IpartekDAOHibernate implements IpartekDAO {

	protected EntityManager man = null;

	public IpartekDAOHibernate() {
	}

	public IpartekDAOHibernate(EntityManager man) {
		this.man = man;
	}
}
