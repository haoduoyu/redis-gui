package com.rain.stage.component;

import javafx.scene.Parent;

/**
 * @author rain.z
 * @description BaseComponent
 * @date 2022/05/09
 */
public interface BaseComponent {
    public Parent createContent();

    public void refresh();
}
