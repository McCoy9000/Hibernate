package dataAccessLayer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pojos.Imagen;

public class ImagenDAOHibernate extends IpartekDAOHibernate implements ImagenDAO {

	
	@Override
	public long insert(Imagen imagen) {

		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.persist(imagen);
		transaction.commit();
		long id = imagen.getId();
		man.close();	
		return id;
	}

	@Override
	public void update(Imagen imagen) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		transaction.begin();
		man.merge(imagen);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(Imagen imagen) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Imagen img = man.find(Imagen.class, imagen.getId());
		transaction.begin();
		man.remove(img);
		transaction.commit();
		man.close();
	}

	@Override
	public void delete(long id) {
		EntityManager man = emf.createEntityManager();
		EntityTransaction transaction = man.getTransaction();
		Imagen imagen = man.find(Imagen.class, id);
		transaction.begin();
		man.remove(imagen);
		transaction.commit();
		man.close();
	}

	@Override
	public List<Imagen> findAll() {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Imagen> imagenes = (List<Imagen>)man.createQuery("FROM Imagen").getResultList();
		man.close();
		return imagenes;
	}

	@Override
	public Imagen findById(long id) {
		EntityManager man = emf.createEntityManager();
		Imagen imagen = man.find(Imagen.class, id);
		man.close();
		return imagen;
	}

	@Override
	public Imagen findByUrl(String url) {
		EntityManager man = emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Imagen> imagenes = (List<Imagen>)man.createQuery("FROM Imagen WHERE url='" + url + "'").getResultList();
		Imagen imagen = imagenes.get(0);
		man.close();
		return imagen;
	}

	@Override
	public boolean validar(Imagen imagen) {
		EntityManager man = emf.createEntityManager();
		Imagen img = man.find(Imagen.class, imagen.getId());
		man.close();
		if (img == null)
		return false;
		return true;
	}

	@Override
	public boolean validarUrl(Imagen imagen) {
		if(this.findByUrl(imagen.getUrl()) != null)
		return true;
		return true;
	}

}
