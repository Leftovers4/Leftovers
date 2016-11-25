package presentation.userui.userscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.userui.usercontroller.RegisteredHotelController;

import java.io.IOException;

/**
 * Created by wyj on 2016/11/22.
 */
public class RegisteredHotelPane extends Pane {

    public RegisteredHotelPane(Stage primaryStage) {
        loadFxml(primaryStage);
    }

    private void loadFxml(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user/UserRegisteredHotel.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RegisteredHotelController registeredHotelController = fxmlLoader.getController();
        registeredHotelController.launch(primaryStage);
    }
}