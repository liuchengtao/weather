package com.example.administrator.weather;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.administrator.weather.dao.CityDao;
import com.example.administrator.weather.dao.CountyDao;
import com.example.administrator.weather.dao.ProvinceDao;

import com.example.administrator.weather.module.City;
import com.example.administrator.weather.module.County;
import com.example.administrator.weather.module.Province;
import com.example.administrator.weather.util.Gethttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected  static final int PROVINCE=1;
    protected  static final  int CITY=2;
    protected  static final  int COUNTY=3;
    protected  static final  int LAST=4;
    protected int table;
    private ListView listView;
    private ArrayAdapter adapter;
    List<String> DATA=new ArrayList<String>();
    List<String> DATA1=new ArrayList<String>();
    List<String> DATA2=new ArrayList<String>();
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {

            if(msg.what==PROVINCE){
                table=PROVINCE;
                DATA1 = (List<String>) msg.obj;
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, DATA1);
                listView.setAdapter(adapter);
            }else if(msg.what==CITY){

                table=CITY;
                DATA1 = (List<String>) msg.obj;
                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, DATA1);
                listView.setAdapter(adapter);
            }else if(msg.what==COUNTY){
                table=COUNTY;
                DATA1 = (List<String>) msg.obj;


                adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, DATA1);
                listView.setAdapter(adapter);

            }else if(msg.what==LAST){

                //int aw=msg.arg1;
                String countyName= (String) msg.obj;
                Log.i("data333" ,countyName);

                //String countyName=DATA.get(aw);
              //  String countyName="茶陵";
                CountyDao countyDao=new CountyDao(MainActivity.this);
                String weatherId=  countyDao.WeatherId(countyName);
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("weather_id",weatherId);
                startActivity(intent);
            }}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        ProvinceDao provinceDao1 = new ProvinceDao(this);
        List<Province> provinceList1 = provinceDao1.findAll();
        //省 市 县 初始化.
        if (provinceList1.size() == 0) {
            new Thread() {
                public void run() {
                    String path = "http://guolin.tech/api/china";
                    String text = Gethttp.getJson(path);
                    parseProvince(text);

                    for (int i = 0; i <= 33; i++) {

                        String path1 = path + "/" + (i + 1);
                        String cityText = Gethttp.getJson(path1);

                        int provinceId = i + 1;
                        parseCity(cityText, provinceId);

                    }
                    for (int j = 1; j <= 350; j++) {

                        String path2 = path + "/" + 1 + "/" + j;
                        String countyText = Gethttp.getJson(path2);
                        parseCounty(countyText, j);

                    }
                    ProvinceDao provinceDao = new ProvinceDao(MainActivity.this);
                    final List<Province> provinceList = provinceDao.findAll();
                    for (Province province : provinceList) {
                        String provinceName = province.getProvinceName();
                        DATA.add(provinceName);
                    }

                    Message msg = new Message();
                    msg.obj=DATA;
                    msg.what = PROVINCE;
                    handler.sendMessage(msg);
                }


            }.start();


        } else {
            new Thread() {
                public void run() {
                    ProvinceDao provinceDao = new ProvinceDao(MainActivity.this);
                    final List<Province> provinceList = provinceDao.findAll();
                    for (Province province : provinceList) {
                        String provinceName = province.getProvinceName();
                        DATA.add(provinceName);
                    }

                    Message msg = new Message();
                    msg.what = PROVINCE;
                    msg.obj=DATA;
                    handler.sendMessage(msg);
                }
            }.start();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // int dataChange=DATA1.size();
                String cityname= DATA1.get(i);

                //  Log.i("code",cityname);
                DATA1.clear();

                if(table==PROVINCE) {
                    CityDao dao = new CityDao(MainActivity.this);
                    List<City> cityList = dao.allCity(i + 1 + "");
                    for (City city : cityList) {
                        String cityName = city.getCityName();
                        DATA.add(cityName);
                    }
                    Message meg = new Message();
                    meg.what=CITY;
                    meg.obj = DATA;
                    handler.sendMessage(meg);
                    // Toast.makeText(MainActivity.this, "your click" + i + "行", Toast.LENGTH_SHORT).show();
                }else if(table==CITY){

                    CityDao cityDao=new CityDao(MainActivity.this);
                    City city=cityDao.test(cityname);

                    Log.i("code1", city.getId() + "");
                    Log.i("code2", city.getCityName());
                    //  Log.i("code3", city.getCityCode() + "");
                    //  Log.i("code4", city.getProvinceId() + "");
                    //   Log.i("code5", String.valueOf(city.getId() == 0));

                    CountyDao countyDao = new CountyDao(MainActivity.this);
                    List<County> counties = countyDao.allCounty(city.getCityCode() + "");
                    for (County county : counties) {
                        String countyName = county.getCountyName();
                        String weatherID=county.getWeatherId();
                        DATA.add(countyName);
                        DATA2.add(countyName);
                    }
                    Message meg = new Message();
                    meg.what = COUNTY;
                    meg.obj = DATA2;


                    handler.sendMessage(meg);


                    Log.i("code6", "test");
                    //  Toast.makeText(MainActivity.this, "your click" + "i" + "行", Toast.LENGTH_SHORT).show();






                }else if(table==COUNTY){

                    Message meg = new Message();
                    Log.i("data" ,DATA.size()+"");
                    Log.i("data1" ,DATA1.size()+"");
                    Log.i("data2" ,DATA2.size()+"");
                      meg.obj =DATA.get(i);
                    meg.what = LAST;



                    handler.sendMessage(meg);
                }


            }
        });
    }
    public  void parseCity(String response,int id){
        if(!TextUtils.isEmpty(response)){
            CityDao dao=new CityDao(MainActivity.this);
            try {
                JSONArray allCity=new JSONArray(response);
                for(int i=0;i<=allCity.length();i++){
                    JSONObject jsonObject= allCity.getJSONObject(i);
                    City city=new City();
                    city.setCityName(jsonObject.getString("name"));
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setProvinceId(id);
                    dao.insertCity(city);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public void parseProvince(String response){
        if(!TextUtils.isEmpty(response)){

            try {
                ProvinceDao dao=new ProvinceDao(MainActivity.this);
                JSONArray allProvince=new JSONArray(response);
                for(int i=0;i<=allProvince.length();i++){
                    JSONObject jsonObject= allProvince.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(jsonObject.getString("name"));
                    province.setProvinceCode(jsonObject.getInt("id"));
                    dao.insertProvince(province);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public void parseCounty(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            CountyDao dao=new CountyDao(MainActivity.this);
            try {
                JSONArray allCounty=new JSONArray(response);
                for(int i=0;i<=allCounty.length();i++){
                    JSONObject object=allCounty.getJSONObject(i);
                    County county=new County();
                    county.setCityId(cityId);
                    county.setCountyName(object.getString("name"));
                    county.setWeatherId(object.getString("weather_id"));
                    dao.insertCounty(county);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
