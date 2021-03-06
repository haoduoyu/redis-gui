package com.rain.stage.component.main.sub;

import com.alibaba.fastjson.JSONObject;
import com.rain.entity.AllRedisInfo;
import com.rain.entity.RedisKVStatistics;
import com.rain.service.RedisService;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.Function;

/**
 * @author rain.z
 * @description MainRedisInfoPane
 * @date 2022/05/14
 */
public class MainRedisInfoPane extends BasePane {

    private JSONObject data;
    private final VBox dashboard = new VBox();
    private final HBox firstRow = new HBox(10);
    private final VBox secondRow = new VBox(10);
    private final VBox thirdRow = new VBox(10);

    private final Color ROW_BACKGROUND_COLOR = new Color(245 / 250.0, 245 / 250.0, 245 / 250.0, 1);
    private final Color LABEL_BACKGROUND_COLOR = new Color(244 / 250.0, 244 / 250.0, 245 / 250.0, 1);
    private final Background LABEL_BACKGROUND = new Background(new BackgroundFill(LABEL_BACKGROUND_COLOR, new CornerRadii(2), Insets.EMPTY));
    private final Border LABEL_BORDER = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderWidths.DEFAULT));
    private final Insets ROW_PADDING_INSETS = new Insets(10, 10, 10, 10);
    private final BorderWidths ROW_BORDER_WIDTHS = new BorderWidths(2, 2, 2, 2,
        false, false, false, false);
    private final Border BOX_BORDER = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(10), ROW_BORDER_WIDTHS));
    private final Background BOX_BORDER_BACKGROUND = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY));

    public MainRedisInfoPane(JSONObject data) {
        this.data = data;
        this.init();
        this.createPane();
    }

    private void init() {
        dashboard.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        widthProperty().addListener(event -> {
            ReadOnlyDoubleProperty s = (ReadOnlyDoubleProperty) event;
            firstRow.setPrefWidth(s.getValue());
        });
        heightProperty().addListener(event -> {
            ReadOnlyDoubleProperty s = (ReadOnlyDoubleProperty) event;
            dashboard.setPrefHeight(s.getValue());
        });

        firstRow.setTranslateY(50);
        firstRow.setPadding(ROW_PADDING_INSETS);
        firstRow.setBackground(new Background(new BackgroundFill(ROW_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        secondRow.setPadding(ROW_PADDING_INSETS);
        secondRow.setTranslateY(60);
        secondRow.setBackground(new Background(new BackgroundFill(ROW_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        secondRow.setBorder(BOX_BORDER);

        thirdRow.setPadding(ROW_PADDING_INSETS);
        thirdRow.setTranslateY(60);
        thirdRow.setBackground(new Background(new BackgroundFill(ROW_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        thirdRow.setBorder(BOX_BORDER);
    }

    @Override
    public void createPane() {
        this.createFistRow();
        this.createSecondRow();
        this.createThirdRow();
        getChildren().add(dashboard);
    }

    /**
     * ???????????????????????????
     */
    private void createFistRow() {

        // ?????????????????????????????????
        Function<JSONObject, VBox> createVBox = (srcData) -> {
            VBox box = new VBox(20);
            box.prefWidthProperty().bind(widthProperty().divide(3.15));
            box.setPrefHeight(200);
            box.setPadding(new Insets(10));
            box.setBackground(BOX_BORDER_BACKGROUND);
            box.setBorder(BOX_BORDER);
            return box;
        };

        // ?????????????????????
        VBox serverVBox = createVBox.apply(null);

        Label serverLabel = new Label("?????????");
        Separator serverSeparator = new Separator(Orientation.HORIZONTAL);
        serverSeparator.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Label versionLabel = this.createLabelForFirstRow(serverVBox, "Redis ??????");
        Label osLabel = this.createLabelForFirstRow(serverVBox, "OS");
        Label processLabel = this.createLabelForFirstRow(serverVBox, "??????");

        serverVBox.getChildren().addAll(serverLabel, serverSeparator, versionLabel, osLabel, processLabel);
        firstRow.getChildren().add(serverVBox);
        // ?????????????????????

        // ??????????????????
        VBox memoryVBox = createVBox.apply(null);

        Label memoryLabel = new Label("??????");
        Separator memorySeparator = new Separator(Orientation.HORIZONTAL); // -----------------
        memorySeparator.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Label usedMemoryLabel = this.createLabelForFirstRow(serverVBox, "????????????");
        Label memoryPeakLabel = this.createLabelForFirstRow(serverVBox, "??????????????????");
        Label luaMemConsumptionLabel = this.createLabelForFirstRow(serverVBox, "Lua????????????");

        memoryVBox.getChildren().addAll(memoryLabel, memorySeparator, usedMemoryLabel, memoryPeakLabel, luaMemConsumptionLabel);
        firstRow.getChildren().add(memoryVBox);
        // ??????????????????

        // ??????????????????
        VBox statusVBox = createVBox.apply(null);

        Label statusLabel = new Label("??????");
        Separator statusSeparator = new Separator(Orientation.HORIZONTAL);
        statusSeparator.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Label clientConnectCountLabel = this.createLabelForFirstRow(serverVBox, "???????????????");
        Label historyConnectCountLabel = this.createLabelForFirstRow(serverVBox, "???????????????");
        Label historyCommandCountLabel = this.createLabelForFirstRow(serverVBox, "???????????????");

        statusVBox.getChildren().addAll(statusLabel, statusSeparator, clientConnectCountLabel, historyConnectCountLabel, historyCommandCountLabel);
        firstRow.getChildren().add(statusVBox);
        // ??????????????????
        dashboard.getChildren().add(firstRow);
    }

    /**
     * ???????????????????????????
     */
    private void createSecondRow() {
        VBox keyStatisticsBox = new VBox();
        keyStatisticsBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Label kvStatistics = new Label("????????????");
        kvStatistics.setAlignment(Pos.CENTER_LEFT);
        kvStatistics.setPrefHeight(30);
        kvStatistics.setFont(Font.font(15));
        kvStatistics.prefWidthProperty().bind(widthProperty());

        Separator kyStatisticsSeparator = new Separator(Orientation.HORIZONTAL); // -----------------
        kyStatisticsSeparator.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        TableView<RedisKVStatistics> tableView = this.createKVStatisticsView();

        keyStatisticsBox.getChildren().addAll(kvStatistics, kyStatisticsSeparator, tableView);
        secondRow.getChildren().add(keyStatisticsBox);
        dashboard.getChildren().add(secondRow);
    }

    /**
     * ???????????????????????????
     */
    private void createThirdRow() {
        VBox allRedisInfoBox = new VBox();
        allRedisInfoBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Label allRedis = new Label("Redis????????????");
        allRedis.setAlignment(Pos.CENTER_LEFT);
        allRedis.setPrefHeight(30);
        allRedis.setFont(Font.font(15));
        allRedis.prefWidthProperty().bind(widthProperty());

        Separator allRedisSeparator = new Separator(Orientation.HORIZONTAL); // -----------------
        allRedisSeparator.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // ????????????????????? db ??????????????????????????? 'info keyspace' ??????
        TableView<AllRedisInfo> allRedisInfoTableView = this.createAllRedisInfoView();

        allRedisInfoBox.getChildren().addAll(allRedis, allRedisSeparator, allRedisInfoTableView);
        thirdRow.getChildren().add(allRedisInfoBox);
        dashboard.getChildren().add(thirdRow);
    }

    /**
     * ??????label??????
     *
     * @param parentContainer label??????????????????
     * @param labelText       ???????????????
     * @return
     */
    private Label createLabelForFirstRow(Pane parentContainer, String labelText) {
        Label newLabel = new Label(labelText);
        newLabel.setBackground(LABEL_BACKGROUND);
        newLabel.setBorder(LABEL_BORDER);
        newLabel.setPrefHeight(20);
        newLabel.setPadding(new Insets(5));
        newLabel.prefWidthProperty().bind(parentContainer.prefWidthProperty());
        return newLabel;
    }

    /**
     * ??????????????????
     * ????????????????????? db ??????????????????????????? 'info keyspace' ??????
     *
     * @return ??????
     */
    private TableView<RedisKVStatistics> createKVStatisticsView() {
        TableView<RedisKVStatistics> tableView = new TableView<>();
        tableView.prefHeightProperty().bind(dashboard.prefHeightProperty().multiply(0.33));
        tableView.prefWidthProperty().bind(widthProperty());

        Function<JSONObject, TableColumn<RedisKVStatistics, String>> createTableColumn = (srcData) -> {
            String text = srcData.getString("text");
            double divideNum = srcData.getDouble("divideNum");
            String cellValueBindAttr = srcData.getString("attr");
            String style = "-fx-alignment: center;";

            TableColumn<RedisKVStatistics, String> col = new TableColumn<>(text);
            col.prefWidthProperty().bind(tableView.prefWidthProperty().divide(divideNum));
            col.setCellValueFactory(new PropertyValueFactory<>(cellValueBindAttr));
            col.setStyle(style);
            return col;
        };

        JSONObject srcData = new JSONObject();
        srcData.put("divideNum", 4.4);

        srcData.put("text", "DB");
        srcData.put("attr", "col1");
        TableColumn<RedisKVStatistics, String> dbCol = createTableColumn.apply(srcData);

        srcData.put("text", "Keys");
        srcData.put("attr", "col2");
        TableColumn<RedisKVStatistics, String> keysCol = createTableColumn.apply(srcData);

        srcData.put("text", "Expires");
        srcData.put("attr", "col3");
        TableColumn<RedisKVStatistics, String> expireCol = createTableColumn.apply(srcData);

        srcData.put("text", "Avg TTL");
        srcData.put("attr", "col4");
        TableColumn<RedisKVStatistics, String> avgTTLCol = createTableColumn.apply(srcData);

        tableView.getColumns().addAll(dbCol, keysCol, expireCol, avgTTLCol);

        String id = this.data.getString("id");
        String keyspace = new RedisService().info(id, "Keyspace");
        String[] split = keyspace.split("\r\n");
        if (split.length > 1) {
            ObservableList<RedisKVStatistics> redisInfoData = FXCollections.observableArrayList();
            for (int i = 1; i < split.length; i++) {
                String rowInfo = split[i];
                String[] tempSplit = rowInfo.split(":");
                String dbName = tempSplit[0];
                String dbInfo = tempSplit[1];
                String[] dbInfoSplit = dbInfo.split(",");
                String keyInfo = dbInfoSplit[0];
                String expiresInfo = dbInfoSplit[1];
                String avgTTLInfo = dbInfoSplit[2];

                RedisKVStatistics kvInfo = new RedisKVStatistics(dbName, keyInfo.split("=")[1],
                    expiresInfo.split("=")[1], avgTTLInfo.split("=")[1]);
                redisInfoData.add(kvInfo);
            }
            tableView.setItems(redisInfoData);
        }

        return tableView;
    }

    /**
     * ??????redis??????????????????
     *
     * @return ??????
     */
    private TableView<AllRedisInfo> createAllRedisInfoView() {
        TableView<AllRedisInfo> allRedisInfoTableView = new TableView<>();
        allRedisInfoTableView.prefHeightProperty().bind(dashboard.prefHeightProperty().multiply(0.5));
        allRedisInfoTableView.prefWidthProperty().bind(widthProperty());

        TableColumn<AllRedisInfo, String> keyCol = new TableColumn<>("Key");
        keyCol.prefWidthProperty().bind(allRedisInfoTableView.prefWidthProperty().divide(2.2));
        keyCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        keyCol.setStyle("-fx-alignment: center;");

        TableColumn<AllRedisInfo, String> valueCol = new TableColumn<>("Value");
        valueCol.prefWidthProperty().bind(allRedisInfoTableView.prefWidthProperty().divide(2.2));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueCol.setStyle("-fx-alignment: center;");

        allRedisInfoTableView.getColumns().addAll(keyCol, valueCol);

        String id = this.data.getString("id");
        String info = new RedisService().info(id);
        String[] split = info.split("\r\n");
        if (split.length > 1) {
            ObservableList<AllRedisInfo> allRedisList = FXCollections.observableArrayList();
            for (int i = 1; i < split.length; i++) {
                String rowInfo = split[i];
                if (rowInfo.trim().length() == 0 || rowInfo.contains("#")) {
                    continue;
                }

                String[] tempSplit = rowInfo.split(":");
                String key = "";
                String value = "";

                if (tempSplit.length > 1) {
                    value = tempSplit[1];
                }

                if (tempSplit.length > 0) {
                    key = tempSplit[0];
                }

                AllRedisInfo kvInfo = new AllRedisInfo(key, value);
                allRedisList.add(kvInfo);
            }
            allRedisInfoTableView.setItems(allRedisList);
        }

        return allRedisInfoTableView;
    }
}
