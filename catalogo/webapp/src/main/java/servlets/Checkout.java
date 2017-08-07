package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.ArticuloCantidad;
import pojos.ArticuloStock;
import pojos.ArticuloVendido;
import pojos.Comprador;
import pojos.Factura;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.CarritoDAO;
import dataAccessLayer.CarritoDAOFactory;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.FacturaDAO;
import dataAccessLayer.ImagenDAO;
import dataAccessLayer.UsuarioDAO;
import factories.ArticuloFactory;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {

	private static final long serialVersionUID = 6627315683844676739L;
	
	private static Logger log = Logger.getLogger(Checkout.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		String op = request.getParameter("op");
		
		BigInteger cantidad = null;
		if(request.getParameter("cantidad") != null)
			cantidad = new BigInteger(request.getParameter("cantidad"));

		CarritoDAO carritoDAO = (CarritoDAO) session.getAttribute("carritoDAO");
		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		session.setAttribute("articulosCarrito", carritoDAO.findAll());
		session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());
		session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

		if (carritoDAO.findAll().size() == 0) {
			daoManager.cerrar();
			request.getRequestDispatcher(Constantes.RUTA_SERVLET_CATALOGO).forward(request, response);
			return;
		}

		if (op == null) {
			daoManager.cerrar();
			request.getRequestDispatcher(Constantes.RUTA_CHECKOUT).forward(request, response);
			return;
		}

		switch (op) {

		case "vaciarcarrito":
			carritoDAO = CarritoDAOFactory.getCarritoDAO();
			daoManager.cerrar();
			session.setAttribute("carritoDAO", carritoDAO);
			session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());
			request.getRequestDispatcher(Constantes.RUTA_SERVLET_CATALOGO).forward(request, response);
			break;

		case "pagar":
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			if (usuario == null) {
				request.getRequestDispatcher(Constantes.RUTA_SERVLET_LOGIN).forward(request, response);
				return;
			}
			
			ArticuloStockDAO articuloStockDAO = daoManager.getArticuloStockDAO();
			UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
			daoManager.iniciarTransaccion();
			try {
				usuario = usuarioDAO.findById(usuario.getId());
				daoManager.terminarTransaccion();
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al recuperar el usuario con id " + usuario.getId() + " de la base de datos.");
			}
			FacturaDAO facturaDAO = daoManager.getFacturaDAO();
			daoManager.iniciarTransaccion();
			try {
				Factura factura = new Factura(usuario, new Comprador(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getDocumento(),
						usuario.getTelefono(), usuario.getEmail(), usuario.getDireccion(), usuario.getEmpresa()), LocalDate.now());
				facturaDAO.insert(factura);
	
				List<ArticuloCantidad> articulosCarrito = carritoDAO.findAll();
				List<ArticuloVendido> articulosFactura = new ArrayList<ArticuloVendido>();
				
				for (ArticuloCantidad ac : articulosCarrito) {
					articulosFactura.add(ArticuloFactory.getArticuloVendido(ac, ac.getCantidad(), factura));
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
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al pagar la compra. Revise sus facturas y, en caso necesario, repita la operación");
			} finally {
				daoManager.cerrar();
			}
			
			
			session.setAttribute("carritoDAO", CarritoDAOFactory.getCarritoDAO());
			session.setAttribute("articulosCarrito", carritoDAO.findAll());
			session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());
			session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

			request.getRequestDispatcher(Constantes.RUTA_SERVLET_CATALOGO).forward(request, response);

			break;

		case "quitar":

			long id;

			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (NumberFormatException nfe) {
				request.getRequestDispatcher(Constantes.RUTA_SERVLET_CHECKOUT).forward(request, response);
				break;
			}
			
			ArticuloCantidad articulo = carritoDAO.findById(id);

			if (carritoDAO.validar(articulo)) {
				carritoDAO.delete(id, cantidad);
			}
			daoManager.cerrar();
			session.setAttribute("carritoDAO", carritoDAO);
			session.setAttribute("articulosCarrito", carritoDAO.findAll());
			session.setAttribute("numeroArticulos", carritoDAO.getTotalArticulos());
			session.setAttribute("precioTotal", carritoDAO.getPrecioTotal());

			if (carritoDAO.findAll().size() == 0) {
				request.getRequestDispatcher(Constantes.RUTA_SERVLET_CATALOGO).forward(request, response);
				break;
			}
			request.getRequestDispatcher(Constantes.RUTA_CHECKOUT).forward(request, response);
			break;
		default:
			daoManager.cerrar();
			request.getRequestDispatcher(Constantes.RUTA_CHECKOUT).forward(request, response);
		}
	}
}
