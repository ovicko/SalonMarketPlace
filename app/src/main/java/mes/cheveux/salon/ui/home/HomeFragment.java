package mes.cheveux.salon.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.data.GpsUtils;
import mes.cheveux.salon.common.data.LocationModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView parentRecyclerView;
    private List<ModuleModel> moduleModelList = new ArrayList<>();
    private HomeSalonModulesAdapter modulesAdapter;
    TextView testLocation;
    private boolean isContinue = false;
    private boolean isGPS = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getLocation().observe(getViewLifecycleOwner(), locationModel -> {
            if (locationModel == null) {
                showDialog("Location","Turn on location to access nearby salon data");
            }
        });

        parentRecyclerView = root.findViewById(R.id.parentRecyclerView);
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        modulesAdapter = new HomeSalonModulesAdapter(getContext(),moduleModelList);
        parentRecyclerView.setAdapter(modulesAdapter);

        homeViewModel.getModuleResponse().observe(getViewLifecycleOwner(),moduleResponse -> {
            if (moduleResponse == null) {
                Toast.makeText(getActivity(), "Could not load data", Toast.LENGTH_SHORT).show();
            } else {
                moduleModelList.clear();
                moduleModelList.addAll(moduleResponse.getModules());
                modulesAdapter.notifyDataSetChanged();
            }
        });


        return root;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    private  void showDialog(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes",
                        (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}