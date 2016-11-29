package presentation.webmarketerui.webmarketerscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.webmarketerui.webmarketercontroller.FindOrderPaneController;

import java.io.IOException;

/**
 * Created by Hitiger on 2016/11/21.
 * Description :
 */
public class FindOrderPane extends Pane{

    public FindOrderPane(Stage primaryStage, Pane mainPane) {
        loadFxml(primaryStage,mainPane);
    }

    private void loadFxml(Stage primaryStage,Pane mainPane) {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/webmarketer/webfindorder.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FindOrderPaneController findOrderPaneController = fxmlLoader.getController();
        findOrderPaneController.launch(primaryStage,mainPane);
    }
}