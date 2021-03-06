package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;

import pojos.Empresa;

public class EmpresaDAOHibernate extends IpartekDAOHibernate implements EmpresaDAO {

	public EmpresaDAOHibernate() {

	}
	
	public EmpresaDAOHibernate(EntityManager man) {
		this.man = man;
	}

	
	@Override
	public long insert(Empresa empresa) {
		man.persist(empresa);
		long id = empresa.getId();
		return id;
	}

	@Override
	public void update(Empresa empresa) {
		man.merge(empresa);
	}

	@Override
	public void delete(Empresa empresa) {
		Empresa emp = man.find(Empresa.class, empresa.getId());
		man.remove(emp);
	}

	@Override
	public void delete(long id) {
		Empresa empresa = man.find(Empresa.class, id);
		man.remove(empresa);
	}

	@Override
	public List<Empresa> findAll() {
		@SuppressWarnings("unchecked")
		List<Empresa> empresas = (List<Empresa>) man.createQuery("FROM Empresa").getResultList();
		return empresas;
	}

	@Override
	public Empresa findById(long id) {
		Empresa empresa = man.find(Empresa.class, id);
		return empresa;
	}

	@Override
	public List<Empresa> findByName(String nombre) {
		@SuppressWarnings("unchecked")
		List<Empresa> empresas = (List<Empresa>) man.createQuery("FROM Empresa WHERE nombre='" + nombre + "'").getResultList();
		return empresas;
	}

	@Override
	public boolean validar(Empresa empresa) {
		Empresa emp = man.find(Empresa.class, empresa.getId());
		if (emp == null)
			return false;
		return true;
	}

	@Override
	public boolean validarNombre(Empresa empresa) {
		if (this.findByName(empresa.getNombre()) != null)
			return true;
		return true;
	}

}
