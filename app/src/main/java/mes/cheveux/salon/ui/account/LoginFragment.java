package mes.cheveux.salon.ui.account;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import mes.cheveux.salon.MainActivity;
import mes.cheveux.salon.R;
import mes.cheveux.salon.common.Constants;
import mes.cheveux.salon.data.account.LoginFormRequest;
import mes.cheveux.salon.data.account.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private  TextInputEditText inputEmail,inputPassword;
    private TextView userLogin;
    private UserViewModel userViewModel;
    private RelativeLayout llProgressBar;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        userLogin = view.findViewById(R.id.userLogin);
        userLogin.setOnClickListener(v -> customerLogin());

        llProgressBar = view.findViewById(R.id.llProgressBar);

        return view;
    }

    private void customerLogin() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        boolean valid = true;

        if (email.isEmpty() || email.length() < 4){
            inputEmail.setError("Email cannot be empty,minimum length is 4");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 6){
            inputEmail.setError("Password length must be 5 characters or more");
            valid = false;
        }

        if (valid){
            llProgressBar.setVisibility(View.VISIBLE);
            LoginFormRequest loginForm = new LoginFormRequest(email,password);

            userViewModel.loginUser(loginForm).observe(getViewLifecycleOwner(),messageResponse -> {
                llProgressBar.setVisibility(View.GONE);

                if (messageResponse.getCode() == Constants.SUCCESS_CODE){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(),messageResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
