package dataAccessLayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DAOManagerHibernate {
	protected static EntityManagerFactory emf = null;
	protected EntityManager man = null;
	protected EntityTransaction trans = null;
	
	protected ArticuloDAO articuloDAO = null;
	protected ArticuloVendidoDAO articuloVendidoDAO = null;
	protected CompradorDAO compradorDAO = null;
	protected DireccionDAO direccionDAO = null;
	protected EmpresaDAO empresaDAO = null;
	protected FacturaDAO facturaDAO = null;
	protected ImagenDAO imagenDAO = null;
	protected RolDAO rolDAO = null;
	protected UsuarioDAO usuarioDAO = null;
	
	public DAOManagerHibernate() {
		if (emf == null)
			emf = Persistence.createEntityManagerFactory("persistencia");
	}
	
	public void abrir() {
		this.man = emf.createEntityManager();
	}

	
	public void cerrar() {
		man.close();
	}

	
	public void iniciarTransaccion() {
		trans = man.getTransaction();
		trans.begin();
	}

	
	public void terminarTransaccion() {
		trans.commit();
	}


	public ArticuloDAO getArticuloDAO() {
			this.articuloDAO = new ArticuloDAOHibernate(this.man);
		
		return this.articuloDAO;
	}
	public ArticuloVendidoDAO getArticuloVendidoDAO() {
			this.articuloVendidoDAO = new ArticuloVendidoDAOHibernate(this.man);
		
		return this.articuloVendidoDAO;
	}
	public CompradorDAO getCompradorDAO() {
			this.compradorDAO = new CompradorDAOHibernate(this.man);
		
		return this.compradorDAO;
	}
	public DireccionDAO getDireccionDAO() {
			this.direccionDAO = new DireccionDAOHibernate(this.man);
		
		return this.direccionDAO;
	}
	public EmpresaDAO getEmpresaDAO() {
			this.empresaDAO = new EmpresaDAOHibernate(this.man);
		
		return this.empresaDAO;
	}
	public FacturaDAO getFacturaDAO() {
			this.facturaDAO = new FacturaDAOHibernate(this.man);
		
		return this.facturaDAO;
	}
	public ImagenDAO getImagenDAO() {
			this.imagenDAO = new ImagenDAOHibernate(this.man);
		
		return this.imagenDAO;
	}
	public RolDAO getRolDAO() {
			this.rolDAO = new RolDAOHibernate(this.man);
		
		return this.rolDAO;
	}
	public UsuarioDAO getUsuarioDAO() {
			this.usuarioDAO = new UsuarioDAOHibernate(this.man);
		
		return this.usuarioDAO;
	}
	
}
