package com.rain.stage.component.main;

import com.rain.constant.ConnectionMenuItemId;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.function.Consumer;

/**
 * @author rain.z
 * @description ConnectionContentMenu
 * @date 2022/05/11
 */
public class ConnectionContentMenu extends ContextMenu {
    private static ConnectionContentMenu INSTANCE = null;

    private static MenuItem connectMenuItem = null;
    private static MenuItem deleteMenuItem = null;

    private ConnectionContentMenu() {
        connectMenuItem = new MenuItem("连接");
        connectMenuItem.setId(ConnectionMenuItemId.CONNECTION_MENU_ITEM);

        deleteMenuItem = new MenuItem("删除");
        deleteMenuItem.setId(ConnectionMenuItemId.DELETE_MENU_ITEM);
        getItems().add(connectMenuItem);
        getItems().add(deleteMenuItem);
    }

    public static ConnectionContentMenu getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ConnectionContentMenu();
        }

        return INSTANCE;
    }

    public void showContextMenu(Node node, Side side, double dx, double dy, Consumer<ActionEvent> consumer) {
        if (null == INSTANCE) {
            throw new RuntimeException("未初始化");
        }

        connectMenuItem.setOnAction(consumer::accept);
        deleteMenuItem.setOnAction(consumer::accept);

        INSTANCE.setId(node.getId());
        INSTANCE.show(node, side, dx, dy);
    }
}
