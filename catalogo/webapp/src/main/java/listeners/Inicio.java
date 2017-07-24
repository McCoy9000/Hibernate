package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import dataAccessLayer.UsuarioDAO;
import dataAccessLayer.UsuarioDAOFactory;

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
	   
	   PropertyConfigurator.configure(Inicio.class.getClassLoader().getResource("log4j.properties"));
	   
	   UsuarioDAO usuarioDAO = UsuarioDAOFactory.getUsuarioDAO();
	   
	   application.setAttribute("usuarioDAO", usuarioDAO);
	   
	   
    }
	
}
