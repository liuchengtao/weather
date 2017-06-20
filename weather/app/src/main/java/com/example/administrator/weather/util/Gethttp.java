package com.example.administrator.weather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2017/5/2.
 */

public class Gethttp {
    public static String getJson(String path) {
        String text="";
        try {
            URL url=new URL(path);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(500000);
            connection.setReadTimeout(500000);
            int code=connection.getResponseCode();
            if(code==200){
                InputStream is=connection.getInputStream();
                InputStreamReader isReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isReader);
                String line;
                StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                isReader.close();
                is.close();
                connection.disconnect();
                text=sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
       return  text;
    }
}
