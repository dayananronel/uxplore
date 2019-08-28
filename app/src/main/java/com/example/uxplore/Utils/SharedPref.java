package com.example.uxplore.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    //APP
    private static final String APP_ID = "uxplore";

    //LOGIN STATUS
    public static final  String KEY_ISLOGIN = "isLogin";
    public static final  String KEY_NOTIFYENABLED = "isNotifyEnabled";

    public static final  String KEY_ID = "ID";
    public static final  String KEY_UserID = "UserID";
    public static final  String KEY_UserTypeID = "UserTypeID";
    public static final  String KEY_AMOUNTCONSUMED = "amountConsumed";
    public static final  String KEY_EmailAddress = "EmailAddress";
    public static final  String KEY_IPAddress = "IPAddress";
    public static final  String KEY_LastLogin = "LastLogin";
    public static final  String KEY_MobileNumber = "MobileNumber";
    public static final  String KEY_Status = "Status";
    public static final  String KEY_Package = "package";
    public static final  String KEY_PACKAGEID = "packageid";
    public static final  String KEY_PAYMENTID = "paymentid";



    public static void saveLoginStatus(Context context, String status) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_ISLOGIN, status);
        editor.apply();
    }

    public static String getLoginStatus(Context context) {
        return getStringPreference(context, KEY_ISLOGIN);
    }
    public static void saveAmountConsumed(Context context, String amountConsumed) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_AMOUNTCONSUMED, amountConsumed);
        editor.apply();
    }

    public static String getKeyAmountconsumed(Context context) {
        return getStringPreference(context, KEY_AMOUNTCONSUMED);
    }

    public static void savePaymentID(Context context, String paymentid) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PAYMENTID, paymentid);
        editor.apply();
    }

    public static String getKeyPaymentid(Context context) {
        return getStringPreference(context, KEY_PAYMENTID);
    }


    public static void saveID(Context context, String id) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_ID, id);
        editor.apply();
    }

    public static String getID(Context context) {
        return getStringPreference(context, KEY_ID);
    }

    public static void saveNotifyConfig(Context context, String status) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_NOTIFYENABLED, status);
        editor.apply();
    }

    public static String getNotifyConfig(Context context) {
        return getStringPreference(context, KEY_NOTIFYENABLED);
    }


    public static void saveEmailAdd(Context context, String emailadd) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_EmailAddress, emailadd);
        editor.apply();
    }

    public static String getEmailAdd(Context context) {
        return getStringPreference(context, KEY_EmailAddress);
    }

    public static void saveMobileNumber(Context context, String mobile) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_MobileNumber, mobile);
        editor.apply();
    }

    public static String getMobileNumber(Context context) {
        return getStringPreference(context, KEY_MobileNumber);
    }

    public static void saveUserID(Context context, String userid) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_UserID, userid);
        editor.apply();
    }

    public static String getUserID(Context context) {
        return getStringPreference(context, KEY_UserID);
    }

    public static void savePackageID(Context context, String packageid) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PACKAGEID, packageid);
        editor.apply();
    }

    public static String getPackageID(Context context) {
        return getStringPreference(context, KEY_PACKAGEID);
    }

    public static void saveUserType(Context context, String usertype) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_UserTypeID, usertype);
        editor.apply();
    }

    public static void savePackage(Context context, String packages,String key) {

        key = KEY_Package;

        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_Package, packages);
        editor.apply();
    }

    public static String getPackage(Context context) {
        return getStringPreference(context, KEY_Package);
    }

    public static String getUserType(Context context) {
        return getStringPreference(context, KEY_UserTypeID);
    }

    private static String getStringPreference(Context context, String key) {
        String value = "";
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        value = prefs.getString(key, "");
        return value;
    }




    public static void clearPreferences(Context context){

        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        editor.remove(APP_ID);
        editor.apply();

        editor.remove(KEY_NOTIFYENABLED);
        editor.apply();

        editor.remove(KEY_ISLOGIN);
        editor.apply();

        editor.remove(KEY_EmailAddress);
        editor.apply();

        editor.remove(KEY_UserID);
        editor.apply();

        editor.remove(KEY_ID);
        editor.apply();

        editor.remove(KEY_UserTypeID);
        editor.apply();

        editor.remove(KEY_MobileNumber);
        editor.apply();

        editor.remove(KEY_Package);
        editor.apply();

        editor.remove(KEY_PAYMENTID);
        editor.apply();

    }

    public static void clearPref(Context context,String key){

        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.remove(key);
        editor.apply();

    }
}

