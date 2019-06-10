package com.example.saravanamurali.farmersgen.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.File;
import java.util.HashMap;

public class SessionManager {

    public static final String KEY_SERVICE_FIREBASE_UPDATE = "firebase_update_service";
    public static final String KEY_SERVICE_LOCATION = "location_update_service";
    //public static final String KEY_firebase_token = "firebase_token";


    public static final String KEY_brand_id = "brand_id";

    public static final String KEY_product_name = "product_name";
    public static final String KEY_product_rating = "product_rating";
    public static final String KEY_current_user_id = "current_user_id";

    public static final String KEY_FCM_TOKEN="firebase_token";



    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String PREF_NAME = "Android";
    private static final String FIREBASE_PREF_NAME = "Android_Firebase";


    private static final String SERVICE_PREF_NAME = "Android_Service";
    // public static final String KEY_mail_copy_county_id = "transl_mail_copy_country_id";
    static Context _context;
    static SharedPreferences.Editor editor;
    static SharedPreferences.Editor firebase_editor;
    static SharedPreferences.Editor service_editor;
    int PRIVATE_MODE = 0;
    int flag = 0;
    SharedPreferences pref;
    SharedPreferences firebase_pref;
    SharedPreferences service_pref;


    public SessionManager(Context paramContext){

        _context = paramContext;
        this.pref = _context.getSharedPreferences(PREF_NAME,
                this.PRIVATE_MODE);
        editor = this.pref.edit();

        this.firebase_pref = _context.getSharedPreferences(FIREBASE_PREF_NAME,
                this.PRIVATE_MODE);

        firebase_editor = this.firebase_pref.edit();

        this.service_pref = _context.getSharedPreferences(SERVICE_PREF_NAME,
                this.PRIVATE_MODE);
        service_editor = this.service_pref.edit();

    }

    public void storeFCMToken(String token){

        firebase_editor.putString(KEY_FCM_TOKEN,token);

        firebase_editor.commit();

    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public void createLoginSession(String user_name, String email, String phone_no, String auth_code,String country_code,String dial_code) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */
     /*   editor.putString(KEY_first_name, user_name);
        editor.putString(KEY_email, email);
        editor.putString(KEY_mobile, phone_no);
        editor.putString(KEY_access_token, auth_code);
        editor.putString(KEY_country_code, country_code);
        editor.putString(KEY_dial_code, dial_code);*/


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public HashMap<String,String> getFcmToken(){
        HashMap<String, String> fcmToken = new HashMap<String, String>();

        fcmToken.put(KEY_FCM_TOKEN,firebase_pref.getString(KEY_FCM_TOKEN,null));

        return fcmToken;


    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        /*
         * user.put(KEY_name, pref.getString(KEY_name, null));
         *
         * user.put(KEY_image, pref.getString(KEY_image, null));
         * user.put(KEY_email, pref.getString(KEY_email, null));
         */


        user.put(KEY_brand_id, pref.getString(KEY_brand_id, null));
        user.put(KEY_product_name, pref.getString(KEY_product_name, null));
        user.put(KEY_product_rating, pref.getString(KEY_product_rating, null));
        user.put(KEY_current_user_id, pref.getString(KEY_current_user_id, null));


        //user.put(KEY_to_string_first, pref.getString(KEY_to_string_first, null));



        // user email id

        // return user
        return user;
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }







    public void create_products(String current_user_id,String brand_id, String product_name,String product_rating) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */


        editor.putString(KEY_current_user_id, current_user_id);
        editor.putString(KEY_brand_id, brand_id);
        editor.putString(KEY_product_name, product_name);
        editor.putString(KEY_product_rating, product_rating);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void clear_to_string_values() {
        // Clearing all data from Shared Preferences
        // editor.clear();
        // editor.commit();


        //  editor.remove(KEY_to_string);


        editor.apply();

        // After logout redirect user to Loing Activity
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

        // Add new Flag to start new Activity
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

    }

}
