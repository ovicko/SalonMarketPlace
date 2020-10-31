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
import android.widget.Toast;

import mes.cheveux.salon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    private  int salonId;

    RecyclerView reviewsRecyclerView;
    SalonReviewAdapter salonReviewsAdapter;
    SalonViewModel salonViewModel;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    public static ReviewsFragment newInstance(int salonId) {
        
        Bundle args = new Bundle();

        ReviewsFragment fragment = new ReviewsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviewsRecyclerView = view.findViewById(R.id.reviewsRecyclerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        salonViewModel.getSalonReviews(salonId).observe(getViewLifecycleOwner(),salonReviewsModelList -> {
            if (salonReviewsModelList != null) {
                salonReviewsAdapter = new SalonReviewAdapter(getActivity(),salonReviewsModelList);
                salonReviewsAdapter.notifyDataSetChanged();
                reviewsRecyclerView.setAdapter(salonReviewsAdapter);
            } else {
                Toast.makeText(getActivity(), "Error loading Data", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
