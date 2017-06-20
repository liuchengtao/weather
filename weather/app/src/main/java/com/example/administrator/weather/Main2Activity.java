package com.example.administrator.weather;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.weather.gson.AQI;
import com.example.administrator.weather.gson.Basic;
import com.example.administrator.weather.gson.Forecast;
import com.example.administrator.weather.gson.Now;
import com.example.administrator.weather.gson.Suggestion;
import com.example.administrator.weather.gson.Weather;
import com.example.administrator.weather.util.Gethttp;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private TextView max;
    private TextView min,aqi,pm,sugg,tianqi;

    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            Weather w= (Weather) msg.obj;

            AQI a=w.aqi;

            Suggestion suggestion=w.suggestion;
            List<Forecast> forecastList=w.forecastList;
            Forecast f= forecastList.get(0);

            // String aqitext=   aqi.city.aqi;
            // String pm25 =aqi.city.pm25;
            max.setText(f.temperature.max);
            min.setText(f.temperature.min);
            aqi.setText(a.city.aqi);
            pm.setText(a.city.pm25);
            sugg.setText(suggestion.comfort.info);
            tianqi.setText(f.more.info);


        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        max= (TextView) findViewById(R.id.max);
        min= (TextView) findViewById(R.id.min);
        aqi= (TextView) findViewById(R.id.aqi);
        pm= (TextView) findViewById(R.id.pm);
        sugg= (TextView) findViewById(R.id.sugg);
        tianqi= (TextView) findViewById(R.id.tianqi);
        Intent intent =getIntent();
        String weatherId=  intent.getStringExtra("weather_id");
        String path="http://guolin.tech/api/weather?cityid=";
        String path1=weatherId;
        String path2= "&key=bc0418b57b2d4918819d3974ac1285d9";
        final String path0=path+path1+path2;
        new Thread(){
            public  void run(){
                String response=  Gethttp.getJson(path0);
                     System.out.println(response);
                Weather w=  parseWeather(response) ;


                Message msg = new Message();
                msg.obj=w;


                handler.sendMessage(msg);
            }



        }.start();

    }
    public static Weather parseWeather(String response){
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray= jsonObject.getJSONArray("HeWeather");
            String contentWeather=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(contentWeather,Weather.class);
            // System.out.println(contentWeather);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
