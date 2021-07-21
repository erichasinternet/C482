package Controller;


import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for ModifyProduct.fxml
 */
public class ModifyProductController implements Initializable {

    public TextField searchAssociatedPartsField;
    public TextField searchAvailablePartsField;

    public TableView<Part> partTableView;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;

    public TableView<Part> associatedPartTableView;
    public TableColumn associatedPartIDColumn;
    public TableColumn associatedPartNameColumn;
    public TableColumn associatedPartInventoryColumn;
    public TableColumn associatedPartPriceColumn;

    public TextField inventoryField;
    public TextField nameField;
    public TextField priceField;
    public TextField minField;
    public TextField maxField;
    public TextField idField;
    public Label errorLabel;
    public int mpIndex;

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Sets cell values for part and product tables
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getInventoryParts());


        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Brings data from selected Product object and uses it to populate fields
     */
    public void sendProductData(Product selectedProduct) {
        associatedParts.addAll(selectedProduct.getAssociatedParts());
        associatedPartTableView.setItems(associatedParts);

        idField.setText(Integer.toString(selectedProduct.getId()));
        inventoryField.setText(Integer.toString(selectedProduct.getStock()));
        nameField.setText(selectedProduct.getName());
        priceField.setText(Double.toString(selectedProduct.getPrice()));
        minField.setText(Integer.toString(selectedProduct.getMin()));
        maxField.setText(Integer.toString(selectedProduct.getMax()));
        mpIndex = Inventory.getInventoryProducts().indexOf(selectedProduct);
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

            Product mod = new Product(id, name, price, stock, min, max);
            mod.setId(id);
            mod.getAssociatedParts().clear();
            mod.getAssociatedParts().addAll(associatedParts);
            Inventory.modifyProduct(mpIndex, mod);


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

    /**
     * Searches for Associated Part that matches user input
     */
    public void searchAssociatedPartsEnter(ActionEvent actionEvent) {
        String q = searchAssociatedPartsField.getText().trim();
        if (q.isEmpty())
            associatedPartTableView.setItems(associatedParts);
        if (q.matches("[0-9]+")) {
            int id = Integer.parseInt(q);
            ObservableList<Part> partID = FXCollections.observableArrayList();
            for (int i = 0; i < associatedParts.size(); i++) {
                if (associatedParts.get(i).getId() == id) {
                    partID.add(associatedParts.get(i));
                }
                associatedPartTableView.setItems(partID);
            }
        }
        if (q.matches("[a-zA-Z]+")) {
            ObservableList<Part> partName = FXCollections.observableArrayList();
            for (int i = 0; i < associatedParts.size(); i++) {
                if (associatedParts.get(i).getName().toLowerCase().contains(q.toLowerCase()))
                    partName.add(associatedParts.get(i));
                associatedPartTableView.setItems(partName);
            }
        }
    }

    /**
     * Searches for Available Part that matches user input
     */
    public void searchAvailablePartsEnter(ActionEvent actionEvent) {
        String q = searchAvailablePartsField.getText().trim();
        if (q.isEmpty())
            partTableView.setItems(Inventory.getInventoryParts());
        else if (q.matches("[0-9]+")) {
            int id = Integer.parseInt(q);
            ObservableList<Part> partID = FXCollections.observableArrayList();
            partID.add(Inventory.searchPart(id));
            if (partID.get(0) != null)
                partTableView.setItems(partID);
        } else
            partTableView.setItems(Inventory.searchByPartName(q));
    }

    /**
     * @param actionEvent The selected part is added to the associated parts table upon clicking the Add button
     */
    public void addPartsButtonClick(ActionEvent actionEvent) {
        Part SP = partTableView.getSelectionModel().getSelectedItem();
        if (SP != null && !associatedParts.contains(SP)) {
            associatedParts.add(SP);
            associatedPartTableView.setItems(associatedParts);
        } else if (associatedParts.contains(SP)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("The associated parts table already contains this part.");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * @param actionEvent The selected part is removed from the associated parts table upon clicking the Remove button
     */
    public void removePartsButtonClick(ActionEvent actionEvent) {
        Part SP = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (SP != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            alert.setHeaderText("Delete " + associatedPartTableView.getSelectionModel().getSelectedItem().getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;
            } else if (result.get() == ButtonType.OK) {
                associatedParts.remove(SP);
                associatedPartTableView.setItems(associatedParts);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
            return;
        }
    }
}



