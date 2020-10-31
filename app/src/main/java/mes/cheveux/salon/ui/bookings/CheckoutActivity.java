package mes.cheveux.salon.ui.bookings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mes.cheveux.salon.MainActivity;
import mes.cheveux.salon.R;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.common.MesApp;
import mes.cheveux.salon.common.api.RetrofitClient;
import mes.cheveux.salon.common.data.ChargeCall;
import mes.cheveux.salon.data.booking.BookingForm;
import mes.cheveux.salon.ui.salon.CardEntryBackgroundHandler;
import mes.cheveux.salon.ui.salon.CartAdapter;
import mes.cheveux.salon.ui.salon.SalonCartBroadCastReceiver;
import mes.cheveux.salon.ui.salon.SalonServiceAdapter;
import mes.cheveux.salon.ui.salon.SalonServiceModel;
import mes.cheveux.salon.ui.salon.SalonViewModel;
import retrofit2.Retrofit;
import sqip.CardEntry;

public class CheckoutActivity extends AppCompatActivity {
    private int salonId;
    private String serviceString;
    private BookingForm bookingForm;
    private AppCompatButton confirmOrderBtn;
    private RecyclerView cartRecyclerView;
    private SalonViewModel salonViewModel;
    private TextView priceTotal;
    private Double totalCost = 0.00;
    private ChargeCall.Factory chargeCallFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        if (getIntent().getExtras() != null) {
            String BOOKING_MODEL = getIntent().getExtras().getString("BOOKING_MODEL");
            bookingForm = HelperMethods.getGsonParser().fromJson(BOOKING_MODEL,BookingForm.class);
            salonId = bookingForm.getSalonId();
        }

        Retrofit retrofit = RetrofitClient.createRetrofitInstance();
        chargeCallFactory = new ChargeCall.Factory(retrofit);


        salonViewModel = ViewModelProviders.of(this).get(SalonViewModel.class);

        CartAdapter serviceAdapter = new CartAdapter(this,
                bookingForm.getBookedServices(),salonViewModel);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(serviceAdapter);

        priceTotal = findViewById(R.id.priceTotal);

        confirmOrderBtn = findViewById(R.id.confirmOrderBtn);
        confirmOrderBtn.setOnClickListener(v -> {
            CardEntryBackgroundHandler cardHandler =
                    new CardEntryBackgroundHandler(chargeCallFactory, getResources(),bookingForm);
            CardEntry.setCardNonceBackgroundHandler(cardHandler);
            CardEntry.startCardEntryActivity(CheckoutActivity.this);
        });

        for (SalonServiceModel serviceModel : bookingForm.getBookedServices() ) {
            totalCost = totalCost+ serviceModel.getPrice();
        }

        priceTotal.setText(String.valueOf(totalCost));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        CardEntry.handleActivityResult(data, cardEntryActivityResult -> {
            if (cardEntryActivityResult.isSuccess()) {
                showOkDialog("Success!","Your payment is being processed",1);
            } else if (cardEntryActivityResult.isCanceled()) {
                showOkDialog("Cancelled","Payment is being processed",0);
            }
        });
    }

    private void showOkDialog(String title, CharSequence message,int type) {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                      if (type == 1)  {
                          Intent intent = new Intent(CheckoutActivity.this,CheckoutActivity.class);
                          startActivity(intent);
                          finish();
                      }
                })
                .show();
    }
}
