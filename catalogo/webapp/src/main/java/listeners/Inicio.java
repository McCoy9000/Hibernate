package listeners;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pojos.ArticuloStock;
import pojos.Factura;
import pojos.Imagen;
import pojos.Rol;
import pojos.Usuario;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.DAOManagerHibernate;
import dataAccessLayer.FacturaDAO;
import dataAccessLayer.RolDAO;
import dataAccessLayer.UsuarioDAO;
import encriptacion.Encriptador;

@WebListener("/inicio")
public class Inicio implements ServletContextListener {

	private static Logger log = Logger.getLogger(Inicio.class);

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent sce) {

		ServletContext application = sce.getServletContext();

		String path = application.getContextPath();
		application.setAttribute("rutaBase", path);

		PropertyConfigurator.configure(Inicio.class.getClassLoader().getResource("log4j.properties"));

		// UsuarioDAO usuarioDAO = UsuarioDAOFactory.getUsuarioDAO();
		// ArticuloStockDAO articuloDAO = ArticuloStockDAOFactory.getArticuloDAO();
		// DireccionDAO direccionDAO = DireccionDAOFactory.getDireccionDAO();
		// EmpresaDAO empresaDAO = EmpresaDAOFactory.getEmpresaDAO();
		// ImagenDAO imagenDAO = ImagenDAOFactory.getImagenDAO();
		// FacturaDAO facturaDAO = FacturaDAOFactory.getFacturaDAO();
		// ArticuloVendidoDAO articuloVendidoDAO = ArticuloVendidoDAOFactory.getArticuloVendidoDAO();
		// CompradorDAO compradorDAO = CompradorDAOFactory.getCompradorDAO();
		// RolDAO rolDAO = RolDAOFactory.getRolDAO();
		// log.info("Iniciados los DAO");
		// application.setAttribute("usuarioDAO", usuarioDAO);
		// application.setAttribute("articuloDAO", articuloDAO);
		// application.setAttribute("rolDAO", rolDAO);
		// application.setAttribute("direccionDAO", direccionDAO);
		// application.setAttribute("empresaDAO", empresaDAO);
		// application.setAttribute("imagenDAO", imagenDAO);
		// application.setAttribute("facturaDAO", facturaDAO);
		// application.setAttribute("articuloVendidoDAO", articuloVendidoDAO);
		// application.setAttribute("compradorDAO", compradorDAO);
		// log.info("Guardados los DAO en application");
		DAOManagerHibernate daoManager = new DAOManagerHibernate();
		daoManager.abrir();
		RolDAO rolDAO = daoManager.getRolDAO();
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
		ArticuloStockDAO articuloStockDAO = daoManager.getArticuloStockDAO();
		FacturaDAO facturaDAO = daoManager.getFacturaDAO();

		daoManager.iniciarTransaccion();

		// rolDAO.abrirManager();
		// rolDAO.iniciarTransaccion();
		rolDAO.insert(new Rol(1, "Administrador", "Administrador de la web"));
		rolDAO.insert(new Rol(2, "Usuario", "Usuario de la web"));
		rolDAO.insert(new Rol(3, "Departamento", "Departamento de ventas"));
		log.info("Creados los roles");
		usuarioDAO.insert(new Usuario("DEVOLUCION", "DEVOLUCION", "DEVOLUCION", "DEVOLUCION", "DEVOLUCION", rolDAO.findByName("Departamento")));
		log.info("Creado el usuario DEVOLUCION");
		// rolDAO.terminarTransaccion();
		// rolDAO.cerrarManager();

		String rawadmin = "admin", admin;

		Encriptador miEncriptador = null;
		try {
			miEncriptador = new Encriptador();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
			e1.printStackTrace();
		}

		admin = miEncriptador.encriptar(rawadmin);
		// usuarioDAO.abrirManager();
		// usuarioDAO.iniciarTransaccion();
		usuarioDAO.insert(new Usuario("admin", "admin", "admin@admin.com", "admin", admin, rolDAO.findByName("Administrador")));
		log.info("Creado el usuario admin");
		articuloStockDAO.insert(new ArticuloStock("001", "Mustang", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("002", "Charger", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("003", "Challenger", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("004", "Ford GT", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("005", "Cobra", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("006", "Eldorado", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		log.info("Creados 6 productos de prueba");
		// usuarioDAO.terminarTransaccion();
		application.setAttribute("catalogo", articuloStockDAO.findAll());
		log.info("Guardado el catalogo en application");
		Factura.setSiguienteFactura(facturaDAO.getMaxId());
		log.info("Registrado el último número de factura: " + Factura.getSiguienteFactura());
		// usuarioDAO.cerrarManager();
		daoManager.terminarTransaccion();
		daoManager.cerrar();

	}

}
