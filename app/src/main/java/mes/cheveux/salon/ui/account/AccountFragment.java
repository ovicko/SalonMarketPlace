package mes.cheveux.salon.ui.account;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import mes.cheveux.salon.R;
import mes.cheveux.salon.common.Constants;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.ui.account.data.UserModel;
import mes.cheveux.salon.ui.settings.SettingsViewModel;

public class AccountFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private TextView termsOfService,privacyPolicy,Logout,appVersion,customerNameView;
    private TextView customerPhone,customerEmail,changePassword,website,helpPage,editCustomer,saveCustomer;

    private EditText inputName,inputPhone,inputEmail;
    private LinearLayout formLayout,detailLayout;
    private RelativeLayout llProgressBar;
    private int userId = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        termsOfService = view.findViewById(R.id.termsOfService);
        termsOfService.setOnClickListener(v -> loadWebPage(v,Constants.TERMS_PAGE));

        privacyPolicy = view.findViewById(R.id.privacyPolicy);
        privacyPolicy.setOnClickListener(v -> loadWebPage(v,Constants.POLICY_PAGE));

        changePassword = view.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(v -> loadWebPage(v,Constants.CHANGE_PASSWORD_PAGE));

        website = view.findViewById(R.id.website);
        website.setOnClickListener(v -> loadWebPage(v,Constants.FRONTEND_BASE_URL));

        helpPage = view.findViewById(R.id.helpPage);
        helpPage.setOnClickListener(v -> loadWebPage(v,Constants.HELP_PAGE));


        editCustomer = view.findViewById(R.id.editCustomer);

        llProgressBar = view.findViewById(R.id.llProgressBar);

        saveCustomer = view.findViewById(R.id.saveCustomer);
        saveCustomer.setOnClickListener(v -> updateCustomerDetail());

        customerNameView = view.findViewById(R.id.customerNameView);
        customerEmail = view.findViewById(R.id.customerEmail);
        customerPhone = view.findViewById(R.id.customerPhone);

        inputName = view.findViewById(R.id.inputName);
        inputPhone = view.findViewById(R.id.inputPhone);
        inputEmail = view.findViewById(R.id.inputEmail);

        detailLayout = view.findViewById(R.id.detailLayout);
        formLayout = view.findViewById(R.id.formLayout);

        Logout = view.findViewById(R.id.Logout);
        Logout.setOnClickListener(v -> logoutCustomer());

        appVersion = view.findViewById(R.id.appVersion);

        editCustomer.setOnClickListener(v -> {
            detailLayout.setVisibility(View.GONE);
            formLayout.setVisibility(View.VISIBLE);
        });

        settingsViewModel.getSessionLiveData()
                .observe(getViewLifecycleOwner(),sessionManager -> {
                    customerNameView.setText(sessionManager.getUsername());
                    customerPhone.setText(sessionManager.getCustomerPhone());
                    customerEmail.setText(sessionManager.getUserEmail());

                    inputName.setText(sessionManager.getUsername());
                    inputPhone.setText(sessionManager.getCustomerPhone());
                    inputEmail.setText(sessionManager.getUserEmail());
                    userId = sessionManager.getUserId();

        });

        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            appVersion.setText("Version "+packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            //Handle exception
        }

        return view;
    }

    private void updateCustomerDetail() {
        String customerName = inputName.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        boolean valid = true;

        if (customerName.length() < 6){
            inputName.setError("Name cannot be empty");
            valid = false;
        }

        if (phone.length() < 9){
            inputPhone.setError("Enter a correct phone number");
            valid = false;
        }

        if (email.length() < 6 || !email.contains("@")){
            inputEmail.setError("Enter a correct email");
            valid = false;
        }

        if (valid){
            llProgressBar.setVisibility(View.VISIBLE);
            UserModel userModel = new UserModel();
            userModel.setEmail(email);
            userModel.setUsername(customerName);
            userModel.setPhone(phone);
            userModel.setId(userId);

            settingsViewModel.updateProfile(userModel)
                    .observe(getViewLifecycleOwner(),messageResponse ->{
                        llProgressBar.setVisibility(View.GONE);

                        Toast.makeText(getContext(),messageResponse.getMessage() , Toast.LENGTH_SHORT).show();

                        if (messageResponse.getCode() == Constants.SUCCESS_CODE){
                            formLayout.setVisibility(View.GONE);
                            detailLayout.setVisibility(View.VISIBLE);
                        }
                    });
        }


    }

    private void logoutCustomer() {
        settingsViewModel.customerLogout();
    }

    private void loadWebPage(View view,String pageUrl){
        Bundle bundle = new Bundle();
        bundle.putString("PAGE_URL", pageUrl);
        Navigation.findNavController(view).navigate(R.id.commonWebViewFragment,bundle);
    }
}