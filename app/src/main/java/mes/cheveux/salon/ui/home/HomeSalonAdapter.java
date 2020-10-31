package mes.cheveux.salon.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.ui.salon.SalonModel;

public class HomeSalonAdapter extends RecyclerView.Adapter<HomeSalonAdapter.SalonViewHolder>  {
    Context mContext;
    List<SalonModel> salonModelList;

    public HomeSalonAdapter(Context mContext, List<SalonModel> salonModelList) {
        this.mContext = mContext;
        this.salonModelList = salonModelList;
    }

    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SalonViewHolder(LayoutInflater
                .from(mContext)
                .inflate(R.layout.salon_front_list_item_, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SalonViewHolder holder, int position) {
        SalonModel salonModel = salonModelList.get(position);
        holder.txtSalonName.setText(salonModel.getName());
        holder.txtAddress.setText(salonModel.getAddress());
        holder.bookBtn.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            String salonJsonString = HelperMethods.getGsonParser().toJson(salonModel);
            bundle.putString("SALON_DETAILS", salonJsonString);
            Navigation.findNavController(view).navigate(R.id.salonDetailFragment,bundle);
        });
        Picasso.get().load(salonModel.getLogoUrl()).into(holder.salonImageView);
    }

    @Override
    public int getItemCount() {
        return   (salonModelList != null )? salonModelList.size(): 0;
    }

    public class SalonViewHolder extends RecyclerView.ViewHolder{
        private TextView txtSalonName;
        private TextView txtAddress;
        private CardView salonCardView;
        private AppCompatButton bookBtn;

        private ImageView salonImageView;
        public SalonViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSalonName = itemView.findViewById(R.id.salonNameView);
            txtAddress = itemView.findViewById(R.id.salonAddress);
            bookBtn = itemView.findViewById(R.id.bookBtn);
            salonCardView = itemView.findViewById(R.id.salonCardView);
            bookBtn = itemView.findViewById(R.id.bookBtn);
            salonImageView = itemView.findViewById(R.id.salonImageView);
        }
    }
}
