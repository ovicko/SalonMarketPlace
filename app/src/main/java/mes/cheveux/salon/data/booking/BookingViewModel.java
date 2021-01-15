package mes.cheveux.salon.data.booking;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mes.cheveux.salon.common.data.MessageResponse;

public class BookingViewModel extends ViewModel {

    private BookingRepository bookingRepository;

    public BookingViewModel() {
        bookingRepository = BookingRepository.getInstance();
    }

    public MutableLiveData<List<BookingModel>> upcomingBookings(int customerId) {
        return bookingRepository.customerUpcomingBookings(customerId);
    }

    public MutableLiveData<List<BookingModel>> bookingHistory(int customerId) {
        return bookingRepository.customerBookingHistory(customerId);
    }

    public MutableLiveData<MessageResponse> rateSalon(SalonRatingForm ratingForm){
        return bookingRepository.rateSalon(ratingForm);
    }
}