package ro.magazin.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

/**
 * Incarca ecranele FXML si afiseaza mesajele catre utilizator.
 */
public final class ViewManager {

    private static ViewManager instance;

    private final Stage stage;

    private ViewManager(Stage stage) {
        this.stage = stage;
    }

    public static void init(Stage stage) {
        instance = new ViewManager(stage);
    }

    public static ViewManager getInstance() {
        return instance;
    }

    /** Inlocuieste ecranul curent si intoarce controller-ul noului ecran. */
    public <T> T arata(String fxml) {
        FXMLLoader loader = loader(fxml);
        Parent root = incarca(loader);
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.sizeToScene();
        return loader.getController();
    }

    /** Deschide un ecran intr-o fereastra modala; controller-ul poate fi configurat inainte de afisare. */
    public <T> T deschideDialog(String fxml, String titlu) {
        FXMLLoader loader = loader(fxml);
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle(titlu);
        dialog.setScene(new Scene(incarca(loader)));
        dialog.show();
        return loader.getController();
    }

    public void eroare(String mesaj) {
        new Alert(Alert.AlertType.ERROR, mesaj).showAndWait();
    }

    public void info(String mesaj) {
        new Alert(Alert.AlertType.INFORMATION, mesaj).showAndWait();
    }

    public boolean confirma(String mesaj) {
        Optional<ButtonType> r = new Alert(Alert.AlertType.CONFIRMATION, mesaj).showAndWait();
        return r.isPresent() && r.get() == ButtonType.OK;
    }

    public Stage getStage() {
        return stage;
    }

    private FXMLLoader loader(String fxml) {
        return new FXMLLoader(ViewManager.class.getResource(fxml));
    }

    private Parent incarca(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Ecranul nu a putut fi incarcat: " + loader.getLocation(), e);
        }
    }
}
