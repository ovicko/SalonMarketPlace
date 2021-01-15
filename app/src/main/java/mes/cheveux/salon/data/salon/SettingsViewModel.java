package mes.cheveux.salon.data.salon;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mes.cheveux.salon.common.data.MessageResponse;
import mes.cheveux.salon.common.data.SessionManager;
import mes.cheveux.salon.data.account.UserModel;
import mes.cheveux.salon.data.account.UserRepository;

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