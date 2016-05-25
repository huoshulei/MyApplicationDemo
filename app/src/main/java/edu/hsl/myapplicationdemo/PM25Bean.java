package edu.hsl.myapplicationdemo;

/**
 * Created by Administrator on 2016/5/24.
 */
public class PM25Bean {
    String city_name;
    String dateTime;
    String curPm;//污染指数
    String pm25;
    String pm10;
    String quality;//污染等级
    String des;

    public PM25Bean(String city_name, String dateTime, String curPm, String pm25,
                    String pm10, String quality, String des) {
        this.city_name = city_name;
        this.dateTime = dateTime;
        this.curPm = curPm;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.quality = quality;
        this.des = des;
    }

    @Override
    public String toString() {
        return city_name + dateTime + curPm + pm25 + pm10 + quality + des;
    }
}
