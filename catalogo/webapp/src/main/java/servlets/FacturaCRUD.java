package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.ArticuloFactura;
import pojos.Factura;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.FacturaDAO;

@WebServlet("/admin/facturacrud")
public class FacturaCRUD extends HttpServlet {

	private static final long serialVersionUID = -487312998985232007L;

	private static Logger log = Logger.getLogger(FacturaCRUD.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		FacturaDAO facturaDAO = daoManager.getFacturaDAO();

		HttpSession session = request.getSession();

		session.removeAttribute("errorFactura");

		String op = request.getParameter("op");

		if (op == null) {
			
			List<Factura> facturas = null;
			daoManager.iniciarTransaccion();
			try {
				facturas = facturaDAO.findAll();
				daoManager.terminarTransaccion();
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al recuperar la lista de todas las facturas");
			} finally {
				daoManager.cerrar();
			}
			application.setAttribute("facturas", facturas);

			request.getRequestDispatcher(Constantes.RUTA_LISTADO_FACTURA).forward(request, response);

		} else {

			switch (op) {

			case "ver":

				Factura factura = null;
				List<ArticuloFactura> productosFactura = null;
				BigDecimal ivaFactura = BigDecimal.ZERO;
				BigDecimal precioFactura = BigDecimal.ZERO;
				Usuario usuarioFactura = null;

				long id = 0;

				try {
					id = Long.parseLong(request.getParameter("id"));
				} catch (Exception e) {
					daoManager.cerrar();
					session.setAttribute("errorFactura", "Error al recuperar la factura. Inténtelo de nuevo");
					request.getRequestDispatcher(Constantes.RUTA_ERROR_FACTURA).forward(request, response);
					return;
				}
				daoManager.iniciarTransaccion();
				try {
					factura = facturaDAO.findById(id);

					productosFactura = factura.getArticulos();
					productosFactura.size();
				
					ivaFactura = facturaDAO.getIvaTotal(id);

					precioFactura = facturaDAO.getPrecioTotal(id);

					usuarioFactura = factura.getUsuario();
					daoManager.terminarTransaccion();
				} catch (Exception e) {
					daoManager.abortarTransaccion();
					e.printStackTrace();
					log.info("Error al recuperar la factura");
				} finally {
					daoManager.cerrar();
				}
				session.setAttribute("factura", factura);
				session.setAttribute("articulosFactura", productosFactura);
				session.setAttribute("ivaFactura", ivaFactura);
				session.setAttribute("precioFactura", precioFactura);
				session.setAttribute("usuarioFactura", usuarioFactura);
				request.getRequestDispatcher(Constantes.RUTA_FACTURA_FACTURA).forward(request, response);
				break;
			case "devolucion":
				daoManager.cerrar();
				request.getRequestDispatcher(Constantes.RUTA_DEVOLUCION).forward(request, response);
				break;

			}

		}
	}

}
