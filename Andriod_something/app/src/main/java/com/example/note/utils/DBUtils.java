package com.example.note.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtils {
    public static final String DATABASE_NAME = "Notepad";
    public static final String DATABASE_TABLE = "Note";
    public static final int DATABASE_VERSION = 1;
    public static final String NOTEPAD_ID = "id";
    public static final String NOTEPAD_CONTENT = "content";
    public static final String NOTEPAD_TIME = "notetime";
    public static final String getNotepadTime(){
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
