package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains lists of Products and Parts
 */
public class Inventory {
    private static final ObservableList<Part> inventoryParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> inventoryProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to ObservableList
     */
    public static void addPart(Part newPart) {
        inventoryParts.add(newPart);
    }

    /**
     * Gets ObservableList of Parts
     */
    public static ObservableList<Part> getInventoryParts() {
        return inventoryParts;
    }

    /**
     * Adds a product to ObservableList
     */
    public static void addProduct(Product newProduct) {
        inventoryProducts.add(newProduct);
    }

    /**
     * Gets ObservableList of Products
     */
    public static ObservableList<Product> getInventoryProducts() {
        return inventoryProducts;
    }

    /**
     * Finds Part that contains String matching user input
     */
    public static ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        for (Part gp : Inventory.getInventoryParts()) {
            if (gp.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedPart.add(gp);
            }
        }
        return namedPart;
    }

    /**
     * Modifies the selected Part
     */
    public static void modifyPart(int index, Part modifyPart) {
        inventoryParts.set(index, modifyPart);
    }

    /**
     * Modifies the selected Product
     */
    public static void modifyProduct(int index, Product modifyProduct) {
        inventoryProducts.set(index, modifyProduct);
    }

    /**
     * Removes the selected Part
     */
    public static boolean removePart(Part selectedPart) {
        return inventoryParts.remove(selectedPart);
    }

    /**
     * Removes the selected Product
     */
    public static boolean removeProduct(Product selectedProduct) {
        return inventoryProducts.remove(selectedProduct);
    }

    /**
     * Finds Part that contains ID matching user input
     */
    public static Part searchPart(int partID) {
        for (Part inventoryPart : inventoryParts) {
            if (inventoryPart.getId() == partID)
                return inventoryPart;
        }
        return null;
    }

    /**
     * Finds Product that contains String matching user input
     */
    public static ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        for (Product gp : Inventory.getInventoryProducts()) {
            if (gp.getName().toLowerCase().contains(partialName.toLowerCase())) {
                namedProduct.add(gp);
            }
        }
        return namedProduct;
    }

    /**
     * Finds Product that contains ID matching user input
     */
    public static Product searchProduct(int partID) {
        for (Product inventoryProduct : inventoryProducts) {
            if (inventoryProduct.getId() == partID)
                return inventoryProduct;
        }
        return null;
    }
}
