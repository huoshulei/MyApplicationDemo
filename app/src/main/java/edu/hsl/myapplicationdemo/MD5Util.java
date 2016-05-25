package edu.hsl.myapplicationdemo;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/05/17.
 */
public class MD5Util {
    public static final String TAG = "dddd";
    StringBuffer sb;

    private String getMd5(String pwd) {
        try {
            MessageDigest md     = MessageDigest.getInstance("MD5");
            byte[]        digest = md.digest(pwd.getBytes());
            for (byte b : digest) {
                int    s         = ((b + 4) % 256) & 0xff;
                String hexString = Integer.toHexString(s);
                if (hexString.length() == 1) {
                    hexString = 0 + hexString;
                }
                sb.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        Log.d(TAG, "getMd5: 000000000000..." + sb);
        return sb + "d";
    }

    public String encode(String pwd) {
        sb = new StringBuffer();
        return getMd5(getMd5(pwd));
    }
}
