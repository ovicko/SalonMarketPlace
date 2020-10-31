/*
 * Copyright (c) 2019. AMWOLLO VICTOR <https://ovicko.com>
 */

package mes.cheveux.salon.ui.account.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mes.cheveux.salon.common.data.MessageResponse;


public class UserViewModel extends ViewModel {
    //login user
    private UserRepository userRepository;
    private MutableLiveData<MessageResponse> messageResponseMutableLiveData;


    public UserViewModel() {
        userRepository = UserRepository.getInstance();
    }


    public LiveData<MessageResponse> loginUser(LoginFormRequest loginForm) {
        messageResponseMutableLiveData = userRepository.login(loginForm);

        return messageResponseMutableLiveData;
    }

    //register user
    public LiveData<MessageResponse> registerUser(RegisterFormRequest registerFormRequest) {
        messageResponseMutableLiveData = userRepository.registerUser(registerFormRequest);

        return messageResponseMutableLiveData;
    }

}
