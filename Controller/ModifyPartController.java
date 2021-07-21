package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
 * Controller for ModifyPart.fxml
 */
public class ModifyPartController {

    public Label variableName;
    public TextField inventoryField;
    public TextField nameField;
    public TextField priceField;
    public TextField minField;
    public TextField maxField;
    public TextField idField;
    public TextField variableField;
    public RadioButton inHouseRadio;
    public RadioButton outSourcedRadio;
    public Label errorLabel;
    public int mpIndex;

    /**
     * Sets label to appropriate title if the inHouse radio button is selected
     */
    public void inHouseSelect(ActionEvent actionEvent) {
        variableName.setText("Machine ID");

    }

    /**
     * Sets label to appropriate title if the outSourced radio button is selected
     */
    public void outSourcedSelect(ActionEvent actionEvent) {
        variableName.setText("Company Name");
    }

    /**
     * Handles the data input when the modify button is pressed
     */
    public void modifyButtonClick(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
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

            if (inHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(variableField.getText());
                InHouse modifiedPart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.modifyPart(mpIndex, modifiedPart);
            } else if (outSourcedRadio.isSelected()) {
                String companyName = variableField.getText();
                Outsourced modifiedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.modifyPart(mpIndex, modifiedPart);
            }
            cancelButtonClicked(actionEvent);
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
    public void cancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 450);
        stage.setTitle("Inventory Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Parses selected data into relevant fields when ModifyPartController is launched
     */
    public void sendData(Part part) {
        if (part instanceof Outsourced) {
            outSourcedRadio.setSelected(true);
            variableName.setText("Company Name");
            variableField.setText(((Outsourced) part).getCompanyName());
        } else if (part instanceof InHouse) {
            inHouseRadio.setSelected(true);
            variableField.setText(Integer.toString(((InHouse) part).getMachineID()));
        }
        idField.setText(Integer.toString(part.getId()));
        inventoryField.setText(Integer.toString(part.getStock()));
        nameField.setText(part.getName());
        priceField.setText(Double.toString(part.getPrice()));
        minField.setText(Integer.toString(part.getMin()));
        maxField.setText(Integer.toString(part.getMax()));
        mpIndex = Inventory.getInventoryParts().indexOf(part);
    }
}
