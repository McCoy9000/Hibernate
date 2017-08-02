package servlets;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.ArticuloCantidad;
import pojos.ArticuloStock;
import recursos.Constantes;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import factories.ProductoFactory;

@WebServlet("/catalogo")
public class Catalogo extends HttpServlet {
	
	private static final long serialVersionUID = 5511427823584098761L;
	
	private static Logger log = Logger.getLogger(Catalogo.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();
		HttpSession session = request.getSession();

		DAOManager daoManager = DAOManagerFactory.getDAOManager();

		daoManager.abrir();
		ArticuloStockDAO articuloStockDAO = daoManager.getArticuloStockDAO();
		application.setAttribute("catalogo", articuloStockDAO.findAll());
		CarritoDAO carritoDAO = (CarritoDAO) session.getAttribute("carritoDAO");

		if (carritoDAO == null) {
			carritoDAO = CarritoDAOFactory.getCarritoDAO();
		}

		long id;

		String op = request.getParameter("op");

		if (op == null) {

			// Si se llega al catálogo sin opciones, el carrito se empaqueta
			// en el objeto sesión
			session.setAttribute("carritoDAO", carritoDAO);
			session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());

			request.getRequestDispatcher("/WEB-INF/vistas/catalogo.jsp").forward(request, response);
			return;
		}

		switch (op) {

		case "anadir":
			id = Long.parseLong(request.getParameter("id"));
			BigInteger cantidad = BigInteger.ONE;
			try {
				cantidad = new BigInteger(request.getParameter("cantidad"));
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error al parsear la cantidad. Se utilizará 1 por defecto como precaución");
			}

			log.info("Cantidad recogida: " + cantidad);
			
			daoManager.iniciarTransaccion();
			ArticuloStock as = articuloStockDAO.findById(id);
			ArticuloCantidad ac = ProductoFactory.getArticuloCantidad(as, cantidad);
			long acId = carritoDAO.insert(ac);
			if (carritoDAO.findById(acId).getCantidad().compareTo(as.getStock()) > 0)
				carritoDAO.findById(acId).setCantidad(as.getStock());
			daoManager.terminarTransaccion();
			daoManager.cerrar();

			session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());

			request.getRequestDispatcher(Constantes.RUTA_CATALOGO).forward(request, response);
			break;

		case "ver":
			id = Long.parseLong(request.getParameter("id"));

			ArticuloStock art = articuloStockDAO.findById(id);
			session.setAttribute("articulo", art);
			daoManager.cerrar();
			request.getRequestDispatcher(Constantes.RUTA_ARTICULO).forward(request, response);
			break;

		default:
			daoManager.cerrar();
			session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());
			request.getRequestDispatcher(Constantes.RUTA_CATALOGO).forward(request, response);

		}

	}

}
