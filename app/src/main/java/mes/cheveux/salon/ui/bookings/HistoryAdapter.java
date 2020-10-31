package mes.cheveux.salon.ui.bookings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.data.booking.BookingModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    Context context;
    List<BookingModel> bookingModelList;

    public HistoryAdapter(Context context, List<BookingModel> bookingModelList) {
        this.context = context;
        this.bookingModelList = bookingModelList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.booking_history_item, parent, false);
        return new HistoryViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        BookingModel bookingModel = bookingModelList.get(position);

        holder.bookingDate.setText(bookingModel.getHistoryDate());
        holder.salonNameView.setText(bookingModel.getSalonName());
        holder.salonAddressView.setText(bookingModel.getSalonAddress());

        if (bookingModel.getReview()){
            holder.rateSalon.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                String bookingJsonString = HelperMethods.getGsonParser().toJson(bookingModel);
                bundle.putString("BOOKING_DETAILS", bookingJsonString);
                Navigation.findNavController(view).navigate(R.id.rateSalonFragment,bundle);
            });
        } else {
            holder.rateSalon.setVisibility(View.GONE);
        }

        Picasso.get().load(bookingModel.getLogoUrl()).into(holder.salonImageView);

    }

    @Override
    public int getItemCount() {
        return (bookingModelList != null ) ? bookingModelList.size(): 0 ;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout salonContainer;
        private TextView salonNameView,salonAddressView,rateSalon,bookingDate;
        private ImageView salonImageView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            salonContainer = itemView.findViewById(R.id.salonContainer);
            salonImageView = itemView.findViewById(R.id.salonImageView);
            salonNameView = itemView.findViewById(R.id.salonNameView);
            rateSalon = itemView.findViewById(R.id.rateSalon);
            salonAddressView = itemView.findViewById(R.id.salonAddressView);
            bookingDate = itemView.findViewById(R.id.bookingDate);
        }
    }
}
