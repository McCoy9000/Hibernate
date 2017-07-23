package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
import pojos.Imagen;
import pojos.Usuario;


@WebServlet("/testhibernate")
public class TestHibernate extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private static EntityManager man;
	private static EntityManagerFactory emf;
	private static Logger log = Logger.getLogger(TestHibernate.class);
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext application = request.getServletContext();
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();


		emf = Persistence.createEntityManagerFactory("persistencia");
		man = emf.createEntityManager();
		
		Usuario usuario1 = new Usuario("Mikel", "Cuenca", LocalDate.of(1979, 04, 11), "78903356Q", "677034057", "mcmilbao@gmail.com", new Direccion("48012", "España", "Bizkaia", "Bilbao", "Autonomía", "29", "4ID"), new Empresa("Ipartek", "A-87654321", new Direccion()), new Imagen(), "mikel", "mikel");
	
		man.getTransaction().begin();
		man.persist(usuario1);
		man.getTransaction().commit();
		
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) man.createQuery("FROM Usuario").getResultList();
		
		application.setAttribute("usuarios", usuarios);
		log.info("test message");
		log.info(usuarios.size());
		request.getRequestDispatcher("/WEB-INF/vistas/usuarios.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
