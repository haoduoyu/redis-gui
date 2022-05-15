package com.rain.stage.component.main;

import com.alibaba.fastjson.JSONObject;
import com.rain.stage.component.BaseComponent;
import com.rain.stage.component.main.sub.BasePane;
import com.rain.stage.component.main.sub.MainRedisInfoPane;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
        String id = jsonObject.getString("id");

        if (!tabMap.containsKey(id)) {
            Tab tab = this.createTab(jsonObject);
            tab.setId(id);
            tabMap.put(id, tab);
            int index = tabPosMap.size();
            tabPosMap.put(tab, index);
            this.tabPane.getTabs().add(tab);
        }
    }

    private Tab createTab(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Tab newTab = new Tab(name);

        BasePane basePane = new MainRedisInfoPane(jsonObject);
        newTab.setContent(basePane);
        newTab.setOnClosed(event -> {
            Tab closeTab = (Tab)event.getTarget();
            Tab removeItem = tabMap.remove(closeTab.getId());
            tabPosMap.remove(removeItem);
        });
        return newTab;
    }

    @Override
    public void refresh() {

    }
}
