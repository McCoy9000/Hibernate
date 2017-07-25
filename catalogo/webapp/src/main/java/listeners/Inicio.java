package listeners;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import pojos.Articulo;
import pojos.Factura;
import pojos.Imagen;
import pojos.Usuario;
import dataAccessLayer.ArticuloDAO;
import dataAccessLayer.ArticuloDAOFactory;
import dataAccessLayer.ArticuloVendidoDAO;
import dataAccessLayer.ArticuloVendidoDAOFactory;
import dataAccessLayer.CompradorDAO;
import dataAccessLayer.CompradorDAOFactory;
import dataAccessLayer.DireccionDAO;
import dataAccessLayer.DireccionDAOFactory;
import dataAccessLayer.EmpresaDAO;
import dataAccessLayer.EmpresaDAOFactory;
import dataAccessLayer.FacturaDAO;
import dataAccessLayer.FacturaDAOFactory;
import dataAccessLayer.ImagenDAO;
import dataAccessLayer.ImagenDAOFactory;
import dataAccessLayer.RolDAO;
import dataAccessLayer.RolDAOFactory;
import dataAccessLayer.UsuarioDAO;
import dataAccessLayer.UsuarioDAOFactory;
import encriptacion.Encriptador;

/**
 * Application Lifecycle Listener implementation class Inicio
 *
 */
public class Inicio implements ServletContextListener {

   public Inicio() {
        // TODO Auto-generated constructor stub
    }

   public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

   public void contextInitialized(ServletContextEvent sce)  { 
		
	   ServletContext application = sce.getServletContext();
	   
		String path = application.getContextPath();
		application.setAttribute("rutaBase", path);

	   PropertyConfigurator.configure(Inicio.class.getClassLoader().getResource("log4j.properties"));
	   
	   UsuarioDAO usuarioDAO = UsuarioDAOFactory.getUsuarioDAO();
	   ArticuloDAO articuloDAO = ArticuloDAOFactory.getArticuloDAO();
	   RolDAO rolDAO = RolDAOFactory.getRolDAO();
	   DireccionDAO direccionDAO = DireccionDAOFactory.getDireccionDAO();
	   EmpresaDAO empresaDAO = EmpresaDAOFactory.getEmpresaDAO();
	   ImagenDAO imagenDAO = ImagenDAOFactory.getImagenDAO();
	   FacturaDAO facturaDAO = FacturaDAOFactory.getFacturaDAO();
	   ArticuloVendidoDAO articuloVendidoDAO = ArticuloVendidoDAOFactory.getArticuloVendidoDAO();
	   CompradorDAO compradorDAO = CompradorDAOFactory.getCompradorDAO();
	   
	   application.setAttribute("usuarioDAO", usuarioDAO);
	   application.setAttribute("articuloDAO", articuloDAO);
	   application.setAttribute("rolDAO", rolDAO);
	   application.setAttribute("direccionDAO", direccionDAO);
	   application.setAttribute("empresaDAO", empresaDAO);
	   application.setAttribute("imagenDAO", imagenDAO);
	   application.setAttribute("facturaDAO", facturaDAO);
	   application.setAttribute("articuloVendidoDAO", articuloVendidoDAO);
	   application.setAttribute("compradorDAO", compradorDAO);

	   application.setAttribute("catalogo", articuloDAO.findAll());
	   
	   String rawadmin = "admin", admin;
	   
		Encriptador miEncriptador = null;
		try {
			miEncriptador = new Encriptador();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
			e1.printStackTrace();
		}

		admin = miEncriptador.encriptar(rawadmin);
	   
	   usuarioDAO.insert(new Usuario ("admin", "admin", "admin@admin.com", "admin", admin, rolDAO.findByName("Usuario")));
	   
	   articuloDAO.insert(new Articulo("001", "Mustang", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
	   articuloDAO.insert(new Articulo("002", "Charger", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
	   articuloDAO.insert(new Articulo("003", "Challenger", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
	   articuloDAO.insert(new Articulo("004", "Ford GT", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
	   articuloDAO.insert(new Articulo("005", "Cobra", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));
	   articuloDAO.insert(new Articulo("006", "Eldorado", "Descripción", new Imagen(), new BigDecimal("1000").setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigInteger("10")));

	   Factura.setSiguienteFactura(facturaDAO.getMaxId());
	   
    }
	
}
