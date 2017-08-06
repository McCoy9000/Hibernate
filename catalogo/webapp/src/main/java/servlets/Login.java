package servlets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.Usuario;
import recursos.Constantes;
import recursos.Encriptador;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.UsuarioDAO;

@WebServlet("/login")
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = -7432700501449176526L;

	private static Logger log = Logger.getLogger(Login.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// RECOPILACIÓN DE LOS OBJETOS A ANALIZAR Y UTILIZAR
		HttpSession session = request.getSession();

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();

		// Borrado de errores en sesión por si llegan aquí desde los formularios CRUD
		session.removeAttribute("errorLogin");
		// Recogida de datos de la request
		String username = null, rawpassword, password = null;

		if (request.getParameter("username") != null) {
			username = request.getParameter("username").trim();
		}

		if (request.getParameter("password") != null) {
			rawpassword = request.getParameter("password").trim();
			Encriptador miEncriptador = null;
			try {
				miEncriptador = new Encriptador();
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
				e1.printStackTrace();
			}
			password = miEncriptador.encriptar(rawpassword);

		}

		String op = request.getParameter("op");

		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario == null) {
			usuario = new Usuario();
			usuario.setUsername(username);
			usuario.setPassword(password);
		}

		boolean quiereSalir = ("logout").equals(op);
		boolean yaLogueado = ("si").equals(session.getAttribute("logueado"));
		boolean sinDatos = username == null || username == "" || password == "" || password == null;
		boolean uInexistente = false;
		boolean esValido = false;
		daoManager.iniciarTransaccion();
		try {
			uInexistente = !usuarioDAO.validarNombre(usuario);
			esValido = usuarioDAO.validar(usuario);
			daoManager.terminarTransaccion();
		} catch (Exception e) {
			daoManager.abortarTransaccion();
			e.printStackTrace();
			log.info("Error al validar el usuario");
		}
		RequestDispatcher login = request.getRequestDispatcher(Constantes.RUTA_LOGIN);
		RequestDispatcher catalogo = request.getRequestDispatcher("/catalogo");

		if (quiereSalir) {
			// Se invalida la sesión y se le envía al catálogo que es el punto de partida de la aplicación
			daoManager.cerrar();
			session.invalidate();
			session = request.getSession();
			catalogo.forward(request, response);
			return;
		}
		if (yaLogueado) {
			// Si ya está logueado el login le deja pasar directamente a la página principal, el catálogo
			daoManager.cerrar();
			session.removeAttribute("errorLogin");
			catalogo.forward(request, response);
			return;
		}
		if (sinDatos) {
			// Si no se rellenan los datos se le envía al jsp del login con el mensaje de error. Da el fallo de que un usuario
			// que entra por primera vez a esta página no ha podido rellenar aún ningún dato por lo que se le mostrará el mensaje
			// de error sin que haya interactuado con la página.
			daoManager.cerrar();
			login.forward(request, response);
			return;
		}
		if (uInexistente) {
			// Si el username no existe en la base de datos se le reenvía a la jsp de login con el correspondiente mensaje de error
			daoManager.cerrar();
			session.setAttribute("errorLogin", "Usuario no encontrado");
			login.forward(request, response);
			return;
		}
		if (esValido) {
			daoManager.iniciarTransaccion();
			try {
				usuario = usuarioDAO.findByName(username);
				daoManager.terminarTransaccion();
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al recuperar el usuario de username " + username + ".");
			} finally {
				daoManager.cerrar();
			}
			session.removeAttribute("errorLogin");
			session.setAttribute("logueado", "si");
			session.setAttribute("usuario", usuario);
			
			String referer;
			try {
				referer = new URI(request.getHeader("referer")).getPath();
			} catch (URISyntaxException e) {
				referer = "";
				e.printStackTrace();
			}

			if (referer.contains("checkout")) {
				request.getRequestDispatcher(Constantes.RUTA_SERVLET_CHECKOUT).forward(request, response);
				return;
			}
			
			if (referer.contains("usuarioperfil")) {
				request.getRequestDispatcher(Constantes.RUTA_SERVLET_PERFIL_USUARIO + "?opform=formulario").forward(request, response);
				return;
			}

			if (referer.contains("facturasusuario")) {
				request.getRequestDispatcher(Constantes.RUTA_SERVLET_FACTURA_USUARIOS).forward(request, response);
				return;
			}
//			TODO redireccionamiento login
			// Se le envía al catálogo
			catalogo.forward(request, response);
			return;
		} else {
			daoManager.cerrar();
			session.setAttribute("errorLogin", "Contraseña incorrecta");
			login.forward(request, response);
			return;
		}

	}

}
