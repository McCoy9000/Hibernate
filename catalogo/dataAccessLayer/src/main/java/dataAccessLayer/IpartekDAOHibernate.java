package dataAccessLayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class IpartekDAOHibernate implements IpartekDAO {

//	protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
	protected EntityManager man = null;
	protected EntityTransaction trans = null;
//
	public IpartekDAOHibernate() {
	}

	public IpartekDAOHibernate(EntityManager man) {
		this.man = man;
	}
	
	@Override
	public void abrirManager() {
//		man = emf.createEntityManager();
	}

	@Override
	public void cerrarManager() {
		man.close();
	}

	@Override
	public void iniciarTransaccion() {
		trans = man.getTransaction();
		trans.begin();
	}

	@Override
	public void terminarTransaccion() {
		trans.commit();
	}

}
