package servlets;

import interfaces.IEncriptador;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.RolDAO;
import dataAccessLayer.UsuarioDAO;
import factories.EncriptadorFactory;

@WebServlet("/admin/usuarioform")
public class UsuarioForm extends HttpServlet {
	
	private static final long serialVersionUID = 178925444152187664L;
	
	private static Logger log = Logger.getLogger(UsuarioForm.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		session.removeAttribute("errorUsuario");

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
		RolDAO rolDAO = daoManager.getRolDAO();
		
		
		String op = request.getParameter("opform");

		Usuario usuario = null;

		long id;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (Exception e) {
			id = 0;
		}

		String username, rawpassword, rawpassword2, password = null, password2 = null, email, nombre, apellidos;

		IEncriptador miEncriptador = null;

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
			miEncriptador = EncriptadorFactory.getEncriptador();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}

		if (rawpassword != null) {
			password = miEncriptador.encriptar(rawpassword);
		}

		if (rawpassword2 != null) {
			password2 = miEncriptador.encriptar(rawpassword2);
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
		if (("Administrador").equals(request.getParameter("rol"))) {
			rol = rolDAO.findByName("Administrador");
		}

		RequestDispatcher rutaListado = request.getRequestDispatcher(Constantes.RUTA_SERVLET_LISTADO_USUARIO);

		if (op == null) {
			usuario = new Usuario(nombre, apellidos, email, username, password, rol);
			daoManager.cerrar();
			request.setAttribute("usuario", usuario);
			session.removeAttribute("errorUsuario");
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=alta").forward(request, response);
		} else {

			switch (op) {

			case "alta":

				usuario = new Usuario(nombre, apellidos, email, username, password, rol);

				if (password != null && password != "" && password.equals(password2)) {
					if (!usuarioDAO.validarNombre(usuario)) {
						daoManager.iniciarTransaccion();
						try {
							usuarioDAO.insert(usuario);
							daoManager.terminarTransaccion();
							log.info("Usuario " + usuario.getUsername() + " dado de alta");
						} catch (Exception e) {
							daoManager.abortarTransaccion();
							e.printStackTrace();
							log.info("Error al insertar usuario");
						} finally {
							daoManager.cerrar();
						}
						session.removeAttribute("errorUsuario");
						rutaListado.forward(request, response);
					} else {
						daoManager.cerrar();
						session.setAttribute("errorUsuario", "El usuario ya existe");
						request.setAttribute("usuario", usuario);
						request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=alta").forward(request, response);
					}
				} else {
					daoManager.cerrar();
					session.setAttribute("errorUsuario", "Las contraseñas deben ser iguales");
					request.setAttribute("usuario", usuario);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=alta").forward(request, response);
				}
				break;

			case "modificar":
				daoManager.iniciarTransaccion();
				usuario = usuarioDAO.findById(id);
				if (!("admin").equals(usuario.getUsername())) {
					try {
						usuario.setUsername(username);
						usuario.setPassword(password2);
						usuario.setEmail(email);
						usuario.setNombre(nombre);
						usuario.setApellidos(apellidos);
						usuario.setRol(rol);
						daoManager.terminarTransaccion();
					
					} catch (Exception e) {
						daoManager.abortarTransaccion();
						e.printStackTrace();
						log.info("Error al actualizar el usuario");
					} finally {
						daoManager.cerrar();
					}
					session.removeAttribute("errorUsuario");
					rutaListado.forward(request, response);
				} else {
					daoManager.cerrar();
					session.setAttribute("errorUsuario", "Por el momento no es posible modificar el usuario 'admin'");
					request.setAttribute("usuario", usuario);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=modificar").forward(request, response);
				}
				break;

			case "borrar":
				daoManager.iniciarTransaccion();
				try {
					usuario = usuarioDAO.findById(id);
					daoManager.terminarTransaccion();
				} catch (Exception e) {
					daoManager.abortarTransaccion();
					e.printStackTrace();
					log.info("Error al buscar el usuario con id " + id + ".");
				}
				if (!("admin").equals(usuario.getUsername())) {
					daoManager.iniciarTransaccion();
					try {
						usuarioDAO.delete(id);
						daoManager.terminarTransaccion();
					} catch (Exception e) {
						daoManager.abortarTransaccion();
						e.printStackTrace();
						log.info("Error al borrar el usuario");
					} finally {
						daoManager.cerrar();
					}
					session.removeAttribute("errorUsuario");
					rutaListado.forward(request, response);
					break;
				} else {
					daoManager.cerrar();
					session.setAttribute("errorUsuario", "Por el momento no es posible borrar el usuario 'admin'");
					request.setAttribute("usuario", usuario);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_USUARIO + "?op=borrar").forward(request, response);
				}
				break;
			default:
				daoManager.cerrar();
				session.removeAttribute("errorUsuario");
				rutaListado.forward(request, response);
			}
		}
	}

}
