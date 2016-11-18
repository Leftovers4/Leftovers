package presentation.hotelworkerui.hotelworkercontroller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.hotelworkerui.hotelworkerscene.hotelinfopane.InfoPane;

/**
 * Created by Hitiger on 2016/11/18.
 * Description : 酒店主界面控制器，负责基本信息、浏览订单、录入可用客房界面的跳转
 */
public class CommonSceneController {


    @FXML private Pane mainPane;


    public void launch(Stage primaryStage){
        mainPane.getChildren().add(new InfoPane(primaryStage));
    }

}
