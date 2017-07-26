package dataAccessLayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class IpartekDAOHibernate implements IpartekDAO {

	protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");
	protected EntityManager man;
	protected EntityTransaction trans;

	public IpartekDAOHibernate() {
	}

	@Override
	public void abrirManager() {
		man = emf.createEntityManager();
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
