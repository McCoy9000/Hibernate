package servlets;

import java.io.IOException;
import java.util.Iterator;
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
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.UsuarioDAO;

@WebServlet("/admin/usuariocrud")
public class UsuarioCRUD extends HttpServlet {

	private static final long serialVersionUID = 5262202010560510599L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
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
			Iterator<Usuario> it = usuarios.iterator();
			do {
				Usuario usuario = it.next();
				if (usuario.getRol().getId() == 3)
					it.remove();;
			} while (it.hasNext());

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
