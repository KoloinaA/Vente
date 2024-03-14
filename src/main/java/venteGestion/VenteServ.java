package venteGestion;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class VenteServ
 */
@WebServlet("/VenteServ")
public class VenteServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VenteServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); 
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Max-Age", "86400");
	    response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");		
		VenteDB ventedb=new VenteDB();
		double maxPrix=ventedb.maxPrix();
		double minPrix=ventedb.minPrix();
		double montantTotal=ventedb.sommePrixQuantite();

		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("minPrix", minPrix);
		jsonResponse.addProperty("maxPrix", maxPrix);
		jsonResponse.addProperty("montantTotal", montantTotal);

		// Set response content type to JSON
		response.setContentType("application/json");

		// Write JSON data to the response
		try (PrintWriter out = response.getWriter()) {
		    out.print(jsonResponse.toString());
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
