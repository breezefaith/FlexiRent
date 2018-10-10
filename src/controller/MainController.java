package controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.constant.PropertyStatus;
import model.dao.ApartmentDao;
import model.dao.PremiumSuiteDao;
import model.dao.RentalPropertyDao;
import model.dao.RentalRecordDao;
import model.entity.Apartment;
import model.entity.PremiumSuite;
import model.entity.RentalProperty;
import model.entity.RentalRecord;
import model.util.DateTime;

public class MainController implements Initializable {

	@FXML
	ScrollPane scrollPanePropertyList;

	@FXML
	TilePane tilePanePropertyItemList;

	@FXML
	MenuItem menuItemExport;

	@FXML
	MenuItem menuItemImport;

	@FXML
	MenuItem menuItemQuit;

	@FXML
	RadioButton radioButtonAll;

	@FXML
	RadioButton radioButtonApartment;

	@FXML
	RadioButton radioButtonPremiumSuite;

	@FXML
	ChoiceBox<String> choiceBoxBedroomNumber;

	@FXML
	ChoiceBox<String> choiceBoxStatus;

	@FXML
	TextField textFieldSuburb;

	final ToggleGroup group = new ToggleGroup();

	Stage stage;
	
	List<RentalProperty> rentalProperties;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rentalProperties = RentalPropertyDao.getAllRentalProperty();

		radioButtonAll.setUserData("(1=1)");
		radioButtonAll.setToggleGroup(group);
		radioButtonApartment.setUserData("(property_type='apartment')");
		radioButtonApartment.setToggleGroup(group);
		radioButtonPremiumSuite.setUserData("(property_type='premium_suite')");
		radioButtonPremiumSuite.setToggleGroup(group);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (group.getSelectedToggle() != null) {
					String bedroomsNumberCondition = "(bedrooms_number='" + choiceBoxBedroomNumber.getValue() + "')";
					if (choiceBoxBedroomNumber.getValue().equals("All")) {
						bedroomsNumberCondition = "(1=1)";
					}
					String statusCondition = "(property_status='" + choiceBoxStatus.getValue() + "')";
					if (choiceBoxStatus.getValue().equals("All")) {
						statusCondition = "(1=1)";
					}

					String propertyTypeCondition = (String) group.getSelectedToggle().getUserData();

					String suburbCondition = "(suburb like '%" + textFieldSuburb.getText() + "%')";
					if (textFieldSuburb.getText() == null || textFieldSuburb.getText().trim().equals("")) {
						suburbCondition = "(1=1)";
					}

					rentalProperties = RentalPropertyDao
							.getAllRentalPropertyWithConditon(propertyTypeCondition + " and " + bedroomsNumberCondition
									+ " and " + statusCondition + " and " + suburbCondition);
					initial(stage);
					System.out.println(propertyTypeCondition + " and " + bedroomsNumberCondition + " and "
							+ statusCondition + " and " + suburbCondition);
				}
			}
		});

		choiceBoxBedroomNumber.setItems(FXCollections.observableArrayList("All", "1", "2", "3"));
		choiceBoxBedroomNumber.setValue("All");
		choiceBoxBedroomNumber.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String propertyTypeCondition = group.getSelectedToggle().getUserData().toString();

				String bedroomsNumberCondition = newValue.equals("All") ? "(1=1)"
						: "(bedrooms_number='" + newValue + "')";

				String statusCondition = "(property_status='" + choiceBoxStatus.getValue() + "')";
				if (choiceBoxStatus.getValue().equals("All")) {
					statusCondition = "(1=1)";
				}

				String suburbCondition = "(suburb like '%" + textFieldSuburb.getText() + "%')";
				if (textFieldSuburb.getText() == null || textFieldSuburb.getText().trim().equals("")) {
					suburbCondition = "(1=1)";
				}
				rentalProperties = RentalPropertyDao.getAllRentalPropertyWithConditon(propertyTypeCondition + " and "
						+ bedroomsNumberCondition + " and " + statusCondition + " and " + suburbCondition);
				initial(stage);
