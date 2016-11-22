package presentation.hotelworkerui.hotelworkerscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.hotelworkerui.hotelworkercontroller.UpdateCheckInPaneController;

import java.io.IOException;
import java.io.PipedReader;

/**
 * Created by Hitiger on 2016/11/20.
 * Description :
 */
public class UpdateCheckInPane extends Pane {


    public UpdateCheckInPane(Stage primaryStage,Pane mainPane) {
        loadFxml(primaryStage,mainPane);
    }

    private void loadFxml(Stage primaryStage,Pane mainPane) {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/hotelworker/hotelupdatecheckin1.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateCheckInPaneController updateCheckInPaneController = fxmlLoader.getController();
        updateCheckInPaneController.launch(primaryStage,mainPane);
    }
}