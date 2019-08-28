package com.example.uxplore.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunctions {

    public static int getConnectivityStatus(Context context) {

        int NETWORK = 0;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.d("activeNetwork", "wifi");
                return 1;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.d("activeNetwork", "mobile");
                return 2;
            } else {
                Log.d("activeNetwork", "no active");
                return 0;
            }
        } else {
            Log.d("activeNetwork", "bull");
            return 0;
        }
    }


    public static String commaFormatter(String number) {
        if (number != null) {
            String pattern = "#,###,##0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            return decimalFormat.format(Double.parseDouble(number));
        } else {
            return "";
        }
    }
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static String parseXML(String data, String nametag) {
        String result = "";
        int startpoint;
        int endpoint;

        //getting the starting point
        startpoint = data.indexOf("<" + nametag + ">");
        //getting the endpoint
        endpoint = data.indexOf("</" + nametag + ">");
        if (startpoint == -1 || endpoint == -1) {
            //return empty
            result = "NONE";
        } else {
            int starttaglen = nametag.length() + 2;
            //returning the extracted data
            result = data.substring(startpoint + starttaglen, endpoint);
        }

        return result;
    }

    public static String parseJSON(String json, String key) {
        String result = "";
        try {
            JSONObject obj = new JSONObject(json);
            result = obj.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String capitalizeWord(String word) {
        if (word != null) {
            if (word.length() > 0) {
                StringBuilder builder = new StringBuilder();
                try {

                    String[] cap_word_arr = word.toLowerCase().split(" ");

                    for (String s : cap_word_arr) {
                        String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                        builder.append(cap + " ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return builder.toString();

            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    public static String currencyFormatter(String number) {

        String pattern = "#,###,##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        try {
            return decimalFormat.format(Double.parseDouble(number));
        } catch (Exception e) {
            e.printStackTrace();
            return number;
        }
    }

    public static boolean requestPermission(Activity context){
        boolean isGranted = false;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.NFC)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_PHONE_STATE) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(context,
                            Manifest.permission.NFC) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(context,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.NFC,Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }
            }
        } else {
            // Permission has already been granted
            isGranted = true;
        }
        return isGranted;
    }
    public static String trimCommaOfString(String string) {
//        String returnString;
        if(string.contains(",")){
            return string.replace(",","");}
        else {
            return string;
        }

    }

}