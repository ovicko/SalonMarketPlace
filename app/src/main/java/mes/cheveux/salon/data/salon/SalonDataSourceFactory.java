package mes.cheveux.salon.data.salon;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import mes.cheveux.salon.common.data.SalonSearchModel;

public class SalonDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<SalonDataSource> salonDataSourceLiveData;
    private SalonSearchModel salonSearchModel;
    private SalonDataSource salonDataSource;

    public SalonDataSourceFactory(SalonSearchModel salonSearchModel) {
        this.salonSearchModel = salonSearchModel;
        salonDataSource = new SalonDataSource(salonSearchModel);
        salonDataSourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        salonDataSource = new SalonDataSource(this.salonSearchModel);
        salonDataSourceLiveData.postValue(salonDataSource);
        return salonDataSource;
    }

    public MutableLiveData<SalonDataSource> getSalonDataSourceLiveData() {
        return salonDataSourceLiveData;
    }
}
