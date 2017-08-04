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
import pojos.Rol;
import pojos.Usuario;
import recursos.Encriptador;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.FacturaDAO;
import dataAccessLayer.RolDAO;
import dataAccessLayer.UsuarioDAO;

@WebListener("/inicio")
public class Inicio implements ServletContextListener {

	private static Logger log = Logger.getLogger(Inicio.class);

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void contextInitialized(ServletContextEvent sce) {

		ServletContext application = sce.getServletContext();

		String path = application.getContextPath();
		application.setAttribute("rutaBase", path);

		PropertyConfigurator.configure(Inicio.class.getClassLoader().getResource("log4j.properties"));

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		RolDAO rolDAO = daoManager.getRolDAO();
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
		ArticuloStockDAO articuloStockDAO = daoManager.getArticuloStockDAO();
		FacturaDAO facturaDAO = daoManager.getFacturaDAO();

		daoManager.iniciarTransaccion();
		try {
		rolDAO.insert(new Rol(1, "Administrador", "Administrador de la web"));
		rolDAO.insert(new Rol(2, "Usuario", "Usuario de la web"));
		rolDAO.insert(new Rol(3, "Departamento", "Departamento de ventas"));
		log.info("Creados los roles");
		usuarioDAO.insert(new Usuario("DEVOLUCION", "DEVOLUCION", "DEVOLUCION", "DEVOLUCION", "DEVOLUCION", rolDAO.findByName("Departamento")));
		log.info("Creado el usuario DEVOLUCION");

		String rawadmin = "admin", admin;

		Encriptador miEncriptador = null;
		try {
			miEncriptador = new Encriptador();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
			e1.printStackTrace();
		}

		admin = miEncriptador.encriptar(rawadmin);
		usuarioDAO.insert(new Usuario("admin", "admin", "admin@admin.com", "admin", admin, rolDAO.findByName("Administrador")));
		log.info("Creado el usuario admin");
		articuloStockDAO.insert(new ArticuloStock("1", "Mustang", "Descripción", new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("2", "Charger", "Descripción", new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("3", "Challenger", "Descripción", new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("4", "Ford GT", "Descripción", new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("5", "Cobra", "Descripción", new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		articuloStockDAO.insert(new ArticuloStock("6", "Eldorado", "Descripción", new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
		log.info("Creados 6 productos de prueba");
		application.setAttribute("catalogo", articuloStockDAO.findAll());
		log.info("Guardado el catalogo en application");
		Factura.setSiguienteFactura(facturaDAO.getNextId());
		log.info("Registrado el último número de factura: " + Factura.getSiguienteFactura());
		daoManager.terminarTransaccion();
		} catch (Exception e) {
			daoManager.abortarTransaccion();
			e.printStackTrace();
			log.info("Error al crear el entorno de la web. Por favor, reinicie la aplicación.");
		} finally {
			daoManager.cerrar();
		}
	}

}
