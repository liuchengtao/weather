package com.example.administrator.weather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.administrator.weather.module.City;
import com.example.administrator.weather.util.WeatherDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class CityDao  {
    private WeatherDBHelper weatherDBHelper;

    public CityDao(Context context) {
        weatherDBHelper =new WeatherDBHelper(context) ;
    }
    public List<String> cityCodeFind(String provinceId ){
      List<String> list=new ArrayList<String>();
        SQLiteDatabase db= weatherDBHelper.getWritableDatabase();
         Cursor cursor=db.rawQuery("select * from city where provinceId",new String[]{provinceId});
        while(cursor.moveToNext()){
            int cityCode=cursor.getInt(cursor.getColumnIndex("cityCode"));
            list.add(cityCode+"");
        }
        return  list;
    }
    public void insertCity(City city){
      SQLiteDatabase db= weatherDBHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("cityName",city.getCityName());
        values.put("cityCode",city.getCityCode());
        values.put("provinceId",city.getProvinceId());
        db.insert("city",null,values);
        values.clear();
        db.close();
    }
    public List<City> allCity(String provinceID){
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
       Cursor cursor= db.rawQuery("select * from city where provinceId=?",new String[]{provinceID});
        List<City> allCity=new ArrayList<City>();
        while(cursor.moveToNext()){
            int id=cursor.getColumnIndex("id");
            String cityName=cursor.getString(cursor.getColumnIndex("cityName"));
            int cityCode=cursor.getColumnIndex("cityCode");
           int provinceId=cursor.getColumnIndex("provinceId");
            City city=new City();
            city.setId(id);
            city.setCityName(cityName);
            city.setCityCode(cityCode);
            city.setProvinceId(provinceId);
            allCity.add(city);
        }
        cursor.close();
        db.close();
        return allCity;


    }
    public City test(String cityNAME){
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from city where cityName=?", new String[]{cityNAME});
        City city=new City();
        while(cursor.moveToNext()){

            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String cityName=cursor.getString(cursor.getColumnIndex("cityName"));
          int cityCode=cursor.getInt(cursor.getColumnIndex("cityCode"));
            int provinceId=cursor.getInt(cursor.getColumnIndex("provinceId"));
            city.setId(id);
            city.setCityName(cityName);
            city.setCityCode(cityCode);
            city.setProvinceId(provinceId);
        }
        return  city;
    }
    public int selectCityCode(String cityName){
        int cityCode=200;
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from city where cityName=?", new String[]{cityName} );
        while(cursor.moveToNext()){
           cityCode=cursor.getColumnIndex("cityCode");

        }

        cursor.close();
        db.close();
        return  cityCode;

    }


}
