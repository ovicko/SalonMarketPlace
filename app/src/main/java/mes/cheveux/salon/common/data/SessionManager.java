/*
 * Copyright (c) 2019. AMWOLLO VICTOR <https://ovicko.com>
 */

package mes.cheveux.salon.common.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

import mes.cheveux.salon.MainActivity;
import mes.cheveux.salon.common.Constants;
import mes.cheveux.salon.ui.account.AccountActivity;

public class SessionManager {

    private static final String KEY_TELEPHONE = "customer_phone";
    private static final String KEY_USER_EMAIL = "customer_email";
    private static String TAG = SessionManager.class.getSimpleName();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared preferences file name
    private static final String PREF_NAME = Constants.PREF_MAIN_KEY;
    private static final String KEY_IS_LOGGEDIN = "loggedIn";
    private static final String CATEGORY_LAST_LOADED = "category_last_loaded";
    private static final String KEY_USER_PROFILE_IMAGE = "profile_image";
    private static final String KEY_APP_VERSION = "app_cheveux_mes_v1";

    public static final String KEY_TOKEN = "auth_key";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USERNAME = "username";


    public static final String KEY_LAST_LATITUDE = "last_latitude";
    public static final String KEY_LAST_LONGITUDE = "last_longitude";
    public static final String KEY_LAST_NAME = "last_place_name";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void setProfileImage(String image) {
        editor.putString(KEY_USER_PROFILE_IMAGE,image);
        editor.commit();
        Log.d(TAG, "Image string saved");
    }
    public String getProfileImage(){
        Log.d(KEY_USER_PROFILE_IMAGE, "user_image");
        return pref.getString(KEY_USER_PROFILE_IMAGE,"");
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified to "+ isLoggedIn );
    }

    public void setCategoryLastLoaded(){
        long dateNow = new Date().getTime();
        editor.putLong(CATEGORY_LAST_LOADED,dateNow);
        editor.commit();
        Log.d(CATEGORY_LAST_LOADED, "TIME "+ dateNow );
    }

    public void setParamLastLoaded(String param){
        long dateNow = new Date().getTime();
        editor.putLong(param,dateNow);
        editor.commit();
        Log.d(param, "TIME "+ dateNow );
    }

    public boolean reloadCategories(){
        boolean valid = false;
        long dateNow = new Date().getTime();
        long lastTime = pref.getLong(CATEGORY_LAST_LOADED,0);
        long longDiffInMinutes = (dateNow - lastTime)/(60 * 1000) % 60;

        if(longDiffInMinutes > 5 || lastTime == 0 ){
            valid = true;
        }

        return  valid;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }


    public int getUserId(){
        String supplier = pref.getString(KEY_USER_ID,"0");
        return  Integer.parseInt(supplier);
    }

    public String getUserAuthKey(){
       return pref.getString(KEY_TOKEN,"0");
    }

    public String getCustomerPhone(){
       return pref.getString(KEY_TELEPHONE,"Not set");
    }

    public String getUsername(){
        return pref.getString(KEY_USERNAME,"unknown");
    }

    public String getUserEmail(){
        return pref.getString(KEY_USER_EMAIL,"not set");
    }

    public void createSession(String phone,String username,String email, String authkey,String supplierId){
        editor.putBoolean(KEY_IS_LOGGEDIN, true);
        editor.putString(KEY_TOKEN, authkey);
        editor.putString(KEY_TELEPHONE, phone);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_ID, supplierId);
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
    }

    public void updateSession(String phone,String username,String email){
        editor.putString(KEY_TELEPHONE, phone);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
    }

    public void newAppVersion(String version) {
        editor.putString(KEY_APP_VERSION, version);
        editor.commit();
    }

    public String checkAppVersion(){
        String version = pref.getString(KEY_APP_VERSION,"0");
        return version;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean checkLogin()  {
        // Check login status
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, AccountActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

        return false;
    }

    public void saveLastLocation(Double latitude,Double longitude,String name){
        editor.putString(KEY_LAST_LATITUDE,String.valueOf(latitude));
        editor.putString(KEY_LAST_LONGITUDE,String.valueOf(longitude));
        editor.putString(KEY_LAST_NAME,String.valueOf(name));
        editor.commit();
    }


    public LocationModel getLastLocation(){
        Double latitude =  Double.valueOf(pref.getString(KEY_LAST_LATITUDE,"0.00000"));
        Double longitude =  Double.valueOf(pref.getString(KEY_LAST_LONGITUDE,"0.00000"));
        String placeName = pref.getString(KEY_LAST_NAME,"No data available");
        LocationModel model = new LocationModel();
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        model.setPlaceName(placeName);
        return model;
    }
}
