package mes.cheveux.salon.ui.salon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.ui.home.HomeSalonAdapter;

public class NearbySalonsAdapter extends PagedListAdapter<SalonModel,NearbySalonsAdapter.SalonViewHolder> {
    private Context context;
    private List<SalonModel> salonList;

    public NearbySalonsAdapter() {
        super(SalonModel.CALLBACK);
    }



    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.salon_list_item, parent, false);

        return new SalonViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonViewHolder holder, int position) {
        SalonModel salonModel = getItem(position);
        holder.salonNameView.setText(salonModel.getName());
        holder.salonAddressView.setText(salonModel.getAddress());
        holder.salonPhoneView.setText(salonModel.getPhone());
        holder.salonRatingView.setText(salonModel.getAverage_rating().toString());
        holder.salonDistanceView.setText(String.valueOf(salonModel.getDistance()));
        holder.bookBtn.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            String salonJsonString = HelperMethods.getGsonParser().toJson(salonModel);
            bundle.putString("SALON_DETAILS", salonJsonString);
            Navigation.findNavController(view).navigate(R.id.salonDetailFragment,bundle);
        });
        Picasso.get().load(salonModel.getLogoUrl()).into(holder.salonImageView);

    }

    class SalonViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout salonContainer;
        private ImageView salonImageView;
        private TextView salonNameView,salonDistanceView,salonPhoneView,
                salonAddressView,salonRatingView;
        private AppCompatButton bookBtn;

        public SalonViewHolder(@NonNull View itemView) {
            super(itemView);
            salonContainer = itemView.findViewById(R.id.salonContainer);
            salonImageView = itemView.findViewById(R.id.salonImageView);
            salonPhoneView = itemView.findViewById(R.id.salonPhoneView);
            salonDistanceView = itemView.findViewById(R.id.salonDistanceView);
            salonNameView = itemView.findViewById(R.id.salonNameView);
            salonAddressView = itemView.findViewById(R.id.salonAddressView);
            salonRatingView = itemView.findViewById(R.id.salonRatingView);
            bookBtn = itemView.findViewById(R.id.bookBtn);

        }

    }
}
