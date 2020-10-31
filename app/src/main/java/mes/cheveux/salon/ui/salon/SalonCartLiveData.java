package mes.cheveux.salon.ui.salon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import mes.cheveux.salon.data.booking.BookingForm;

public class SalonCartLiveData extends MutableLiveData<BookingForm> {

    private Context context;

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }
}
