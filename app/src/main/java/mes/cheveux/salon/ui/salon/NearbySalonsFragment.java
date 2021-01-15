package mes.cheveux.salon.ui.salon;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.data.SalonSearchModel;
import mes.cheveux.salon.data.salon.SalonModel;
import mes.cheveux.salon.data.salon.SalonViewModel;

public class NearbySalonsFragment extends Fragment {

    private SalonViewModel salonViewModel;
    private List<SalonModel> salonModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private  NearbySalonsAdapter salonsAdapter;
    SalonSearchModel salonSearchModel;
    public NearbySalonsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salonViewModel =
                ViewModelProviders.of(this).get(SalonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_salons, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        salonViewModel.getSalonsPagedList()
                .observe(getViewLifecycleOwner(),salonModelsResponse -> {
                        salonsAdapter = new NearbySalonsAdapter();
                        salonsAdapter.submitList(salonModelsResponse);

                    recyclerView.setAdapter(salonsAdapter);
        });
        return view;
    }

}
