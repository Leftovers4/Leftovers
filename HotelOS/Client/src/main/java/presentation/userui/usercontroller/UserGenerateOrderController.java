package presentation.userui.usercontroller;

import bl.hotelbl.HotelBLService;
import bl.hotelbl.impl.HotelBlServiceImpl;
import bl.hotelbl.impl.Room;
import bl.orderbl.OrderBLService;
import bl.orderbl.impl.Order;
import bl.orderbl.impl.OrderBlServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.userui.userscene.OrderDetailUserPane;
import presentation.userui.userscene.UserOrderListPane;
import presentation.util.alert.AlertController;
import presentation.util.other.CancelDateBefore;
import presentation.util.alert.InputWrongAlert;
import presentation.util.other.JudgeInput;
import util.EnumFactory;
import util.ResultMessage;
import util.RoomType;
import vo.hotel.HotelVO;
import vo.hotel.RoomVO;
import vo.order.OrderVO;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

/**
 * Created by wyj on 2016/11/24.
 * description: 生成订单
 */
public class UserGenerateOrderController {

    private Stage stage;
    private Pane mainPane;
    private String userID;
    private long hotelID;
    private RoomType choseRoom;

    @FXML private DatePicker checkInDatePicker;
    @FXML private DatePicker checkOutDatePicker;
    @FXML private ComboBox checkInHour;
    @FXML private ComboBox checkInMin;
    @FXML private ComboBox checkOutHour;
    @FXML private ComboBox checkOutMin;
    @FXML private ComboBox roomType;
    @FXML private ComboBox roomNum;
    @FXML private RadioButton childHave;
    @FXML private RadioButton childNone;
    @FXML private ComboBox peopleNum;

    @FXML private Label writeOrder;
    @FXML private Label confirmPromotion;
    @FXML private Label submit;
    @FXML private Button confirmBtn;
    @FXML private Button backToEdit;
    @FXML private Button nextBtn;

    @FXML private Label checkInTimeLabel;
    @FXML private Label checkOutTime;
    @FXML private Label roomTypeLabel;
    @FXML private Label roomNumLabel;
    @FXML private Label peopleNumLabel;
    @FXML private Label child;

    @FXML private Label hotelnameLabel;
    @FXML private Label hoteladdressLabel;
    @FXML private Label hotelserviceLabel;

    @FXML private Label priceLabel;
    @FXML private Label promotionLabel;

    @FXML private ImageView hotelphoto;

    private OrderBLService orderBlService;
    private HotelBLService hotelBlService;

    private AlertController alertController;

    private HotelVO hotelVO;

    private OrderVO newOrder;

    public void launch(Stage primaryStage, Pane mainPane, String userID, long hotelID, RoomType roomType) {
        this.stage = primaryStage;
        this.mainPane = mainPane;
        this.userID = userID;
        this.hotelID = hotelID;
        this.choseRoom = roomType;

        writeOrder.setStyle("-fx-text-fill: deepskyblue");

        alertController = new AlertController();

        try {
            orderBlService = new OrderBlServiceImpl();
            hotelBlService = new HotelBlServiceImpl();

            initial();
            initPhoto();
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }


    }

