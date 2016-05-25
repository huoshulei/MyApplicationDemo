package edu.hsl.myapplicationdemo.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import edu.hsl.myapplicationdemo.base.MyActivity;

public class MyService extends Service {
    static MyActivity.Util util;

    public MyService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        util = new MyActivity().new Util();
        String sms       = intent.getStringExtra("SMS");
        String telnumber = util.getTelnumber(getApplicationContext(), "telnumber");
//        if (!SmsWriteOpUtil.isWriteEnabled(getApplicationContext())) {
//            SmsWriteOpUtil.setWriteEnabled(
//                    getApplicationContext(), true);
//        }
        deleteSMS(telnumber);
        switch (sms) {//根据获得的短信内容执行相关操作
            case "位置":
                Toast.makeText(MyService.this, "位置", Toast.LENGTH_SHORT).show();
                break;
            case "警报":
                Toast.makeText(MyService.this, "警报", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        stopSelf();//结束当前Service
        return START_NOT_STICKY;
    }

    /**
     * 删除短信 貌似无法实现
     */
    private void deleteSMS(String telnum) {
        Uri    uri    = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null, "read=" + 0, null, null);
        while (cursor.moveToNext()) {
            String phone = cursor.getString(cursor.getColumnIndex("address")).trim();
            if (phone.equals(telnum)) {
                long id = cursor.getLong(0);
                int delete = getContentResolver()
                        .delete(Telephony.Sms.CONTENT_URI, "_id=" + id, null);
            }
        }
        cursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 创建一个广播接收器 静态注册实现监听短信广播
     */
    public static class MyReceiver extends BroadcastReceiver {

        String messageBody;

        @Override
        public void onReceive(Context context, Intent intent) {
            util = new MyActivity().new Util();
//            TelephonyManager tm= (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            Bundle extras = intent.getExtras();//获取广播信息
            if (extras == null) {
                return;
            }
            Object[] pdus = (Object[]) extras.get("pdus");
            for (Object pdu : pdus) {
                SmsMessage message            = SmsMessage.createFromPdu((byte[]) pdu);
                String     originatingAddress = message.getOriginatingAddress();//获取发送人号码
                String     telnum             = util.getTelnumber(context, "telnumber");//获取绑定号码
                if (originatingAddress.equals(telnum)) {//判断发送人和绑定号码是否一致
                    messageBody = message.getMessageBody();//获取短信内容
                    Intent intent1 = new Intent(context.getApplicationContext(), MyService.class);
                    intent1.putExtra("SMS", messageBody);
                    context.startService(intent1);//启动Service
                    this.abortBroadcast();//中断广播>>>貌似也无法实现
                }

            }
        }
    }
}
