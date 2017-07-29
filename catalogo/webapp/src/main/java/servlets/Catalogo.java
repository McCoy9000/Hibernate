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

import pojos.ArticuloStock;
import pojos.ArticuloCantidad;
import recursos.Constantes;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;
import dataAccessLayer.DAOManagerHibernate;
import factories.ProductoFactory;

@WebServlet("/catalogo")
public class Catalogo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Catalogo.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();
		HttpSession session = request.getSession();

		// ArticuloStockDAO articuloDAO = (ArticuloStockDAO) application.getAttribute("articuloDAO");
		DAOManagerHibernate daoManager = new DAOManagerHibernate();
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
			String codigoArticulo = request.getParameter("codigoArticulo");
			BigInteger cantidad = BigInteger.ONE;
			try {
				cantidad = new BigInteger(request.getParameter("cantidad"));
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error al parsear la cantidad. Se utilizará 1 por defecto como precaución");
			}

			log.info("Cantidad recogida: " + cantidad);
			// articuloDAO.abrirManager();
			// articuloDAO.iniciarTransaccion();

			ArticuloStock articulo = articuloStockDAO.findById(id);
			ArticuloCantidad orden = ProductoFactory.getArticuloCantidad(articulo, cantidad);
			carritoDAO.insert(orden);
			// daoManager.iniciarTransaccion();
			// articuloDAO.restarCantidad(articulo, cantidad);
			// articuloDAO.terminarTransaccion();
			// articuloDAO.cerrarManager();
			// daoManager.terminarTransaccion();
			// daoManager.cerrar();

			session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());

			request.getRequestDispatcher(Constantes.RUTA_CATALOGO).forward(request, response);
			break;

		case "ver":
			id = Long.parseLong(request.getParameter("id"));
			// articuloDAO.abrirManager();

			ArticuloStock art = articuloStockDAO.findById(id);
			session.setAttribute("articulo", art);
			// articuloDAO.cerrarManager();
			daoManager.cerrar();
			request.getRequestDispatcher(Constantes.RUTA_ARTICULO).forward(request, response);
			break;

		default:

			request.getRequestDispatcher(Constantes.RUTA_CATALOGO).forward(request, response);

		}

	}

}
