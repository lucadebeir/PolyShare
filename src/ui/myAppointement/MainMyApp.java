package ui.myAppointement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMyApp extends Application {

	private Stage stage;
	private AnchorPane loginLayout;
	// private AnchorPane anchorPane;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.stage = primaryStage;
		initSkillLayout();
	}

	private void initSkillLayout() throws IOException {

		try {
			FXMLLoader root = new FXMLLoader(getClass().getResource("MyAppointements.fxml"));
			Parent skillLayout = root.load();
			Scene scene = new Scene(skillLayout, 700, 600);

			stage.setTitle("MySkills");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}