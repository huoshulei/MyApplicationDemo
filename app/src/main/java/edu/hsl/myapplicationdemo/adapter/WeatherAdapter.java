package edu.hsl.myapplicationdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.hsl.myapplicationdemo.R;
import edu.hsl.myapplicationdemo.bean.WeatherInfoBean;

/**
 * Created by Administrator on 2016/5/24.
 * 天气适配器
 */
public class WeatherAdapter extends BaseAdapter {
    LayoutInflater        inflater;
    List<WeatherInfoBean> data;

    public WeatherAdapter(Context context, List<WeatherInfoBean> data) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public WeatherInfoBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHaber haber;
        if (convertView == null) {
            haber = new ViewHaber();
            convertView = inflater.inflate(R.layout.layout_list_weather, null);
            haber.data = (TextView) convertView.findViewById(R.id.tv_date);
            haber.weather = (TextView) convertView.findViewById(R.id.tv_weather);
            haber.temperature = (TextView) convertView.findViewById(R.id.tv_temperature);
            haber.direct = (TextView) convertView.findViewById(R.id.tv_direct);
            haber.power = (TextView) convertView.findViewById(R.id.tv_power);
            haber.week = (TextView) convertView.findViewById(R.id.tv_week);
            convertView.setTag(haber);
        } else {
            haber = (ViewHaber) convertView.getTag();
        }
        haber.data.setText(getItem(position).data);
        haber.weather.setText(getItem(position).weather_day);
        haber.temperature.setText("温度" + getItem(position)
                .temperature_night + "~" + getItem(position).temperature_day + "℃");
        haber.direct.setText(getItem(position).direct_day);
        haber.power.setText(getItem(position).power_day);
        haber.week.setText("星期" + getItem(position).week);
        return convertView;
    }

    class ViewHaber {
        TextView data;//日期
        TextView weather;//天气
        TextView temperature;//温度
        TextView direct;//风向
        TextView power;//风级
        TextView week;//星期
    }
}
