package com.rain;

import com.rain.stage.MainStage;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartMainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new MainStage(1024, 760).show();
//        double width = Screen.getScreens().get(0).getBounds().getWidth();
//        new NewConnectionStage(width + 60 , -500, 800, 300).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
