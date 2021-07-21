package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class creates an application for managing inventory
 */

public class Main extends Application {

    /**
     * Test data
     */
    public static void main(String[] args) {
        InHouse part = new InHouse(1, "Deck", 60, 1, 1, 1, 4);
        InHouse part1 = new InHouse(2, "Trucks", 45, 2, 1, 2, 5);
        InHouse part2 = new InHouse(3, "Bearings", 45, 16, 8, 16, 4);
        Outsourced part3 = new Outsourced(4, "Grip tape", 5, 2, 1, 2, "Mob");
        Outsourced part4 = new Outsourced(5, "Wheels", 30, 4, 1, 4, "Bones");
        Outsourced part5 = new Outsourced(6, "Long deck", 70, 4, 1, 4, "Sector9");
        Product product = new Product(1, "Skateboard", 150, 4, 1, 4);
        Product product1 = new Product(2, "Longboard", 120, 2, 1, 2);
        Inventory.addPart(part);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        product.addAssociatedPart(part);
        product.addAssociatedPart(part1);
        product.addAssociatedPart(part2);
        product.addAssociatedPart(part3);
        product.addAssociatedPart(part4);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part5);
        Inventory.addProduct(product);
        Inventory.addProduct(product1);

        launch(args);
    }

    /**
     * Initializes Main.fxml
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        primaryStage.setTitle("Inventory Manager");
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
    }
}


