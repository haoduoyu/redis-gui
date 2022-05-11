package com.rain.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.rain.util.DBUtil;

/**
 * @author rain.z
 * @description DBService
 * @date 2022/05/10
 */
public class DBService {
    public List<JSONObject> getData() {
        List<JSONObject> list = new ArrayList<>();
        ResultSet rs = DBUtil.select("SELECT ID, NAME, PARAMS FROM R_CONNECTION_INFO");
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String params = rs.getString("params");

                JSONObject temp = new JSONObject();
                temp.put("id", id);
                temp.put("name", name);
                temp.put("params", JSONObject.parseObject(params));

                list.add(temp);
            }

            DBUtil.closeResource(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void saveData(JSONObject dataJson) {
        String sql = String.format("INSERT INTO R_CONNECTION_INFO (NAME, PARAMS) VALUES ('%s', '%s')",
            dataJson.getString("connectName"),
            dataJson.toJSONString());
        DBUtil.insert(sql);
    }
}
