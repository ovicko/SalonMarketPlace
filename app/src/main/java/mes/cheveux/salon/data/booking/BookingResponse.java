package mes.cheveux.salon.data.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse {
    @SerializedName("bookings")
    @Expose
    private List<BookingModel> bookingModelList;

    public List<BookingModel> getBookingModelList() {
        return bookingModelList;
    }

    public void setBookingModelList(List<BookingModel> bookingModelList) {
        this.bookingModelList = bookingModelList;
    }
}
