package com.rain.stage;

import com.alibaba.fastjson.JSONObject;
import com.rain.service.DBService;
import com.rain.stage.component.BaseComponent;
import com.rain.stage.component.main.Left;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.StringUtils;

/**
 * @author rain.z
 * @description NewConnectionStage
 * @date 2022/05/08
 */
public class NewConnectionStage extends Stage {
    private final static double DEFAULT_WIDTH = 800;
    private final static double DEFAULT_HEIGHT = 600;

    private final DBService dbService;
    private BaseComponent invoker = null;
    private double screenWidth;
    private double screenHeight;

    private double initX;
    private double intY;

    /**
     * stage 中最外围的面板
     */
    GridPane gridPane = null;
    private final Label addressLabel = new Label("地址");
    private TextField addressTF = null;
    private final Label portLabel = new Label("端口");
    private TextField portTF = null;
    private final Label passwordLabel = new Label("密码");
    private PasswordField passwordF = null;
    private final Label usernameLabel = new Label("用户名");
    private TextField usernameTF = null;
    private final Label connectNameLabel = new Label("连接名称");
    private TextField connectNameTF = null;
    private final Label separatorLabel = new Label("分隔符");
    private TextField separatorTF = null;

    public NewConnectionStage(double x, double y) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public NewConnectionStage(double x, double y, double screenWidth, double screenHeight) {
        this(x, y, screenWidth, screenHeight, null);
    }

    public NewConnectionStage(double x, double y, double screenWidth, double screenHeight, BaseComponent invoker) {
        this.initX = x;
        this.intY = y;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.invoker = invoker;
        this.dbService = new DBService();

        initStage();
    }

    private void initStage() {
        this.setScreenSize();
        Scene scene = new Scene(createContent());
        this.setScene(scene);
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);
    }

    private void setScreenSize() {
        setX(this.initX);
        setY(this.intY);
        setWidth(this.screenWidth);
        setHeight(this.screenHeight);
        setResizable(false);
    }

    private Parent createContent() {
        gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Node topLabel = this.createTopLabel();
        Node baseInfoPane = this.createBaseInfoPane();
        Node optionPane = this.createOptionPane();
        Node operatePane = this.createOperatePane();

        gridPane.add(topLabel, 0, 0);
        gridPane.add(baseInfoPane, 0, 1);
        gridPane.add(optionPane, 0, 2);
        gridPane.add(operatePane, 0, 3);

        return gridPane;
    }

    private Node createTopLabel() {
        Label label = new Label("新建连接");
        label.setFont(Font.font(20));
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(50);
        label.setPrefWidth(150);
        return label;
    }

    /**
     * 创建基本信息区域 包含用户名、密码、地址、端口等
     *
     * @return 创建的面板
     */
    private Node createBaseInfoPane() {
        GridPane baseInfoPane = new GridPane();
        baseInfoPane.setAlignment(Pos.CENTER);
        baseInfoPane.prefWidthProperty().bind(widthProperty());
        // baseInfoPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY,
        // Insets.EMPTY)));

        addressTF = new TextField();
        addressTF.setPromptText("127.0.0.1");
        VBox addressBox = this.getCommonVBox(baseInfoPane, addressLabel, addressTF);
        baseInfoPane.add(addressBox, 0, 0);

        portTF = new TextField();
        portTF.setText("6379");
        VBox portBox = this.getCommonVBox(baseInfoPane, portLabel, portTF);
        baseInfoPane.add(portBox, 1, 0);

        passwordF = new PasswordField();
        VBox passwordBox = this.getCommonVBox(baseInfoPane, passwordLabel, passwordF);
        baseInfoPane.add(passwordBox, 0, 1);

        usernameTF = new TextField();
        VBox usernameBox = this.getCommonVBox(baseInfoPane, usernameLabel, usernameTF);
        baseInfoPane.add(usernameBox, 1, 1);

        connectNameTF = new TextField();
        VBox connectNameBox = this.getCommonVBox(baseInfoPane, connectNameLabel, connectNameTF);
        baseInfoPane.add(connectNameBox, 0, 2);

        separatorTF = new TextField();
        VBox separatorBox = this.getCommonVBox(baseInfoPane, separatorLabel, separatorTF);
        baseInfoPane.add(separatorBox, 1, 2);

        return baseInfoPane;
    }

    /**
     * 创建基础输入信息
     *
     * @param parent 父级元素
     * @param node 待添加至vbox中的元素
     * @return vbox
     */
    private VBox getCommonVBox(GridPane parent, Node... node) {
        VBox vBox = new VBox();
        vBox.prefWidthProperty().bind(parent.prefWidthProperty().divide(2.1));
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(node);
        return vBox;
    }

    private Node createOptionPane() {
        Pane pane = new Pane();

        CheckBox sshCheckBox = new CheckBox("SSH");
        sshCheckBox.setLayoutX(50);

        CheckBox sslCheckBox = new CheckBox("SSL");
        sslCheckBox.setLayoutX(150);

        CheckBox sentinelCheckBox = new CheckBox("Sentinel");
        sentinelCheckBox.setLayoutX(250);

        CheckBox clusterCheckBox = new CheckBox("Cluster");
        clusterCheckBox.setLayoutX(380);

        CheckBox readOnlyCheckBox = new CheckBox("ReadOnly");
        readOnlyCheckBox.setLayoutX(500);

        pane.getChildren().addAll(sshCheckBox, sslCheckBox, sentinelCheckBox, clusterCheckBox, readOnlyCheckBox);
        return pane;
    }

    private Node createOperatePane() {
        Pane pane = new Pane();

        Button cancelBtn = new Button("取消");
        cancelBtn.setStyle("-fx-border-color: gray; -fx-border-radius: 10px");
        cancelBtn.setLayoutX(650);
        cancelBtn.setPrefWidth(60);
        cancelBtn.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 1), new CornerRadii(10), Insets.EMPTY)));

        cancelBtn.setOnAction(event -> {
            close();
        });

        Button submitBtn = new Button("确定");
        submitBtn.setStyle("-fx-text-fill: white");
        submitBtn.setLayoutX(720);
        submitBtn.setPrefWidth(60);
        submitBtn.setBackground(
                new Background(new BackgroundFill(new Color(88 / 255.0, 158 / 255.0, 248 / 255.0, 1), new CornerRadii(10), Insets.EMPTY)));

        submitBtn.setOnAction(event -> {

            if (!checkInputContent()) {
                new Alert(Alert.AlertType.WARNING, "请填写必要信息", ButtonType.OK).show();
                return;
            }
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("address", addressTF.getText());
            jsonObject.put("port", portTF.getText());
            jsonObject.put("password", passwordF.getText());
            jsonObject.put("username", usernameTF.getText());
            jsonObject.put("connectName", connectNameTF.getText());
            jsonObject.put("separator", separatorTF.getText());

            dbService.saveData(jsonObject);

            if (null != invoker) {
                Left invokerObj = (Left) invoker;
                invokerObj.refresh();
            }
            close();
        });

        pane.getChildren().addAll(cancelBtn, submitBtn);
        return pane;
    }

    private boolean checkInputContent() {
        // TODO 应根据复选框、不同类型（ip、端口、字符串等）等进行校验
        return !StringUtils.isAnyBlank(addressTF.getText(), portTF.getText(), passwordF.getText(),
            usernameTF.getText(), connectNameTF.getText(), separatorTF.getText());
    }
}
