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

import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.UsuarioDAO;
import encriptacion.Encriptador;

@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		ServletContext application = request.getServletContext();
		UsuarioDAO usuarioDAO = (UsuarioDAO) application.getAttribute("usuarioDAO");
		
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
		if (request.getParameter("password2") != null) {
			rawpassword2 = request.getParameter("password2").trim();
		}
		
		Usuario usuario = new Usuario(nombre, apellidos, email, username, password, "Usuario");
		
		
		boolean sinDatos = username == null || rawpassword == null || rawpassword2 == null;
		boolean nombreLargo = username.length() > 20;
		boolean passDistintas = rawpassword != null && rawpassword.equals(rawpassword2);
		boolean emailNoValido = false;
			if(email != null) {
				String user = email.split("@")[0];
				String dominio = email.split("@")[1];
				if (user == null || user.length() < 1)
					emailNoValido = true;
				if (dominio == null || dominio.split(".").length < 2)
					emailNoValido = true;
			}
		boolean userExiste = usuarioDAO.validarNombre(usuario);

		RequestDispatcher rutaLogin = request.getRequestDispatcher(Constantes.RUTA_LOGIN);
		RequestDispatcher rutaSignup = request.getRequestDispatcher(Constantes.RUTA_SIGNUP);
	
		if (sinDatos) {
			rutaSignup.forward(request, response);
			return;
		}
		if (nombreLargo) {
			session.setAttribute("errorSignup", "El username debe tener un máximo de 20 caracteres");
			rutaSignup.forward(request, response);
			return;
		}
		if (passDistintas) {
			session.setAttribute("errorSignup", "Las contraseñas deben ser iguales");
			rutaSignup.forward(request, response);
			return;
		}
		if (emailNoValido) {
			session.setAttribute("errorSignup", "El email no es válido");
			rutaSignup.forward(request, response);
			return;
		}
		if (userExiste) {
			session.setAttribute("errorSignup", "Ya existe un usuario con ese username");
			rutaSignup.forward(request, response);
			return;
		}
		
		usuarioDAO.insert(usuario);
		rutaLogin.forward(request, response);
	}
}
