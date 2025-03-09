package Frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Hopital extends Application {
    private TextField patientIDField;
    private ComboBox<String> patientInfo;
    private TextField informationField;
    private Text patientInfoText;

    // Trouver le client par son ID

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Portail Hôpital");

        patientInfoText = new Text("Patient Information : \n \n Name : \n \n Age : \n \n " +
                "Gender : \n \n Symptoms : \n \n Priority : \n \n");
        patientIDField = new TextField();
        patientInfo = new ComboBox<>();
        patientInfo.getItems().addAll("ID", "Name", "Age", "Gender", "Symptoms", "Priority level");
        informationField = new TextField();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmit());

        VBox vBox = new VBox(10,
                patientInfoText,
                new Label("Enter Patient ID:"), patientIDField,
                new Label("Which info would you like to modify?"), patientInfo,
                new Label("Enter Value:"), informationField,
                submitButton
        );

        Scene scene = new Scene(vBox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSubmit() {
        String patientID = patientIDField.getText();
        String infoType = patientInfo.getValue();
        String infoValue = informationField.getText();

        if (infoType != null) {
            System.out.printf("Patient ID: %s, Modify: %s to %s%n",
                    patientID, infoType, infoValue);
            // Perform your database modification logic here
        } else {
            System.out.println("Invalid input");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
