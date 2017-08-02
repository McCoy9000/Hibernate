package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import pojos.ArticuloStock;
import recursos.Constantes;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;

@WebServlet("/admin/articulocrud")
public class ArticuloCRUD extends HttpServlet {

	private static final long serialVersionUID = 8657287130744542024L;
	
	private static Logger log = Logger.getLogger(ArticuloCRUD.class);
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = getServletContext();


		String op = request.getParameter("op");
		DAOManager daoManager = DAOManagerFactory.getDAOManager();
		daoManager.abrir();
		ArticuloStockDAO articuloStockDAO = daoManager.getArticuloStockDAO();
		
		if (op == null) {
			List<ArticuloStock> articulos = null;
			daoManager.iniciarTransaccion();
			try {
				articulos = articuloStockDAO.findAll();
				daoManager.terminarTransaccion();
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al recuperar los articulos del catálogo");
			} finally {
				daoManager.cerrar();
			}
			application.setAttribute("articulos", articulos);
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
			return;
		}

		ArticuloStock articulo = null;

		switch (op) {
		case "modificar":
		case "borrar":
			long id;
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error al parsear el id de artículo");
				request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
				break;
			}
			daoManager.iniciarTransaccion();
			try {
				articulo = articuloStockDAO.findById(id);
				daoManager.terminarTransaccion();
			} catch (Exception e) {
				daoManager.abortarTransaccion();
				e.printStackTrace();
				log.info("Error al recuperar el artículo según Id");
			} finally {
				daoManager.cerrar();
			}
			request.setAttribute("articulo", articulo);
			
		case "alta":
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO).forward(request, response);
			break;
		default:
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
		}
	}
}
