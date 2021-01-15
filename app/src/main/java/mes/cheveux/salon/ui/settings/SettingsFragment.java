package mes.cheveux.salon.ui.settings;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import mes.cheveux.salon.R;
import mes.cheveux.salon.data.salon.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private TextView termsOfService,privacyPolicy,Logout,appVersion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        termsOfService = view.findViewById(R.id.termsOfService);
        privacyPolicy = view.findViewById(R.id.privacyPolicy);

        Logout = view.findViewById(R.id.Logout);
        Logout.setOnClickListener(v -> logoutCustomer());

        appVersion = view.findViewById(R.id.appVersion);

        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            appVersion.setText("Version "+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            //Handle exception
        }

        return view;
    }

    private void logoutCustomer() {
        settingsViewModel.customerLogout();
    }
}