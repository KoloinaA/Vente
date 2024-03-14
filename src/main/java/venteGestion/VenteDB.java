package venteGestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class VenteDB {
	private static final String insert_vente="INSERT INTO vente"+" (design, prix, quantite)VALUES"+" (?,?,?);";
	private static final String select_vente_by_id="select numProduit,design,prix,quantite from vente where numProduit=?";
	private static final String select_vente="select*from vente";
	private static final String delete_vente="delete from vente where numProduit=?";
	private static final String update_vente="update vente set design=?, prix=?, quantite=? where numProduit=?";
	public Connection getConnection(){
		Connection connection=null;
	    try {
	    	Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vente", "root", "root");
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la connexion à la base de données:");
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        System.err.println("Driver MySQL introuvable. Assurez-vous d'avoir la bibliothèque MySQL JDBC dans votre projet.");
	        e.printStackTrace();
	    }
		return connection;
}
	/*create*/
	public void create(Vente vente) {
		try(Connection connection=getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(insert_vente)){
				
				preparedStatement.setString(1,vente.getDesign());
				preparedStatement.setInt(2,vente.getPrix());
				preparedStatement.setInt(3,vente.getQuantite());
				preparedStatement.executeUpdate();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}
	/*select*/
	public List<Vente> selectVente() {
		List<Vente> ventes=new ArrayList<>();
		try(Connection connection=getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(select_vente);){
	
			System.out.println(preparedStatement);
			ResultSet res=preparedStatement.executeQuery();
			while(res.next()) {
				int numProduit=res.getInt("numProduit");
				String design=res.getString("design");
				int prix=res.getInt("prix");
				int quantite=res.getInt("quantite");
				ventes.add(new Vente(numProduit,design,prix,quantite));
			}
		}
			catch(SQLException e) {
				e.printStackTrace();
			}
		
		return ventes;
	}
	
	/*select to JSON*/
	
	
		/*delete*/
	public void delete(int numProduit) {
		try(Connection connection=getConnection();
				PreparedStatement statement=connection.prepareStatement(delete_vente);){
			statement.setInt(1, numProduit);
			statement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void updateVente(Vente vente)throws SQLException{
		try(Connection connection=getConnection();
				PreparedStatement statement=connection.prepareStatement(update_vente);
				){
			statement.setString(1, vente.getDesign());
			statement.setInt(2, vente.getPrix());
			statement.setInt(3, vente.getQuantite());
			statement.setInt(4, vente.getNumProduit());
			
			statement.executeUpdate();
		}
		
	}
	
	public double minPrix() {
	    double minPrix = Double.MAX_VALUE;

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement("SELECT MIN(prix) FROM vente");
	         ResultSet resultSet = statement.executeQuery()) {

	        if (resultSet.next()) {
	            minPrix = resultSet.getDouble(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return minPrix;
	}
	public double maxPrix() {
	    double maxPrix = Double.MAX_VALUE;

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement("SELECT MAX(prix) FROM vente");
	         ResultSet resultSet = statement.executeQuery()) {

	        if (resultSet.next()) {
	            maxPrix = resultSet.getDouble(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return maxPrix;
	}
	public double sommePrixQuantite() {
	    double somme = 0;

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement("SELECT SUM(prix * quantite) FROM vente");
	         ResultSet resultSet = statement.executeQuery()) {

	        if (resultSet.next()) {
	            somme = resultSet.getDouble(1);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    return somme;
	}
	 public String convertListToJson(List<Vente> ventes) {
	        Gson gson = new Gson();
	        return gson.toJson(ventes);
	    }
}
