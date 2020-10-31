package mes.cheveux.salon.ui.salon;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.common.MesApp;
import mes.cheveux.salon.data.booking.BookingForm;
import sqip.CardEntry;

public class CheckoutFragment extends Fragment {
    private int salonId;
    private String serviceString;
    private BookingForm bookingForm;
    private AppCompatButton confirmOrderBtn;
    private SalonCartBroadCastReceiver cartBroadCastReceiver;


    public CheckoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            serviceString = bundle.getString("BOOKING_MODEL");
            bookingForm = HelperMethods.getGsonParser().fromJson(serviceString,BookingForm.class);
            salonId = bookingForm.getSalonId();
        }

        cartBroadCastReceiver = new SalonCartBroadCastReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        confirmOrderBtn = view.findViewById(R.id.confirmOrderBtn);
        confirmOrderBtn.setOnClickListener(v -> {
            CardEntry.startCardEntryActivity(getActivity());

        });
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
        //getActivity().unregisterReceiver(cartBroadCastReceiver);
    }
}
