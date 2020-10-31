package mes.cheveux.salon.ui.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mes.cheveux.salon.common.data.MessageResponse;
import mes.cheveux.salon.common.data.SessionManager;
import mes.cheveux.salon.ui.account.data.LoginFormRequest;
import mes.cheveux.salon.ui.account.data.UserModel;
import mes.cheveux.salon.ui.account.data.UserRepository;

public class SettingsViewModel extends AndroidViewModel {

    private SessionManager sessionManager;
    private UserRepository userRepository;
    private MutableLiveData<SessionManager> sessionLiveData = new MutableLiveData<>();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<SessionManager> getSessionLiveData() {
        sessionLiveData = userRepository.getUpdatedSessionLiveData();
        return sessionLiveData;
    }

    public LiveData<MessageResponse> updateProfile(UserModel userModel) {
        MutableLiveData<MessageResponse> responseMutableLiveData = userRepository.updateProfile(userModel);
        userRepository.getUpdatedSessionLiveData();
        return responseMutableLiveData;
    }


    public void customerLogout(){
        userRepository.logoutUser();
    }
}