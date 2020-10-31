package mes.cheveux.salon.ui.salon;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.util.ArrayList;
import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.common.MesApp;
import mes.cheveux.salon.data.booking.BookingForm;
import mes.cheveux.salon.ui.bookings.CheckoutActivity;

import static mes.cheveux.salon.common.HelperMethods.formatCurrency;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalonServicesFragment extends Fragment {

    private  int salonId,cartSize;

    private RecyclerView servicesRecyclerView;
    private SalonServiceAdapter salonServiceAdapter;
    private SalonViewModel salonViewModel;
    private TextView priceTotal;
    private AppCompatButton checkoutNowBtn;
    private SingleDateAndTimePicker appointmentDate;
    private String _appointmentDate;
    private Double totalCost = 0.00;
    private List<SalonServiceModel> selectedServices = new ArrayList<>();


    public SalonServicesFragment() {
        // Required empty public constructor
    }

    public static SalonServicesFragment newInstance(int salonId) {

        Bundle args = new Bundle();

        SalonServicesFragment fragment = new SalonServicesFragment();
        args.putInt("salonId", salonId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salonViewModel =
                ViewModelProviders.of(this).get(SalonViewModel.class);

        if (getArguments() != null) {
            salonId = getArguments().getInt("salonId",0);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_salon_services, container, false);
        servicesRecyclerView = view.findViewById(R.id.servicesRecyclerView);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        priceTotal = view.findViewById(R.id.priceTotal);

        appointmentDate = view.findViewById(R.id.appointmentDate);

        appointmentDate.addOnDateChangedListener((displayed, date) -> {
            _appointmentDate = date.toString();
            Toast.makeText(getContext(), displayed, Toast.LENGTH_SHORT).show();
        });

        checkoutNowBtn = view.findViewById(R.id.checkoutNowBtn);
        checkoutNowBtn.setOnClickListener(this::checkoutNow);


        salonViewModel.getSalonServices(salonId).observe(getViewLifecycleOwner(),salonServiceModels -> {
            if (salonServiceModels != null) {
                salonServiceAdapter = new SalonServiceAdapter(getActivity(),salonServiceModels,salonViewModel);
                salonServiceAdapter.notifyDataSetChanged();
                servicesRecyclerView.setAdapter(salonServiceAdapter);
            } else {
                Toast.makeText(getActivity(), "Error loading Data", Toast.LENGTH_SHORT).show();
            }
        });

        salonViewModel.servicesAddedToCartLiveData
                .observe(getViewLifecycleOwner(),salonServiceModel -> {
                    Double tempTotal = 0.00;

                    if (salonServiceModel != null) {
                        selectedServices = salonServiceModel;

                        for (SalonServiceModel serviceModel : salonServiceModel ) {
                            tempTotal = tempTotal+ serviceModel.getPrice();
                        }

                        cartSize = salonServiceModel.size();
                        priceTotal.setText("Total $ "+formatCurrency(tempTotal));
                        totalCost = formatCurrency(tempTotal);

                    } else {
                        Toast.makeText(getActivity(), "Nothing here ", Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

    private void checkoutNow(View view) {

        if (cartSize < 1) {
            BookingForm bookingForm = new BookingForm(salonId,MesApp.getUserId(),
                    totalCost,_appointmentDate,selectedServices);
            Bundle bundle = new Bundle();
            String serviceJsonString = HelperMethods.getGsonParser().toJson(bookingForm);
            bundle.putString("BOOKING_MODEL", serviceJsonString);

            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getContext(), "No services selected!", Toast.LENGTH_SHORT).show();
        }

    }

}
