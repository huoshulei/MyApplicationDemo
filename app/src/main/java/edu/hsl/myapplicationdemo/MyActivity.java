package edu.hsl.myapplicationdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2016/05/18.
 */
public class MyActivity extends AppCompatActivity {
    Util util = new Util();
    static WeatherUtil mWeatherUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVeiw();
        initData();
        initEvent();
    }

    public void initData() {

    }

    public void initVeiw() {

    }

    public void initNext(View view) {

    }

    public void initBack(View view) {

    }

    public void initEvent() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myHandleMessage(msg);
        }
    };

    protected void myHandleMessage(Message msg) {

    }

    public void startActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        finish();
    }

    class Util {
        SharedPreferences preferences;

        public void putString(Context content, String key, String value) {
            preferences = getPreferences(content);
            SharedPreferences.Editor e = preferences.edit();
            e.putString(key, value);
            e.commit();
        }

        public SharedPreferences getPreferences(Context context) {
            if (preferences == null) {
                preferences = context.getSharedPreferences("SP", Context.MODE_PRIVATE);
            }
            return preferences;
        }

        public String getString(Context context, String key) {
            preferences = getPreferences(context);
            return preferences.getString(key, null);
        }

        public boolean contains(Context context, String key) {
            preferences = getPreferences(context);
            return preferences.contains(key);
        }

        public String getTelnumber(Context context, String key) {
            String       telnumber = getString(context, key);
            String[]     num       = telnumber.split("-");
            StringBuffer telnum    = new StringBuffer();
            for (String s : num) {
                telnum.append(s);
            }
            return telnum.toString();
        }

        public void putboolean(Context content, String key, boolean isopen) {
            preferences = getPreferences(content);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, isopen);
            editor.commit();
        }

        public boolean getboolean(Context context, String key) {
            preferences = getPreferences(context);
            return preferences.getBoolean(key, false);
        }
    }
}
