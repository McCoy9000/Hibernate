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

import pojos.ArticuloVendido;
import pojos.Factura;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.DAOManagerHibernate;
import dataAccessLayer.FacturaDAO;

@WebServlet("/admin/facturacrud")
public class FacturaCRUD extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();

		DAOManagerHibernate daoManager = new DAOManagerHibernate();
		daoManager.abrir();
		FacturaDAO facturaDAO = daoManager.getFacturaDAO();

		HttpSession session = request.getSession();

		session.removeAttribute("errorFactura");

		String op = request.getParameter("op");

		if (op == null) {

			daoManager.iniciarTransaccion();
			List<Factura> facturas = facturaDAO.findAll();
			daoManager.terminarTransaccion();
			daoManager.cerrar();
			application.setAttribute("facturas", facturas);

			request.getRequestDispatcher(Constantes.RUTA_LISTADO_FACTURA).forward(request, response);

		} else {

			switch (op) {

			case "ver":

				Factura factura;
				List<ArticuloVendido> productosFactura;
				BigDecimal ivaFactura;
				BigDecimal precioFactura;
				Usuario usuarioFactura;

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
				factura = facturaDAO.findById(id);

				productosFactura = factura.getArticulos();
				productosFactura.size();
				
				ivaFactura = facturaDAO.getIvaTotal(id);

				precioFactura = facturaDAO.getPrecioTotal(id);

				usuarioFactura = factura.getUsuario();
				daoManager.terminarTransaccion();
				daoManager.cerrar();
				session.setAttribute("factura", factura);
				session.setAttribute("productosFactura", productosFactura);
				session.setAttribute("ivaFactura", ivaFactura);
				session.setAttribute("precioFactura", precioFactura);
				session.setAttribute("usuarioFactura", usuarioFactura);
				request.getRequestDispatcher(Constantes.RUTA_FACTURA_FACTURA).forward(request, response);
				break;
			case "devolucion":
				daoManager.cerrar();
				request.getRequestDispatcher("/WEB-INF/vistas/devolucionform.jsp").forward(request, response);
				break;

			}

		}
	}

}
