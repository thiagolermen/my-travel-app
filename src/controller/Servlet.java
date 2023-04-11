package controller;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Facade;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	Facade home;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String op = request.getParameter("op");
//		if (op.equals("ajoutpersonne")) {
//			System.out.println("Salut, je suis dans ajoutpersonne");
//			this.annuaire.ajoutPersonne(request.getParameter("nom"),request.getParameter("prenom"));
//			request.getRequestDispatcher("index.html").forward(request, response);
//		} else if (op.equals("ajoutadresse")) {
//			System.out.println("Salut, je suis dans ajoutadresse");
//			this.annuaire.ajoutAdresse(request.getParameter("rue"),request.getParameter("ville"));
//			request.getRequestDispatcher("index.html").forward(request, response);			
//		} else if (op.equals("associer1")) {
//			if ((this.annuaire.listePersonnes().size() > 0) && (this.annuaire.listeAdresses().size() > 0)) {
//				request.setAttribute("listepersonnes", this.annuaire.listePersonnes());
//				request.setAttribute("listeadresses",this.annuaire.listeAdresses());
//				request.getRequestDispatcher("associer.jsp").forward(request, response);					
//			} else {
//				request.getRequestDispatcher("casvide.html").forward(request, response);
//			}
//		} else if (op.equals("associer2")) {
//			Integer idPersonne = Integer.parseInt(request.getParameter("idpersonne"));
//			Integer idAdresse = Integer.parseInt(request.getParameter("idadresse"));
//			annuaire.associer(idPersonne, idAdresse);
//			request.getRequestDispatcher("index.html").forward(request, response);
//		} else if (op.equals("lister")) {
//			request.setAttribute("listepersonnes", this.annuaire.listePersonnes());
//			request.getRequestDispatcher("lister.jsp").forward(request, response);	
//		} else if (op.equals("retour")) {
//			request.getRequestDispatcher("index.html").forward(request, response);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}