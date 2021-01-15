package mes.cheveux.salon.data.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SalonCartBroadCastReceiver extends BroadcastReceiver {
SalonCartLiveData salonCartLiveData =  new SalonCartLiveData();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.i("Receiver", "Broadcast received: " + action);

        if(action.equals("Myaction")){
            String name = intent.getStringExtra("name");
            Log.i("Receiver", "Name received: " + name);
        }
    }
}
