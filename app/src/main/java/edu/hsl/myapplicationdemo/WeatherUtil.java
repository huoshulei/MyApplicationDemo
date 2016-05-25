package edu.hsl.myapplicationdemo;
//c8e95327158622fea81a828a45fca513/key
//聚合0fdb111d480794e6a0c6f1bdbfba2188/key

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.hsl.myapplicationdemo.LifeBean;
import edu.hsl.myapplicationdemo.PM25Bean;
import edu.hsl.myapplicationdemo.WeatherBean;
import edu.hsl.myapplicationdemo.WeatherInfoBean;

/**
 * Created by Administrator on 2016/5/23.
 */
public class WeatherUtil {
    public static final  String APPKEY = "0fdb111d480794e6a0c6f1bdbfba2188";
    private static final String TAG = "WeatherUtil";
    public WeatherUtil(String city_name) {
        getRequest(city_name);
    }

    private String getRequest(String cityname) {
        String            rs            = null;
        BufferedReader    reader        = null;
        StringBuffer      sb            = new StringBuffer();
        HttpURLConnection urlConnection = null;
        String            url           = "http://op.juhe.cn/onebox/weather/query?";
        String uri = Uri.parse(url).buildUpon()
                .appendQueryParameter("cityname", cityname)
                .appendQueryParameter("dtype", "")
                .appendQueryParameter("key", APPKEY)
                .build().toString();
        try {
            URL url1 = new URL(uri);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(in));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead).append("\n");
            }
            rs = sb.toString();
            Log.d(TAG, "getRequest: "+rs);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert urlConnection != null;
            urlConnection.disconnect();
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        getWeatherDataFromJson(rs, cityname);
        return rs;
    }

    private static JSONObject jsonRealTime;
    private static JSONObject jsonLife;
    private static JSONArray  jsonWeather;
    private static JSONObject jsonPM25;

    private void getWeatherDataFromJson(String jsonStr, String cityname) {
        try {
            JSONObject json = new JSONObject(jsonStr);

            String     str  = json.getString("reason");
            int        code = json.getInt("error_code");
            if (str.equals("成功") || str.equals("successed!") || code == 0) {
                JSONObject jsonResult = json.getJSONObject("result");
                //            JSONObject jsonRealTime=new JSONObject(jsonData.getString("data"));
                JSONObject jsonData = jsonResult.getJSONObject("data");
                jsonRealTime = jsonData.getJSONObject("realtime");//实时数据
                jsonLife = jsonData.getJSONObject("life");//生活指数
                jsonWeather = jsonData.getJSONArray("weather");//一周天气
                jsonPM25 = jsonData.getJSONObject("pm25");//PM25

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public PM25Bean getJsonPM25() {
        String city_name = null;
        String date_time = null;
        String curPm     = null;//污染指数
        String pm25      = null;
        String pm10      = null;
        String quality   = null;//污染等级
        String des       = null;
        try {
            JSONObject json = jsonPM25.getJSONObject("pm25");
            city_name = jsonPM25.getString("cityName");
            date_time = jsonPM25.getString("dateTime");
            curPm = json.getString("curPm");
            pm25 = json.getString("pm25");
            pm10 = json.getString("pm10");
            quality = json.getString("quality");
            des = json.getString("des");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PM25Bean bean = new PM25Bean(city_name, date_time, curPm, pm25, pm10, quality, des);
//        Log.d(TAG, "getJsonPM25: " + bean.toString());
        return bean;
    }

    public List<WeatherInfoBean> getJsonWeather() {
        List<WeatherInfoBean> bean = new ArrayList<>();
        for (int i = 0; i < jsonWeather.length(); i++) {
            String date              = null;
            String weather_day       = null;//天气
            String temperature_day   = null;//温度
            String direct_day        = null;//风向
            String power_day         = null;//风级
            String sun_up            = null;//太阳升起
            String weather_night     = null;//天气
            String temperature_night = null;//温度
            String direct_night      = null;//风向
            String power_night       = null;//风级
            String sun_down          = null;//降落
            String week              = null;
            String moon              = null;
            int    id_day            = -1;
            int    id_night          = -1;
            try {
                JSONObject data  = jsonWeather.getJSONObject(i);
                JSONObject info  = data.getJSONObject("info");
                JSONArray  night = info.getJSONArray("night");
                JSONArray  day   = info.getJSONArray("day");
                date = data.getString("date");
                weather_day = day.getString(1);
                temperature_day = day.getString(2);
                direct_day = day.getString(3);
                power_day = day.getString(4);
                sun_up = day.getString(5);
                weather_night = night.getString(1);
                temperature_night = night.getString(2);
                direct_night = night.getString(3);
                power_night = night.getString(4);
                sun_down = night.getString(5);
                week = data.getString("week");
                moon = data.getString("nongli");
                id_day = day.getInt(0);
                id_night = night.getInt(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            WeatherInfoBean infoBean = new WeatherInfoBean(date, weather_day, temperature_day,
                    direct_day, power_day, sun_up, weather_night, temperature_night,
                    direct_night, power_night, sun_down, week, moon, id_day, id_night);
            bean.add(infoBean);
        }
        return bean;
    }

    public List<LifeBean> getJsonLife() {
        LifeBean       lifeBean;
        List<LifeBean> bean      = new ArrayList<>();
        String         kongtiao  = null;
        String         yundong   = null;
        String         ziwaixian = null;
        String         ganmao    = null;
        String         xiche     = null;
        String         wuran     = null;
        String         chuanyi   = null;
        try {
            lifeBean = getString("kongtiao");
            lifeBean.title = "空调指数";
            bean.add(lifeBean);
            lifeBean = getString("yundong");
            lifeBean.title = "运动指数";
            bean.add(lifeBean);
            lifeBean = getString("ziwaixian");
            lifeBean.title = "紫外线指数";
            bean.add(lifeBean);
            lifeBean = getString("ganmao");
            lifeBean.title = "感冒指数";
            bean.add(lifeBean);
            lifeBean = getString("xiche");
            lifeBean.title = "洗车指数";
            bean.add(lifeBean);
            lifeBean = getString("wuran");
            lifeBean.title = "污染指数";
            bean.add(lifeBean);
            lifeBean = getString("chuanyi");
            lifeBean.title = "穿衣指数";
            bean.add(lifeBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @NonNull
    private LifeBean getString(String key) throws JSONException {
        LifeBean   bean;
        JSONObject info      = jsonLife.getJSONObject("info");
        JSONArray  jsonArray = info.getJSONArray(key);
        bean = new LifeBean(jsonArray.getString(0), jsonArray.getString(1));
        return bean;
    }

    public WeatherBean getRealTime() {
        String time        = null;
        String data        = null;
        String city_name   = null;
        String week        = null;
        String moon        = null;
        String direct      = null;
        String power       = null;
        String humidity    = null;
        String info        = null;
        String temperature = null;
        int    id          = -1;
        try {
            JSONObject wind    = jsonRealTime.getJSONObject("wind");
            JSONObject weather = jsonRealTime.getJSONObject("weather");
            time = jsonRealTime.getString("time");
            data = jsonRealTime.getString("date");
            city_name = jsonRealTime.getString("city_name");
            Object week_object = jsonRealTime.get("week");
            week = getWeek(week_object);
            moon = jsonRealTime.getString("moon");
            direct = wind.getString("direct");
            power = wind.getString("power");
            humidity = weather.getString("humidity");
            info = weather.getString("info");
            temperature = weather.getString("temperature");
            id = weather.getInt("img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WeatherBean bean = new WeatherBean(direct, power, time, humidity, info
                , temperature, data, city_name, week, moon, id);
        return bean;
    }

    private String getWeek(Object week_object) {
        String week = null;
        if (week_object instanceof Integer) {
            int week_int = (int) week_object;
            switch (week_int) {
                case 1:
                    week = "一";
                    break;
                case 2:
                    week = "二";
                    break;
                case 3:
                    week = "三";
                    break;
                case 4:
                    week = "四";
                    break;
                case 5:
                    week = "五";
                    break;
                case 6:
                    week = "六";
                    break;
                case 7:
                    week = "日";
                    break;
            }
        } else {
            week = week_object.toString();
        }
        return week;
    }
}
