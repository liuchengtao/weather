package com.example.administrator.weather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.administrator.weather.module.Province;
import com.example.administrator.weather.util.WeatherDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ProvinceDao {


    private WeatherDBHelper weatherDBHelper;
    public ProvinceDao(Context context) {
       weatherDBHelper =new WeatherDBHelper(context) ;
    }
    public void insertProvince(Province province){
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        String provinceName= province.getProvinceName();
        int provinceCode=province.getProvinceCode();
        values.put("provinceName",provinceName);
        values.put("provinceCode",provinceCode);
        db.insert("province",null,values);
        values.clear();
        db.close();

    }
    //把province表初始化.
    public void insert(List<Province> provinceList){
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();

        for(Province province:provinceList){
            ContentValues values=new ContentValues();
         String provinceName= province.getProvinceName();
            int provinceCode=province.getProvinceCode();
            values.put("provinceName",provinceName);
            values.put("provinceCode",provinceCode);
            db.insert("province",null,values);
            values.clear();
        }
        db.close();

    }
    //查询所有province表中数据(省名);
    public List<Province> findAll(){
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from province",new String[]{});
        List<Province> provinces=new ArrayList<Province>();
        while(cursor.moveToNext()){
            int id=cursor.getInt( cursor.getColumnIndex("id"));
            String provinceName=cursor.getString(cursor.getColumnIndex("provinceName"));
            int provinceCode=cursor.getInt(cursor.getColumnIndex("provinceCode"));
            Province province=new Province();
            province.setId(id);
            province.setProvinceName(provinceName);
            province.setProvinceCode(provinceCode);
            provinces.add(province);
        }
        cursor.close();
        db.close();
        return provinces;
    }
    public int text(int i){


        return i;
    }
    public String selectId(String id){
        SQLiteDatabase db=weatherDBHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from city where id=?",new String[]{id});
        String provinceName="";
        while(cursor.moveToNext()){

             provinceName=cursor.getString(cursor.getColumnIndex("provinceName"));


        }
        cursor.close();
        db.close();
        return  provinceName;
    }

}
