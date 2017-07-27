package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pojos.ArticuloVendido;
import pojos.Comprador;
import pojos.Factura;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.CompradorDAO;
import dataAccessLayer.DAOManagerHibernate;
import dataAccessLayer.FacturaDAO;

@WebServlet("/facturasusuario")
public class FacturasUsuario extends HttpServlet {

	private static final long serialVersionUID = 7854898201744246941L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();

		session.removeAttribute("errorFactura");

		Usuario usuario = (Usuario) session.getAttribute("usuario");

//		FacturaDAO facturaDAO = (FacturaDAO) application.getAttribute("facturaDAO");
//		CompradorDAO compradorDAO = (CompradorDAO) application.getAttribute("compradorDAO");
		DAOManagerHibernate daomanager = new DAOManagerHibernate();
		FacturaDAO facturaDAO = daomanager.getFacturaDAO();
		CompradorDAO compradorDAO = daomanager.getCompradorDAO();
		String op = request.getParameter("op");

		if (op == null) {

			List<Factura> facturasUsuario = new ArrayList<Factura>();

			if (usuario != null) {
//				compradorDAO.abrirManager();
				daomanager.abrir();
				daomanager.iniciarTransaccion();
				List<Comprador> compradores = compradorDAO.findByIdUsuario(usuario.getId());
//				compradorDAO.cerrarManager();
				daomanager.terminarTransaccion();
				daomanager.cerrar();
				for (Comprador c : compradores) {
					facturasUsuario.add(c.getFactura());
				}

			}
			session.setAttribute("facturasUsuario", facturasUsuario);
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_FACTURA_USUARIOS).forward(request, response);
			return;
		} else {

			switch (op) {

			case "ver":

				Factura factura;
				List<ArticuloVendido> articulosFactura;
				BigDecimal ivaFactura;
				BigDecimal precioFactura;
				Usuario usuarioFactura;

				long id = 0;

				try {
					id = Long.parseLong(request.getParameter("id"));
				} catch (Exception e) {
					session.setAttribute("errorFactura", "Error al recuperar la factura. Inténtelo de nuevo");
					request.getRequestDispatcher(Constantes.RUTA_ERROR_FACTURA).forward(request, response);
					return;
				}
				
//				facturaDAO.abrirManager();
				daomanager.abrir();
				daomanager.iniciarTransaccion();
				factura = facturaDAO.findById(id);

				articulosFactura = factura.getArticulos();

				ivaFactura = facturaDAO.getIvaTotal(id);

				precioFactura = facturaDAO.getPrecioTotal(id);

				usuarioFactura = factura.getUsuario();
//				facturaDAO.cerrarManager();
				daomanager.terminarTransaccion();
				daomanager.cerrar();
				session.setAttribute("factura", factura);
				session.setAttribute("articulosFactura", articulosFactura);
				session.setAttribute("ivaFactura", ivaFactura);
				session.setAttribute("precioFactura", precioFactura);
				session.setAttribute("usuarioFactura", usuarioFactura);
				request.getRequestDispatcher(Constantes.RUTA_FACTURA_FACTURA).forward(request, response);
				break;
			}

		}

	}

}
