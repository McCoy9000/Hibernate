package servlets;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.Direccion;
import pojos.Empresa;
import pojos.Rol;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.DireccionDAO;
import dataAccessLayer.EmpresaDAO;
import dataAccessLayer.ImagenDAO;
import dataAccessLayer.RolDAO;
import dataAccessLayer.UsuarioDAO;
import encriptacion.Encriptador;

@WebServlet("/usuarioperfil")
public class UsuarioPerfil extends HttpServlet {

	private static final long serialVersionUID = -1675125190483219932L;

	private static Logger log = Logger.getLogger(UsuarioPerfil.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = request.getServletContext();

		HttpSession session = request.getSession();

		session.removeAttribute("errorPerfil");

		UsuarioDAO usuarioDAO = (UsuarioDAO) application.getAttribute("usuarioDAO");
		RolDAO rolDAO = (RolDAO) application.getAttribute("rolDAO");
		EmpresaDAO empresaDAO = (EmpresaDAO) application.getAttribute("empresaDAO");
		ImagenDAO imagenDAO = (ImagenDAO) application.getAttribute("imagenDAO");
		DireccionDAO direccionDAO = (DireccionDAO) application.getAttribute("direccionDAO");
		
		long id = ((Usuario) session.getAttribute("usuario")).getId();

		String username = request.getParameter("username");

		Encriptador miEncriptador = null;

		String password = null;

		String password2 = null;

		String rawpassword = request.getParameter("rawpassword");

		String rawpassword2 = request.getParameter("rawpassword2");

		if (request.getParameter("rawpassword") != null) {
			rawpassword = request.getParameter("rawpassword").trim();
		} else {
			rawpassword = request.getParameter("rawpassword");
		}

		if (request.getParameter("rawpassword2") != null) {
			rawpassword2 = request.getParameter("rawpassword2").trim();
		} else {
			rawpassword2 = request.getParameter("rawpassword2");
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
			password2 = miEncriptador.encriptar(rawpassword2);
		}

		String nombre = request.getParameter("nombre");

		String apellidos = request.getParameter("apellidos");

		String documento = request.getParameter("documento");

		String telefono = request.getParameter("telefono");

		String email = request.getParameter("email");
		
		String piso = request.getParameter("piso");

		String puerta = request.getParameter("puerta");

		String calle = request.getParameter("calle");

		String codigoPostal = request.getParameter("codigoPostal");

		String ciudad = request.getParameter("ciudad");

		String region = request.getParameter("region");

		String pais = request.getParameter("pais");

		String nombreEmpresa = request.getParameter("empresa");

		Rol rol = rolDAO.findByName("Usuario");

		String op = request.getParameter("op");

		String opform = request.getParameter("opform");

		Usuario usuario;
		
		if (("ver").equals(op)) {
			usuario = usuarioDAO.findById(id);
			session.setAttribute("usuario", usuario);
			request.getRequestDispatcher(Constantes.RUTA_PERFIL_USUARIO).forward(request, response);
			return;
		} else {
			switch (opform) {
			case "formulario":
				usuario = usuarioDAO.findById(id);
				session.setAttribute("usuario", usuario);

				request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PERFIL_USUARIO).forward(request, response);
				return;

			case "modificar":

				usuario = new Usuario(nombre, apellidos, email, username, password, rol);
				boolean usuarioExistente = usuarioDAO.validarNombre(usuario) && usuario.getNombre() != nombre;

				if (usuarioExistente) {
					log.info("pasa por usuario existente");
					session.setAttribute("errorPerfil", "El nombre de usuario ya existe");
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PERFIL_USUARIO).forward(request, response);
					return;
				} else if (password != null && password != "" && password.equals(password2)) {

					usuario = usuarioDAO.findById(id);
					Direccion direccion = direccionDAO.findById(usuario.getDireccion().getId());
					Empresa empresa = empresaDAO.findById(usuario.getEmpresa().getId());
					
					usuario.setUsername(username);
					usuario.setPassword(password);
					usuario.setNombre(nombre);
					usuario.setApellidos(apellidos);
					usuario.setDocumento(documento);
					usuario.setTelefono(telefono);
					direccion.setCodigoPostal(codigoPostal);
					direccion.setPuerta(puerta);
					direccion.setPiso(piso);
					direccion.setCalle(calle);
					direccion.setCiudad(ciudad);
					direccion.setRegion(region);
					direccion.setPais(pais);
					usuario.setDireccion(direccion);
					empresa.setNombre(nombreEmpresa);
					usuario.setEmpresa(empresa);
					
					usuarioDAO.update(usuario);

					usuarioDAO.findById(id);

					session.setAttribute("usuario", usuario);
					session.setAttribute("usuarioFactura", usuario);
					session.removeAttribute("errorPerfil");
					request.getRequestDispatcher(Constantes.RUTA_PERFIL_USUARIO).forward(request, response);
					return;
				} else {
					session.setAttribute("errorPerfil", "Las contraseñas no coinciden");
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PERFIL_USUARIO).forward(request, response);
					return;
				}
			}
		}
	}

}
