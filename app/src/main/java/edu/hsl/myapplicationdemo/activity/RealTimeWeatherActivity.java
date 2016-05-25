package edu.hsl.myapplicationdemo.activity;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import edu.hsl.myapplicationdemo.adapter.LifeAdapter;
import edu.hsl.myapplicationdemo.bean.LifeBean;
import edu.hsl.myapplicationdemo.base.MyActivity;
import edu.hsl.myapplicationdemo.bean.PM25Bean;
import edu.hsl.myapplicationdemo.R;
import edu.hsl.myapplicationdemo.bean.WeatherBean;
import edu.hsl.myapplicationdemo.util.ImageUtil;

/**
 * 实时天气数据 天气主页
 */
public class RealTimeWeatherActivity extends MyActivity {
    ListView       lv_life;
    TextView       tv_direct;//风向
    TextView       tv_power;//风级
    TextView       tv_time;//更新时间
    TextView       tv_humidity;//湿度
    TextView       tv_weather;//天气
    TextView       tv_temperature;//温度
    TextView       tv_date;//日期
    TextView       tv_city_name;//地区
    TextView       tv_week;//星期
    TextView       tv_moon;//农历日期
    ImageView      imageView;
    LifeAdapter    adapter;
    List<LifeBean> data;
    TextView       tv_data_time;
    TextView       tv_curpm;
    TextView       tv_pm25;
    TextView       tv_pm10;
    TextView       tv_des;
    TextView       tv_quality;

    @Override
    public void initVeiw() {
        setContentView(R.layout.activity_real_time_weather);
        lv_life = (ListView) findViewById(R.id.lv_life);
        tv_direct = (TextView) findViewById(R.id.tv_direct);//风向
        tv_power = (TextView) findViewById(R.id.tv_power);//风级
        tv_time = (TextView) findViewById(R.id.tv_time);//更新时间
        tv_humidity = (TextView) findViewById(R.id.tv_humidity);//湿度
        tv_weather = (TextView) findViewById(R.id.tv_weather);//天气
        tv_temperature = (TextView) findViewById(R.id.tv_temperature);//温度
        tv_date = (TextView) findViewById(R.id.tv_date);//日期
        tv_city_name = (TextView) findViewById(R.id.tv_city_name);//地区
        tv_week = (TextView) findViewById(R.id.tv_week);//星期
        tv_moon = (TextView) findViewById(R.id.tv_moon);//农历日期
        imageView = (ImageView) findViewById(R.id.imageView);
        tv_data_time = (TextView) findViewById(R.id.tv_data_time);
        tv_curpm = (TextView) findViewById(R.id.tv_cur_pm);
        tv_pm25 = (TextView) findViewById(R.id.tv_pm25);
        tv_pm10 = (TextView) findViewById(R.id.tv_pm10);
        tv_des = (TextView) findViewById(R.id.tv_des);
        tv_quality = (TextView) findViewById(R.id.tv_quality);
    }

    @Override
    public void initData() {

        WeatherBean realTime = mWeatherUtil.getRealTime();
        tv_direct.setText(realTime.direct);//风向
        tv_power.setText(realTime.power);//风级
        tv_time.setText("更新时间" + realTime.time);//更新时间
        tv_humidity.setText("相对湿度" + realTime.humidity + "%");//湿度
        tv_weather.setText(realTime.weather);//天气
        tv_temperature.setText("温度" + realTime.temperature + "℃");//温度
        tv_date.setText(realTime.date);//日期
        tv_city_name.setText(realTime.city_name);//地区
        tv_week.setText("星期" + realTime.week);//星期
        tv_moon.setText("农历" + realTime.moon);//农历日期
        imageView.setImageResource(ImageUtil.getImageDay(realTime.id));
        PM25Bean bean = mWeatherUtil.getJsonPM25();
        tv_data_time.setText("更新时间:" + bean.dateTime);
        tv_curpm.setText("污染指数:" + bean.curPm);
        tv_pm25.setText("PM2.5: " + bean.pm25);
        tv_pm10.setText("PM10:  " + bean.pm10);
        tv_des.setText(bean.des);
        tv_quality.setText("污染等级:" + bean.quality);
        data = mWeatherUtil.getJsonLife();
        adapter = new LifeAdapter(getApplicationContext(), data);
        lv_life.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }
}
