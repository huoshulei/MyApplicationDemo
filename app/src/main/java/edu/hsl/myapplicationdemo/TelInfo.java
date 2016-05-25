package edu.hsl.myapplicationdemo;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/05/19.
 */
public class TelInfo {
    public static final String TAG = "dddddddd";
    String             name;
    String             tel_num;
    ArrayList<TelInfo> data;

    public TelInfo() {
    }

    public TelInfo(String name, String tel_num) {
        this.name = name;
        this.tel_num = tel_num;
    }

    public String getName() {
        return name;
    }

    public String getTel_num() {
        return tel_num;
    }
//
//    public List<TelInfo> getData() {
//        data = new ArrayList<>();
//        data.add(new TelInfo("蔡伟", "110"));
//        data.add(new TelInfo("蔡伟", "111"));
//        data.add(new TelInfo("蔡伟", "112"));
//        data.add(new TelInfo("蔡伟", "113"));
//        data.add(new TelInfo("蔡伟", "114"));
//        data.add(new TelInfo("蔡伟", "115"));
//        data.add(new TelInfo("蔡伟", "116"));
//        return data;
//    }

    public ArrayList<TelInfo> getData(Context context) {
        Log.d(TAG, "getData:1 " + name);
        data = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int telid  = 0;
        int nameid = 0;
        assert cursor != null;
        if (cursor.getCount() > 0) {
            telid = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameid = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while (cursor.moveToNext()) {
            Log.d(TAG, "getData:2 " + name);
            String name   = cursor.getString(nameid);
            String telnum = cursor.getString(telid);
            Cursor phones = context.getContentResolver()
                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone
                                    .CONTACT_ID + "=" + telnum,
                            null, null);
            int phoneid = 0;
            assert phones != null;
            if (phones.getCount() > 0) {
                Log.d(TAG, "getData:3 " + name);
                phoneid = phones
                        .getColumnIndex(ContactsContract
                                .CommonDataKinds.Phone.NUMBER);
            }
            String number = null;
            while (phones.moveToNext()) {
                number = phones.getString(phoneid);
                TelInfo info = new TelInfo(name, number);
                data.add(info);
                Log.d(TAG, "getData:4 " + name);
            }

//            phones.close();
        }
        cursor.close();
        return data;
    }
}
