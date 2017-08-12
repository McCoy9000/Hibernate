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

import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.RolDAO;
import dataAccessLayer.UsuarioDAO;
import factories.EncriptadorFactory;

@WebServlet("/signup")
public class Signup extends HttpServlet {

	private static final long serialVersionUID = -1879791727577370812L;

	private static Logger log = Logger.getLogger(Signup.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
		RolDAO rolDAO = daoManager.getRolDAO();
		
		HttpSession session = request.getSession();
		session.removeAttribute("errorSignup");

		String nombre = null, apellidos = null, email = null, username = null, rawpassword = null, rawpassword2 = null, password = null;

		if (request.getParameter("nombre") != null) {
			nombre = request.getParameter("nombre").trim();
		}
		if (request.getParameter("apellidos") != null) {
			apellidos = request.getParameter("apellidos").trim();
		}
		if (request.getParameter("email") != null) {
			email = request.getParameter("email").trim();
		}
		if (request.getParameter("username") != null) {
			username = request.getParameter("username").trim();
		}
		IEncriptador miEncriptador = null;
		if (request.getParameter("password") != null) {
			rawpassword = request.getParameter("password").trim();
			try {
				miEncriptador = EncriptadorFactory.getEncriptador();
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}
			password = miEncriptador.encriptar(rawpassword);
		}
		if (request.getParameter("password2") != null) {
			rawpassword2 = request.getParameter("password2").trim();
		}
		
		Usuario usuario = null;
		daoManager.iniciarTransaccion();
		try {
			usuario = new Usuario(nombre, apellidos, email, username, password, rolDAO.findByName("Usuario"));
			daoManager.terminarTransaccion();
		} catch (Exception e) {
			daoManager.abortarTransaccion();
			e.printStackTrace();
			log.info("Error al recuperar el rol de nombre Usuario");
		}
		boolean sinDatos = username == null || rawpassword == null || rawpassword2 == null;
		boolean nombreLargo = username != null && username.length() > 20;
		boolean passDistintas = rawpassword != null && !rawpassword.equals(rawpassword2);
		boolean emailNoValido = false;
		if (email != null) {
			String user = email.split("@")[0];
			String dominio = null;
			try {
			dominio = email.split("@")[1];
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				emailNoValido = true;
			}
			if (user == null || user.length() < 1)
				emailNoValido = true;
			if (dominio == null)
				emailNoValido = true;
		}
		boolean userExiste = usuarioDAO.validarNombre(usuario);

		RequestDispatcher rutaLogin = request.getRequestDispatcher(Constantes.RUTA_LOGIN);
		RequestDispatcher rutaSignup = request.getRequestDispatcher(Constantes.RUTA_SIGNUP);

		if (sinDatos) {
			daoManager.cerrar();
			rutaSignup.forward(request, response);
			return;
		}
		if (nombreLargo) {
			daoManager.cerrar();
			session.setAttribute("errorSignup", "El username debe tener un máximo de 20 caracteres");
			rutaSignup.forward(request, response);
			return;
		}
		if (passDistintas) {
			daoManager.cerrar();
			session.setAttribute("errorSignup", "Las contraseñas deben ser iguales");
			rutaSignup.forward(request, response);
			return;
		}
		if (emailNoValido) {
			daoManager.cerrar();
			session.setAttribute("errorSignup", "El email no es válido");
			rutaSignup.forward(request, response);
			return;
		}
		if (userExiste) {
			daoManager.cerrar();
			session.setAttribute("errorSignup", "Ya existe un usuario con ese username");
			rutaSignup.forward(request, response);
			return;
		}

		daoManager.iniciarTransaccion();
		try {
			usuarioDAO.insert(usuario);
			daoManager.terminarTransaccion();
		} catch (Exception e) {
			daoManager.abortarTransaccion();
			e.printStackTrace();
			log.info("Error al insertar el usuario");
		} finally {
			daoManager.cerrar();
		}

		rutaLogin.forward(request, response);
	}
}
