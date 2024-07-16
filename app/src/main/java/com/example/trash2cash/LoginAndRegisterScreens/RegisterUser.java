package com.example.trash2cash.LoginAndRegisterScreens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.R;
import com.example.trash2cash.UserNavigationActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RegisterUser extends Fragment {
    private final boolean[] input = {false, false, false, false};
    private final ActivityResultLauncher<String> pickMedia = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            this::onImagePicked
    );
    private Context context;
    TextView email, username, password;
    ImageView image;
    private Button button;

    public RegisterUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void openImagePicker() {
        pickMedia.launch("image/*");
    }


    private void onImagePicked(Uri imageUri) {
        if(imageUri != null){
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            try {
                InputStream stream = context.getContentResolver().openInputStream(imageUri);
                File file = createTempFileFromInputStream(context, stream);
                String urlString = okHttpHandler.uploadImage(OkHttpHandler.getPATH() + "uploadUserImage.php", file);

                try {
                    URL url = new URL(urlString);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    image.setImageBitmap(bmp);
                    image.setTag(urlString);
                    input[3] = true;
                    button.setEnabled(input[0] && input[1] && input[2]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static File createTempFileFromInputStream(Context context, InputStream inputStream) {
        File tempFile = null;
        FileOutputStream outputStream = null;

        try {
            // Create a temporary file
            tempFile = File.createTempFile("temp", ".tmp", context.getCacheDir());

            // Open an OutputStream to write to the temporary file
            outputStream = new FileOutputStream(tempFile);

            // Read from the InputStream and write to the OutputStream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the streams
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return tempFile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflates the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register_user, container, false);
        button = rootView.findViewById(R.id.registerBtn);
        button.setEnabled(false);
        email = rootView.findViewById(R.id.editTextTextEmailAddress);
        username = rootView.findViewById(R.id.editTextTextUserName);
        password = rootView.findViewById(R.id.editTextTextPassword);
        image = rootView.findViewById(R.id.userImage);


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* do nothing */ }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equalsIgnoreCase("admin")) {
                    input[0] = false;
                } else {
                    input[0] = !(s.toString().isEmpty());
                }
                button.setEnabled(input[0] && input[1] && input[2] && input[3]);
            }
            @Override
            public void afterTextChanged(Editable s) { /* do nothing */ }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* do nothing */ }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input[2] = !(s.toString().isEmpty());
                button.setEnabled(input[0] && input[1] && input[2] && input[3]);
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
                button.setEnabled(input[0] && input[1] && input[2] && input[3]);
            }
            @Override
            public void afterTextChanged(Editable s) { /* do nothing */ }
        });

        button.setOnClickListener(view -> {
            try {
                register();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        image.setOnClickListener(view -> openImagePicker());

        context = requireContext();

        return rootView;
    }

    private void register() throws IOException {
        boolean result;
        int registerCode;
        OkHttpHandler okHttpHandler = new OkHttpHandler();



        result = okHttpHandler.userExists(email.getText().toString(), password.getText().toString());

        if(result) {
            Toast.makeText(getContext(), "User already exists!", Toast.LENGTH_LONG).show();
        } else {
            try {
                registerCode = okHttpHandler.registerUser(email.getText().toString(), username.getText().toString(), password.getText().toString(), image.getTag().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (registerCode == 200){
                Toast.makeText(getContext(), "Register successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(requireActivity(), UserNavigationActivity.class);
                startActivity(intent);
            }
        }
    }
}