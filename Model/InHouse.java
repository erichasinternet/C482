package Model;

/**
 * InHouse class inherits Part class
 */
public class InHouse extends Part {
    int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Gets Machine ID
     */
    public int getMachineID() { //gets machineID
        return machineID;
    }

    /**
     * Sets Machine ID
     */
    public void setMachineID(int id) { //setsMachineID
        this.machineID = id;
    }


}
