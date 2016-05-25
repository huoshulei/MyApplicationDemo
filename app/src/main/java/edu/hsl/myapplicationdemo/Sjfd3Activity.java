package edu.hsl.myapplicationdemo;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Sjfd3Activity extends MyActivity {
    public static final String TAG = "ddddddddddddddddddddddd";
    Button        btn_txl;
    Button        btn_txl1;
    EditText      et_telnum;
    String        et_num;
    List<TelInfo> data;
    TelInfo       info;
    ListView      lv_tel;
    MyAdapter     adapter;

    @Override
    public void initVeiw() {
        setContentView(R.layout.activity_sjfd3);
        btn_txl = (Button) findViewById(R.id.btn_txl);
        btn_txl1 = (Button) findViewById(R.id.btn_txl1);
        et_telnum = (EditText) findViewById(R.id.et_telnum);
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(util.getString(getApplicationContext(), "telnumber"))) {
            et_telnum.setHint(util.getString(getApplicationContext(), "telnumber"));
        }
        info = new TelInfo();
        data = info.getData(getApplicationContext());
        Toast.makeText(getApplicationContext(), "ddddddd" + data.size(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "initData: " + data.size());
    }

    @Override
    public void initEvent() {
        btn_txl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Sjfd3Activity.this);
                View view = View.inflate(getApplicationContext(),
                        R.layout.layout_view_tel, null);
                lv_tel = (ListView) view.findViewById(R.id.lv_tel);
                adapter = new MyAdapter(getApplicationContext(), data);
                lv_tel.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                builder.setView(view);
                final AlertDialog dialog = builder.show();
                lv_tel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        et_telnum.setText(adapter.getItem(position).getTel_num());
                        dialog.dismiss();
                    }
                });

            }
        });
        btn_txl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI), 1);
            }
        });
    }

    @Override
    public void initNext(View view) {
        et_num = et_telnum.getText().toString();
        util.putString(getApplicationContext(), "telnumber", et_num);
        startActivity(Sjfd4Activity.class);
    }

    @Override
    public void initBack(View view) {
        startActivity(Sjfd2Activity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data == null) {
                    return;
                }
                Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                cursor.moveToFirst();
                String telid = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + telid, null, null);
                while (phone.moveToNext()) {
                    et_telnum.setText(phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }
        }
    }
}
