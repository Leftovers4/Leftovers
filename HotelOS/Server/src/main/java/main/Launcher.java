package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import rmi.RemoteHelper;
import sun.management.jmxremote.SingleEntryRegistry;
import thread.AbnormalOrderAutoChanger;

import java.rmi.RemoteException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 2016/11/16.
 */
public class Launcher extends Application{

    @FXML private Button launchBtn;

    private RemoteHelper remoteHelper;

    private static boolean running;



    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/server.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Leftovers服务器");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    private void launchServer() {
        // 将开启按钮设为不可点击
        launchBtn.setDisable(true);

        // 改变服务器状态
        running = true;

        // 启动rmi连接
        (remoteHelper = new RemoteHelper()).run();

        // 开启线程监控订单状态（超时自动置为异常订单）
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new AbnormalOrderAutoChanger());

    }

    @FXML
    private void stopServer() {
        remoteHelper.stop();
        running = false;
        launchBtn.setDisable(false);
//        Platform.exit();

    }


    public static boolean isRunning() {
        return running;
    }





}