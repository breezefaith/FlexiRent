package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.constant.PropertyStatus;
import model.constant.PropertyType;
import model.dao.RentalRecordDao;
import model.entity.PremiumSuite;
import model.entity.RentalProperty;
import model.entity.RentalRecord;
import model.exception.CompleteMaintenanceException;
import model.exception.PerformMaintenanceException;
import model.util.DateTime;

public class RentalPropertyDetailController implements Initializable{

	@FXML GridPane gridPaneButtons;
	@FXML Label labelStreetName;
	@FXML Button buttonRent;
	@FXML TextArea textAreaDescription;
	@FXML ImageView imageViewMain;
	@FXML TableView<RentalRecord> tableViewRecords;
	@FXML TableColumn<RentalRecord,String> tableColumnId;
	@FXML TableColumn<RentalRecord,String> tableColumnCustomer;
	@FXML TableColumn<RentalRecord,String> tableColumnRentDate;
	@FXML TableColumn<RentalRecord,String> tableColumnEstimatedReturnDate;
	@FXML TableColumn<RentalRecord,String> tableColumnActualReturnDate;
	@FXML TableColumn<RentalRecord,Double> tableColumnRentalFee;
	@FXML TableColumn<RentalRecord,Double> tableColumnLateFee;
	@FXML Button buttonReturn;
	@FXML Button buttonMaintenance;
	@FXML Button buttonComplete;
	
	PropertyItemController propertyItemController;

	RentalProperty rentalProperty;
	List<RentalRecord> rentalRecords;
	ObservableList<RentalRecord> observableListRecords;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttonRent.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setButtonRentAction();
			}
		});
		
		buttonReturn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setButtonReturnAction();
			}
		});
		
		buttonMaintenance.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				setButtonMaintenanceAction(rentalProperty);
			}
		});
		
		buttonComplete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setButtonCompleteAction(rentalProperty);
			}
		});
	}

	public void initial(RentalProperty rentalProperty) {
		this.rentalProperty=rentalProperty;
		this.rentalRecords=RentalRecordDao.getAllRecordByProperty(rentalProperty);

		labelStreetName.setText(rentalProperty.getStreetName());
		textAreaDescription.setText(rentalProperty.getStreetNum()+" "+rentalProperty.getStreetName()+" "+rentalProperty.getSuburb()+"\n\n"+rentalProperty.getDescription());
		imageViewMain.setImage(new Image("file://../images/"+rentalProperty.getImageName()));
		observableListRecords=FXCollections.observableArrayList(rentalRecords);

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("recordId"));
		tableColumnCustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		tableColumnRentDate.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
		tableColumnEstimatedReturnDate.setCellValueFactory(new PropertyValueFactory<>("estimatedReturnDate"));
		tableColumnActualReturnDate.setCellValueFactory(new PropertyValueFactory<>("actualReturnDate"));
		tableColumnRentalFee.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
		tableColumnLateFee.setCellValueFactory(new PropertyValueFactory<>("lateFee"));

		tableViewRecords.setItems(observableListRecords);
		
		setButtonIsDisable(rentalProperty);
	}
	
	private void setButtonRentAction() {
		URL location=getClass().getResource("/view/BookRentalPropertyItem.fxml");
		FXMLLoader fxmlLoader=new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		try {
			VBox vbox = fxmlLoader.load();
			BookRentalPropertyItemController bookRentalPropertyItemController=fxmlLoader.getController();
			bookRentalPropertyItemController.bookRentalProperty(rentalProperty);
			
			Stage stage=new Stage();
			bookRentalPropertyItemController.setStage(stage);
			stage.setTitle("Book");
			stage.setScene(new Scene(vbox));
			stage.setResizable(false);
			stage.show();
			bookRentalPropertyItemController.setRentalPropertyDetailController(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setButtonReturnAction() {
		URL location=getClass().getResource("/view/BookRentalPropertyItem.fxml");
		FXMLLoader fxmlLoader=new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		try {
			VBox vbox = fxmlLoader.load();
			BookRentalPropertyItemController bookRentalPropertyItemController=fxmlLoader.getController();
			bookRentalPropertyItemController.returnRentalProperty(rentalProperty);
			bookRentalPropertyItemController.setRentalPropertyDetailController(this);
			Stage stage=new Stage();
			bookRentalPropertyItemController.setStage(stage);
			stage.setTitle("Return");
			stage.setScene(new Scene(vbox));
			stage.setResizable(false);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void setButtonCompleteAction(RentalProperty rentalProperty) {
		try {
			((PremiumSuite)rentalProperty).completeMaintenance(new DateTime(new DateTime().getTime()));
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("success");
			alert.setContentText("complete maintenance");
			alert.showAndWait();
			
		} catch (CompleteMaintenanceException|PerformMaintenanceException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(e.getMessage());
			alert.setContentText(e.toString());
			alert.showAndWait();
		}finally {
			propertyItemController.refresh();
		}
	}
	
	protected void setButtonMaintenanceAction(RentalProperty rentalProperty2) {
		try {
			((PremiumSuite)rentalProperty).performMaintenance();
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("success");
			alert.setContentText("perform maintenance success");
			alert.showAndWait();	
		} catch (PerformMaintenanceException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(e.getMessage());
			alert.setContentText(e.toString());
			alert.showAndWait();
		}finally {
			propertyItemController.refresh();
		}
	}
	
	
	public void setPropertyItemController(PropertyItemController propertyItemController) {
		this.propertyItemController = propertyItemController;
	}
	
	public void refresh() {
		this.rentalRecords=RentalRecordDao.getAllRecordByProperty(rentalProperty);
		observableListRecords.clear();
		observableListRecords.addAll(rentalRecords);
		setButtonIsDisable(rentalProperty);
		propertyItemController.refresh();
	}
	
	private void setButtonIsDisable(RentalProperty rentalProperty) {
		if(rentalProperty.getStatus().equals(PropertyStatus.AVAILABLE)) {
			buttonRent.setDisable(false);
		}else {
			buttonRent.setDisable(true);
		}
		if(rentalProperty.getStatus().equals(PropertyStatus.BEING_RENTED)) {
			buttonReturn.setDisable(false);
		}else {
			buttonReturn.setDisable(true);
		}
		
		if(rentalProperty.getPropertyType().equals(PropertyType.PREMIUM_SUITE)) {
			if(rentalProperty.getStatus().equals(PropertyStatus.PremiumSuiteStatus.UNDER_MAINTENANCE)){
				buttonMaintenance.setDisable(true);
				buttonComplete.setDisable(false);
			}else if(rentalProperty.getStatus().equals(PropertyStatus.PremiumSuiteStatus.BEING_RENTED)){
				buttonMaintenance.setDisable(true);
				buttonComplete.setDisable(true);
			}else if(rentalProperty.getStatus().equals(PropertyStatus.PremiumSuiteStatus.AVAILABLE)) {
				buttonMaintenance.setDisable(false);
				buttonComplete.setDisable(true);
			}
		}else {
			buttonMaintenance.setVisible(false);
			buttonComplete.setVisible(false);
		}
	}
}
