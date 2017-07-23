package servlets;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import encriptacion.Encriptador;

@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	private String receiveUserData(HttpServletRequest request) {
		
		String nombre, apellidos, email, username, password;
		
		if (request.getParameter("nombre") != null) {
			nombre = request.getParameter("nombre").trim();
		}
		if (request.getParameter("apellidos") != null) {
			nombre = request.getParameter("apellidos").trim();
		}
		if (request.getParameter("email") != null) {
			nombre = request.getParameter("nombre").trim();
		}
		if (request.getParameter("username") != null) {
			nombre = request.getParameter("username").trim();
		}
		if (request.getParameter("password") != null) {
			Encriptador miEncriptador = null;
			try {
				miEncriptador = new Encriptador();
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e1) {
				e1.printStackTrace();
			}
			String rawpassword = request.getParameter("password").trim();
			password = miEncriptador.encriptar(rawpassword);
		}
		
		
		
		return null;
	}
	
	private boolean registerUserData() {
		return false;
	}
	
	private void redirectToAppropriateJSP() {
		
	}
}
