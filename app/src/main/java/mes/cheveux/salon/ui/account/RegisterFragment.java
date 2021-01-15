package mes.cheveux.salon.ui.account;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import mes.cheveux.salon.MainActivity;
import mes.cheveux.salon.R;
import mes.cheveux.salon.common.Constants;
import mes.cheveux.salon.data.account.RegisterFormRequest;
import mes.cheveux.salon.data.account.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private EditText inputEmail,inputPassword,inputUsername,inputPhone,inputPasswordConfirm;
    private TextView registerCustomer;
    private UserViewModel userViewModel;
    private RelativeLayout llProgressBar;



    public RegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputUsername = view.findViewById(R.id.inputUsername);
        inputPhone = view.findViewById(R.id.inputPhone);
        inputPasswordConfirm = view.findViewById(R.id.inputPasswordConfirm);

        registerCustomer = view.findViewById(R.id.registerCustomer);
        registerCustomer.setOnClickListener(v -> customerRegister());

        llProgressBar = view.findViewById(R.id.llProgressBar);

        return view;
    }

    private void customerRegister() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String confirmPassword = inputPasswordConfirm.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();
        String username = inputUsername.getText().toString().trim();

        boolean valid = true;

        if (email.length() < 4){
            inputEmail.setError("Email cannot be empty,minimum length is 4");
            valid = false;
        }

        if (phone.length() < 9){
            inputPhone.setError("Phone cannot be empty,minimum length is 9");
            valid = false;
        }

        if (username.length() < 6){
            inputEmail.setError("Username cannot be empty,minimum length is 9");
            valid = false;
        }

        if (!confirmPassword.equals(password)){
            inputPassword.setError("Passwords do not match");
            inputPasswordConfirm.setError("Passwords do not match");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 6){
            inputEmail.setError("Password length must be 5 characters or more");
            valid = false;
        }

        if (valid){
            llProgressBar.setVisibility(View.VISIBLE);
            RegisterFormRequest registerForm = new RegisterFormRequest(username,password,email,phone);

            userViewModel.registerUser(registerForm).observe(getViewLifecycleOwner(),messageResponse -> {
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
