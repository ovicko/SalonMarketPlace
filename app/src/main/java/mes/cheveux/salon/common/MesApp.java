package mes.cheveux.salon.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.multidex.MultiDex;

import mes.cheveux.salon.common.data.ChargeCall;
import mes.cheveux.salon.common.data.NetworkLiveData;
import mes.cheveux.salon.common.data.SessionManager;
import mes.cheveux.salon.data.booking.BookingForm;

public class MesApp extends Application {
    public static final String TAG = MesApp.class.getSimpleName();

    public static Handler mainHandler;
    public  Context context;
    private static MesApp mInstance;
    public NetworkLiveData networkLiveData;
    private ChargeCall.Factory chargeCallFactory;
    public BookingForm bookingForm;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        networkLiveData = new NetworkLiveData(getApplicationContext());
        context = getApplicationContext();
        MultiDex.install(this);



    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized MesApp getInstance() {
        return mInstance;
    }

    public static Handler getMainHandler() {
        if (mainHandler == null) {
            mainHandler = new Handler();
        }
        return mainHandler;
    }

    public  static SessionManager getSessionManager() {
        return new SessionManager(getInstance().context);
    }

    public  void setBookingForm(BookingForm bookingForm) {
        Log.i("BOOKING_SALON","salon id "+bookingForm.getSalonId());
        this.bookingForm = bookingForm;
    }

    public BookingForm getBookingForm() {
        Log.i("BOOKING_SALON","INIT GOT salon id "+bookingForm.getSalonId());
        return bookingForm;
    }

    public static boolean userLoggedIn(){
        return  getSessionManager().isLoggedIn();
    }

    public static String getUserAuthKey(){
        return  getSessionManager().getUserAuthKey();
    }

    public static String getUsername(){
        return  getSessionManager().getUsername();
    }

    public static int getUserId(){
        return  getSessionManager().getUserId();
    }
    public static boolean checkLogin(){
        return  getSessionManager().checkLogin();
    }


    public static void logout(){
        getSessionManager().logoutUser();
    }

    public static boolean reloadCategories(){
        return  getSessionManager().reloadCategories();
    }

    public static void setCategoriesLastLoad(){
        getSessionManager().setCategoryLastLoaded();
    }



}
