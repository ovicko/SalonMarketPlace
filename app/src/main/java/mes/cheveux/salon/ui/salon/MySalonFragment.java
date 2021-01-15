package mes.cheveux.salon.ui.salon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import mes.cheveux.salon.R;
import mes.cheveux.salon.data.salon.MySalonViewModel;

public class MySalonFragment extends Fragment {

    private MySalonViewModel mySalonViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mySalonViewModel =
                ViewModelProviders.of(this).get(MySalonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_salon, container, false);

        return root;
    }
}