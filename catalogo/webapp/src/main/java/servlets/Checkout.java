package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pojos.ArticuloCantidad;
import pojos.ArticuloStock;
import pojos.ArticuloVendido;
import pojos.Comprador;
import pojos.Factura;
import pojos.Usuario;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;
import dataAccessLayer.DAOManagerHibernate;
import dataAccessLayer.FacturaDAO;
import dataAccessLayer.ImagenDAO;
import dataAccessLayer.UsuarioDAO;
import factories.ProductoFactory;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();

		String op = request.getParameter("op");
		
		BigInteger cantidad = null;
		if(request.getParameter("cantidad") != null)
			cantidad = new BigInteger(request.getParameter("cantidad"));

		CarritoDAO carritoDAO = (CarritoDAO) session.getAttribute("carritoDAO");
		DAOManagerHibernate daoManager = new DAOManagerHibernate();
		daoManager.abrir();
		session.setAttribute("articulosCarrito", carritoDAO.findAll());
		session.setAttribute("numeroProductos", carritoDAO.findAll().size());
		session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

		if (carritoDAO.findAll().size() == 0) {
			daoManager.cerrar();
			request.getRequestDispatcher("/catalogo").forward(request, response);
			return;
		}

		if (op == null) {
			daoManager.cerrar();
			request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
			return;
		}

		switch (op) {

		case "vaciarcarrito":
			carritoDAO = CarritoDAOFactory.getCarritoDAO();
			daoManager.cerrar();
			session.setAttribute("carritoDAO", carritoDAO);
			session.setAttribute("numeroProductos", carritoDAO.findAll().size());
			request.getRequestDispatcher("/catalogo").forward(request, response);
			break;

		case "pagar":
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			if (usuario == null) {
				request.getRequestDispatcher("/login").forward(request, response);
				return;
			}
			
			ArticuloStockDAO articuloStockDAO = daoManager.getArticuloStockDAO();
			UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
			usuario = usuarioDAO.findById(usuario.getId());
			FacturaDAO facturaDAO = daoManager.getFacturaDAO();
			daoManager.iniciarTransaccion();
			Factura factura = new Factura(usuario, new Comprador(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getDocumento(),
					usuario.getTelefono(), usuario.getEmail(), usuario.getDireccion(), usuario.getEmpresa()), LocalDate.now());
			facturaDAO.insert(factura);

			List<ArticuloCantidad> articulosCarrito = carritoDAO.findAll();
			List<ArticuloVendido> articulosFactura = new ArrayList<ArticuloVendido>();
			
			for (ArticuloCantidad ac : articulosCarrito) {
				articulosFactura.add(ProductoFactory.getArticuloVendido(ac, ac.getCantidad(), factura));
				ArticuloStock as = articuloStockDAO.findById(ac.getId());
				as.setStock(as.getStock().subtract(ac.getCantidad()));
			}
			
			
			ImagenDAO imagenDAO = daoManager.getImagenDAO();
			for (ArticuloVendido av: articulosFactura) {
				av.setImagen(imagenDAO.findById(av.getImagen().getId()));
				
			}
			factura.setArticulos(articulosFactura);


			session.setAttribute("factura", factura);
			session.setAttribute("articulosFactura", factura.getArticulos());
			session.setAttribute("ivaFactura", facturaDAO.getIvaTotal(factura.getId()));
			session.setAttribute("precioFactura", facturaDAO.getPrecioTotal(factura.getId()));
			session.setAttribute("usuarioFactura", factura.getComprador());
			daoManager.terminarTransaccion();
			daoManager.cerrar();
			
			session.setAttribute("carritoDAO", CarritoDAOFactory.getCarritoDAO());
			session.setAttribute("articulosCarrito", carritoDAO.findAll());
			session.setAttribute("numeroProductos", carritoDAO.findAll().size());
			session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

			request.getRequestDispatcher("/catalogo").forward(request, response);

			break;

		case "quitar":

			long id;

			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (NumberFormatException nfe) {
				request.getRequestDispatcher("/catalogo").forward(request, response);
				break;
			}
			
			ArticuloCantidad articulo = carritoDAO.findById(id);

			if (carritoDAO.validar(articulo)) {
				carritoDAO.delete(id, cantidad);
			}
			daoManager.cerrar();
			session.setAttribute("carritoDAO", carritoDAO);
			session.setAttribute("articulosCarrito", carritoDAO.findAll());
			session.setAttribute("numeroProductos", carritoDAO.findAll().size());
			session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

			if (carritoDAO.findAll().size() == 0) {
				request.getRequestDispatcher("/catalogo").forward(request, response);
				break;
			}
			request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
			break;
		default:
			daoManager.cerrar();
			request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
		}
	}
}
