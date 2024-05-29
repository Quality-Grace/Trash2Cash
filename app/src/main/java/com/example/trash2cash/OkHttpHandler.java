package com.example.trash2cash;

import android.os.StrictMode;

import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler {
    private int id;

    public OkHttpHandler() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    public int loginUser(String url, String email, String password) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        assert response.body() != null;
        return Integer.parseInt(response.body().string());
    }

    public int registerUser(String url, String email, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String urlTmp = "http://" + LoginRegisterActivity.getIP() + "/trash2cash/createDBIfNotExists.php";

        Request requestTmp = new Request.Builder()
                .url(urlTmp)
                .build();

        client.newCall(requestTmp).execute();

        OkHttpClient clientRe = new OkHttpClient().newBuilder().build();


        RequestBody body = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("email", email)
                .add("password", password)
                .build();
        id++;
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = clientRe.newCall(request).execute();
        assert response.body() != null;
        return Integer.parseInt(response.body().string());
    }
}

