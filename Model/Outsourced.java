package Model;

/**
 * Outsourced class inherits Part class
 */
public class Outsourced extends Part {
    String companyName;

    /**
     * Constructor for Outsourced class
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Gets Company Name
     */
    public String getCompanyName() { //gets companyName
        return companyName;
    }

    /**
     * Sets Company Name
     */
    public void setCompanyName(String companyName) { //sets CompanyName
        this.companyName = companyName;
    }
}
