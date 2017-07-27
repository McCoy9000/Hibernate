package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;

import pojos.Imagen;

public class ImagenDAOHibernate extends IpartekDAOHibernate implements ImagenDAO {

	public ImagenDAOHibernate() {

	}

	public ImagenDAOHibernate(EntityManager man) {
		this.man = man;
	}

	@Override
	public long insert(Imagen imagen) {
		man.persist(imagen);
		long id = imagen.getId();
		return id;
	}

	@Override
	public void update(Imagen imagen) {
		man.merge(imagen);
	}

	@Override
	public void delete(Imagen imagen) {
		Imagen img = man.find(Imagen.class, imagen.getId());
		man.remove(img);
	}

	@Override
	public void delete(long id) {
		Imagen imagen = man.find(Imagen.class, id);
		man.remove(imagen);
	}

	@Override
	public List<Imagen> findAll() {
		@SuppressWarnings("unchecked")
		List<Imagen> imagenes = (List<Imagen>) man.createQuery("FROM Imagen").getResultList();
		return imagenes;
	}

	@Override
	public Imagen findById(long id) {
		Imagen imagen = man.find(Imagen.class, id);
		return imagen;
	}

	@Override
	public Imagen findByUrl(String url) {
		@SuppressWarnings("unchecked")
		List<Imagen> imagenes = (List<Imagen>) man.createQuery("FROM Imagen WHERE url='" + url + "'").getResultList();
		Imagen imagen = imagenes.get(0);
		return imagen;
	}

	@Override
	public boolean validar(Imagen imagen) {
		Imagen img = man.find(Imagen.class, imagen.getId());
		if (img == null)
			return false;
		return true;
	}

	@Override
	public boolean validarUrl(Imagen imagen) {
		if (this.findByUrl(imagen.getUrl()) != null)
			return true;
		return true;
	}

}
