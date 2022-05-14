package com.rain.entity;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author rain.z
 * @description RedisInfo
 * @date 2022/05/14
 */
public class RedisKVStatistics {
    private final SimpleStringProperty col1;
    private final SimpleStringProperty col2;
    private final SimpleStringProperty col3;
    private final SimpleStringProperty col4;

    public RedisKVStatistics(String col1, String col2, String col3, String col4) {
        this.col1 = new SimpleStringProperty(col1);
        this.col2 = new SimpleStringProperty(col2);
        this.col3 = new SimpleStringProperty(col3);
        this.col4 = new SimpleStringProperty(col4);
    }

    public String getCol1() {
        return col1.get();
    }

    public SimpleStringProperty col1Property() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1.set(col1);
    }

    public String getCol2() {
        return col2.get();
    }

    public SimpleStringProperty col2Property() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2.set(col2);
    }

    public String getCol3() {
        return col3.get();
    }

    public SimpleStringProperty col3Property() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3.set(col3);
    }

    public String getCol4() {
        return col4.get();
    }

    public SimpleStringProperty col4Property() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4.set(col4);
    }
}
