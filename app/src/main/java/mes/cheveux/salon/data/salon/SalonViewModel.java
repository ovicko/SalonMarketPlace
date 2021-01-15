package mes.cheveux.salon.data.salon;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import mes.cheveux.salon.common.data.LocationLiveData;
import mes.cheveux.salon.common.data.LocationModel;
import mes.cheveux.salon.common.data.SalonSearchModel;
import mes.cheveux.salon.common.data.SessionManager;
import mes.cheveux.salon.ui.salon.reviews.SalonReviewsModel;
import mes.cheveux.salon.ui.salon.services.SalonServiceModel;

public class SalonViewModel extends AndroidViewModel {

    private SalonRepository salonRepository;
    public LiveData<PagedList<SalonModel>> salonsPagedList;
    public MutableLiveData<SalonDataSource> salonDataSourceMutableLiveData;
    public MutableLiveData<List<SalonServiceModel>> servicesAddedToCartLiveData = new MutableLiveData<>();
    private Executor executor;
    private  SalonDataSourceFactory salonDataSourceFactory;
    private LiveData<LocationModel> locationModelLiveData;
    private SessionManager sessionManager;

    public SalonViewModel(@NonNull Application application) {
        super(application);
        sessionManager = new SessionManager(application);
        salonRepository = SalonRepository.getInstance();
        locationModelLiveData = new LocationLiveData(application);
    }

    public LiveData<PagedList<SalonModel>> getSalonsPagedList() {

        LocationModel lastLocation = sessionManager.getLastLocation();
        SalonSearchModel searchModel;
        searchModel = new SalonSearchModel(lastLocation.getLatitude(),lastLocation.getLongitude(),30);

        salonDataSourceFactory = new SalonDataSourceFactory(searchModel);

        salonDataSourceMutableLiveData = salonDataSourceFactory.getSalonDataSourceLiveData();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();
        executor = Executors.newFixedThreadPool(5);

        salonsPagedList = (new LivePagedListBuilder<Integer,SalonModel>(
                salonDataSourceFactory,config))
//                .setFetchExecutor(executor)
                .build();

        return  salonsPagedList;
    }

    public LiveData<LocationModel> getLocation() {
        return locationModelLiveData;
    }

    public LiveData<List<SalonServiceModel>> getSalonServices(int salonId) {
        return salonRepository.getSalonServices(salonId);
    }

    public LiveData<List<SalonReviewsModel>> getSalonReviews(int salonId) {
        return salonRepository.getSalonReviews(salonId);
    }

    public MutableLiveData<List<SalonServiceModel>> getServicesAddedToCartLiveData() {
        return servicesAddedToCartLiveData;
    }
}
