package com.rain.constant;

public enum TabType {
    MAIN_INFO("main"),
    STRING("string"),
    LIST("list"),
    SET("set"),
    HASH("hash"),
    SORTED_SET("sortedSet"),
    BITMAP("bitmap"),
    HYPERLOGLOG("hyperloglog"),
    STREAM("stream"),
    GEO("geo");

    private String typeName;

    TabType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TabType typeNameToValue(String typeName) {
        for (TabType value : TabType.values()) {
            if (value.getTypeName().equalsIgnoreCase(typeName)) {
                return value;
            }
        }

        return null;
    }
}