    private void initPhoto() {
        String newpath = "C:/Leftovers/client/user/hotelImg/";

        try {

            HotelVO hotelVO = hotelBlService.viewBasicHotelInfo(hotelID);

            if (hotelVO != null) {
                if (hotelVO.image != null) {
                    String path = newpath + hotelID + ".jpg";
                    File file = new File(path);

                    if (file.exists()) {
                        Image image = new Image("file:///"+path);
                        hotelphoto.setImage(image);
                    }
                }
            }
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
    }

    private void initial() {

        if (choseRoom != null) {
            roomType.setValue(EnumFactory.getString(choseRoom));
        }

        for (int i = 0; i<24; i++) {
            if (i<10) {
                checkInHour.getItems().add("0" +i);
                checkOutHour.getItems().add("0" +i);
            } else {
                checkInHour.getItems().add(i);
                checkOutHour.getItems().add(i);
            }
        }
        for (int i = 0; i<60; i++) {
            if (i<10) {
                checkInMin.getItems().add("0" + i);
                checkOutMin.getItems().add("0" + i);
            } else {
                checkInMin.getItems().add(i);
                checkOutMin.getItems().add(i);
            }
        }

        List<RoomVO> roomVO;
        try {
            roomVO = hotelBlService.viewAllHotelRooms(hotelID);

            for (int i = 0; i<roomVO.size(); i++) {
                roomType.getItems().add(EnumFactory.getString(roomVO.get(i).roomType));
            }


            hotelVO = hotelBlService.viewDetailedHotelInfo(hotelID, userID);

            hotelnameLabel.setText(hotelVO.hotelName);
            hoteladdressLabel.setText(hotelVO.address + hotelVO.tradingArea);
            hotelserviceLabel.setText(hotelVO.service);

            for (int i = 0; i<10; i++) {
                peopleNum.getItems().add(i+1);
            }
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }


        checkInDatePicker.setDayCellFactory(new CancelDateBefore(checkInDatePicker, LocalDate.now()));
        checkInDatePicker.setOnAction(event -> {
            checkOutDatePicker.setDayCellFactory(new CancelDateBefore(checkOutDatePicker, checkInDatePicker.getValue()));
        });

    }


    @FXML
    private void actionInitRoomNum() {
        try {
            List<RoomVO> roomVOList = hotelBlService.viewAllHotelRooms(hotelID);

            if (roomType.getValue() != null) {


                if (judgedateblank()) {

                    LocalDateTime checkin = LocalDateTime.of(checkInDatePicker.getValue(),
                            LocalTime.of(Integer.parseInt(checkInHour.getValue().toString()),
                                    Integer.parseInt(checkInMin.getValue().toString())));
                    LocalDateTime checkout = LocalDateTime.of(checkOutDatePicker.getValue(),
                            LocalTime.of(Integer.parseInt(checkOutHour.getValue().toString()),
                                    Integer.parseInt(checkOutMin.getValue().toString())));


                    for (int i = 0; i<roomVOList.size(); i++) {
                        if (EnumFactory.getString(roomVOList.get(i).roomType) == roomType.getValue()) {
                            RoomVO roomVO = hotelBlService.viewFullRoomInfo(roomVOList.get(i).roomID, checkin, checkout);
                            roomNum.getItems().clear();
                            for (int j = 0; j<roomVO.bookable; j++) {
                                roomNum.getItems().add(j+1);
                            }
                        }
                    }
                } else {
                    alertController.showInputWrongAlert("请先选择入住时间", "错误提示");
                }
            } else {
                alertController.showInputWrongAlert("请先选择房间类型", "错误提示");
            }
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
    }

    private boolean judgedateblank() {
        boolean checkindate = checkInDatePicker.getValue() != null;
        boolean checkinhour = checkInHour.getValue() != null;
        boolean checkinmin = checkInMin.getValue() != null;

        boolean checkoutdate = checkOutDatePicker.getValue() != null;
        boolean checkouthour = checkOutHour.getValue() != null;
        boolean checkoutmin = checkOutMin.getValue()!= null;

        return checkindate && checkinhour && checkinmin && checkoutdate && checkouthour && checkoutmin;
    }


    /**
     * 下一步，确认促销优惠
     */
    @FXML
    private void nextBtnEvent() {
        if (!judgeBlank()) {
            OrderVO orderVO = new OrderVO();

            orderVO.hotelID = hotelID;
            orderVO.username = userID;
            orderVO.orderTimeVO.expectedCheckinTime = LocalDateTime.of(checkInDatePicker.getValue(),
                    LocalTime.of(Integer.parseInt(checkInHour.getValue().toString()),
                            Integer.parseInt(checkInMin.getValue().toString())));
            orderVO.orderTimeVO.expectedLeaveTime = LocalDateTime.of(checkOutDatePicker.getValue(),
                    LocalTime.of(Integer.parseInt(checkOutHour.getValue().toString()),
                            Integer.parseInt(checkOutMin.getValue().toString())));

//            boolean isdetaok = JudgeInput.judgeDateSeq(orderVO.orderTimeVO.expectedCheckinTime,
//                    orderVO.orderTimeVO.expectedLeaveTime);

            orderVO.roomType = (RoomType) EnumFactory.getEnum(roomType.getValue().toString());
            orderVO.roomAmount = (int) (roomNum.getValue());
            orderVO.personAmount = (int) (peopleNum.getValue());
            orderVO.withChildren = childHave.isSelected();
            List<RoomVO> roomVOList = hotelVO.rooms;
            Period gap = Period.between(checkInDatePicker.getValue(), checkOutDatePicker.getValue());
            System.out.println(gap.getDays()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if (!checkInDatePicker.getValue().toString().equals(checkOutDatePicker.getValue().toString())) {
                for (int i = 0; i<roomVOList.size(); i++) {
                    if (roomVOList.get(i).roomType == orderVO.roomType) {
                        orderVO.orderPriceVO.originPrice = roomVOList.get(i).price * orderVO.roomAmount * gap.getDays();
                    }
                }
            } else {
                for (int i = 0; i<roomVOList.size(); i++) {
                    if (roomVOList.get(i).roomType == orderVO.roomType) {
                        orderVO.orderPriceVO.originPrice = roomVOList.get(i).price * orderVO.roomAmount;
                    }
                }
            }

            newOrder = orderVO;

            if (orderVO.orderTimeVO.expectedLeaveTime.isAfter(orderVO.orderTimeVO.expectedCheckinTime)) {
                try {
                    double price = orderBlService.getOrderActualPrice(orderVO);

                    newOrder.orderPriceVO.actualPrice = price;

                    System.out.println(price+"---------------------------------------");

                    priceLabel.setText(String.valueOf(Math.round(price)));
                    if (orderVO.orderPromoInfoVO.promotionType == null) {
                        promotionLabel.setText("无");
                    } else {
                        promotionLabel.setText(EnumFactory.getString(orderVO.orderPromoInfoVO.promotionType));
                    }
                } catch (RemoteException e) {
                    alertController.showNetConnectAlert();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                alertController.showInputWrongAlert("开始日期需要小于结束日期，请重新输入","格式错误");
                return;
            }

            checkInTimeLabel.setText(checkInDatePicker.getValue().toString() + "  " +
                    checkInHour.getValue().toString() + ":" + checkInMin.getValue().toString());
            checkOutTime.setText(checkOutDatePicker.getValue().toString() + "  " +
                    checkOutHour.getValue().toString() + ":" + checkOutMin.getValue().toString());
            roomTypeLabel.setText(roomType.getValue().toString());
            roomNumLabel.setText(roomNum.getValue().toString());
            peopleNumLabel.setText(peopleNum.getValue().toString());
            if (childHave.isSelected()) {
                child.setText("有");
            } else {
                child.setText("无");
            }


            checkInDatePicker.setVisible(false);
            checkOutDatePicker.setVisible(false);
            checkInHour.setVisible(false);
            checkInMin.setVisible(false);
            checkOutHour.setVisible(false);
            checkOutMin.setVisible(false);
            roomType.setVisible(false);
            roomNum.setVisible(false);
            childHave.setVisible(false);
            childNone.setVisible(false);
            peopleNum.setVisible(false);
            writeOrder.setStyle("-fx-text-fill: #757575");
            confirmPromotion.setStyle("-fx-text-fill: deepskyblue");
            confirmBtn.setVisible(true);
            backToEdit.setVisible(true);
            nextBtn.setVisible(false);

            checkInTimeLabel.setVisible(true);
            checkOutTime.setVisible(true);
            roomTypeLabel.setVisible(true);
            roomNumLabel.setVisible(true);
            peopleNumLabel.setVisible(true);
            child.setVisible(true);

        } else {
            alertController.showInputWrongAlert("信息填写不完整", "警告");
        }


    }

    /**
     * 判断是否有内容未填写
     * @return
     */
    private boolean judgeBlank() {
        boolean checkindate = checkInDatePicker.getValue() == null;
        boolean checkinhour = checkInHour.getValue() == null;
        boolean checkinmin = checkInMin.getValue() == null;
        boolean checkoutdate = checkOutDatePicker.getValue() == null;
        boolean checkouthour = checkOutHour.getValue() == null;
        boolean checkoutmin = checkOutMin.getValue() == null;
        boolean roomtype = roomType.getValue() == null;
        boolean roomnum = roomNum.getValue() == null;
        boolean peoplenum = peopleNum.getValue() == null;
        boolean childhave = childHave.isSelected();
        boolean childhavenot = childNone.isSelected();

        boolean isChildSelected;
        if (!childhave && !childhavenot) {
            isChildSelected = false;
        } else {
            isChildSelected = true;
        }
        return checkindate || checkinhour || checkinmin || checkoutdate || roomnum || peoplenum
                || checkouthour || checkoutmin || roomtype || !isChildSelected;
    }

    /**
     * 返回按钮事件，返回到填写订单界面
     */
    @FXML
    private void backToEdit() {
        checkInDatePicker.setVisible(true);
        checkOutDatePicker.setVisible(true);
        checkInHour.setVisible(true);
        checkInMin.setVisible(true);
        checkOutHour.setVisible(true);
        checkOutMin.setVisible(true);
        roomType.setVisible(true);
        roomNum.setVisible(true);
        childHave.setVisible(true);
        childNone.setVisible(true);
        peopleNum.setVisible(true);
        confirmPromotion.setStyle("-fx-text-fill: black");
        writeOrder.setStyle("-fx-text-fill: deepskyblue");
        confirmBtn.setVisible(false);
        backToEdit.setVisible(true);
        nextBtn.setVisible(true);

        promotionLabel.setText("无");
        priceLabel.setText("0");

        checkInTimeLabel.setVisible(false);
        checkOutTime.setVisible(false);
        roomTypeLabel.setVisible(false);
        roomNumLabel.setVisible(false);
        peopleNumLabel.setVisible(false);
        child.setVisible(false);
        backToEdit.setVisible(false);
    }

    /**
     * 提交并生成订单
     */
    @FXML
    private void submitBtnEvent() {
        submit.setStyle("-fx-text-fill: deepskyblue");
        confirmPromotion.setStyle("-fx-text-fill: black");
        backToEdit.setVisible(false);

        try {
            ResultMessage resultMessage = orderBlService.addOrder(newOrder);

            if (resultMessage == ResultMessage.Success) {
                System.out.println("new order");
                alertController.showUpdateSuccessAlert("订单已生成！", "成功提示");
                mainPane.getChildren().clear();
                mainPane.getChildren().add(new UserOrderListPane(stage, mainPane, userID, true));
            } else if (resultMessage == ResultMessage.CreditNotEnough){
                alertController.showInputWrongAlert("订单生成失败，您的信用值不足", "错误提示");
                System.out.println(resultMessage);
            } else {
                alertController.showInputWrongAlert("订单生成失败", "错误提示");
                System.out.println(resultMessage);
            }
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
    }

    @FXML
    private void back(){
        mainPane.getChildren().remove(mainPane.getChildren().size() - 1);
    }
}
