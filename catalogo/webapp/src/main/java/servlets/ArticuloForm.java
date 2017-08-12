package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pojos.ArticuloStock;
import recursos.Constantes;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;

@WebServlet("/admin/articuloform")
public class ArticuloForm extends HttpServlet {

	private static final long serialVersionUID = 3997952646417125446L;

	private static Logger log = Logger.getLogger(ArticuloForm.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Recoger el objeto application del ServletContext
		HttpSession session = request.getSession();

		// Borrar los errores que puedan venir en sesión
		session.removeAttribute("errorProducto");

		// Recoger la opción elegida por el usuario en el formulario enviada por url
		String op = request.getParameter("opform");

		// Declaro aquí los dispatcher porque en un momento me dio un problema extraño por declararlos en el momento en que
		// los necesitaba
		RequestDispatcher rutaListado = request.getRequestDispatcher(Constantes.RUTA_SERVLET_LISTADO_PRODUCTO);
		RequestDispatcher rutaFormulario = request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO);

		// Declaración de las variables para construir el objeto con el que se trabajará e iniciarlas con los valores recogidos
		// del formulario

		ArticuloStock articulo = null;

		long id = 0;

		if (request.getParameter("id") != null) {
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error al parsear el Id del artículo. Se considerará 0.");
			}
		}

		String codigoArticulo, nuevoCodigoArticulo;

		if (request.getParameter("codigoArticulo") != null) {
			codigoArticulo = request.getParameter("codigoArticulo").trim();
		} else {
			codigoArticulo = request.getParameter("codigoArticulo");
		}
 

		if (request.getParameter("nuevoCodigoArticulo") != null) {
			nuevoCodigoArticulo = request.getParameter("nuevoCodigoArticulo").trim();
		} else {
			nuevoCodigoArticulo = request.getParameter("nuevoCodigoArticulo");
		}
		

		String nombre, descripcion;

		if (request.getParameter("nombre") != null) {
			nombre = request.getParameter("nombre").trim();
		} else {
			nombre = request.getParameter("nombre");
		}
 

		if (request.getParameter("descripcion") != null) {
			descripcion = request.getParameter("descripcion").trim();
		} else {
			descripcion = request.getParameter("descripcion");
		}

		BigDecimal precio = BigDecimal.ZERO;

		if (request.getParameter("precio") != null) {
			try {
				precio = new BigDecimal(request.getParameter("precio"));
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error al parsear precio");
			}
		}

		BigInteger cantidad = BigInteger.ONE;

		if (request.getParameter("cantidad") != null) {
			try {
				cantidad = new BigInteger(request.getParameter("cantidad"));
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error al parsear la cantidad");
			}
		}
		// Lógica del servlet según la opción elegida por el usuario y enviada por el navegador
		// encapsulada en opform.

		// Si el usuario aún no ha elegido una opción. 
		
		if (op == null) {
			articulo = new ArticuloStock(codigoArticulo, nombre, descripcion, precio, cantidad);
			session.removeAttribute("errorProducto");
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO + "?op=alta").forward(request, response);

		} else {

			switch (op) {
			case "alta":

				articulo = new ArticuloStock(nuevoCodigoArticulo, nombre, descripcion, precio, cantidad);

				if (nombre == null || nombre == "") {
					session.setAttribute("errorProducto", "Debes introducir un nombre de producto");
					request.setAttribute("articulo", articulo);
					rutaFormulario.forward(request, response);
				} else if (precio.compareTo(BigDecimal.ZERO) == -1 || precio == null) {
					session.setAttribute("errorProducto", "Debes introducir un precio mayor que 0");
					request.setAttribute("articulo", articulo);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO + "?op=alta").forward(request, response);
				} else {
					DAOManager daoManager = DAOManagerFactory.getDAOManager();
					daoManager.abrir();
					ArticuloStockDAO articuloStockDAO = daoManager.getArticuloStockDAO();
					daoManager.iniciarTransaccion();
					try {
						articuloStockDAO.insert(articulo);
						daoManager.terminarTransaccion();
						log.info("Articulo dado de alta.");
					} catch (Exception e) {
						daoManager.abortarTransaccion();
						e.printStackTrace();
						log.info("Error al insertar el producto.");
					} finally {
						daoManager.cerrar();
					}
					session.removeAttribute("errorProducto");
					rutaListado.forward(request, response);
				}
				break;
			case "modificar":

				DAOManager manager = DAOManagerFactory.getDAOManager();
				manager.abrir();
				ArticuloStockDAO articuloDAO = manager.getArticuloStockDAO();
				manager.iniciarTransaccion();
				try {
					articulo = articuloDAO.findById(id);
					manager.terminarTransaccion();
				} catch (Exception e) {
					manager.abortarTransaccion();
					e.printStackTrace();
					log.info("Error al recuperar el producto con id " + id + " de la base de datos.");
				}
				
				if (nombre == null || nombre == "") {
					
					session.setAttribute("errorProducto", "Debes introducir un nombre de producto");
					request.setAttribute("articulo", articulo);
					request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO + "?op=modificar").forward(request, response);

					break;
				}
			
				manager.iniciarTransaccion();
				try {
					articulo.setCodigoArticulo(codigoArticulo);
					articulo.setNombre(nombre);
					articulo.setDescripcion(descripcion);
					articulo.setPrecio(precio);
					articulo.setStock(cantidad);
					
					manager.terminarTransaccion();
				} catch (Exception e) {
					manager.abortarTransaccion();
					e.printStackTrace();
					log.info("Error al actualizar el artículo.");
				} finally {
					manager.cerrar();
				}
				session.removeAttribute("errorProducto");
				rutaListado.forward(request, response);
				break;
			case "borrar":
				DAOManager daoman = DAOManagerFactory.getDAOManager();
				daoman.abrir();
				ArticuloStockDAO articuloStockDAO = daoman.getArticuloStockDAO();
				daoman.iniciarTransaccion();
				try {
					articuloStockDAO.delete(id);
					daoman.terminarTransaccion();
				} catch (Exception e) {
					daoman.abortarTransaccion();
					e.printStackTrace();
					log.info("Error al borrar el artículo.");
				} finally {
					daoman.cerrar();
				}
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
