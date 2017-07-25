package servlets;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.Rol;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.RolDAO;
import dataAccessLayer.UsuarioDAO;
import encriptacion.Encriptador;

@WebServlet("/admin/usuarioform")
public class UsuarioForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UsuarioForm.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();
		HttpSession session = request.getSession();

		session.removeAttribute("errorUsuario");

		UsuarioDAO usuarioDAO = (UsuarioDAO) application.getAttribute("usuarioDAO");
		RolDAO rolDAO = (RolDAO) application.getAttribute("rolDAO");

		String op = request.getParameter("opform");

		Usuario usuario;

		long id;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}

		String username, rawpassword, rawpassword2, password = null, password2 = null, email, nombre, apellidos;

		Encriptador miEncriptador = null;

		if (request.getParameter("username") != null) {
			username = request.getParameter("username").trim();
		} else {
			username = request.getParameter("username");
		}

		if (request.getParameter("password") != null) {
			rawpassword = request.getParameter("password").trim();
		} else {
			rawpassword = request.getParameter("password");
		}

		if (request.getParameter("password2") != null) {
			rawpassword2 = request.getParameter("password2").trim();
		} else {
			rawpassword2 = request.getParameter("password2");
		}

		try {
			miEncriptador = new Encriptador();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
			e1.printStackTrace();
		}

		if (rawpassword != null) {
			password = miEncriptador.encriptar(rawpassword);
		}

		if (rawpassword2 != null) {
			password2 = miEncriptador.encriptar(password2);
		}
		
		if (request.getParameter("email") != null) {
			email = request.getParameter("email").trim();
		} else {
			email = request.getParameter("email");
		}

		if (request.getParameter("nombre") != null) {
			nombre = request.getParameter("nombre").trim();
		} else {
			nombre = request.getParameter("nombre");
		}

		if (request.getParameter("apellidos") != null) {
			apellidos = request.getParameter("apellidos").trim();
		} else {
			apellidos = request.getParameter("apellidos");
		}

		Rol rol = rolDAO.findByName("Usuario");

		
		RequestDispatcher rutaListado = request.getRequestDispatcher(Constantes.RUTA_SERVLET_LISTADO_USUARIO);

		if (op == null) {
			usuario = new Usuario(nombre, apellidos, email, username, password, rol);
			session.removeAttribute("errorUsuario");
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=alta").forward(request, response);
		} else {

			switch (op) {

			case "alta":

				usuario = new Usuario(nombre, apellidos, email, username, password, rol);
				
				if (password != null && password != "" && password.equals(password2)) {
					if(!usuarioDAO.validarNombre(usuario)) {					
						usuarioDAO.insert(usuario);
						session.removeAttribute("errorUsuario");
						log.info("Usuario " + usuario.getUsername() + " dado de alta");
					session.removeAttribute("errorUsuario");
					rutaListado.forward(request, response);
					} else {
						session.setAttribute("errorUsuario", "El usuario ya existe");
						request.setAttribute("usuario", usuario);
						request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=alta").forward(request, response);
					}
				} else {
					session.setAttribute("errorUsuario", "Las contraseñas deben ser iguales");
					request.setAttribute("usuario", usuario);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=alta").forward(request, response);
				}
				break;

			case "modificar":
				usuario = usuarioDAO.findById(id);
				
				usuario.setUsername(username);
				usuario.setPassword(password2);
				usuario.setEmail(email);
				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setRol(rol);
				
				if (!("admin").equals(usuario.getUsername())) {
					usuario.setUsername(username);
					usuario.setPassword(password2);
					usuario.setEmail(email);
					usuario.setNombre(nombre);
					usuario.setApellidos(apellidos);
					usuario.setRol(rol);
					usuarioDAO.update(usuario);
				} else {
					session.setAttribute("errorUsuario", "Por el momento no es posible modificar el usuario 'admin'");
					request.setAttribute("usuario", usuario);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=modificar").forward(request, response);
				}
				break;

			case "borrar":
				usuario = usuarioDAO.findById(id);
				if (!("admin").equals(usuario.getUsername())) {
					usuarioDAO.delete(id);
					session.removeAttribute("errorUsuario");
					rutaListado.forward(request, response);
					break;
				} else {
					session.setAttribute("errorUsuario", "Por el momento no es posible borrar el usuario 'admin'");
					request.setAttribute("usuario", usuario);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=borrar").forward(request, response);
				}
				break;
			default:
				session.removeAttribute("errorUsuario");
				rutaListado.forward(request, response);
			}
		}
	}

}
