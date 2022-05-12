package com.rain.stage.component.main;

import com.alibaba.fastjson.JSONObject;
import com.rain.stage.component.BaseComponent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rain.z
 * @description Right
 * @date 2022/05/09
 */
public class Right implements BaseComponent {

    private Stage stage;

    private TabPane tabPane;

    private static Map<String, Tab> tabMap = new HashMap<>();
    private static Map<Tab, Integer> tabPosMap = new HashMap<>();

    public Right(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Parent createContent() {
        tabPane = new TabPane();
        return tabPane;
    }

    public void addTab(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String id = jsonObject.getString("id");

        Tab tab = null;
        if (!tabMap.containsKey(id)) {
            tab = this.createTab(name);
            tabMap.put(id, tab);
            int index = tabPosMap.size();
            tabPosMap.put(tab, index);
            this.tabPane.getTabs().add(tab);
        }
    }

    private Tab createTab(String name) {
        Tab newTab = new Tab(name);
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Label label = new Label("连接成功(这里可以展示一些redis server信息)");
        label.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.getChildren().add(label);
        newTab.setContent(pane);
        return newTab;
    }

    @Override
    public void refresh() {

    }
}
