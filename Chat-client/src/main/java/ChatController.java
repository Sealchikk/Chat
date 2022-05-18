
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChatController implements Initializable {

    @FXML
    private VBox mainPanel;

    @FXML
    private TextArea chatArea;

    @FXML
    private ListView<String> contacts;

    @FXML
    private TextField inputField;

    @FXML
    private Button btnSend;

    public void mockAction(ActionEvent actionEvent) {
        System.out.println("mock");
    }

    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void sendMessage(ActionEvent actionEvent) {
        MultipleSelectionModel <String> contact = contacts.getSelectionModel();
        String text = inputField.getText();
        if (text == null || text.isBlank()) {
            return;
        }
        if (contact.getSelectedItem() != null && !contact.getSelectedItem().equals("Select All")) {
            chatArea.appendText(contact.getSelectedItems() + ": " + text + System.lineSeparator());
            inputField.clear();
        } else {
            chatArea.appendText( "[Broadcast]: " + text + System.lineSeparator());
            inputField.clear();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> names = List.of("Vasya", "Masha", "Petya", "Valera", "Nastya", "Select All");
        contacts.setItems(FXCollections.observableList(names));
    }

    public void openHelp(ActionEvent actionEvent) {
        Alert helpWindow = new Alert(Alert.AlertType.INFORMATION);
        helpWindow.setTitle("Instruction");
        helpWindow.setHeaderText(null);
        helpWindow.setContentText("Hello! Figure it out yourself.");
        helpWindow.showAndWait();
    }
}