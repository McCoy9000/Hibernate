package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import recursos.Constantes;

@WebServlet("/facturaout")
public class FacturaOut extends HttpServlet {

	private static final long serialVersionUID = -374351075129665738L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String op = request.getParameter("op");

		if (op == null) {
			request.getRequestDispatcher(Constantes.RUTA_AGRADECIMIENTO).forward(request, response);
		} else {
			switch (op) {
			case "factura":
				request.getRequestDispatcher(Constantes.RUTA_FACTURA_FACTURA).forward(request, response);
				break;
			}
		}

	}
}
