package servlets;

import java.io.IOException;
import java.math.BigDecimal;
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

import pojos.ArticuloVendido;
import pojos.Comprador;
import pojos.Factura;
import pojos.Imagen;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.DAOManagerHibernate;
import dataAccessLayer.FacturaDAO;
import dataAccessLayer.UsuarioDAO;

@WebServlet("/admin/devolucionform")
public class DevolucionForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(DevolucionForm.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		session.removeAttribute("errorDevolucion");


		String op = request.getParameter("opform");

		String mensaje = request.getParameter("mensaje");

		BigDecimal importe = null;

		if ((request.getParameter("importe") != null) && (request.getParameter("importe") != "")) {
			try {
				importe = new BigDecimal(request.getParameter("importe"));
			} catch (NumberFormatException nfe) {
				session.setAttribute("errorDevolucion", "Debes introducir un importe negativo para hacer una devolución");
				request.getRequestDispatcher(Constantes.RUTA_DEVOLUCION + "?op=devolucion").forward(request, response);
				return;
			}

			if (BigDecimal.ZERO.compareTo(importe) < 0) {
				session.setAttribute("errorDevolucion", "Debe introducir un importe negativo para hacer una devolución");
				request.getRequestDispatcher(Constantes.RUTA_DEVOLUCION + "?op=devolucion").forward(request, response);
				return;
			}

		} else {
			session.setAttribute("errorDevolucion", "Debe introducir un importe negativo para hacer una devolución");
			request.getRequestDispatcher(Constantes.RUTA_DEVOLUCION + "?op=devolucion").forward(request, response);
			return;
		}
		DAOManagerHibernate daomanager = new DAOManagerHibernate();
		daomanager.abrir();
		UsuarioDAO usuarioDAO = daomanager.getUsuarioDAO();
		daomanager.iniciarTransaccion();
		Usuario usuario = usuarioDAO.findById(1);
		daomanager.terminarTransaccion();
		Factura factura = new Factura(usuario, new Comprador(usuario.getId(), usuario.getNombre(), usuario.getApellidos(), usuario.getFechaNacimiento(), usuario.getDocumento(), usuario.getTelefono(),
				usuario.getEmail(), usuario.getDireccion(), usuario.getEmpresa()), LocalDate.now());

		ArticuloVendido devolucion = new ArticuloVendido("DEVOLUCION", mensaje, "", new Imagen(), importe, BigInteger.ONE, factura);

		switch (op) {

		case "devolucion":
			
			FacturaDAO facturaDAO = daomanager.getFacturaDAO();
			daomanager.iniciarTransaccion();
			facturaDAO.insert(factura);
			List<ArticuloVendido> articulosFactura = new ArrayList<ArticuloVendido>();
			articulosFactura.add(devolucion);
			factura.setArticulos(articulosFactura);
			daomanager.terminarTransaccion();
			daomanager.cerrar();
			request.getRequestDispatcher("/admin/facturacrud").forward(request, response);
			break;
		}
	}

}
