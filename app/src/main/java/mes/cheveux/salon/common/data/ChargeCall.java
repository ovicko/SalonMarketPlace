package mes.cheveux.salon.common.data;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import mes.cheveux.salon.BuildConfig;
import mes.cheveux.salon.common.api.RetrofitApiService;
import mes.cheveux.salon.data.booking.BookingForm;
import mes.cheveux.salon.data.cart.ChargeResult;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import sqip.Call;

public class ChargeCall implements Call<ChargeResult> {

    public static class Factory {
        private final RetrofitApiService service;
        private final Converter<ResponseBody, ChargeRequest.ChargeErrorResponse> errorConverter;

        public Factory(Retrofit retrofit) {
            service = retrofit.create(RetrofitApiService.class);
            Annotation[] noAnnotations = {};
            Type errorResponseType = ChargeRequest.ChargeErrorResponse.class;
            errorConverter = retrofit.responseBodyConverter(errorResponseType, noAnnotations);
        }

        public Call<ChargeResult> create(String nonce,BookingForm bookingForm) {
            return new ChargeCall(this,bookingForm, nonce);
        }
    }

    private final ChargeCall.Factory factory;
    private final String nonce;
    private final BookingForm bookingForm;
    private final retrofit2.Call<Void> call;

    private ChargeCall(ChargeCall.Factory factory, BookingForm bookingForm,
                       String nonce) {
        this.factory = factory;
        this.nonce = nonce;
        this.bookingForm = bookingForm;
        call = factory.service.charge(new RetrofitApiService.ChargeRequest(nonce, bookingForm));
    }

    @Override
    public ChargeResult execute() {
        Response<Void> response;
        try {
            response = call.execute();
        } catch (IOException e) {
            return ChargeResult.networkError();
        }
        return responseToResult(response);
    }

    @Override
    public void enqueue(sqip.Callback<ChargeResult> callback) {
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Void> call, @NonNull Response<Void> response) {
                //Log.v("CHARGE_RESPONSE");
                callback.onResult(responseToResult(response));

            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<Void> call, Throwable throwable) {
                if (throwable instanceof IOException) {
                    callback.onResult(ChargeResult.networkError());
                } else {
                    throw new RuntimeException("Unexpected exception", throwable);
                }
            }
        });
    }

    private ChargeResult responseToResult(Response<Void> response) {
        if (response.isSuccessful()) {
            return ChargeResult.success();
        }
        try {
            //noinspection ConstantConditions
            ResponseBody errorBody = response.errorBody();
            ChargeRequest.ChargeErrorResponse errorResponse = factory.errorConverter.convert(errorBody);
            return ChargeResult.error(errorResponse.errorMessage);
        } catch (IOException exception) {
            if (BuildConfig.DEBUG) {
                Log.d("ChargeCall", "Error while parsing error response: " + response.toString(),
                        exception);
            }
            return ChargeResult.networkError();
        }
    }

    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }

    @NonNull
    @Override
    public Call<ChargeResult> clone() {
        return factory.create(nonce,bookingForm);
    }
}