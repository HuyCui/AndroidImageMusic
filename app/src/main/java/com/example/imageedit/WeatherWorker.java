package com.example.imageedit;


import java.util.Date;
import java.util.List;

public abstract class WeatherWorker {
    public abstract List<WeatherBean> getWeather(final String... args) throws Exception;

    public class WeatherBean {
        public String cityName;
        public String cityCode;
        public Date dataTime;
        public Date date;
        public String weather;
        public Integer temprature_1;
        public Integer temprature_2;
        public String wind;
        public String wind_way;
        public String wind_power;
        public String img_1;
        public String img_2;
        public String temprature_str;

        @Override
        public String toString() {
            return "WeatherBean [cityName=" + cityName + ", cityCode="
                    + cityCode + ", dataTime=" + dataTime + ", date=" + date
                    + ", weather=" + weather + ", temprature_1=" + temprature_1
                    + ", temprature_2=" + temprature_2 + ", wind=" + wind
                    + ", wind_way=" + wind_way + ", wind_power=" + wind_power
                    + ", img_1=" + img_1 + ", img_2=" + img_2 + "]";
        }

    }
}