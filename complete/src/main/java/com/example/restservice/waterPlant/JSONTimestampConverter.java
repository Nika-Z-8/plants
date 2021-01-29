package com.example.restservice.waterPlant;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONTimestampConverter {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static Timestamp toTimestamp(String s) throws ParseException {
        return new Timestamp(new SimpleDateFormat(DATE_FORMAT).parse(s).getTime());
    }

    public static String toString(Timestamp ts) {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date(ts.getTime()));
    }
}
