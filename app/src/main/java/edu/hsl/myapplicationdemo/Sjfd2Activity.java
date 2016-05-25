package edu.hsl.myapplicationdemo;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Sjfd2Activity extends MyActivity {
    LinearLayout ll_sim;
    ImageView    iv_icon;
    boolean      is_simbind;
    Util util = new Util();
    String simSerialNumber;

    @Override
    public void initVeiw() {
        setContentView(R.layout.activity_sjfd2);
        simSerialNumber = util.getString(getApplicationContext(), "is_simbind");
        this.is_simbind = TextUtils.isEmpty(simSerialNumber);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        if (this.is_simbind) {
            iv_icon.setImageResource(R.mipmap.ic_launcher1);

        } else {
            iv_icon.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public void initData() {

        ll_sim = (LinearLayout) findViewById(R.id.ll_sim);

        ll_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_simbind) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    is_simbind = false;
                    simSerialNumber = tm.getSimSerialNumber();
                    iv_icon.setImageResource(R.mipmap.ic_launcher);
                } else {
                    is_simbind = true;
                    simSerialNumber = "";
                    iv_icon.setImageResource(R.mipmap.ic_launcher1);
                }
            }
        });

    }

    @Override
    public void initNext(View view) {
        util.putString(getApplicationContext(), "is_simbind", simSerialNumber);
        startActivity(Sjfd3Activity.class);
    }

    @Override
    public void initBack(View view) {
        startActivity(Sjfd1Activity.class);
    }
}
