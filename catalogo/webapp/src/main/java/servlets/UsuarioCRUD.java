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

import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.DAOManagerHibernate;
import dataAccessLayer.UsuarioDAO;

@WebServlet("/admin/usuariocrud")
public class UsuarioCRUD extends HttpServlet {

	private static final long serialVersionUID = 5262202010560510599L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();

		DAOManagerHibernate daoManager = new DAOManagerHibernate();
		daoManager.abrir();
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
		
		HttpSession session = request.getSession();
		// Borrado de errores en sesión por si llegan aquí desde los formularios CRUD
		session.removeAttribute("errorProducto");
		session.removeAttribute("errorUsuario");
		session.removeAttribute("errorLogin");
		session.removeAttribute("errorSignup");

		String op = request.getParameter("op");

		if (op == null) {
			List<Usuario> usuarios = usuarioDAO.findAll();
			for (Usuario u : usuarios) {
				if (u.getRol().getId() == 1) {
					usuarios.remove(u);
				}
			}
			daoManager.cerrar();
			application.setAttribute("usuarios", usuarios);
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_USUARIO).forward(request, response);
			return;
		}

		Usuario usuario;

		switch (op) {
		case "modificar":
		case "borrar":
			long id;
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (Exception e) {
				e.printStackTrace();
				request.getRequestDispatcher(Constantes.RUTA_LISTADO_USUARIO).forward(request, response);
				break;
			}
			
			usuario = usuarioDAO.findById(id);
			request.setAttribute("usuario", usuario);
		case "alta":
			daoManager.cerrar();;
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO).forward(request, response);
			break;
		default:
			daoManager.cerrar();
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_USUARIO).forward(request, response);
		}
	}
}
