package mes.cheveux.salon.ui.salon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MySalonViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MySalonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is salon fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}