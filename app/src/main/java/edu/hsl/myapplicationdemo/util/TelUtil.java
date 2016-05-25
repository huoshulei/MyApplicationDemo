package edu.hsl.myapplicationdemo.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/05/19.
 * 读取手机电话本信息
 */
public class TelUtil {
    String             name;
    String             tel_num;
    ArrayList<TelUtil> data;

    public TelUtil() {
    }

    public TelUtil(String name, String tel_num) {
        this.name = name;
        this.tel_num = tel_num;
    }

    public String getName() {
        return name;
    }

    public String getTel_num() {
        return tel_num;
    }

    public ArrayList<TelUtil> getData(Context context) {
        data = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int telid  = 0;
        int nameid = 0;
        assert cursor != null;
        //取得姓名和_ID对应的index
        if (cursor.getCount() > 0) {
            telid = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameid = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        //根据index获取对应的姓名和_id
        while (cursor.moveToNext()) {
            String name   = cursor.getString(nameid);
            String telnum = cursor.getString(telid);
            Cursor phones = context.getContentResolver()
                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone
                                    .CONTACT_ID + "=" + telnum,
                            null, null);
            int phoneid = 0;
            assert phones != null;
            //获得电话号码对应的index
            if (phones.getCount() > 0) {
                phoneid = phones
                        .getColumnIndex(ContactsContract
                                .CommonDataKinds.Phone.NUMBER);
            }
            String number = null;
            while (phones.moveToNext()) {
                number = phones.getString(phoneid);
                TelUtil info = new TelUtil(name, number);
                data.add(info);
            }

        }
        cursor.close();
        return data;
    }
}
