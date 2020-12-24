package main;

import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class MyApp extends Application {

	private static Scene mainScene;
	private static Scene internalLinksScene;
	private static Scene externalLinksScene;
	private static Scene phoneNumbersScene;
	private static Scene emailsScene;
	private static Scene socialMediaScene;
	private static Scene colorsScene;
	private static Scene findEmailScene;
	private static Scene findAreaCodeScene;
	private static Scene findEmailInputScene;
	private static Scene findAreaCodeinputScene;
	private static Stage mainStage;
	private static Program program;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;

		program = new Program();
		Program.crawler.start();
		Program.scraper.start();

		mainScene = getMainScene();
		internalLinksScene = getInternalLinksScene();
		externalLinksScene = getExternalLinksScene();
		phoneNumbersScene = getPhoneNumbersScene();
		emailsScene = getEmailsScene();
		socialMediaScene = getSocialMediaScene();
		colorsScene = getColorsScene();
		findEmailScene = getFindEmailScene("");
		findAreaCodeScene = getFindAreaCodeScene("");
		findEmailInputScene = getFindEmailInputScene();
		findAreaCodeinputScene = getFindAreaCodeInputScene();

		primaryStage.setScene(mainScene);
		primaryStage.setTitle("MyApp");
		primaryStage.show();
	}

	private Scene getFindAreaCodeInputScene() {
		Label note = new Label("Please specify");
		TextField inputTextField = new TextField();
		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			mainStage.setScene(getFindAreaCodeScene(inputTextField.getText()));
		});
		GridPane gridPane = new GridPane();
		gridPane.add(note, 0, 0);
		gridPane.add(inputTextField, 0, 1);
		gridPane.add(submit, 0, 2);
		gridPane.setVgap(10);
		gridPane.setHgap(30);
		gridPane.setPadding(new Insets(30));
		return new Scene(gridPane);
	}

	private Scene getFindEmailInputScene() {
		Label note = new Label("Please specify");
		TextField inputTextField = new TextField();
		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			mainStage.setScene(getFindEmailScene(inputTextField.getText()));
		});
		GridPane gridPane = new GridPane();
		gridPane.add(note, 0, 0);
		gridPane.add(inputTextField, 0, 1);
		gridPane.add(submit, 0, 2);
		gridPane.setVgap(10);
		gridPane.setHgap(30);
		gridPane.setPadding(new Insets(30));
		return new Scene(gridPane);
	}

	private Scene getFindAreaCodeScene(String input) {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getPhoneNumbersWithAreaCode(input));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	private Scene getFindEmailScene(String input) {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getSpecifiedEmail(input));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	private Scene getColorsScene() {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getTableContent("Color"));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	private Scene getSocialMediaScene() {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getTableContent("SocialMedia"));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	private Scene getEmailsScene() {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getTableContent("Email"));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	private Scene getPhoneNumbersScene() {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getTableContent("PhoneNumber"));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	private Scene getExternalLinksScene() {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getTableContent("ExternalLink"));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	private Scene getInternalLinksScene() {
		ListView<String> listView = new ListView<>();
		listView.getItems().addAll(program.getTableContent("InternalLink"));
		listView.setPrefSize(500, 350);
		Button backButton = getBackButton();
		VBox box = new VBox(listView, backButton);
		box.setAlignment(Pos.BOTTOM_CENTER);
		return new Scene(box);
	}

	public Scene getMainScene() {
		Label internalLinksLabel = new Label("Internal Links");
		Label externalLinksLabel = new Label("External Links");
		Label phoneLabel = new Label("Phone Numbers");
		Label emailLabel = new Label("Email Adresses");
		Label socialMediaLabel = new Label("Social Media");
		Label colorLabel = new Label("Colors");
		Label findEmailLabel = new Label("Find Email");
		Label findAreaCodeLabel = new Label("Area Code");

		Button internalLinksButton = new Button("View");
		Button externalLinksButton = new Button("View");
		Button phoneButton = new Button("View");
		Button emailButton = new Button("View");
		Button socialMediaButton = new Button("View");
		Button colorButton = new Button("View");
		Button findEmailButton = new Button("View");
		Button findAreaCodeButton = new Button("View");
		Button exitButton = getExitButton();
		Button updateButton = getUpdateButton();

		internalLinksButton.setOnAction(e -> mainStage.setScene(internalLinksScene));
		externalLinksButton.setOnAction(e -> mainStage.setScene(externalLinksScene));
		phoneButton.setOnAction(e -> mainStage.setScene(phoneNumbersScene));
		emailButton.setOnAction(e -> mainStage.setScene(emailsScene));
		socialMediaButton.setOnAction(e -> mainStage.setScene(socialMediaScene));
		colorButton.setOnAction(e -> mainStage.setScene(colorsScene));
		findEmailButton.setOnAction(e -> mainStage.setScene(findEmailInputScene));
		findAreaCodeButton.setOnAction(e -> mainStage.setScene(findAreaCodeinputScene));

		GridPane mainGridPane = new GridPane();

		mainGridPane.add(internalLinksLabel, 0, 0);
		mainGridPane.add(externalLinksLabel, 0, 1);
		mainGridPane.add(phoneLabel, 0, 2);
		mainGridPane.add(emailLabel, 0, 3);
		mainGridPane.add(internalLinksButton, 1, 0);
		mainGridPane.add(externalLinksButton, 1, 1);
		mainGridPane.add(phoneButton, 1, 2);
		mainGridPane.add(emailButton, 1, 3);
		mainGridPane.add(socialMediaLabel, 2, 0);
		mainGridPane.add(colorLabel, 2, 1);
		mainGridPane.add(findEmailLabel, 2, 2);
		mainGridPane.add(findAreaCodeLabel, 2, 3);
		mainGridPane.add(socialMediaButton, 3, 0);
		mainGridPane.add(colorButton, 3, 1);
		mainGridPane.add(findEmailButton, 3, 2);
		mainGridPane.add(findAreaCodeButton, 3, 3);
		mainGridPane.add(exitButton, 4, 1);
		mainGridPane.add(updateButton, 4, 2);

		mainGridPane.setVgap(10);
		mainGridPane.setHgap(30);
		mainGridPane.setPadding(new Insets(30));

		return new Scene(mainGridPane);
	}

	private Button getUpdateButton() {
		Button updateButton = new Button("Update");
		updateButton.setOnAction(e -> update());
		return updateButton;
	}

	public Button getBackButton() {
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> mainStage.setScene(mainScene));
		return backButton;
	}

	public Button getExitButton() {
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> {
			program.endThreads();
			Platform.exit();
		});
		return exitButton;
	}

	public void update() {
		mainScene = getMainScene();
		internalLinksScene = getInternalLinksScene();
		externalLinksScene = getExternalLinksScene();
		phoneNumbersScene = getPhoneNumbersScene();
		emailsScene = getEmailsScene();
		socialMediaScene = getSocialMediaScene();
		colorsScene = getColorsScene();
		findEmailScene = getFindEmailScene("");
		findAreaCodeScene = getFindAreaCodeScene("");
		findEmailInputScene = getFindEmailInputScene();
		findAreaCodeinputScene = getFindAreaCodeInputScene();
	}
}
