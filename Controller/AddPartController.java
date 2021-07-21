package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for AddPart.fxml
 */
public class AddPartController {

    public Label variableName;
    public TextField inventoryField;
    public TextField nameField;
    public TextField priceField;
    public TextField minField;
    public TextField maxField;
    public TextField idField;
    public RadioButton inHouse;
    public RadioButton outSourced;
    public Label errorLabel;


    /**
     * Sets label to appropriate title if the inHouse radio button is selected
     */
    public void inHouseSelected(ActionEvent actionEvent) {
        variableName.setText("Machine ID");

    }

    /**
     * Sets label to appropriate title if the outSourced radio button is selected
     */
    public void outSourcedSelected(ActionEvent actionEvent) {
        variableName.setText("Company Name");
    }

    /**
     * Handles the data input when the add button is pressed
     */
    public void addButtonClick(ActionEvent actionEvent) throws IOException {
        int id = 0;
        for (int i = 0; i < Inventory.getInventoryParts().size(); i++) {
            if (id <= Inventory.getInventoryParts().get(i).getId())
                id = Inventory.getInventoryParts().get(i).getId() + 1;
        }

        try {
            String name = nameField.getText().trim();
            int stock = Integer.parseInt(inventoryField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            int min = Integer.parseInt(minField.getText().trim());
            int max = Integer.parseInt(maxField.getText().trim());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The min must be less than the max");
                alert.showAndWait();
                return;
            }
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Stock cannot be more than max or less than min");
                alert.showAndWait();
                return;
            }
            if (inHouse.isSelected()) {
                int machineID = Integer.parseInt(idField.getText().trim());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            } else if (outSourced.isSelected()) {
                String companyName = idField.getText().trim();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
            cancelButtonClick(actionEvent);
        } catch (Exception error) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid data entered");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Returns user to Main.fxml
     */
    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 450);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }
}

