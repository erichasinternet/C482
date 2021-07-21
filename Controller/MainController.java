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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for Main.fxml (Contains FUTURE ENHANCEMENT and RUNTIME ERROR)
 */
public class MainController implements Initializable {


    public TableView<Part> partTableView;
    public TableColumn partIDColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;

    public TableView<Product> productTableView;
    public TableColumn productIDColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryColumn;
    public TableColumn productPriceColumn;

    public AnchorPane anchorPane;
    public TextField searchPart;
    public TextField searchProduct;


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

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView.setItems(Inventory.getInventoryProducts());
    }

    /**
     * Clicking the Add button under the parts table opens AddPart.fxml
     */
    public void addPartsClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Clicking the Modify button opens the selected part in ModifyPart.fxml
     */
    public void modifyPartsClick(ActionEvent actionEvent) throws IOException {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
            Parent scene = loader.load();
            ModifyPartController controller = loader.getController();
            controller.sendData(selectedPart);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * FUTURE ENHANCEMENT:
     * Add the ability to select and delete multiple Parts at once.
     * ///
     * Upon clicking Delete button the user is prompted to confirm deletion of part
     */
    public void deletePartsClicked(ActionEvent actionEvent) {
        Part deletePart = partTableView.getSelectionModel().getSelectedItem();
        if (deletePart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            alert.setHeaderText("Delete " + partTableView.getSelectionModel().getSelectedItem().getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;
            }
            if (result.get() == ButtonType.OK) {
                Inventory.removePart(deletePart);
                partTableView.setItems(Inventory.getInventoryParts());
                for (int i = 0; i < Inventory.getInventoryProducts().size(); i++) {
                    if (Inventory.getInventoryProducts().get(i).getAssociatedParts().contains(deletePart)) {
                        Inventory.getInventoryProducts().get(i).removeAssociatedPart(deletePart);
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Clicking the add button under the product table opens AddProduct.fxml
     */
    public void addProductsClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 950, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Clicking the Modify button opens the selected part in ModifyProduct.fxml
     */
    public void modifyProductsClick(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
            Parent scene = loader.load();
            ModifyProductController controller = loader.getController();
            controller.sendProductData(selectedProduct);
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Upon clicking Delete button the user is prompted to confirm deletion of product
     */
    public void deleteProductsClick(ActionEvent actionEvent) {
        Product deleteProduct = productTableView.getSelectionModel().getSelectedItem();
        if (deleteProduct != null && deleteProduct.getAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            alert.setHeaderText("Delete " + deleteProduct.getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.removeProduct(productTableView.getSelectionModel().getSelectedItem());
                productTableView.setItems(Inventory.getInventoryProducts());
                return;
            }
            if (result.get() == ButtonType.CANCEL) {
                return;
            }
        } else if (deleteProduct != null && deleteProduct.getAssociatedParts().size() > 0) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING, "Product is associated with a part");
            alert1.setHeaderText(deleteProduct.getName() + " cannot be deleted");
            alert1.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }
        return;
    }

    /**
     * RUNTIME ERROR:
     * Could not get the partTableView to display string search queries. It would say "No content in table."
     * To Fix this I deleted the allParts ObservableList and used Inventory.getInventoryParts() directly.
     * I rewrote the code to convert text to lower case and double checked that my fields were correct.
     * This fixed my error.
     * ///
     * Takes searchPart TextField input, compares it against inventory, and populates the table if matches are found.
     */
    public void searchPartEnter(ActionEvent actionEvent) {
        String q = searchPart.getText().trim();
        ObservableList<Part> parts = Inventory.searchByPartName(q);
        partTableView.setItems(parts);

        try {
            int id = Integer.parseInt(q);
            ObservableList<Part> partID = FXCollections.observableArrayList();
            partID.add(Inventory.searchPart(id));
            if (partID.get(0) != null)
                partTableView.setItems(partID);

        } catch (Exception e) {
            //ignore
        }
    }

    /**
     * Takes searchProduct TextField input, compares it against inventory, and populates the table if matches are found.
     */
    public void searchProductEnter(ActionEvent actionEvent) {
        String q = searchProduct.getText().trim();
        ObservableList<Product> products = Inventory.searchByProductName(q);
        productTableView.setItems(products);

        try {
            int id = Integer.parseInt(q);
            ObservableList<Product> productID = FXCollections.observableArrayList();
            productID.add(Inventory.searchProduct(id));
            if (productID.get(0) != null)
                productTableView.setItems(productID);

        } catch (Exception e) {
            //ignore
        }
    }

    /**
     * Terminates the application
     */
    public void exitButtonClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}

