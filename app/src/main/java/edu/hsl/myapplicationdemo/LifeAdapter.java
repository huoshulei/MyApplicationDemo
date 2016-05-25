package edu.hsl.myapplicationdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class LifeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<LifeBean> data;

    public LifeAdapter(Context context, List<LifeBean> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public LifeBean getItem(int position) {
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
            convertView = inflater.inflate(R.layout.layout_life_weather, null);
            haber.title = (TextView) convertView.findViewById(R.id.tv_title);
            haber.exp = (TextView) convertView.findViewById(R.id.tv_exp);
            haber.miute = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(haber);
        } else {
            haber = (ViewHaber) convertView.getTag();
        }
        haber.title.setText(getItem(position).title);
        haber.exp.setText(getItem(position).exp);
        haber.miute.setText(getItem(position).content);
        return convertView;
    }

    class ViewHaber {
        TextView title;
        TextView exp;
        TextView miute;
    }
}
