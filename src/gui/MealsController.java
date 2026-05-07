package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.edu.esprit.entities.FoodItem;
import tn.edu.esprit.services.ServiceFoodItem;

import java.time.LocalDate;

public class MealsController {

    @FXML private Label selectedPlanLabel;
    @FXML private TextField fName, fCalPer100, fProtPer100, fCarbPer100, fFatPer100, fQuantity, fAllergens;
    @FXML private ComboBox<String> fMealType;
    @FXML private DatePicker fMealDate;
    @FXML private TableView<FoodItem> tableFoods;
    @FXML private TableColumn<FoodItem, String> colFoodName, colMealType, colFoodDate, colAllergens;
    @FXML private TableColumn<FoodItem, Integer> colFoodCal, colFoodQuantity, colTotalCal;

    private ServiceFoodItem sf = new ServiceFoodItem();
    private NutritionController parentController;

    public void setParentController(NutritionController controller) {
        this.parentController = controller;
    }

    public void setSelectedPlanLabel(String text) {
        selectedPlanLabel.setText(text);
    }

    @FXML
    public void initialize() {
        // Initialiser le ComboBox
        fMealType.getItems().clear();
        fMealType.getItems().addAll("petit_dejeuner", "dejeuner", "diner", "collation");
        fMealType.setValue("dejeuner");
        
        fMealDate.setValue(LocalDate.now());

        colFoodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFoodCal.setCellValueFactory(new PropertyValueFactory<>("caloriesPer100g"));
        colFoodQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityG"));
        colTotalCal.setCellValueFactory(new PropertyValueFactory<>("totalCalories"));
        colMealType.setCellValueFactory(new PropertyValueFactory<>("mealType"));
        colFoodDate.setCellValueFactory(new PropertyValueFactory<>("mealDate"));
        colAllergens.setCellValueFactory(new PropertyValueFactory<>("allergens"));

        refresh();
    }

    public void refresh() {
        tableFoods.setItems(FXCollections.observableArrayList(sf.getAll()));
    }

    @FXML
    private void handleAddFood() {
        if (parentController == null || parentController.getSelectedPlan() == null) {
            showAlert("Erreur", "Sélectionnez d'abord un plan dans l'onglet Plans");
            return;
        }
        try {
            if (fName.getText().isEmpty()) {
                showAlert("Erreur", "Nom de l'aliment requis");
                return;
            }
            
            FoodItem food = new FoodItem(0, parentController.getSelectedPlan().getId(), fName.getText(),
                Integer.parseInt(fCalPer100.getText()),
                Float.parseFloat(fProtPer100.getText()),
                Float.parseFloat(fCarbPer100.getText()),
                Float.parseFloat(fFatPer100.getText()),
                Float.parseFloat(fQuantity.getText()),
                0, fAllergens.getText(), true,
                fMealType.getValue(), fMealDate.getValue().toString(),
                "");
            sf.ajouter(food);
            
            // Mettre à jour le score d'adhérence
            parentController.updateAdherenceScore(fMealDate.getValue().toString());
            
            refresh();
            clearFields();
            showAlert("Succès", "Aliment ajouté !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Valeurs numériques invalides");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateFood() {
        FoodItem selected = tableFoods.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Erreur", "Sélectionnez un aliment");
            return;
        }
        try {
            selected.setName(fName.getText());
            selected.setCaloriesPer100g(Integer.parseInt(fCalPer100.getText()));
            selected.setProteinPer100g(Float.parseFloat(fProtPer100.getText()));
            selected.setCarbsPer100g(Float.parseFloat(fCarbPer100.getText()));
            selected.setFatPer100g(Float.parseFloat(fFatPer100.getText()));
            selected.setQuantityG(Float.parseFloat(fQuantity.getText()));
            selected.setAllergens(fAllergens.getText());
            selected.setMealType(fMealType.getValue());
            selected.setMealDate(fMealDate.getValue().toString());
            sf.modifier(selected);
            
            if (parentController != null) {
                parentController.updateAdherenceScore(fMealDate.getValue().toString());
            }
            
            refresh();
            clearFields();
            showAlert("Succès", "Aliment modifié !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Valeurs numériques invalides");
        }
    }

    @FXML
    private void handleDeleteFood() {
        FoodItem selected = tableFoods.getSelectionModel().getSelectedItem();
        if (selected != null) {
            sf.supprimer(selected.getId());
            
            if (parentController != null && selected.getMealDate() != null) {
                parentController.updateAdherenceScore(selected.getMealDate());
            }
            
            refresh();
            clearFields();
            showAlert("Succès", "Aliment supprimé !");
        } else {
            showAlert("Erreur", "Sélectionnez un aliment");
        }
    }

    private void clearFields() {
        fName.clear(); fCalPer100.clear(); fProtPer100.clear(); fCarbPer100.clear();
        fFatPer100.clear(); fQuantity.clear(); fAllergens.clear();
        fMealType.setValue("dejeuner");
        fMealDate.setValue(LocalDate.now());
        tableFoods.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}