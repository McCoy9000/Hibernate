package dataAccessLayer;

import java.util.List;

import pojos.Imagen;

public interface ImagenDAO extends IpartekDAO {

	public long insert(Imagen imagen);

	public void update(Imagen imagen);

	public void delete(Imagen imagen);

	public void delete(long id);

	public List<Imagen> findAll();

	public Imagen findById(long id);

	public Imagen findByUrl(String url);

	public boolean validar(Imagen imagen);

	public boolean validarUrl(Imagen imagen);

}
