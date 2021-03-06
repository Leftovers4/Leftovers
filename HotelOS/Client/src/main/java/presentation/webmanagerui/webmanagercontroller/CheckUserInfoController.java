package presentation.webmanagerui.webmanagercontroller;

import bl.userbl.UserBLService;
import bl.userbl.impl.UserBlServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import presentation.util.alert.AlertController;
import util.MemberType;
import vo.hotel.HotelVO;
import vo.user.UserVO;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import static presentation.hotelworkerui.hotelworkercontroller.ComWorkerSceneController.hotelID;

/**
 * Created by wyj on 2016/12/12.
 * description: 网站管理人员查看客户信息
 */
public class CheckUserInfoController {

    private Pane mainPane;
    private String userID;

    @FXML private Label vipCompany;
    @FXML private Label vipLevel;
    @FXML private Label userIdLabel;
    @FXML private Label usernameLabel;
    @FXML private Label userSex;
    @FXML private Label birthDate;
    @FXML private Label phone;
    @FXML private Label creditLabel;
    @FXML private ImageView userPhoto;

    private UserBLService userBlService;

    private AlertController alertController;

    public void launch(Pane mainPane, String userID) {
        this.mainPane = mainPane;
        this.userID = userID;

        alertController = new AlertController();

        try {
            userBlService = new UserBlServiceImpl();

            initialData();
            initialPhoto();
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }


    }


    private void initialPhoto() {
        String pathuser = "C:/Leftovers/client/webmanager/userImg/";

        try {
            UserVO userVO = userBlService.viewBasicUserInfo(userID);

            if (userVO.image != null) {
                String path = pathuser + userID + ".jpg";
                File file = new File(pathuser);

                if (file.exists()) {
                    Image image = new Image("file:///" + path);
                    userPhoto.setImage(image);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }


    }

    private void initialData() {
        try {
            UserVO userVO = userBlService.viewBasicUserInfo(userID);

            userIdLabel.setText(userVO.username);
            usernameLabel.setText(userVO.name);

            if (userVO.gender) {
                userSex.setText("男");
            } else {
                userSex.setText("女");
            }
            birthDate.setText(userVO.memberVO.birthday.toString());
            phone.setText(userVO.phone);
            creditLabel.setText(String.valueOf(userVO.creditVO.credit));

            if (userVO.memberVO.memberType == MemberType.NormalMember) {
                vipLevel.setText(String.valueOf(userVO.memberVO.level));
                vipCompany.setText("无");
            } else if (userVO.memberVO.memberType == MemberType.EnterpriseMember) {
                vipCompany.setText(userVO.memberVO.enterprise);
                vipLevel.setText("无");
            } else if (userVO.memberVO.memberType == MemberType.Both) {
                vipLevel.setText(String.valueOf(userVO.memberVO.level));
                vipCompany.setText(userVO.memberVO.enterprise);
            } else {
                vipCompany.setText("无");
                vipLevel.setText("无");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
    }
}
