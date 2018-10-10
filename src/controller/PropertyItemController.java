package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.constant.PropertyStatus;
import model.entity.RentalProperty;

public class PropertyItemController implements Initializable{
	
	@FXML TextArea textAreaDescription;
	@FXML Label labelNew;
	@FXML Label labelStreetName;
	@FXML Button buttonInfo;
	@FXML Button buttonRent;
	@FXML ImageView imageViewMain;
	@FXML GridPane gridPaneButtons;
	
	private RentalProperty rentalProperty;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		buttonInfo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setButtonInfoAction();
				
			}
		});
		
		buttonRent.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				setButtonRentAction();
			}
		});
	}
	
	protected void setButtonInfoAction() {
		URL location=getClass().getResource("/view/RentalPropertyDetail.fxml");
		FXMLLoader fxmlLoader=new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		try {
			VBox vbox = fxmlLoader.load();
			RentalPropertyDetailController rentalPropertyDetailController=fxmlLoader.getController();
			rentalPropertyDetailController.initial(rentalProperty);
			
			Stage stage=new Stage();
			stage.setTitle("Detail");
			stage.setScene(new Scene(vbox));
			stage.setResizable(false);
			stage.show();
			
			rentalPropertyDetailController.setPropertyItemController(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	protected void setButtonRentAction() {
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
			bookRentalPropertyItemController.setPropertyItemController(this);
			stage.setTitle("Book");
			stage.setScene(new Scene(vbox));
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void initial(RentalProperty rentalProperty) {
		if(rentalProperty==null) {
			return;
		}
		this.rentalProperty=rentalProperty;
		
		labelNew.setVisible(false);
		labelStreetName.setText(rentalProperty.getStreetName());
		textAreaDescription.setText(rentalProperty.getStreetNum()+" "+rentalProperty.getStreetName()+" "+rentalProperty.getSuburb()+"\n\n"+rentalProperty.getDescription());
		imageViewMain.setImage(new Image("file://../images/"+rentalProperty.getImageName()));
		setButtonIsDisable(rentalProperty);
	}

	private void setButtonIsDisable(RentalProperty rentalProperty) {
		if(rentalProperty.getStatus().equals(PropertyStatus.AVAILABLE)) {
			buttonRent.setDisable(false);
		}else {
			buttonRent.setDisable(true);
		}
	}
	
	public void refresh() {
		setButtonIsDisable(rentalProperty);
	}

}
