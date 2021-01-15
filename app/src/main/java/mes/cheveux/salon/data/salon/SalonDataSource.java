package mes.cheveux.salon.data.salon;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mes.cheveux.salon.common.api.RetrofitApiService;
import mes.cheveux.salon.common.api.RetrofitClient;
import mes.cheveux.salon.common.data.MetaModel;
import mes.cheveux.salon.common.data.NearbySalonsResponse;
import mes.cheveux.salon.common.data.SalonSearchModel;

public class SalonDataSource extends PageKeyedDataSource<Integer, SalonModel> {
    public RetrofitApiService apiService;
    private MetaModel metaModel;
    private SalonSearchModel salonSearchModel;

    public SalonDataSource(SalonSearchModel searchModel) {
        this.apiService = RetrofitClient.getInstance().getApiService();
        this.metaModel = new MetaModel();
        this.salonSearchModel = searchModel;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, SalonModel> callback) {
        this.apiService.nearBySalons(salonSearchModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                        callback.onResult(response.getSalons(),null,
                                2);
                },throwable -> {});
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, SalonModel> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, SalonModel> callback) {
        this.apiService.nearBySalons(salonSearchModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    NearbySalonsResponse salonsResponse = response;
                    metaModel = salonsResponse.getMeta();
                    List<SalonModel> salonModelList = salonsResponse.getSalons();
                    if (metaModel.getCurrentPage() < metaModel.getPageCount()
                            && metaModel.getCurrentPage() != metaModel.getPageCount()){
                        callback.onResult(salonModelList,metaModel.getCurrentPage()+1);
                    } else {
                        callback.onResult(salonModelList,null);
                    }

                },throwable -> {

                });
    }
}
