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

import pojos.Articulo;
import pojos.ArticuloCantidad;
import recursos.Constantes;
import dataAccessLayer.ArticuloDAO;
import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;

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
		
		ArticuloDAO articuloDAO = (ArticuloDAO) application.getAttribute("articuloDAO");
		
		application.setAttribute("catalogo", articuloDAO.findAll());
		
		CarritoDAO carritoDAO = (CarritoDAO) session.getAttribute("carritoDAO");

		if (carritoDAO == null) {
			carritoDAO = CarritoDAOFactory.getCarritoDAO();
		}

		String op = request.getParameter("op");

		if (op == null) {

			// Si se llega al catálogo sin opciones, el carrito se empaqueta
			// en el objeto sesión
			session.setAttribute("carritoDAO", carritoDAO);
			session.setAttribute("numeroProductos", carritoDAO.findAll().size());

			request.getRequestDispatcher("/WEB-INF/vistas/catalogo.jsp").forward(request, response);
			return;
		}
		
		switch (op) {

		case "anadir":
			String codigoArticulo = request.getParameter("codigoArticulo");
			BigInteger cantidad = BigInteger.ONE;
			try {
				cantidad = new BigInteger(request.getParameter("cantidad"));
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error al parsear la cantidad. Se utilizará 1 por defecto como precaución");
			}
			
			log.info("Cantidad recogida: " + cantidad);
			
			Articulo articulo = articuloDAO.findByCodigo(codigoArticulo);
			ArticuloCantidad orden = new ArticuloCantidad(articulo.getCodigoArticulo(), articulo.getNombre(), articulo.getDescripcion(), articulo.getImagen(), articulo.getPrecio(), articulo.getStock(), cantidad);
			carritoDAO.insert(orden);
			
			session.setAttribute("numeroArticulos", carritoDAO.findAll().size());
			
			request.getRequestDispatcher(Constantes.RUTA_CATALOGO).forward(request, response);
			break;
			
		case "ver":
			long id = Long.parseLong(request.getParameter("id"));
			Articulo art = articuloDAO.findById(id);
			session.setAttribute("articulo", art);
			request.getRequestDispatcher(Constantes.RUTA_ARTICULO).forward(request, response);
			break;

		default:

			request.getRequestDispatcher(Constantes.RUTA_CATALOGO).forward(request, response);

			
		}
			
			
	}

	
}

