package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

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
		PropertyConfigurator.configure(Inicio.class.getClassLoader().getResource("log4j.properties"));
    }
	
}
