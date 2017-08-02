package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pojos.ArticuloStock;
import recursos.Constantes;
import dataAccessLayer.ArticuloStockDAO;
import dataAccessLayer.DAOManager;
import dataAccessLayer.DAOManagerFactory;

@WebServlet("/admin/articulocrud")
public class ArticuloCRUD extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
			
			List<ArticuloStock> articulos = articuloStockDAO.findAll();
			daoManager.cerrar();
			application.setAttribute("articulos", articulos);
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
			return;
		}

		ArticuloStock articulo;

		switch (op) {
		case "modificar":
		case "borrar":
			long id;
			try {
				id = Long.parseLong(request.getParameter("id"));
			} catch (Exception e) {
				e.printStackTrace();
				request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
				break;
			}
			articulo = articuloStockDAO.findById(id);
			daoManager.cerrar();
			request.setAttribute("articulo", articulo);
		case "alta":
			request.getRequestDispatcher(Constantes.RUTA_FORMULARIO_PRODUCTO).forward(request, response);
			break;
		default:
			request.getRequestDispatcher(Constantes.RUTA_LISTADO_PRODUCTO).forward(request, response);
		}
	}
}
