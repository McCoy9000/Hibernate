package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pojos.ArticuloCantidad;
import pojos.ArticuloVendido;
import pojos.Comprador;
import pojos.Factura;
import pojos.Usuario;
import dataAccessLayer.ArticuloDAO;
import dataAccessLayer.ArticuloVendidoDAO;
import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;
import dataAccessLayer.CompradorDAO;
import dataAccessLayer.FacturaDAO;

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

		ArticuloDAO articuloDAO = (ArticuloDAO) application.getAttribute("productos");
		ArticuloVendidoDAO articuloVendidoDAO = (ArticuloVendidoDAO) application.getAttribute("articuloVendidoDAO");
		CompradorDAO compradorDAO = (CompradorDAO) application.getAttribute("compradorDAO");
		CarritoDAO carritoDAO = (CarritoDAO) application.getAttribute("carritoDAO");
		
		session.setAttribute("articulosCarritoArr", articuloDAO.findAll());
		session.setAttribute("productosCarritoArr", carritoDAO.findAll());
		session.setAttribute("numeroProductos", carritoDAO.findAll().size());
		session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());
		
		if (carritoDAO.findAll().size() == 0) {
			request.getRequestDispatcher("/catalogo").forward(request, response);
			return;
		} 
		
		if (op == null) {
			request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
			return;
		}
		
		switch (op) {
		
		case "vaciarcarrito":
			carritoDAO = CarritoDAOFactory.getCarritoDAO();
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

			FacturaDAO facturaDAO = (FacturaDAO) application.getAttribute("facturas");
			Comprador comprador = new Comprador (usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getDocumento(), usuario.getTelefono(), usuario.getEmail(), usuario.getDireccion(), usuario.getEmpresa(), usuario.getImagen());

			compradorDAO.insert(comprador);
			
			Factura factura = new Factura(usuario, comprador, LocalDate.now());
			facturaDAO.insert(factura);
			
			
			List<ArticuloCantidad> articulosCarrito = (List<ArticuloCantidad>) carritoDAO.findAll();
			
			for (ArticuloCantidad ac: articulosCarrito) {
				articuloVendidoDAO.insert(new ArticuloVendido(ac.getId(), ac.getCodigoArticulo(), ac.getNombre(), ac.getDescripcion(), ac.getImagen(), ac.getPrecio(), ac.getCantidad(), factura));
			}
			
			session.setAttribute("factura", factura);
			session.setAttribute("productosFactura", factura.getArticulos());
			session.setAttribute("ivaFactura", facturaDAO.getIvaTotal(factura.getId()));
			session.setAttribute("precioFactura", facturaDAO.getPrecioTotal(factura.getId()));
			session.setAttribute("usuarioFactura", factura.getComprador());
			
			session.setAttribute("carrito", CarritoDAOFactory.getCarritoDAO());
			session.setAttribute("articulosCarrito", carritoDAO.findAll());
			session.setAttribute("numeroProductos", carritoDAO.findAll().size());
			session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());
			
			break;
	
		case "quitar":

			long id;
			
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (NumberFormatException nfe) {
				request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
				break;
			}

			ArticuloCantidad articulo = carritoDAO.findById(id);
			
			if (articulo != null) {
				carritoDAO.delete(articulo);
			}

			session.setAttribute("carrito", CarritoDAOFactory.getCarritoDAO());
			session.setAttribute("articulosCarrito", carritoDAO.findAll());
			session.setAttribute("numeroProductos", carritoDAO.findAll().size());
			session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());
			
			break;

		default:
			request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
		}
	}
}
