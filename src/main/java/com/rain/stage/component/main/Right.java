package com.rain.stage.component.main;

import com.alibaba.fastjson.JSONObject;
import com.rain.constant.TabType;
import com.rain.stage.component.BaseComponent;
import com.rain.stage.component.main.sub.*;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            Tab tab = this.createMainTab(jsonObject);
            tab.setId(id);
            tabMap.put(id, tab);
            tabPosMap.put(tab, tabPosMap.size());
            this.tabPane.getTabs().add(tab);
        }
    }

    public void addTabForValue(JSONObject data) {
        String value = data.getString("value");
        if (Objects.isNull(value)) {
            return;
        }
        this.createValueTab(data);
    }

    private void createValueTab(JSONObject data) {
        TabType type = data.getObject("type", TabType.class);

        BasePane valuePane = null;
        switch (type) {
            case STRING:
                valuePane = new StringValuePane(data);
                break;
            case LIST:
                valuePane = new ListValuePane(data);
                break;
            case SET:
                valuePane = new SetValuePane(data);
                break;
            case HASH:
                valuePane = new HashValuePane(data);
                break;
            case SORTED_SET:
                valuePane = new SortedSetValuePane(data);
                break;
            case BITMAP:
                valuePane = new BitmapValuePane(data);
                break;
            case HYPERLOGLOG:
                valuePane = new HyperloglogValuePane(data);
                break;
            case STREAM:
                valuePane = new StreamValuePane(data);
                break;
            case GEO:
                valuePane = new GeoValuePane(data);
                break;
        }
        // TODO 这里将新创建的面板替换main
        System.out.println(valuePane);
    }

    private Tab createMainTab(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Tab newTab = new Tab(name);

        BasePane basePane = new MainRedisInfoPane(jsonObject);
        newTab.setContent(basePane);
        newTab.setOnClosed(event -> {
            Tab closeTab = (Tab) event.getTarget();
            Tab removeItem = tabMap.remove(closeTab.getId());
            tabPosMap.remove(removeItem);
        });
        return newTab;
    }

    @Override
    public void refresh() {

    }
}
