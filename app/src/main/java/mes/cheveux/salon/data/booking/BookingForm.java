package mes.cheveux.salon.data.booking;

import java.util.List;

import mes.cheveux.salon.ui.salon.services.SalonServiceModel;

public class BookingForm  {
    private int salonId,customerId;
    private Double total;
    private String bookingDate;
    private List<SalonServiceModel> bookedServices;

    public BookingForm(int salonId, int customerId,
                       Double total, String bookingDate,
                       List<SalonServiceModel> bookedServices) {
        this.salonId = salonId;
        this.customerId = customerId;
        this.total = total;
        this.bookingDate = bookingDate;
        this.bookedServices = bookedServices;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public List<SalonServiceModel> getBookedServices() {
        return bookedServices;
    }

    public void setBookedServices(List<SalonServiceModel> bookedServices) {
        this.bookedServices = bookedServices;
    }
}
