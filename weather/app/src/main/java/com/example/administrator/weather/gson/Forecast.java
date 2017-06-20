package com.example.administrator.weather.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {

    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        //度。c
        public String max;

        public String min;

    }

    public class More {
        //天气
        @SerializedName("txt_d")
        public String info;

    }

}
