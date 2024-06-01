package com.example.trash2cash.R1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.LoginRegisterActivity;
import com.example.trash2cash.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RegisterUser extends Fragment {

    TextView email, password;
    private Button button;
    private String ip;

    public RegisterUser() {
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
        View rootView = inflater.inflate(R.layout.fragment_register2, container, false);

        button = rootView.findViewById(R.id.registerBtn);
        email = rootView.findViewById(R.id.editTextTextEmailAddress);
        password = rootView.findViewById(R.id.editTextTextPassword);

        final boolean[] input = {false, false};
        email.addTextChangedListener(new TextWatcher() {
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
        password.addTextChangedListener(new TextWatcher() {
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

        button.setOnClickListener(view -> register());

        return rootView;
    }

    private void register(){
        int result = 0;

        String url = "/trash2cash/addUser.php";
        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            result = okHttpHandler.registerUser(url, email.getText().toString(),
                    password.getText().toString());
            if(result != 0)
                Toast.makeText(getContext(), "Register successful", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == 0) {
            email.setText("");
            password.setText("");
            Toast.makeText(getContext(), "Register has failed", Toast.LENGTH_LONG).show();
        }
    }
}