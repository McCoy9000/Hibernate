package listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;

@WebListener("/sesion")
public class Session implements HttpSessionListener {


	@Override
	public void sessionCreated(HttpSessionEvent se) {

		// Obtener el objeto sesión
		HttpSession session = se.getSession();
		// Darle un tiempo máximo de inactividad de 15 minutos
		session.setMaxInactiveInterval(900);
		// Obtener un DAO de carritoHashMap que le asigna un nuevo carritoHashMap a la sesión
		CarritoDAO carritoDAO = CarritoDAOFactory.getCarritoDAO();
		session.setAttribute("carritoDAO", carritoDAO);
		session.setAttribute("numeroProductos", carritoDAO.findAll().size());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}

}
