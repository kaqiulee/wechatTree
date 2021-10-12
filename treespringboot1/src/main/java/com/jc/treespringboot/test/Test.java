package com.jc.treespringboot.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ParseException {
//        String str = "{\"date\":\"2021-04-19\", \"step\":5000 }";
//        JSONObject object = JSONObject.parseObject(str);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String date = (String) object.get("date");
//        Date time  = dateFormat.parse(date);
//        int step = (int) object.get("step");
//        System.out.println("time : " + time);
//        System.out.println( "step : " + step);

        String str2 = "[{\"date\":\"2021-04-19\", \"step\":5000 }, {\"date\":\"2021-04-20\", \"step\":5001}]";
        parseStep(str2);
    }


    static void parseStep(String s) throws ParseException {
        List<JSONObject> list = JSONArray.parseArray(s, JSONObject.class);

        for (JSONObject object : list) {
            String date = (String) object.get("date");
            int step = (int) object.get("step");
            System.out.println("date : " + date);
            System.out.println( "step : " + step);

        }


    }
}
