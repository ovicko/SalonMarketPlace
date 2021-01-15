package mes.cheveux.salon.common.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import mes.cheveux.salon.common.data.MessageResponse;
import mes.cheveux.salon.common.data.NearbySalonsResponse;
import mes.cheveux.salon.common.data.SalonSearchModel;
import mes.cheveux.salon.data.booking.BookingForm;
import mes.cheveux.salon.data.booking.BookingModel;
import mes.cheveux.salon.data.account.LoginFormRequest;
import mes.cheveux.salon.data.account.RegisterFormRequest;
import mes.cheveux.salon.data.account.UserModel;
import mes.cheveux.salon.data.booking.SalonRatingForm;
import mes.cheveux.salon.ui.home.ModuleResponse;
import mes.cheveux.salon.data.salon.SalonReviewsModel;
import mes.cheveux.salon.data.salon.SalonServiceModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RetrofitApiService {

    @POST("customer/login")
    Observable<LoginFormRequest.LoginResponse> logIn(@Body LoginFormRequest loginFormRequest);

    @POST("customer/update")
    Observable<LoginFormRequest.LoginResponse> updateProfile(@Body UserModel userModel);

    @POST("customer/create")
    Observable<MessageResponse> registerUser(@Body RegisterFormRequest registerFormRequest);


    @POST("salon/review")
    Observable<MessageResponse> rateSalon(@Body SalonRatingForm ratingForm);

    @GET("module/index")
    Observable<ModuleResponse> getHomeModules();

    @POST("salon/nearby")
    Observable<NearbySalonsResponse> nearBySalons(@Body SalonSearchModel searchModel);

    @GET("salon/services")
    Observable<List<SalonServiceModel>> salonServices(@Query("salonId") int salonId);

    @GET("booking/customer")
    Observable<List<BookingModel>> customerUpcomingBookings(@Query("customer_id") int customerId);

    @GET("booking/history")
    Observable<List<BookingModel>> customerBookingHistory(@Query("customer_id") int customerId);

    @GET("salon/reviews")
    Observable<List<SalonReviewsModel>> salonReviews(@Query("salonId") int salonId);

    @Multipart
    @POST("salon/create")
    Observable<MessageResponse> postSalon(@PartMap() Map<String, RequestBody> partMap,
                                            @Part MultipartBody.Part logoImage);

    @POST("square/nonce")
    Call<Void> charge(@Body ChargeRequest request);

    class ChargeErrorResponse {
        String errorMessage;
    }

    class ChargeRequest {
        final String nonce;
        final BookingForm bookingForm;

        public ChargeRequest(String nonce, BookingForm bookingForm) {
            this.nonce = nonce;
            this.bookingForm = bookingForm;
        }
    }
}
