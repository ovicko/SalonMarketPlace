package mes.cheveux.salon.ui.bookings;


import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.MesApp;
import mes.cheveux.salon.data.booking.BookingModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingViewModel bookingViewModel;
    private UpcomingAdapter upcomingAdapter;
    private List<BookingModel> bookingList = new ArrayList<>();


    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);

        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcomingAdapter = new UpcomingAdapter(getContext(),bookingList);
        recyclerView.setAdapter(upcomingAdapter);

        loadUpcomingBookings(MesApp.getUserId());

        return view;
    }

    private void loadUpcomingBookings(int customerId) {
        bookingViewModel.upcomingBookings(customerId).observe(getViewLifecycleOwner(),bookingModels -> {
            if (bookingModels != null) {
                bookingList.clear();
                bookingList.addAll(bookingModels);
                upcomingAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
