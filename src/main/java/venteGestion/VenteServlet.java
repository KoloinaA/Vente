package venteGestion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class VenteServlet
 */
@WebServlet("/VenteServlet")
public class VenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VenteServlet() {
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
	    VenteDB ventedb = new VenteDB();
		List<Vente> ventes = ventedb.selectVente();

	        // Convert data to JSON		
	        String jsonData = ventedb.convertListToJson(ventes);

	        // Set response content type to JSON
	        response.setContentType("application/json");

	        // Write JSON data to the response
	        try (PrintWriter out = response.getWriter()) {
	            out.print(jsonData);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Max-Age", "86400");
	    response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
	   
		VenteDB ventedb=new VenteDB();
		Vente data = new Gson().fromJson(request.getReader(), Vente.class);
		response.setContentType("application/json"); // Set content type to JSON
		PrintWriter writer = response.getWriter();
		Vente responseObject = new Vente(data.design,data.prix,data.quantite);
		ventedb.create(responseObject);
		String jsonData = new Gson().toJson(responseObject);
		writer.println(jsonData);
		writer.close();	
		
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  response.setHeader("Access-Control-Allow-Origin", "*");
		  response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE"); 
		  response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Z-Key"); 
		  response.setHeader("Content-type", "text/plain");
		  response.setHeader("Allow", "GET, HEAD, POST, TRACE, DELETE, OPTIONS");
		  response.setHeader("Access-Control-Max-Age", "86400");
		 
	    
		String numProduitString = request.getParameter("numProduit");

		  if (numProduitString == null || numProduitString.isEmpty()) {
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    response.getWriter().println("Missing required parameter: numProduit");
		    return;
		  }

		  int numProduit;
		  try {
		    numProduit = Integer.parseInt(numProduitString);
		    VenteDB ventedb=new VenteDB();
		    ventedb.delete(numProduit);
		    response.getWriter().println("Nety");
		  } catch (NumberFormatException e) {
		    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		    response.getWriter().println("Invalid numProduit format");
		    return;
		  }
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Max-Age", "86400");
	    response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
	   
	    try {
	        Vente data = new Gson().fromJson(request.getReader(), Vente.class);

	        // Perform database update
	        VenteDB ventedb = new VenteDB();
	        ventedb.updateVente(data);

	        // Send successful response
	        response.setStatus(HttpServletResponse.SC_OK); // 200 OK
	        response.setContentType("application/json");
	        response.getWriter().println("{\"message\": \"Vente updated successfully\"}");

	      } catch (SQLException e) {
	        // Handle database exception gracefully
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	        response.setContentType("application/json");
	        response.getWriter().println("{\"error\": \"Database error occurred\"}");
	        e.printStackTrace(); // Log the exception for debugging
	      } catch (IOException e) {
	        // Handle JSON parsing or request reading errors
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
	        response.setContentType("application/json");
	        response.getWriter().println("{\"error\": \"Invalid request data\"}");
	        e.printStackTrace(); // Log the exception for debugging
	      } finally {
	        // Optional: Close resources (if applicable)
	      }
	}
	/**
	 * @see HttpServlet#doOptions(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		 	response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		    response.setHeader("Access-Control-Max-Age", "86400");
		    response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
	}
	

}
