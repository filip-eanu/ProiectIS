package ro.magazin.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ro.magazin.client.model.AuthModel;
import ro.magazin.client.model.ServerException;
import ro.magazin.client.model.SesiuneUtilizator;
import ro.magazin.client.view.ViewManager;
import ro.magazin.common.dto.Rol;
import ro.magazin.common.dto.UtilizatorDTO;

public class LoginController {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtParola;

    private final AuthModel authModel = new AuthModel();

    @FXML
    public void onAutentificare() {
        String email = txtEmail.getText().trim();
        String parola = txtParola.getText();
        if (email.isEmpty() || parola.isEmpty()) {
            ViewManager.getInstance().eroare("Completati emailul si parola.");
            return;
        }
        try {
            UtilizatorDTO u = authModel.autentifica(email, parola);
            SesiuneUtilizator.getInstance().setUtilizator(u);
            // TODO: ViewManager.getInstance().arata(MainClientView.fxml / MainAngajatView.fxml)
            ViewManager.getInstance().info("Autentificat ca " + u.getNume()
                    + (u.getRol() == Rol.ANGAJAT ? " (angajat)" : " (client)"));
        } catch (ServerException e) {
            ViewManager.getInstance().eroare(e.getMessage());
        }
    }

    @FXML
    public void onCreareCont() {
        // TODO: ViewManager.getInstance().arata("InregistrareView.fxml")
        ViewManager.getInstance().info("Ecranul de inregistrare nu este implementat inca.");
    }
}
