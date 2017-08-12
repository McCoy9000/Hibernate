package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.ArticuloFactura;
import pojos.Comprador;
import pojos.Factura;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.CompradorDAO;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.FacturaDAO;
import dataAccessLayer.UsuarioDAO;

@WebServlet("/facturasusuario")
public class FacturasUsuario extends HttpServlet {

	private static final long serialVersionUID = 7854898201744246941L;

	private static Logger log = Logger.getLogger(FacturasUsuario.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		session.removeAttribute("errorFactura");

		Usuario usuario = (Usuario) session.getAttribute("usuario");

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		FacturaDAO facturaDAO = daoManager.getFacturaDAO();
		CompradorDAO compradorDAO = daoManager.getCompradorDAO();
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
		
		if(usuario != null) {
			daoManager.iniciarTransaccion();
			try {
			usuario = usuarioDAO.findById(usuario.getId());
			daoManager.terminarTransaccion();
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al recuperar el usuario con id " + usuario.getId() + " de la base de datos");
			}
		} else {
			daoManager.cerrar();
			request.getRequestDispatcher(Constantes.RUTA_LOGIN).forward(request, response);
			return;
		}
		
		String op = request.getParameter("op");

		if (op == null) {

			List<Factura> facturasUsuario = new ArrayList<Factura>();

			if (usuario != null) {
				daoManager.iniciarTransaccion();
				List<Comprador> compradores = compradorDAO.findByIdUsuario(usuario.getId());
				for (Comprador c : compradores) {
					facturasUsuario.add(c.getFactura());
				}
				daoManager.terminarTransaccion();
			}

			daoManager.cerrar();
			session.setAttribute("facturasUsuario", facturasUsuario);
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_FACTURA_USUARIOS).forward(request, response);
			return;
		} else {

			switch (op) {

			case "ver":

				Factura factura;
				List<ArticuloFactura> articulosFactura;
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
				
				daoManager.iniciarTransaccion();
				factura = facturaDAO.findById(id);

				articulosFactura = factura.getArticulos();

				ivaFactura = facturaDAO.getIvaTotal(id);

				precioFactura = facturaDAO.getPrecioTotal(id);

				usuarioFactura = factura.getUsuario();
				daoManager.terminarTransaccion();
				daoManager.cerrar();
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
