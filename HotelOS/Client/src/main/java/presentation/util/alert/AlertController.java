package presentation.util.alert;

import javafx.scene.Group;
import javafx.scene.control.ButtonType;
import presentation.loginui.logincontroller.LoginSceneController;
import presentation.loginui.loginscene.LoginScene;

/**
 * Created by Hitiger on 2016/11/26.
 * Description : 各种提示框的控制器，供界面调用
 */
public class AlertController {

    /**
     * 确认取消编辑信息提示框
     * @return
     */
    public Boolean showConfirmCancelAlert(){
        ConfirmAlert confirmAlert = new ConfirmAlert("您确定要取消编辑信息吗？","确认取消");
        confirmAlert.showAndWait();
        final ButtonType rtn = confirmAlert.getResult();
        if (rtn == ButtonType.OK) {
            return true;
        }
        return false;
    }

    /**
     * 确认退出系统提示框
     * @return
     */
    public Boolean showConfirmExitAlert(){
        ConfirmAlert confirmAlert = new ConfirmAlert("您确定要退出系统吗？","确认退出");
        confirmAlert.showAndWait();
        final ButtonType rtn = confirmAlert.getResult();
        if (rtn == ButtonType.OK) {
            return true;
        }
        return false;
    }

    /**
     * 没有选择提示框
     * @param contentText 提示内容
     * @param title       提示标题
     */
    public void showUnSelectItemAlert(String contentText, String title) {
        UnselectedAlert unselectedAlert = new UnselectedAlert(contentText,title);
        unselectedAlert.showAndWait();
    }

    /**
     * 确认删除提示框
     * @param contentText 提示内容
     * @param title       提示标题
     * @return
     */
    public Boolean showConfirmDeleteAlert(String contentText, String title){
        ConfirmAlert confirmAlert = new ConfirmAlert(contentText,title);
        confirmAlert.showAndWait();
        final ButtonType rtn = confirmAlert.getResult();
        if (rtn == ButtonType.OK) {
            return true;
        }
        return false;
    }

    /**
     * 输入错误提示框
     * @param contentText 提示内容
     * @param title       提示标题
     */
    public void showInputWrongAlert(String contentText, String title){
        InputWrongAlert inputWrongAlert = new InputWrongAlert(contentText,title);
        inputWrongAlert.showAndWait();
    }

    /**
     * 服务器连接异常
     */
    public void showNetConnectAlert(){
        RemoteAlert remoteAlert = new RemoteAlert("与服务器连接异常","连接失败");
        remoteAlert.showAndWait();

        final ButtonType rtn = remoteAlert.getResult();
        if (rtn == ButtonType.OK) {
            LoginSceneController.stage.setScene(new LoginScene(new Group(),LoginSceneController.stage));
        }

    }

    /**
     * 查询结果为空提示框
     * @param contentText 提示内容
     * @param title       提示标题
     */
    public void showNullWrongAlert(String contentText,String title){
        NullWrongAlert nullWrongAlert = new NullWrongAlert(contentText,title);
        nullWrongAlert.showAndWait();
    }

    public Boolean showUpdateSuccessAlert(String contentText,String title){
        UpdateSuccessAlert updateSuccessAlert = new UpdateSuccessAlert(contentText, title);
        updateSuccessAlert.showAndWait();
        final ButtonType rtn = updateSuccessAlert.getResult();
        if (rtn == ButtonType.OK) {
            return true;
        }else return false;
    }
}
