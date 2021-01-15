package mes.cheveux.salon.ui.bookings;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import mes.cheveux.salon.R;
import mes.cheveux.salon.common.Constants;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.data.booking.BookingModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateSalonFragment extends Fragment {

    private String bookingString;
    private  BookingModel bookingDetails;
    private int salonId = 0;
    private BookingViewModel bookingViewModel;
    private TextInputEditText inputComment;
    private MaterialRatingBar userRating;
    private TextView submitRating;


    public RateSalonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            bookingString = bundle.getString("BOOKING_DETAILS");
            bookingDetails = HelperMethods.getGsonParser().fromJson(bookingString, BookingModel.class);
            salonId = bookingDetails.getSalonId();
        }

        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rate_salon, container, false);
        inputComment = view.findViewById(R.id.inputComment);
        userRating = view.findViewById(R.id.userRating);
        submitRating = view.findViewById(R.id.submitRating);

        submitRating.setOnClickListener(v -> submitServiceRating());

        return view;
    }

    private void submitServiceRating() {
        float rating = userRating.getRating();
        String comment = inputComment.getText().toString().trim();
        boolean valid = true;

        if (comment.length() < 4){
            valid = false;
        }

        if (valid){
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Processing data...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            SalonRatingForm ratingForm = new SalonRatingForm();

            ratingForm.setBookingId(String.valueOf(bookingDetails.getBookingId()));
            ratingForm.setComment(comment);
            ratingForm.setCustomerId(String.valueOf(bookingDetails.getCustomerId()));
            ratingForm.setRating(String.valueOf(rating));
            ratingForm.setSalonId(String.valueOf(salonId));

            bookingViewModel.rateSalon(ratingForm).observe(getViewLifecycleOwner(),messageResponse -> {
                progressDialog.dismiss();
                Toast.makeText(getContext(), messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                if (messageResponse.getCode() == Constants.SUCCESS_CODE){
                    if(getFragmentManager().getBackStackEntryCount() > 0 ){
                        getFragmentManager().popBackStackImmediate();
                    }
                }
            });
        }
    }

}
