package edu.hsl.myapplicationdemo.activity;

import android.widget.ImageView;
import android.widget.TextView;

import edu.hsl.myapplicationdemo.base.MyActivity;
import edu.hsl.myapplicationdemo.R;
import edu.hsl.myapplicationdemo.bean.WeatherInfoBean;
import edu.hsl.myapplicationdemo.util.ImageUtil;

/**
 * 一周天气详细信息
 */
public class WeatherWeekActivity extends MyActivity {
    TextView  date;//日期
    TextView  weather_day;//天气
    TextView  temperature_day;//温度
    TextView  direct_day;//风向
    TextView  power_day;//风级
    TextView  sun_up;//太阳升起
    TextView  weather_night;//天气
    TextView  temperature_night;//温度
    TextView  direct_night;//风向
    TextView  power_night;//风级
    TextView  sun_down;//降落
    TextView  week;//星期
    TextView  moon;//农历日期
    ImageView id_day;
    ImageView id_night;
    int       id;

    @Override
    public void initVeiw() {
        setContentView(R.layout.activity_weather_week);
        id = getIntent().getIntExtra("id", 0);//获取对应天气item的id值
        date = (TextView) findViewById(R.id.tv_date);//日期
        weather_day = (TextView) findViewById(R.id.tv_weather_day);//天气
        temperature_day = (TextView) findViewById(R.id.tv_temperature_day);
        direct_day = (TextView) findViewById(R.id.tv_direct_day);//风向
        power_day = (TextView) findViewById(R.id.tv_power_day);//风级
        sun_up = (TextView) findViewById(R.id.tv_sun_up);//太阳升起
        weather_night = (TextView) findViewById(R.id.tv_weather_night);//
        temperature_night = (TextView) findViewById(R.id.tv_temperature_night);
        direct_night = (TextView) findViewById(R.id.tv_direct_night);//风
        power_night = (TextView) findViewById(R.id.tv_power_night);//风级
        sun_down = (TextView) findViewById(R.id.tv_sun_down);//降落
        week = (TextView) findViewById(R.id.tv_week);//星期
        moon = (TextView) findViewById(R.id.tv_moon);//农历日期
        id_day = (ImageView) findViewById(R.id.iv_image_day);
        id_night = (ImageView) findViewById(R.id.iv_image_night);
    }

    @Override
    public void initData() {
        WeatherInfoBean data = mWeatherUtil.getJsonWeather().get(id);
        date.setText(data.data);//日期
        weather_day.setText(data.weather_day);//天气
        temperature_day.setText(data.temperature_day + "℃");//温度
        direct_day.setText(data.direct_day);//风向
        power_day.setText(data.power_day);//风级
        sun_up.setText("日出时间:  " + data.sun_up);//太阳升起
        weather_night.setText(data.weather_night);//天气
        temperature_night.setText(data.temperature_night + "℃");//温度
        direct_night.setText(data.direct_night);//风向
        power_night.setText(data.power_night);//风级
        sun_down.setText("日落时间:  " + data.sun_down);//降落
        week.setText("星期" + data.week);//星期
        moon.setText("农历" + data.moon);//农历日期
        id_day.setImageResource(ImageUtil.getImageDay(data.id_day));
        id_night.setImageResource(ImageUtil.getImageNight(data.id_night));
        super.initData();
    }
}
