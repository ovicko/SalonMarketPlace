package mes.cheveux.salon.ui.salon.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.data.salon.SalonReviewsModel;

public class SalonReviewAdapter extends RecyclerView.Adapter<SalonReviewAdapter.ReviewViewHolder>  {
    private Context mContext;
    private List<SalonReviewsModel> salonReviewsList;

    public SalonReviewAdapter(Context mContext, List<SalonReviewsModel> salonModelList) {
        this.mContext = mContext;
        this.salonReviewsList = salonModelList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater
                .from(mContext)
                .inflate(R.layout.salon_review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        SalonReviewsModel reviewModel = salonReviewsList.get(position);
        holder.dateTextView.setText(reviewModel.getDateAdded());
        holder.customerNameView.setText(reviewModel.getCustomer());
        holder.reviewTextView.setText(reviewModel.getComment());
        holder.ratingView.setText(String.valueOf(reviewModel.getRating()));

    }

    @Override
    public int getItemCount() {
        return   (salonReviewsList != null )? salonReviewsList.size(): 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        private TextView dateTextView,customerNameView,ratingView;
        private TextView reviewTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            reviewTextView = itemView.findViewById(R.id.reviewTextView);
            customerNameView = itemView.findViewById(R.id.customerNameView);
            ratingView = itemView.findViewById(R.id.ratingView);
        }
    }
}
