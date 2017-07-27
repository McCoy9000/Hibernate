package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pojos.Articulo;
import recursos.Constantes;
import dataAccessLayer.ArticuloDAO;
import dataAccessLayer.DAOManagerHibernate;

@WebServlet("/admin/articulocrud")
public class ArticuloCRUD extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();
//		ArticuloDAO articuloDAO = (ArticuloDAO) application.getAttribute("articuloDAO");

		HttpSession session = request.getSession();
		// Borrado de errores en sesión por si llegan aquí desde los formularios CRUD
//		session.removeAttribute("errorProducto");
//		session.removeAttribute("errorUsuario");
//		session.removeAttribute("errorLogin");
//		session.removeAttribute("errorSignup");

		String op = request.getParameter("op");
		DAOManagerHibernate daomanager = new DAOManagerHibernate();
		ArticuloDAO articuloDAO = daomanager.getArticuloDAO();
		
		if (op == null) {
//			articuloDAO.abrirManager();
			
			List<Articulo> articulos = articuloDAO.findAll();
//			articuloDAO.cerrarManager();
			daomanager.cerrar();
			application.setAttribute("articulos", articulos);
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
			return;
		}

		Articulo articulo;

		switch (op) {
		case "modificar":
		case "borrar":
			long id;
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (Exception e) {
				e.printStackTrace();
				request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
				break;
			}
//			articuloDAO.abrirManager();
			articulo = articuloDAO.findById(id);
//			articuloDAO.cerrarManager();
			daomanager.cerrar();
			request.setAttribute("articulo", articulo);
		case "alta":
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO).forward(request, response);
			break;
		default:
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
		}
	}
}
