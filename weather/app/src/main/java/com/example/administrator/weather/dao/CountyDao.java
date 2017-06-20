package com.example.administrator.weather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.administrator.weather.module.County;
import com.example.administrator.weather.util.WeatherDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/6.
 */

public class CountyDao {
    private WeatherDBHelper weatherDBHelper;
    public CountyDao(Context context){
         weatherDBHelper=new WeatherDBHelper(context);
    }
    //初始化表中所有县
   public void insertCounty(County county){
       SQLiteDatabase database=weatherDBHelper.getWritableDatabase();
       ContentValues values=new ContentValues();
       values.put("countyName",county.getCountyName());
       values.put("weatherId",county.getWeatherId());
       values.put("cityId",county.getCityId());
       database.insert("county",null,values);
       values.clear();
       database.close();
   }
    public List<County> allCounty(String cityID){
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
      Cursor c= db.rawQuery("select * from county where cityId=?",new String[]{cityID});
        List<County> counties=new ArrayList<County>();
        while(c.moveToNext()){
           int id= c.getInt(c.getColumnIndex("id"));
           String countyName= c.getString(c.getColumnIndex("countyName"));
           String weatherId= c.getString(c.getColumnIndex("weatherId"));
         int cityId=c.getInt(c.getColumnIndex("cityId"));
            County county=new County();
            county.setId(id);
            county.setCountyName(countyName);
            county.setWeatherId(weatherId);
            county.setCityId(cityId);
            counties.add(county);
        }
        db.close();
        c.close();
        return  counties;
    }
    public String WeatherId(String countyName){
        String weatherId="";
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
        Cursor c=db.rawQuery("select * from county where countyName=?",new String[]{countyName});
        while (c.moveToNext()){
             weatherId=c.getString(c.getColumnIndex("weatherId"));
        }
        db.close();
        c.close();
        return  weatherId;
    }

}
