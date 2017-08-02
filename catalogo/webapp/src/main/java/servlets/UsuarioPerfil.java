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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.Direccion;
import pojos.Empresa;
import pojos.Rol;
import pojos.Usuario;
import recursos.Constantes;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;
import dataAccessLayer.DireccionDAO;
import dataAccessLayer.EmpresaDAO;
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

		HttpSession session = request.getSession();

		session.removeAttribute("errorPerfil");

		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		
		UsuarioDAO usuarioDAO = daoManager.getUsuarioDAO();
		RolDAO rolDAO = daoManager.getRolDAO();
		EmpresaDAO empresaDAO = daoManager.getEmpresaDAO();
		DireccionDAO direccionDAO = daoManager.getDireccionDAO();

		long id = ((Usuario) session.getAttribute("usuario")).getId();

		String username = request.getParameter("username");

		Encriptador miEncriptador = null;

		String password = null;

		String password2 = null;

		String rawpassword = request.getParameter("rawpassword");

		String rawpassword2 = request.getParameter("rawpassword2");

		if (request.getParameter("rawpassword") != null) {
			rawpassword = request.getParameter("rawpassword").trim();
		}
		if (request.getParameter("rawpassword2") != null) {
			rawpassword2 = request.getParameter("rawpassword2").trim();
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

		Usuario usuario = null;

		if (("ver").equals(op)) {
			daoManager.iniciarTransaccion();
			try {
				usuario = usuarioDAO.findById(id);
				daoManager.terminarTransaccion();
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al recuperar el usuario con id " + id + ".");
			} finally {
				daoManager.cerrar();
			}
			session.setAttribute("usuario", usuario);
			request.getRequestDispatcher(Constantes.RUTA_PERFIL_USUARIO).forward(request, response);
			return;
		} else {
			switch (opform) {
			case "formulario":
				
				daoManager.iniciarTransaccion();
				try {
					usuario = usuarioDAO.findById(id);
					daoManager.terminarTransaccion();
				} catch (Exception e) {
					daoManager.abortarTransaccion();
					e.printStackTrace();
					log.info("Error al recuperar el usuario con id " + id + ".");
				} finally {
					daoManager.cerrar();
				}
				session.setAttribute("usuario", usuario);

				request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PERFIL_USUARIO).forward(request, response);
				return;

			case "modificar":

				usuario = new Usuario(nombre, apellidos, email, username, password, rol);
				
				boolean usuarioExistente = false;
				daoManager.iniciarTransaccion();
				try {
					usuarioExistente = usuarioDAO.validarNombre(usuario) && !usuario.getUsername().equals(usuarioDAO.findById(id).getUsername());
					daoManager.terminarTransaccion();
				} catch (Exception e) {
					daoManager.abortarTransaccion();
					e.printStackTrace();
					log.info("Error al validar el nombre de usuario " + usuario.getNombre() + ".");
				} 
				if (usuarioExistente) {
					daoManager.cerrar();
					log.info("pasa por usuario existente");
					session.setAttribute("errorPerfil", "El nombre de usuario ya existe");
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PERFIL_USUARIO).forward(request, response);
					return;
				} else if (password != null && password != "" && password.equals(password2)) {
					daoManager.iniciarTransaccion();
					try {
					usuario = usuarioDAO.findById(id);
					
					
					Direccion direccion = null;
					
					if(usuario.getDireccion() != null) {
						direccion = direccionDAO.findById(usuario.getDireccion().getId());
					} else {
						direccion = new Direccion();
						direccionDAO.insert(direccion);
					}
					
					Empresa empresa;
					if(usuario.getEmpresa() != null) {
						empresa = empresaDAO.findById(usuario.getEmpresa().getId());
					} else {
						empresa = new Empresa();
						empresaDAO.insert(empresa);
					}
					
					direccion.setCodigoPostal(codigoPostal);
					direccion.setPuerta(puerta);
					direccion.setPiso(piso);
					direccion.setCalle(calle);
					direccion.setCiudad(ciudad);
					direccion.setRegion(region);
					direccion.setPais(pais);
					daoManager.terminarTransaccion();
					daoManager.iniciarTransaccion();
					empresa.setNombre(nombreEmpresa);
					daoManager.terminarTransaccion();
					daoManager.iniciarTransaccion();
					usuario.setUsername(username);
					usuario.setPassword(password);
					usuario.setNombre(nombre);
					usuario.setApellidos(apellidos);
					usuario.setDocumento(documento);
					usuario.setTelefono(telefono);
					usuario.setDireccion(direccion);
					usuario.setEmpresa(empresa);

					daoManager.terminarTransaccion();
					} catch (Exception e) {
						daoManager.abortarTransaccion();
						e.printStackTrace();
						log.info("Error al buscar el usuario con id " + id + ".");
					} finally {
						daoManager.cerrar();
					}
					session.setAttribute("usuario", usuario);
					session.setAttribute("usuarioFactura", usuario);
					session.removeAttribute("errorPerfil");
					request.getRequestDispatcher(Constantes.RUTA_PERFIL_USUARIO).forward(request, response);
					return;
				} else {
					daoManager.cerrar();
					session.setAttribute("errorPerfil", "Las contraseñas no coinciden");
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PERFIL_USUARIO).forward(request, response);
					return;
				}
			}
		}
	}

}
