package com.rain.entity;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author rain.z
 * @description RedisInfo
 * @date 2022/05/14
 */
public class AllRedisInfo {
    private final SimpleStringProperty key;
    private final SimpleStringProperty value;

    public AllRedisInfo(String key, String value) {
        this.value = new SimpleStringProperty(value);
        this.key = new SimpleStringProperty(key);
    }

    public String getKey() {
        return key.get();
    }

    public SimpleStringProperty keyProperty() {
        return key;
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
