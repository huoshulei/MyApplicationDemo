package edu.hsl.myapplicationdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/05/19.
 */
public class MyAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<TelInfo>  data;

    public MyAdapter(Context context, List data) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TelInfo getItem(int position) {
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
            convertView = inflater.inflate(R.layout.layout_item_tel_num, null);
            haber = new ViewHaber();
            haber.tv_name = (TextView) convertView.findViewById(R.id.tv_tel_name);
            haber.tv_tel = (TextView) convertView.findViewById(R.id.tv_tel_num);
            convertView.setTag(haber);
        } else {
            haber = (ViewHaber) convertView.getTag();
        }
        haber.tv_tel.setText(getItem(position).getTel_num());
        haber.tv_name.setText(getItem(position).getName());
        return convertView;
    }

    class ViewHaber {
        TextView tv_name;
        TextView tv_tel;
    }
}