package edu.hsl.myapplicationdemo.activity;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import edu.hsl.myapplicationdemo.util.ImageUtil;
import edu.hsl.myapplicationdemo.base.MyActivity;
import edu.hsl.myapplicationdemo.R;
import edu.hsl.myapplicationdemo.adapter.WeatherAdapter;
import edu.hsl.myapplicationdemo.bean.WeatherBean;
import edu.hsl.myapplicationdemo.bean.WeatherInfoBean;
import edu.hsl.myapplicationdemo.util.WeatherUtil;

public class WeatherActivity extends MyActivity {


    ListView       lv_weather;
    RelativeLayout rl_real;
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
    WeatherAdapter adapter;

    @Override
    public void initVeiw() {
        setContentView(R.layout.activity_weather);
        lv_weather = (ListView) findViewById(R.id.lv_weather);
        rl_real = (RelativeLayout) findViewById(R.id.rl_real);
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

    }

    /**
     * 更新UI
     */
    @Override
    protected void myHandleMessage(Message msg) {

        switch (msg.what) {
            case 0:
                rl_real.setBackgroundResource(R.color.weatherBackground);
                WeatherBean realTime = (WeatherBean) msg.obj;
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
                break;
            case 1:
                List<WeatherInfoBean> data = (List<WeatherInfoBean>) msg.obj;
                adapter = new WeatherAdapter(getApplicationContext(), data);
                lv_weather.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected void onResume() {
        //new 一个线程进网络访问
        new Thread() {
            @Override
            public void run() {
                mWeatherUtil = new WeatherUtil("济南");
                WeatherBean realTime = mWeatherUtil.getRealTime();
                Message     message  = new Message();
                message.what = 0;
                message.obj = realTime;
                handler.sendMessage(message);
                Message               message1 = new Message();
                List<WeatherInfoBean> data     = mWeatherUtil.getJsonWeather();
                message1.what = 1;
                message1.obj = data;
                handler.sendMessage(message1);
            }
        }.start();


        super.onResume();
    }

    @Override
    public void initEvent() {
        rl_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeatherActivity.this, RealTimeWeatherActivity.class));
            }
        });
        lv_weather.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**根据点击item的id进行传参跳转*/
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WeatherActivity.this, WeatherWeekActivity.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
        super.initEvent();
    }
}
