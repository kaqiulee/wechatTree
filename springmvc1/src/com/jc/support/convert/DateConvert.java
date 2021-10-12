package com.jc.support.convert;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert implements Converter<String, Date> {
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date convert(String s) {
        SimpleDateFormat sf = new SimpleDateFormat(datePattern);

        try {
            Date date = sf.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("无效的格式，请用： " + datePattern);
        }
    }

    //    @Override
//    public Date convert(String s) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
//        try {
//            return simpleDateFormat.parse(s);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException("无效的格式，请用： " + datePattern);
//        }
//
//    }
}
