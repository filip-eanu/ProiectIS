package ro.magazin.client.app;

import javafx.application.Application;
import javafx.stage.Stage;
import ro.magazin.client.network.ServerConnection;
import ro.magazin.client.view.ViewManager;

import java.io.IOException;

public class ClientApp extends Application {

    public static final String HOST = System.getProperty("server.host", "localhost");
    public static final int PORT = Integer.getInteger("server.port", 5000);

    @Override
    public void start(Stage stage) {
        ViewManager.init(stage);
        ViewManager.getInstance().arata("LoginView.fxml");
        stage.setTitle("Magazin de calculatoare");
        stage.show();

        try {
            ServerConnection.getInstance().conecteaza(HOST, PORT);
        } catch (IOException e) {
            ViewManager.getInstance().eroare("Serverul nu este disponibil (" + HOST + ":" + PORT + ").");
        }
    }

    @Override
    public void stop() {
        ServerConnection.getInstance().deconecteaza();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
