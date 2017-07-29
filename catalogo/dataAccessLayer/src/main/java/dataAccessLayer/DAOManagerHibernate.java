package dataAccessLayer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DAOManagerHibernate {
	protected static EntityManagerFactory emf = null;
	protected EntityManager man = null;
	protected EntityTransaction trans = null;
	
	protected ArticuloStockDAO articuloDAO = null;
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


	public ArticuloStockDAO getArticuloStockDAO() {
			this.articuloDAO = ArticuloStockDAOFactory.getArticuloStockDAO(man);
		
		return this.articuloDAO;
	}
	public ArticuloVendidoDAO getArticuloVendidoDAO() {
			this.articuloVendidoDAO = ArticuloVendidoDAOFactory.getArticuloVendidoDAO(man);
		
		return this.articuloVendidoDAO;
	}
	public CompradorDAO getCompradorDAO() {
			this.compradorDAO = CompradorDAOFactory.getCompradorDAO(man);
		
		return this.compradorDAO;
	}
	public DireccionDAO getDireccionDAO() {
			this.direccionDAO = DireccionDAOFactory.getDireccionDAO(man);
		
		return this.direccionDAO;
	}
	public EmpresaDAO getEmpresaDAO() {
			this.empresaDAO = EmpresaDAOFactory.getEmpresaDAO(man);
		
		return this.empresaDAO;
	}
	public FacturaDAO getFacturaDAO() {
			this.facturaDAO = FacturaDAOFactory.getFacturaDAO(man);
		
		return this.facturaDAO;
	}
	public ImagenDAO getImagenDAO() {
			this.imagenDAO = ImagenDAOFactory.getImagenDAO(man);
		
		return this.imagenDAO;
	}
	public RolDAO getRolDAO() {
			this.rolDAO = RolDAOFactory.getRolDAO(man);
		
		return this.rolDAO;
	}
	public UsuarioDAO getUsuarioDAO() {
			this.usuarioDAO = UsuarioDAOFactory.getUsuarioDAO(man);
		
		return this.usuarioDAO;
	}
	
}
