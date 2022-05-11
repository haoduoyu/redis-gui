package com.rain.stage;

import com.rain.stage.component.main.Left;
import com.rain.stage.component.main.Right;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author rain.z
 * @description MainStage
 * @date 2022/05/08
 */
public class MainStage extends Stage {

    private final double DEFAULT_WIDTH = 1024.0;
    private final double DEFAULT_HEIGHT = 760;

    private final double MIN_WIDTH = DEFAULT_WIDTH / 2;
    private final double MIN_HEIGHT = DEFAULT_HEIGHT / 2;

    private double screenWidth;
    private double screenHeight;

    public MainStage(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth < 0 ? DEFAULT_WIDTH : screenWidth;
        this.screenHeight = screenHeight < 0 ? DEFAULT_HEIGHT : screenHeight;

        initStage();
    }

    private void initStage() {
        Scene scene = new Scene(createContent());
        this.setScene(scene);
        this.setScreenSize();
    }

    private Parent createContent() {
        SplitPane splitPane = new SplitPane();
        splitPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        splitPane.setOrientation(Orientation.HORIZONTAL);

        Left leftComponent = new Left(this);
        splitPane.getItems().add(leftComponent.createContent());

        Right rightComponent = new Right(this);
        splitPane.getItems().add(rightComponent.createContent());

        return splitPane;
    }

    private void setScreenSize() {
        setWidth(this.screenWidth);
        setHeight(this.screenHeight);
        setMinHeight(MIN_WIDTH);
        setMinWidth(MIN_HEIGHT);

        // TODO 这里因为用外接屏，所以想每次调试出现在外接屏上
        double width = Screen.getScreens().get(0).getBounds().getWidth();
        setX(width + 50);
        setY(-500);
    }
}
