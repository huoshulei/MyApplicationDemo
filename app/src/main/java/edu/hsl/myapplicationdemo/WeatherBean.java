package edu.hsl.myapplicationdemo;

/**
 * Created by Administrator on 2016/5/24.
 */
public class WeatherBean {
    String direct;//风向
    String power;//风级
    String time;//更新时间
    String humidity;//湿度
    String weather;//天气
    String temperature;//温度
    String date;//日期
    String city_name;//地区
    String week;//星期
    String moon;//农历日期
    int    id;

    public WeatherBean(String direct, String power, String time, String humidity,
                       String weather, String temperature, String data, String city_name,
                       String week, String moon, int id) {
        this.direct = direct;
        this.power = power;
        this.time = time;
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.date = data;
        this.city_name = city_name;
        this.week = week;
        this.moon = moon;
        this.id = id;
    }

    @Override
    public String toString() {
        return direct + power + time + humidity + weather + temperature + date + city_name + week + moon;
    }
}
