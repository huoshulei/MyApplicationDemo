package edu.hsl.myapplicationdemo;

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
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    private static final String TAG = "MyService";
    static MyActivity.Util util;
//    String          messageBody;

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
        String sms = intent.getStringExtra("SMS");
        String telnumber = util.getTelnumber(getApplicationContext(), "telnumber");
        Log.d(TAG, "deleteSMS:000telnumber>> "+telnumber);
//        if (!SmsWriteOpUtil.isWriteEnabled(getApplicationContext())) {
//            SmsWriteOpUtil.setWriteEnabled(
//                    getApplicationContext(), true);
//        }
        deleteSMS(telnumber);
        Log.d(TAG, "onStartCommand:sms>> " + sms);
        switch (sms) {
            case "位置":
                Log.d(TAG, "onStartCommand: 1" + sms);
                Toast.makeText(MyService.this, "位置", Toast.LENGTH_SHORT).show();
                break;
            case "警报":
                Log.d(TAG, "onStartCommand: 2" + sms);
                Toast.makeText(MyService.this, "警报", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        stopSelf();
//        stopSelfResult(-1);
        return START_NOT_STICKY;
    }

    private void deleteSMS(String telnum) {
        Uri    uri    = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null, "read=" + 0, null, null);
        while (cursor.moveToNext()) {
            String phone = cursor.getString(cursor.getColumnIndex("address")).trim();
            Log.d(TAG, "deleteSMS:00phone "+phone);
            if (phone.equals(telnum)) {
                long id = cursor.getLong(0);
                Log.d(TAG, "deleteSMS:00id >>"+id);
                int delete =getContentResolver()
                        .delete(Telephony.Sms.CONTENT_URI, "_id="+id, null);
                Log.d(TAG, "deleteSMS:000 delete>>"+delete);
            }
        }
        cursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static class MyReceiver extends BroadcastReceiver {

        String messageBody;

        @Override
        public void onReceive(Context context, Intent intent) {
            util = new MyActivity().new Util();
//            TelephonyManager tm= (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            }
            Object[] pdus = (Object[]) extras.get("pdus");
            for (Object pdu : pdus) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
                String originatingAddress = message.getOriginatingAddress();//获取发送人号码
                String   telnum = util.getTelnumber(context, "telnumber");
                if (originatingAddress.equals(telnum)) {
                    messageBody = message.getMessageBody();//获取短信内容
                    Intent intent1 = new Intent(context.getApplicationContext(), MyService.class);
                    intent1.putExtra("SMS", messageBody);
                    context.startService(intent1);
                    this.abortBroadcast();
                }

            }
        }
    }
}
