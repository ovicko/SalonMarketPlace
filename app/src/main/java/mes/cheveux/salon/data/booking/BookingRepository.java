package mes.cheveux.salon.data.booking;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mes.cheveux.salon.common.Constants;
import mes.cheveux.salon.common.data.BaseRepository;
import mes.cheveux.salon.common.data.MessageResponse;
import mes.cheveux.salon.ui.bookings.SalonRatingForm;
import mes.cheveux.salon.ui.salon.SalonServiceModel;

public class BookingRepository extends BaseRepository {
    private  static BookingRepository bookingRepository;



    public  static BookingRepository getInstance(){
        if (bookingRepository == null) {
            bookingRepository = new BookingRepository();
        }

        return bookingRepository;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<BookingModel>> customerBookingHistory(int customerId){
        MutableLiveData<List<BookingModel>> response = new MutableLiveData<>();
        apiService.customerBookingHistory(customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ::setValue,throwable -> response.setValue(null));

        return response;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<BookingModel>> customerUpcomingBookings(int customerId){
        MutableLiveData<List<BookingModel>> response = new MutableLiveData<>();
        apiService.customerUpcomingBookings(customerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ::setValue,throwable -> response.setValue(null));

        return response;
    }


    @SuppressLint("CheckResult")
    public MutableLiveData<MessageResponse> rateSalon(SalonRatingForm ratingForm){

        apiService.rateSalon(ratingForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    messageResponse.setCode(loginResponse.getCode());
                    messageResponse.setMessage(loginResponse.getMessage());
                    liveDataMessage.setValue(messageResponse);
                }, this::setErrorMessages);

        return  liveDataMessage;

    }



}

