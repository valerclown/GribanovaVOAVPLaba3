package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    private TextArea htmlTextArea;

    @FXML
    private ChoiceBox<String> fileChoiceBox;

    private static final String HTML_FILES_PATH = "C:\\Users\\grib_\\IdeaProjects\\demo\\src\\main\\java\\com\\example\\demo\\htmlfiles";

    @FXML
    public void initialize() {
        try {
            List<String> htmlFileNames = getHtmlFileNames();
            fileChoiceBox.getItems().addAll(htmlFileNames);
            fileChoiceBox.setValue("Выберете файл");  // Начальный текст в выпадающем списке
            htmlTextArea.setText("Для начала работы необходимо выбрать файл в списке сверху и нажать на кнопку Загрузить.\nПосле этого отредактируйте выбранный html файл и нажмите на кнопку Сохранить.");

        } catch (IOException e) {
            e.printStackTrace();
            // Обработайте исключение в соответствии с вашими требованиями
        }
    }

    @FXML
    public void saveHtml() {
        try {
            String htmlContent = htmlTextArea.getText();
            String selectedFileName = fileChoiceBox.getValue();

            if ("Выберете файл".equals(selectedFileName)) {
                showWarningDialog("Пожалуйста, выберите файл перед сохранением.");
                return;
            }

            Path filePath = Paths.get(HTML_FILES_PATH, selectedFileName);
            Files.writeString(filePath, htmlContent);

            showConfirmationDialog("Изменения загружены");
        } catch (IOException e) {
            e.printStackTrace();
            // Обработайте исключение в соответствии с вашими требованиями
        }
    }

    @FXML
    public void loadHtml() {
        try {
            loadSelectedHtml();
        } catch (IOException e) {
            e.printStackTrace();
            // Обработайте исключение в соответствии с вашими требованиями
        }
    }

    private void loadSelectedHtml() throws IOException {
        String selectedFileName = fileChoiceBox.getValue();

        if ("Выберете файл".equals(selectedFileName)) {
            showWarningDialog("Пожалуйста, выберите файл перед загрузкой.");
            return;
        }

        Path filePath = Paths.get(HTML_FILES_PATH, selectedFileName);
        String htmlContent = Files.readString(filePath);
        htmlTextArea.setText(htmlContent);
    }

    private List<String> getHtmlFileNames() throws IOException {
        return Files.list(Paths.get(HTML_FILES_PATH))
                .filter(path -> path.toString().endsWith(".html"))
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    private void showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Информация");
        alert.showAndWait();
    }

    private void showWarningDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Предупреждение");
        alert.showAndWait();
    }
}
