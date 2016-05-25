package edu.hsl.myapplicationdemo.activity;

import android.view.View;

import edu.hsl.myapplicationdemo.base.MyActivity;
import edu.hsl.myapplicationdemo.R;

public class Sjfd1Activity extends MyActivity {

    @Override
    public void initVeiw() {
        setContentView(R.layout.activity_sjfd1);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initNext(View view) {
        startActivity(Sjfd2Activity.class);
    }

    @Override
    public void initBack(View view) {
        startActivity(MainActivity.class);
    }
}
