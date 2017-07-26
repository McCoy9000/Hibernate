package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.Articulo;
import pojos.Imagen;
import recursos.Constantes;
import dataAccessLayer.ArticuloDAO;

@WebServlet("/admin/productoform")
public class ArticuloForm extends HttpServlet {

	private static final long serialVersionUID = 3997952646417125446L;
	private static Logger log = Logger.getLogger(ArticuloForm.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Recoger el objeto application del ServletContext
		ServletContext application = getServletContext();
		HttpSession session = request.getSession();

		// Borrar los errores que puedan venir en sesión
		session.removeAttribute("errorProducto");

		ArticuloDAO articuloDAO = (ArticuloDAO) application.getAttribute("productos");

		// Recoger la opción elegida por el usuario en el formulario enviada por url
		String op = request.getParameter("opform");

		// Declaro aquí los dispatcher porque en un momento me dio un problema extraño por declararlos en el momento en que
		// los necesitaba
		RequestDispatcher rutaListado = request.getRequestDispatcher(Constantes.RUTA_SERVLET_LISTADO_PRODUCTO);
		RequestDispatcher rutaFormulario = request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO);

		// Declaración de las variables para construir el objeto con el que se trabajará e iniciarlas con los valores recogidos
		// del formulario

		Articulo articulo;

		long id;

		if (request.getParameter("id") != null) {
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (Exception e) {
				id = 0;
			}
		} else {
			id = 0;
		}

		String codigoArticulo;

		if (request.getParameter("id") != null) {
			if (!(("Nuevo grupo de productos").equals(request.getParameter("codigoArticulo")))) {
				try {
					codigoArticulo = request.getParameter("codigoArticulo").split("\\ - ")[0];
				} catch (Exception e) {
					codigoArticulo = "0";
				}
			} else {
				codigoArticulo = request.getParameter("nuevoCodigoArticulo");
			}
		} else {
			codigoArticulo = "0";
		}

		String nombre, descripcion;

		if (request.getParameter("codigoArticulo") != null) {
			if (!(("Nuevo grupo de productos").equals(request.getParameter("codigoArticulo")))) {
				nombre = request.getParameter("codigoArticulo").split("\\ - ")[1];
			} else {
				if (request.getParameter("nombre") != null) {
					nombre = request.getParameter("nombre").trim();
				} else {
					nombre = request.getParameter("nombre");
				}
			}
		} else {
			if (request.getParameter("nombre") != null) {
				nombre = request.getParameter("nombre").trim();
			} else {
				nombre = request.getParameter("nombre");
			}
		}

		if (request.getParameter("descripcion") != null) {
			descripcion = request.getParameter("descripcion").trim();
		} else {
			descripcion = request.getParameter("descripcion");
		}

		BigDecimal precio;

		if (request.getParameter("precio") != null) {
			try {
				precio = new BigDecimal(request.getParameter("precio"));
			} catch (Exception e) {
				precio = BigDecimal.ZERO;
				log.info("Error al parsear precio");
			}
		} else {
			precio = BigDecimal.ZERO;
		}

		BigInteger cantidad;

		if (request.getParameter("cantidad") != null) {
			try {
				cantidad = new BigInteger(request.getParameter("cantidad"));
			} catch (Exception e) {
				cantidad = BigInteger.ONE;
				log.info("Error al parsear la cantidad");
			}
		} else {
			cantidad = BigInteger.ONE;
		}

		Imagen imagen = null;

		// Lógica del servlet según la opción elegida por el usuario y enviada por el navegador
		// encapsulada en opform.
		if (op == null) {
			articulo = new Articulo(codigoArticulo, nombre, descripcion, imagen, precio, cantidad);
			session.removeAttribute("errorProducto");
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO + "?op=alta").forward(request, response);
		} else {

			switch (op) {
			case "alta":

				articulo = new Articulo(codigoArticulo, nombre, descripcion, imagen, precio, cantidad);

				if (nombre == null || nombre == "") {
					session.setAttribute("errorProducto", "Debes introducir un nombre de producto");
					request.setAttribute("articulo", articulo);
					rutaFormulario.forward(request, response);
				} else if (precio.compareTo(BigDecimal.ZERO) == -1 || precio == null) {
					session.setAttribute("errorProducto", "Debes introducir un precio mayor que 0");
					request.setAttribute("articulo", articulo);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO + "?op=alta").forward(request, response);
				} else {
					articuloDAO.abrirManager();
					articuloDAO.iniciarTransaccion();
					articuloDAO.insert(articulo);
					articuloDAO.terminarTransaccion();
					articuloDAO.cerrarManager();
					session.removeAttribute("errorProducto");
					log.info("Producto(s) dado(s) de alta");
					rutaListado.forward(request, response);
				}
				break;
			case "modificar":

				// Aquí hay que declarar un nuevo producto con los datos recogidos del formulario. Como en el caso
				// modificar, el campo groupId está deshabilitado y no lo envía, hay que extraerlo a través del id.
				// Como el nombre se extrae del mismo campo, para este caso solicitamos el parametro "nombre".
				articuloDAO.abrirManager();
				articulo = articuloDAO.findById(id);
				articulo.setCodigoArticulo(codigoArticulo);
				articulo.setNombre(nombre);
				articulo.setDescripcion(descripcion);
				articulo.setImagen(imagen);
				articulo.setPrecio(precio);

				BigInteger nuevaCantidad = articulo.getStock().add(cantidad);

				articulo.setStock(nuevaCantidad);
				articuloDAO.cerrarManager();
				// articuloDAO.update(articulo);

				if (nombre == null || nombre == "") {
					session.setAttribute("errorProducto", "Debes introducir un nombre de producto");
					request.setAttribute("articulo", articulo);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO + "?op=modificar").forward(request, response);
				} else {
					session.removeAttribute("errorProducto");
					rutaListado.forward(request, response);
				}
				break;
			case "borrar":
				articuloDAO.abrirManager();
				articuloDAO.iniciarTransaccion();
				articuloDAO.delete(id);
				articuloDAO.terminarTransaccion();
				articuloDAO.cerrarManager();
				session.removeAttribute("errorProducto");
				rutaListado.forward(request, response);

				break;
			default:
				session.removeAttribute("errorProducto");
				rutaListado.forward(request, response);
			}
		}
	}
}
