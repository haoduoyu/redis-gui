package com.rain.stage.component.main;

import com.rain.stage.component.BaseComponent;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author rain.z
 * @description Right
 * @date 2022/05/09
 */
public class Right implements BaseComponent {

    private Stage stage;

    public Right(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Parent createContent() {
        return new Pane();
    }

    @Override
    public void refresh() {

    }
}
