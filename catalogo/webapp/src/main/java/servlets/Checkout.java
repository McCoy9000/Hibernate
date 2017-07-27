package servlets;

import java.io.IOException;
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
import pojos.ArticuloVendido;
import pojos.Comprador;
import pojos.Factura;
import pojos.Usuario;
import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;
import dataAccessLayer.DAOManagerHibernate;
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

		// ArticuloDAO articuloDAO = (ArticuloDAO) application.getAttribute("productos");
		CarritoDAO carritoDAO = (CarritoDAO) session.getAttribute("carritoDAO");
		// articuloDAO.abrirManager();
		DAOManagerHibernate daomanager = new DAOManagerHibernate();
		daomanager.abrir();
		session.setAttribute("articulosCarrito", carritoDAO.findAll());
		// articuloDAO.cerrarManager();
		session.setAttribute("numeroProductos", carritoDAO.findAll().size());
		session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

		if (carritoDAO.findAll().size() == 0) {
			daomanager.cerrar();
			request.getRequestDispatcher("/catalogo").forward(request, response);
			return;
		}

		if (op == null) {
			daomanager.cerrar();
			request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
			return;
		}

		switch (op) {

		case "vaciarcarrito":
			carritoDAO = CarritoDAOFactory.getCarritoDAO();
			daomanager.cerrar();
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

			// FacturaDAO facturaDAO = (FacturaDAO) application.getAttribute("facturas");
			FacturaDAO facturaDAO = daomanager.getFacturaDAO();
			daomanager.iniciarTransaccion();
			Factura factura = new Factura(usuario, new Comprador(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getDocumento(),
					usuario.getTelefono(), usuario.getEmail(), usuario.getDireccion(), usuario.getEmpresa(), usuario.getImagen()), LocalDate.now());
			// facturaDAO.abrirManager();
			// facturaDAO.iniciarTransaccion();
			facturaDAO.insert(factura);

			List<ArticuloCantidad> articulosCarrito = carritoDAO.findAll();
			List<ArticuloVendido> articulosFactura = new ArrayList<ArticuloVendido>();

			for (ArticuloCantidad ac : articulosCarrito) {
				articulosFactura.add(new ArticuloVendido(ac.getCodigoArticulo(), ac.getNombre(), ac.getDescripcion(), ac.getImagen(), ac.getPrecio(), ac.getCantidad(), factura));
			}

			factura.setArticulos(articulosFactura);
			// facturaDAO.update(factura);

			session.setAttribute("factura", factura);
			session.setAttribute("productosFactura", factura.getArticulos());
			session.setAttribute("ivaFactura", facturaDAO.getIvaTotal(factura.getId()));
			session.setAttribute("precioFactura", facturaDAO.getPrecioTotal(factura.getId()));
			session.setAttribute("usuarioFactura", factura.getComprador());
			daomanager.terminarTransaccion();
			daomanager.cerrar();
			// facturaDAO.terminarTransaccion();
			// facturaDAO.cerrarManager();
			session.setAttribute("carrito", CarritoDAOFactory.getCarritoDAO());
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
				request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
				break;
			}

			ArticuloCantidad articulo = carritoDAO.findById(id);

			if (articulo != null) {
				carritoDAO.delete(articulo);
			}
			daomanager.cerrar();
			session.setAttribute("carrito", CarritoDAOFactory.getCarritoDAO());
			session.setAttribute("articulosCarrito", carritoDAO.findAll());
			session.setAttribute("numeroProductos", carritoDAO.findAll().size());
			session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

			if (carritoDAO.findAll().size() == 0) {
				request.getRequestDispatcher("/catalogo").forward(request, response);
				break;
			}

		default:
			daomanager.cerrar();
			request.getRequestDispatcher("/WEB-INF/vistas/checkout.jsp").forward(request, response);
		}
	}
}
