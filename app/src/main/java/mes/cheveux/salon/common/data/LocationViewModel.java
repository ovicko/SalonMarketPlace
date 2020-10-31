package mes.cheveux.salon.common.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LocationViewModel extends AndroidViewModel {

    private LiveData<LocationModel> locationModelLiveData;

    public LocationViewModel(@NonNull Application application) {
        super(application);

        locationModelLiveData = new LocationLiveData(application);
    }

    public LiveData<LocationModel> getLocation() {
        return locationModelLiveData;
    }
}
