package mes.cheveux.salon.ui.bookings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mes.cheveux.salon.R;
import mes.cheveux.salon.data.booking.BookingModel;
import mes.cheveux.salon.ui.salon.direction.DirectionActivity;

public class UpcomingAdapter  extends RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>{
    Context context;
    List<BookingModel> bookingModelList;

    public UpcomingAdapter(Context context, List<BookingModel> bookingModelList) {
        this.context = context;
        this.bookingModelList = bookingModelList;
    }

    @NonNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View theView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.booked_item, parent, false);
        return new UpcomingViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingViewHolder holder, int position) {
        BookingModel bookingModel = bookingModelList.get(position);
        holder.salonImageView.setText(bookingModel.getAppointmentDate());
        holder.salonNameView.setText(bookingModel.getSalonName());
        holder.salonAddressView.setText(bookingModel.getSalonAddress());

        holder.direction.setOnClickListener(view->{
            Intent mapIntent = new Intent(context, DirectionActivity.class);
            mapIntent.putExtra("LATITUDE",bookingModel.getLatitude());
            mapIntent.putExtra("LONGITUDE",bookingModel.getLongitude());
            mapIntent.putExtra("SALON_NAME",bookingModel.getSalonName());
            context.startActivity(mapIntent);
        });

        holder.callSalonBtn.setOnClickListener(view->callSalon(bookingModel.getPhone()));

    }

    @Override
    public int getItemCount() {
        return (bookingModelList != null ) ? bookingModelList.size(): 0 ;
    }

    public class UpcomingViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout salonContainer;
        private TextView salonImageView,salonNameView,callSalonBtn,
                salonAddressView,direction;
        public UpcomingViewHolder(@NonNull View itemView) {
            super(itemView);

            salonContainer = itemView.findViewById(R.id.salonContainer);
            salonImageView = itemView.findViewById(R.id.salonImageView);
            salonNameView = itemView.findViewById(R.id.salonNameView);
            callSalonBtn = itemView.findViewById(R.id.callSalonBtn);
            salonAddressView = itemView.findViewById(R.id.salonAddressView);
            direction = itemView.findViewById(R.id.direction);
        }
    }

    private void callSalon(String phoneString) {
        String telephone = "tel:"+phoneString;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(telephone));
        context.startActivity(callIntent);
    }

}
