package mes.cheveux.salon.ui.account;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mes.cheveux.salon.common.data.SessionManager;

public class AccountViewModel extends AndroidViewModel {

    private SessionManager sessionManager;

    public AccountViewModel(Application application) {
        super(application);
        sessionManager = new SessionManager(application);
    }

}