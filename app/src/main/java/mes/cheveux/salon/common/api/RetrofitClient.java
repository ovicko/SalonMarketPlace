package mes.cheveux.salon.common.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mes.cheveux.salon.common.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final  String HTTP_API_ENDPOINT = Constants.API_BASE_URL;
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    public RetrofitClient() {
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder().baseUrl(HTTP_API_ENDPOINT)
                .client(new OkHttpClient()
                        .newBuilder()
                        .addNetworkInterceptor(
                                new HttpLoggingInterceptor()
                                        .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .addConverterFactory(GsonConverterFactory
                        .create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofit createRetrofitInstance() {
        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder().baseUrl(HTTP_API_ENDPOINT)
                .client(new OkHttpClient()
                        .newBuilder()
                        .addNetworkInterceptor(
                                new HttpLoggingInterceptor()
                                        .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .addConverterFactory(GsonConverterFactory
                        .create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RetrofitApiService getApiService () {
        return retrofit.create(RetrofitApiService.class);
    }
}
