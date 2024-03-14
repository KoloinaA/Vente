package venteGestion;

public class Vente {
	int numProduit;
	String design;
	int prix;
	int quantite;
	
	
	public Vente(String design, int prix, int quantite) {
		super();
		this.design = design;
		this.prix = prix;
		this.quantite = quantite;
	}
	
	public Vente(int numProduit, String design, int prix, int quantite) {
		super();
		this.numProduit = numProduit;
		this.design = design;
		this.prix = prix;
		this.quantite = quantite;
	}

	public int getNumProduit() {
		return numProduit;
	}
	public void setNumProduit(int numProduit) {
		this.numProduit = numProduit;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
}