//				System.out.println(propertyTypeCondition + " and " + bedroomsNumberCondition + " and " + statusCondition
//						+ " and " + suburbCondition);
			}
		});

		choiceBoxStatus.setItems(FXCollections.observableArrayList("All", PropertyStatus.AVAILABLE,
				PropertyStatus.BEING_RENTED, PropertyStatus.UNDER_MAINTENANCE));
		choiceBoxStatus.setValue("All");
		choiceBoxStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String bedroomsNumberCondition = "(bedrooms_number='" + choiceBoxBedroomNumber.getValue() + "')";
				if (choiceBoxBedroomNumber.getValue().equals("All")) {
					bedroomsNumberCondition = "(1=1)";
				}

				String propertyTypeCondition = group.getSelectedToggle().getUserData().toString();

				String statusCondition = "(property_status='" + newValue + "')";
				if (newValue.equals("All")) {
					statusCondition = "(1=1)";
				}

				String suburbCondition = "(suburb like '%" + textFieldSuburb.getText() + "%')";
				if (textFieldSuburb.getText() == null || textFieldSuburb.getText().trim().equals("")) {
					suburbCondition = "(1=1)";
				}

				rentalProperties = RentalPropertyDao.getAllRentalPropertyWithConditon(propertyTypeCondition + " and "
						+ bedroomsNumberCondition + " and " + statusCondition + " and " + suburbCondition);
				initial(stage);

//				System.out.println(bedroomsNumberCondition + " and " + propertyTypeCondition + " and " + statusCondition
//						+ " and " + suburbCondition);
			}
		});

//		textFieldSuburb.setOnKeyReleased(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event event) {
//				String propertyTypeCondition = group.getSelectedToggle().getUserData().toString();
//
//				String bedroomsNumberCondition = "(bedrooms_number='" + choiceBoxBedroomNumber.getValue() + "')";
//				if (choiceBoxBedroomNumber.getValue().equals("All")) {
//					bedroomsNumberCondition = "(1=1)";
//				}
//
//				String statusCondition = "(property_status='" + choiceBoxStatus.getValue() + "')";
//				if (choiceBoxStatus.getValue().equals("All")) {
//					statusCondition = "(1=1)";
//				}
//
//				String suburbCondition = "(suburb like '%" + textFieldSuburb.getText() + "%')";
//				if (textFieldSuburb.getText() == null || textFieldSuburb.getText().trim().equals("")) {
//					suburbCondition = "(1=1)";
//				}
//				
//				rentalProperties=RentalPropertyDao.getAllRentalPropertyWithConditon(propertyTypeCondition + " and " + bedroomsNumberCondition + " and "+ statusCondition + " and " + suburbCondition);
//				initial();
//				
//				System.out.println(propertyTypeCondition + " and " + bedroomsNumberCondition + " and " + statusCondition
//						+ " and " + suburbCondition);
//			}
//		});
		textFieldSuburb.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String propertyTypeCondition = group.getSelectedToggle().getUserData().toString();

				String bedroomsNumberCondition = "(bedrooms_number='" + choiceBoxBedroomNumber.getValue() + "')";
				if (choiceBoxBedroomNumber.getValue().equals("All")) {
					bedroomsNumberCondition = "(1=1)";
				}

				String statusCondition = "(property_status='" + choiceBoxStatus.getValue() + "')";
				if (choiceBoxStatus.getValue().equals("All")) {
					statusCondition = "(1=1)";
				}

				String suburbCondition = "(suburb like '%" + newValue + "%')";
				if (textFieldSuburb.getText() == null || newValue.trim().equals("")) {
					suburbCondition = "(1=1)";
				}

				rentalProperties = RentalPropertyDao.getAllRentalPropertyWithConditon(propertyTypeCondition + " and "
						+ bedroomsNumberCondition + " and " + statusCondition + " and " + suburbCondition);
				initial(stage);

