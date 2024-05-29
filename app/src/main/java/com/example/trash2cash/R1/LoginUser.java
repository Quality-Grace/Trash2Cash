package com.example.trash2cash.R1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trash2cash.LoginRegisterActivity;
import com.example.trash2cash.OkHttpHandler;
import com.example.trash2cash.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginUser extends Fragment {
    TextInputLayout email, password;
    private Button button;
    private String ip;

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

        // gets the IP from the MainActivity
        ip = LoginRegisterActivity.getIP();

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
        int result = 0;

        String url = "http://" + ip + "/trash2cash/loginUser.php";
        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            result = okHttpHandler.loginUser(url, Objects.requireNonNull(email.getEditText()).getText().toString(),
                    Objects.requireNonNull(password.getEditText()).getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == 0){
            Objects.requireNonNull(email.getEditText()).setText("");
            Objects.requireNonNull(password.getEditText()).setText("");
            Toast.makeText(getContext(), "Login has failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_LONG).show();
//            Metavasi se nea othoni
//            Intent intent = new Intent(requireActivity(), UserMainActivity.class);
//            intent.putExtra("Email", parameter1.getEditText().getText().toString());
//            startActivity(intent);
//            requireActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.no_slide_in_or_out);
//            requireActivity().finish();
        }
    }
}
