package mes.cheveux.salon.ui.bookings;


import android.os.Bundle;

import androidx.annotation.Nullable;
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
import mes.cheveux.salon.data.booking.BookingViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingViewModel bookingViewModel;
    private HistoryAdapter historyAdapter;
    private List<BookingModel> bookingList = new ArrayList<>();

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyAdapter = new HistoryAdapter(getContext(),bookingList);
        recyclerView.setAdapter(historyAdapter);

        loadBookingHistory(MesApp.getUserId());
        return view;
    }

    private void loadBookingHistory(int customerId) {
        bookingViewModel.bookingHistory(customerId).observe(getViewLifecycleOwner(),bookingModels -> {
            if (bookingModels != null) {
                bookingList.clear();
                bookingList.addAll(bookingModels);
                historyAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