//				System.out.println(propertyTypeCondition + " and " + bedroomsNumberCondition + " and " + statusCondition
//						+ " and " + suburbCondition);

			}
		});
		menuItemImport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setImportAction();
			}
		});
		menuItemExport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setExportAction();
			}
		});
		menuItemQuit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
	}

	protected void setExportAction() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File("./"));
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.fr)", "*.fr");
			fileChooser.getExtensionFilters().add(extFilter);
			Stage s = new Stage();
			File file = fileChooser.showSaveDialog(s);
			if (file == null)
				return;
			if (file.exists()) {
				file.delete();
			}
			// String exportFilePath = file.getAbsolutePath();
			// System.out.println("export path:" + exportFilePath);
			BufferedOutputStream outputStream = null;
			List<Apartment> apartments = ApartmentDao.getAllApartment();
			List<PremiumSuite> premiumSuites = PremiumSuiteDao.getAllPremiumSuite();
			List<RentalProperty> rentalProperties = RentalPropertyDao.getAllRentalPropertyForExport();
			List<RentalRecord> rentalRecords = RentalRecordDao.getAllRecord();
			outputStream = new BufferedOutputStream(new FileOutputStream(file));

			outputStream.write("#apartment#\n".getBytes("UTF-8"));
			for (Apartment apartment : apartments) {
				String temp = apartment.getPropertyId() + "::" + apartment.getPerDayFee() + "\n";
				outputStream.write(temp.getBytes("UTF-8"));
			}
			outputStream.write("#apartment#\n".getBytes("UTF-8"));

			outputStream.write("#premium_suite#\n".getBytes("UTF-8"));
			for (PremiumSuite premiumSuite : premiumSuites) {
				String temp = premiumSuite.getPropertyId() + "::" + premiumSuite.getLastMaintenance().toString() + "::"
						+ premiumSuite.getPerDayFee() + "\n";
				outputStream.write(temp.getBytes("UTF-8"));
			}
			outputStream.write("#premium_suite#\n".getBytes("UTF-8"));

			outputStream.write("#rental_property#\n".getBytes("UTF-8"));
			for (RentalProperty rentalProperty : rentalProperties) {
				String temp = rentalProperty.getPropertyId() + "::" + rentalProperty.getStreetNum() + "::"
						+ rentalProperty.getStreetName() + "::" + rentalProperty.getSuburb() + "::"
						+ rentalProperty.getBedroomsNum() + "::" + rentalProperty.getPropertyType() + "::"
						+ rentalProperty.getStatus() + "::" + rentalProperty.getImageName() + "::"
						+ rentalProperty.getDescription() + "::" + rentalProperty.getCreateTime() + "\n";
				outputStream.write(temp.getBytes("UTF-8"));
			}
			outputStream.write("#rental_property#\n".getBytes("UTF-8"));

			outputStream.write("#rental_record#\n".getBytes("UTF-8"));
			for (RentalRecord rentalRecord : rentalRecords) {
				String temp = rentalRecord.getRecordId() + "::";
				temp = temp + rentalRecord.getRentalProperty().getPropertyId() + "::";
				temp = temp + rentalRecord.getCustomerId() + "::" + rentalRecord.getRentDate().toString() + "::";
				temp = temp + rentalRecord.getEstimatedReturnDate().toString() + "::";
				temp = temp + (rentalRecord.getActualReturnDate() == null ? "none"
						: rentalRecord.getActualReturnDate().toString()) + "::";
				temp = temp + rentalRecord.getRentalFee() + "::" + rentalRecord.getLateFee() + "\n";
				outputStream.write(temp.getBytes("UTF-8"));
			}
			outputStream.write("#rental_record#\n".getBytes("UTF-8"));

			outputStream.flush();
			outputStream.close();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("export success");

			alert.showAndWait();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warnning");
			alert.setHeaderText(e.toString());
			alert.setContentText(e.getMessage());

			alert.showAndWait();
		}
	}

	protected void setImportAction() {
		BufferedReader bufferedReader = null;
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File("./"));

			ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.fr)", "*.fr");
			fileChooser.getExtensionFilters().add(extensionFilter);
			Stage s = new Stage();
			File file = fileChooser.showOpenDialog(s);
			if (file == null) {
				throw new Exception("file is null");
			}
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			List<Apartment> apartments = new ArrayList<>();
			List<PremiumSuite> premiumSuites = new ArrayList<>();
			List<RentalProperty> rentalProperties = new ArrayList<>();
			List<RentalRecord> rentalRecords = new ArrayList<>();
			String temp = null;
			if ((temp = bufferedReader.readLine()) != null && temp.equals("#apartment#")) {
				while ((temp = bufferedReader.readLine()) != null && !temp.equals("#apartment#")) {
					String[] properties = temp.split("::");
					Apartment apartment = new Apartment();
					apartment.setPropertyId(properties[0]);
					apartment.setPerDayFee(Double.valueOf(properties[1]));

					apartments.add(apartment);
				}
			}

			if ((temp = bufferedReader.readLine()) != null && temp.equals("#premium_suite#")) {
				while ((temp = bufferedReader.readLine()) != null && !temp.equals("#premium_suite#")) {
					String[] properties = temp.split("::");
					PremiumSuite premiumSuite = new PremiumSuite();
					premiumSuite.setPropertyId(properties[0]);
					premiumSuite.setLastMaintenance(new DateTime(properties[1]));
					premiumSuite.setPerDayFee(Double.valueOf(properties[2]));

					premiumSuites.add(premiumSuite);
				}
			}

			if ((temp = bufferedReader.readLine()) != null && temp.equals("#rental_property#")) {
				while ((temp = bufferedReader.readLine()) != null && !temp.equals("#rental_property#")) {
					String[] properties = temp.split("::");
					RentalProperty rentalProperty = new RentalProperty();
					rentalProperty.setPropertyId(properties[0]);
					rentalProperty.setStreetNum(properties[1]);
					rentalProperty.setStreetName(properties[2]);
					rentalProperty.setSuburb(properties[3]);
					rentalProperty.setBedroomsNum(Integer.valueOf(properties[4]));
					rentalProperty.setPropertyType(properties[5]);
					rentalProperty.setStatus(properties[6]);
					rentalProperty.setImageName(properties[7]);
					rentalProperty.setDescription(properties[8]);
					rentalProperty.setCreateTime(properties[9]);

					rentalProperties.add(rentalProperty);
				}
			}

			if ((temp = bufferedReader.readLine()) != null && temp.equals("#rental_record#")) {
				while ((temp = bufferedReader.readLine()) != null && !temp.equals("#rental_record#")) {
					String[] properties = temp.split("::");
					RentalRecord rentalRecord = new RentalRecord();

					rentalRecord.setRecordId(properties[0]);
					RentalProperty rentalProperty = new RentalProperty();
					rentalProperty.setPropertyId(properties[1]);
					rentalRecord.setRentalProperty(rentalProperty);
					rentalRecord.setCustomerId(properties[2]);
					rentalRecord.setRentDate(new DateTime(properties[3]));
					rentalRecord.setEstimatedReturnDate(new DateTime(properties[4]));
					rentalRecord.setActualReturnDate(properties[5].equals("none") ? null : new DateTime(properties[5]));
					rentalRecord.setRentalFee(Double.valueOf(properties[6]));
					rentalRecord.setLateFee(Double.valueOf(properties[7]));

					rentalRecords.add(rentalRecord);
				}
			}

			ApartmentDao.saveAll(apartments);
			PremiumSuiteDao.saveAll(premiumSuites);
			RentalPropertyDao.saveAll(rentalProperties);
			RentalRecordDao.saveAll(rentalRecords);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("import success");

			alert.showAndWait();
			this.initial(stage);

		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warnning");
			alert.setHeaderText(e.toString());
			alert.setContentText(e.getMessage());

			alert.showAndWait();
		}
	}

	public void initial(Stage stage) {
		this.stage=stage;
		tilePanePropertyItemList.getChildren().clear();
		if (rentalProperties == null) {
			return;
		}
		for (int i = 0; i < rentalProperties.size(); i++) {
			RentalProperty rentalProperty = rentalProperties.get(i);
			URL location = getClass().getResource("/view/RentalPropertyItem.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			try {
				GridPane gridPaneRentalPropertyItem = fxmlLoader.load();
				PropertyItemController propertyItemController = fxmlLoader.getController();
				propertyItemController.initial(rentalProperty);
				if (i == 0) {
					propertyItemController.labelNew.setVisible(true);
				}
				tilePanePropertyItemList.getChildren().add(gridPaneRentalPropertyItem);
				TilePane.setMargin(gridPaneRentalPropertyItem, new Insets(10));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
