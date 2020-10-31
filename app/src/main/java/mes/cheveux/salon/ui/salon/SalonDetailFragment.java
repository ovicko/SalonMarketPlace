package mes.cheveux.salon.ui.salon;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import mes.cheveux.salon.R;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.ui.CommonPagerAdapter;
import mes.cheveux.salon.ui.salon.reviews.ReviewsFragment;
import mes.cheveux.salon.ui.salon.services.SalonServicesFragment;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;


public class SalonDetailFragment extends Fragment {
    private int salonId;
    private String salonString;
    private SalonModel salonDetails;
    private Button servicesBtn,reviewsBtn;

    TabLayout fragmentsTabs;
    TextView salonNameView,salonAddressView,callSalon,direction;
    SalonViewModel salonViewModel;

    private  int FRAGMENT_COUNT = 2;

    public SalonDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            salonString = bundle.getString("SALON_DETAILS");
            salonDetails = HelperMethods.getGsonParser().fromJson(salonString,SalonModel.class);
            salonId = salonDetails.getSalonId();
        }

        salonViewModel =
                ViewModelProviders.of(this).get(SalonViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salon_detail, container, false);

        ViewPager viewPager = view.findViewById(R.id.salonDetailsViewPager);
        CommonPagerAdapter commonPagerAdapter = new CommonPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        commonPagerAdapter.addFragment(SalonServicesFragment.newInstance(salonId));
        commonPagerAdapter.addFragment(ReviewsFragment.newInstance(salonId));
        viewPager.setAdapter(commonPagerAdapter);

        fragmentsTabs = view.findViewById(R.id.fragmentsTabs);
        fragmentsTabs.setupWithViewPager(viewPager);
        setUpIndicators();

//        servicesBtn = view.findViewById(R.id.servicesBtn);
//        servicesBtn.setOnClickListener(v -> viewPager.setCurrentItem(0));
//
//        reviewsBtn = view.findViewById(R.id.reviewsBtn);
//        reviewsBtn.setOnClickListener(v -> viewPager.setCurrentItem(FRAGMENT_COUNT-1));

        ImageView toolbarImage = view.findViewById(R.id.toolbarImage);
        Picasso.get().load(salonDetails.getLogoUrl()).into(toolbarImage);

        String telephone = "tel:"+salonDetails.getPhone();

        callSalon = view.findViewById(R.id.callSalon);
        callSalon.setOnClickListener(v -> callSalon(telephone));

        direction = view.findViewById(R.id.direction);
        direction.setOnClickListener(v -> showDirection());

        salonNameView = view.findViewById(R.id.salonNameView);
        salonNameView.setText(salonDetails.getName());

        salonAddressView = view.findViewById(R.id.salonAddressView);
        salonAddressView.setText(salonDetails.getAddress());

        MaterialRatingBar materialRatingBar = view.findViewById(R.id.salonRatingView);
        materialRatingBar.setRating(Float.valueOf(String.valueOf(salonDetails.getAverage_rating())));

        return view;
    }

    private void showDirection() {
        Intent mapIntent = new Intent(getActivity(),SimpleDeirectionActivity.class);
        mapIntent.putExtra("LATITUDE",salonDetails.getLatitude());
        mapIntent.putExtra("LONGITUDE",salonDetails.getLongitude());
        mapIntent.putExtra("SALON_NAME",salonDetails.getName());
        getActivity().startActivity(mapIntent);
    }

    private void callSalon(String phoneString) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneString));
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CALL_PHONE}, 1);
            }
        }
        startActivity(callIntent);
    }

    private void setUpIndicators() {
        fragmentsTabs.getTabAt(0).setText("Services");
        fragmentsTabs.getTabAt(1).setText("Reviews");
    }




//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }

}
