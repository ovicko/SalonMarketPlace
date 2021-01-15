package mes.cheveux.salon.data.cart;

import android.content.Context;

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
