package com.example.trash2cash.R1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trash2cash.AdminRequestsLogger.AdminRequestsLoggerActivity;
import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.Admin;
import com.example.trash2cash.R;
import com.example.trash2cash.UserProfileActivity;
import com.example.trash2cash.ViewStats.AdminStatisticsActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginUser extends Fragment {
    TextInputLayout email, password;
    private Button button;

    public LoginUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflates the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login_user, container, false);

        button = rootView.findViewById(R.id.login_user_button);
        email = rootView.findViewById(R.id.login_user_parameter1);
        password = rootView.findViewById(R.id.login_user_parameter2);

        final boolean[] input = {false, false};
        Objects.requireNonNull(email.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* do nothing */ }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input[0] = !(s.toString().isEmpty());
                button.setEnabled(input[0] && input[1]);
            }
            @Override
            public void afterTextChanged(Editable s) { /* do nothing */ }
        });
        Objects.requireNonNull(password.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* do nothing */ }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input[1] = !(s.toString().isEmpty());
                button.setEnabled(input[0] && input[1]);
            }
            @Override
            public void afterTextChanged(Editable s) { /* do nothing */ }
        });

        button.setOnClickListener(view -> userLogin());
//        shortcut.setOnClickListener(view -> shortcutClick());

        return rootView;
    }


    private void userLogin() {
        boolean result = false;

        String emailString = (email.getEditText()).getText().toString();
        String passwordString = (password.getEditText()).getText().toString();

        if(emailString.equals("admin") && passwordString.equals("admin")){
            result = true;
        } else {
            try {
                OkHttpHandler okHttpHandler = new OkHttpHandler();
                result = okHttpHandler.loginUser(Objects.requireNonNull(email.getEditText()).getText().toString(),
                        Objects.requireNonNull(password.getEditText()).getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!result){
            Objects.requireNonNull(email.getEditText()).setText("");
            Objects.requireNonNull(password.getEditText()).setText("");
            Toast.makeText(getContext(), "Login has failed", Toast.LENGTH_LONG).show();
        } else if(emailString.equals("admin")){
            Toast.makeText(getContext(), "Login successful as Admin", Toast.LENGTH_LONG).show();
//            Metavasi se nea othoni
            Intent intent = new Intent(requireActivity(), AdminRequestsLoggerActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Login successful as " + emailString, Toast.LENGTH_LONG).show();
//            Metavasi se nea othoni
            Intent intent = new Intent(requireActivity(), UserProfileActivity.class);
            startActivity(intent);
        }
    }
}
