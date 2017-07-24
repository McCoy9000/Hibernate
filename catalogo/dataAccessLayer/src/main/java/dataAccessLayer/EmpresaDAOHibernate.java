package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.Empresa;
import pojos.Usuario;

public class EmpresaDAOHibernate extends IpartekDAOHibernate implements EmpresaDAO {

	
	@Override
	public long insert(Empresa empresa) {

		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.persist(empresa);
		transaction.commit();
		long id = empresa.getId();
		man.close();	
		return id;
	}

	@Override
	public void update(Empresa empresa) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(empresa);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Empresa empresa) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Empresa emp = man.find(Empresa.class, empresa.getId());
		transaction.begin();
		man.remove(emp);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Empresa empresa = man.find(Empresa.class, id);
		transaction.begin();
		man.remove(empresa);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Empresa> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Empresa> empresas = (List<Empresa>)man.createQuery("FROM Empresa");
		man.close();
		return empresas;
	}

	@Override
	public Empresa findById(long id) {
		EntityManager man = emf.createEntityManager();
		Empresa empresa = man.find(Empresa.class, id);
		man.close();
		return empresa;
	}

	@Override
	public Empresa findByName(String nombre) {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Empresa> empresas = (List<Empresa>)man.createQuery("FROM Empresa WHERE username=" + nombre);
		Empresa empresa = empresas.get(0);
		man.close();
		return empresa;
	}

	@Override
	public boolean validar(Empresa empresa) {
		EntityManager man = emf.createEntityManager();
		Empresa emp = man.find(Empresa.class, empresa.getId());
		man.close();
		if (emp == null)
		return false;
		return true;
	}

	@Override
	public boolean validarNombre(Empresa empresa) {
		if(this.findByName(empresa.getNombre()) != null)
		return true;
		return true;
	}

}
