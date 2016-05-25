package edu.hsl.myapplicationdemo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.hsl.myapplicationdemo.util.MD5Util;
import edu.hsl.myapplicationdemo.base.MyActivity;
import edu.hsl.myapplicationdemo.R;

public class MainActivity extends MyActivity {
    boolean isforstload;
    Util    util;//工具
    MD5Util md5;//加密

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_        = (Button) findViewById(R.id.btn_);
        Button btn_weather = (Button) findViewById(R.id.btn_weather);
        SharedPreferences preferences
                = getSharedPreferences("isforstload", Context.MODE_PRIVATE);
        isforstload = preferences.getBoolean("is", true);
        util = new Util();
        md5 = new MD5Util();
        assert btn_weather != null;
        btn_weather.setOnClickListener(getListener());
        if (btn_ != null) {
            btn_.setOnClickListener(getListener());
        }
    }

    @NonNull
    private View.OnClickListener getListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_weather:
                        startActivity(WeatherActivity.class);
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                weatherUtil = new WeatherUtil("济南");
////                            Log.d(TAG, "onClick: " + weatherUtil.getRealTime());
//                                try {
//                                    weatherUtil.getJsonPM25();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }.start();
                        break;
                    case R.id.btn_:
                        if (isforstload) {
                            zhuce();
                        } else {
                            denglu();
                        }
                        break;
                }
            }
        };
    }

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
        }
    };

    private void zhuce() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View                view    = View.inflate(getApplicationContext(), R.layout.demo_zhuce, null);
        builder.setView(view);
        final AlertDialog dialog        = builder.show();
        final EditText    editText_name = (EditText) view.findViewById(R.id.et_name);
        final EditText    editText_pwd  = (EditText) view.findViewById(R.id.et_pwd);
        final EditText    editText_pwds = (EditText) view.findViewById(R.id.et_pwds);
        Button            btn_enroll    = (Button) view.findViewById(R.id.btn_enroll);
        Button            btn_back      = (Button) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_name.getText().toString().trim();
                String pwd  = editText_pwd.getText().toString().trim();
                String pwds = editText_pwds.getText().toString().trim();
                if (util.contains(getApplicationContext(), name)) {
                    Toast.makeText(getApplicationContext(), "用户名已存在", Toast.LENGTH_SHORT).show();
                    editText_name.requestFocus();
                    return;
                }
                if (pwd == null || pwd.equals("")) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.equals(pwds)) {
                    baocun();
                    pwd = md5.encode(pwd);
                    util.putString(getApplicationContext(), name, pwd);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "注册成功" + pwd, Toast.LENGTH_SHORT).show();
                    startActivity(Sjfd1Activity.class);
                } else {
                    editText_pwds.setBackgroundResource(R.color.colorAccent);
                    Toast.makeText(getApplicationContext(), "两次密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
    }

    private void denglu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View                view    = View.inflate(getApplicationContext(), R.layout.demo_denglu, null);
        builder.setView(view);
        final AlertDialog dialog        = builder.show();
        final EditText    editText_name = (EditText) view.findViewById(R.id.et_enorll_name);
        final EditText    editText_pwd  = (EditText) view.findViewById(R.id.et_enorll_pwd);
        Button            btn_enroll    = (Button) view.findViewById(R.id.btn_enorll_dengl);
        Button            btn_back      = (Button) view.findViewById(R.id.btn_enorll_zhuce);
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_name.getText().toString().trim();
                String pwd  = editText_pwd.getText().toString().trim();
                pwd = md5.encode(pwd);
                String pwds = util.getString(getApplicationContext(), name);
                if (name == null || name.equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.equals(pwds)) {
                    Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    startActivity(Sjfd1Activity.class);
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhuce();
                dialog.dismiss();
            }
        });
    }

    private void baocun() {
        SharedPreferences        preferences = getSharedPreferences("isforstload", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor      = preferences.edit();
        editor.putBoolean("is", false);
        editor.commit();
    }


}