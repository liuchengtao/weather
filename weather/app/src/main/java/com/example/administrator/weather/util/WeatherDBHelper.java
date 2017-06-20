package com.example.administrator.weather.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/26.
 */


public class WeatherDBHelper extends SQLiteOpenHelper {
    public WeatherDBHelper(Context context) {
        super(context, "weather2017.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE if not exists province(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "provinceName TEXT," +
                "provinceCode INTEGER) ");
        sqLiteDatabase.execSQL("CREATE TABLE if not exists city(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cityName TEXT," +
                "cityCode INTEGER," +
                "provinceId INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE if not exists county(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "countyName TEXT," +
                "weatherId TEXT," +
                "cityId INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
