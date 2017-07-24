package dataAccessLayer;

import java.util.List;

import pojos.Empresa;

public interface EmpresaDAO {

	public long insert(Empresa empresa);

	public void update(Empresa empresa);

	public void delete(Empresa empresa);

	public void delete(long id);
	
	public List<Empresa> findAll();

	public Empresa findById(long id);

	public Empresa findByName(String nombre);

	public boolean validar(Empresa empresa);

	public boolean validarNombre(Empresa empresa);

}
