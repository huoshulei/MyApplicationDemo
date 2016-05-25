package edu.hsl.myapplicationdemo.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import edu.hsl.myapplicationdemo.base.MyActivity;
import edu.hsl.myapplicationdemo.R;

/**
 * 是否开启防盗功能
 */
public class Sjfd4Activity extends MyActivity {
    boolean      isopen;
    CheckBox     cb_open;
    LinearLayout ll_open;

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initVeiw() {
        setContentView(R.layout.activity_sjfd4);
        cb_open = (CheckBox) findViewById(R.id.checkBox);
        ll_open = (LinearLayout) findViewById(R.id.ll_open);
        isopen = util.getboolean(getApplicationContext(), "isopen");
        super.initVeiw();
    }

    @Override
    public void initEvent() {
        ll_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isopen) {
                    isopen = false;
                    cb_open.setChecked(isopen);
                } else {
                    isopen = true;
                    cb_open.setChecked(isopen);
                }
            }
        });
        super.initEvent();
    }

    @Override
    public void initBack(View view) {
        startActivity(Sjfd3Activity.class);
        super.initBack(view);
    }

    @Override
    public void initNext(View view) {
        util.putboolean(getApplicationContext(), "isopen", isopen);
        startActivity(MainActivity.class);
        super.initNext(view);
    }
}
