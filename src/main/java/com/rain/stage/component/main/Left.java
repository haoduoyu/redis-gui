package com.rain.stage.component.main;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.rain.service.DBService;
import com.rain.service.RedisService;
import com.rain.stage.NewConnectionStage;
import com.rain.stage.component.BaseComponent;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author rain.z
 * @description Left
 * @date 2022/05/09
 */
public class Left implements BaseComponent {
    private final Stage stage;

    private final DBService dbService;
    private final RedisService redisService;

    private long start = 0;
    private ListView<HBox> connectInstanceList = null;

    public Left(Stage stage) {
        this.stage = stage;
        this.dbService = new DBService();
        this.redisService = new RedisService();
    }

    public Parent createContent() {
        VBox vBox = new VBox();
        vBox.setMinWidth(260);
        vBox.setMaxWidth(260);
        vBox.prefHeightProperty().bind(this.stage.heightProperty());
        vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        splitPane.getItems().add(this.createTop(vBox));
        splitPane.getItems().add(this.createBottom(vBox));

        vBox.getChildren().add(splitPane);
        return vBox;
    }

    /**
     * 创建左侧栏上半部分
     * 
     * @return 创建的窗格
     */
    private Parent createTop(VBox vBox) {
        Pane topPane = new Pane();
        topPane.setMaxHeight(30);
        topPane.setMinHeight(30);
        topPane.prefWidthProperty().bind(vBox.widthProperty());

        Button newConnectionBtn = new Button("新建连接");
        newConnectionBtn.setStyle("-fx-text-fill: white;-fx-pref-width: 120;-fx-font-size: 15");
        newConnectionBtn.setBackground(
                new Background(new BackgroundFill(new Color(125 / 255.0, 174 / 255.0, 125 / 255.0, 1), new CornerRadii(4), Insets.EMPTY)));
        newConnectionBtn.setMinHeight(30);
        newConnectionBtn.setOnAction((event) -> {
            // 新建连接弹出框位置在原有窗口中间
            double newConnectionWidth = 800;
            double newConnectionHeight = 300;
            double x = this.stage.getX() + (this.stage.getWidth() - newConnectionWidth) / 2;
            double y = this.stage.getY() + (this.stage.getHeight() - newConnectionHeight) / 2;
            new NewConnectionStage(x, y, newConnectionWidth, newConnectionHeight, this).show();
        });

        topPane.getChildren().add(newConnectionBtn);
        return topPane;
    }

    /**
     * 创建左侧栏下半部分
     * 
     * @return 创建的窗格
     */
    private Parent createBottom(VBox vBox) {
        Pane bottomPane = new Pane();
        bottomPane.prefHeightProperty().bind(vBox.heightProperty());

        connectInstanceList = new ListView<>();
        connectInstanceList.prefHeightProperty().bind(bottomPane.heightProperty());
        connectInstanceList.prefWidthProperty().bind(vBox.widthProperty());
        this.loadConnectData();

        bottomPane.getChildren().add(connectInstanceList);
        return bottomPane;
    }

    private void loadConnectData() {
        List<JSONObject> dataList = this.dbService.getData();

        connectInstanceList.getItems().clear();

        for (int i = 0; i < dataList.size(); i++) {
            JSONObject item = dataList.get(i);

            String displayName = String.format("%s", item.getString("name"));
            // 连接列表的item
            HBox connectItem = new HBox();
            connectItem.prefWidthProperty().bind(connectInstanceList.prefWidthProperty());
            connectItem.setId(String.valueOf(i));
            connectItem.getChildren().add(new Text(displayName));

            connectItem.setOnMouseClicked((event) -> {
                if (System.currentTimeMillis() - start < 200) {
                    HBox temp = (HBox)event.getSource();
                    this.redisService.connectRedis(dataList.get(Integer.parseInt(temp.getId())));
                    return;
                }

                start = System.currentTimeMillis();
            });

            connectInstanceList.getItems().add(connectItem);
        }
    }

    /**
     * 刷新列表
     */
    @Override
    public void refresh() {
        this.loadConnectData();
    }
}
