package com.rain.stage.component.main;

import com.alibaba.fastjson.JSONObject;
import com.rain.constant.ConnectionMenuItemId;
import com.rain.constant.MouseButtonType;
import com.rain.service.LeftService;
import com.rain.stage.NewConnectionStage;
import com.rain.stage.component.BaseComponent;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author rain.z
 * @description Left
 * @date 2022/05/09
 */
public class Left implements BaseComponent {
    private final Stage stage;
    private final Right right;
    private final LeftService leftService;
    private long start = 0;
    private TreeView<HBox> connectInstanceList = null;

    public Left(Stage stage, Right right) {
        this.stage = stage;
        this.right = right;
        this.leftService = new LeftService();
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

        connectInstanceList = new TreeView<>();
        connectInstanceList.setShowRoot(false);
        connectInstanceList.prefHeightProperty().bind(bottomPane.heightProperty());
        connectInstanceList.prefWidthProperty().bind(vBox.widthProperty());
        this.loadConnectData();

        bottomPane.getChildren().add(connectInstanceList);
        return bottomPane;
    }

    private void loadConnectData() {
        List<JSONObject> dataList = this.leftService.getData();
        TreeItem<HBox> rootItem = new TreeItem<>();

        ConnectionContentMenu contextMenu = ConnectionContentMenu.getInstance();
        for (int i = 0; i < dataList.size(); i++) {
            JSONObject item = dataList.get(i);

            String displayName = String.format("%s", item.getString("name"));
            // 连接列表的item
            HBox connectItem = new HBox();
            TreeItem<HBox> rowItem = new TreeItem<>(connectItem);
            connectItem.prefWidthProperty().bind(connectInstanceList.prefWidthProperty());
            connectItem.setId(String.valueOf(i));
            connectItem.getChildren().add(new Text(displayName));

            connectItem.setOnMouseClicked((event) -> {

                // 响应右键效果
                if (MouseButtonType.SECONDARY.equalsIgnoreCase(event.getButton().name())) {
                    HBox temp = (HBox) event.getSource();
                    Point3D intersectedPoint = event.getPickResult().getIntersectedPoint();

                    // 相当于点击弹出菜单的回调
                    Consumer<ActionEvent> consumer = (data) -> {
                        MenuItem selectMenuItem = (MenuItem) data.getTarget();
                        String selectMenuItemId = selectMenuItem.getId();
                        // 下面的判断有待优化
                        String id = temp.getId(); // 这个id是数据的下标
                        if (ConnectionMenuItemId.CONNECTION_MENU_ITEM.equals(selectMenuItemId)) {
                            JSONObject jsonObject = dataList.get(Integer.parseInt(id));
                            if (this.leftService.connectRedis(jsonObject)) {
                                this.right.addTab(jsonObject);
                            }
                        } else if (ConnectionMenuItemId.DELETE_MENU_ITEM.equals(selectMenuItemId)) {
                            // TODO 这个弹框很丑，还是抽时间弄一个自定义的吧
                            Alert alert = new Alert(Alert.AlertType.WARNING, "删除后不可恢复，确认删除嘛?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait().ifPresent((selectButtonType) -> {
                                if (selectButtonType.getText().equalsIgnoreCase("yes")) {
                                    // TODO 最好再来一个关闭redis操作，或者提示，我觉得直接关闭就好，因为前面有个提示
                                    this.leftService.deleteData(dataList.get(Integer.parseInt(id)));
                                    this.refresh();
                                }
                            });
                        }
                    };

                    contextMenu.showContextMenu(temp, Side.BOTTOM, intersectedPoint.getX(), intersectedPoint.getY(), consumer);
                    return;
                }

                // 响应双击动作
                if (System.currentTimeMillis() - start < 200) {
                    HBox temp = (HBox) event.getSource();
                    String id = temp.getId(); // 这个id是数据的下标
                    JSONObject jsonObject = dataList.get(Integer.parseInt(id)); // object里面的id很重要
                    if (this.leftService.connectRedis(jsonObject)) {
                        this.afterConnectSuccess(jsonObject);
                    }
                    return;
                }

                start = System.currentTimeMillis();
            });

            rootItem.getChildren().add(rowItem);
        }
        connectInstanceList.setRoot(rootItem);
    }

    /**
     * 连接成功后
     * 1、添加右侧tab；2、显示左侧keys列表；
     *
     * @param jsonObject 需要传递给右侧的数据
     */
    private void afterConnectSuccess(JSONObject jsonObject) {
        this.right.addTab(jsonObject);
        // TODO 这里默认是db0，后续应该在左侧列表处增加db切换操作
        Set<String> allKeys = this.leftService.getAllKeys(jsonObject);
        System.out.println(allKeys);
    }

    /**
     * 刷新列表
     */
    @Override
    public void refresh() {
        this.loadConnectData();
    }
}
