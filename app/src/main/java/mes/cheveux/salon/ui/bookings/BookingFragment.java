package mes.cheveux.salon.ui.bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import mes.cheveux.salon.R;
import mes.cheveux.salon.data.booking.BookingViewModel;
import mes.cheveux.salon.ui.CommonPagerAdapter;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    TabLayout fragmentsTabs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        ViewPager viewPager = view.findViewById(R.id.customerBookingVP);
        CommonPagerAdapter commonPagerAdapter = new CommonPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        commonPagerAdapter.addFragment(new UpcomingFragment());
        commonPagerAdapter.addFragment(new HistoryFragment());
        viewPager.setAdapter(commonPagerAdapter);

        fragmentsTabs = view.findViewById(R.id.fragmentsTabs);
        fragmentsTabs.setupWithViewPager(viewPager);
        setUpIndicators();

        return view;
    }

    private void setUpIndicators() {
        fragmentsTabs.getTabAt(0).setText("Upcoming");
        fragmentsTabs.getTabAt(1).setText("History");
    }
}