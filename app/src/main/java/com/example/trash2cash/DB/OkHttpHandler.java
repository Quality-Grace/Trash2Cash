package com.example.trash2cash.DB;

import android.os.StrictMode;

import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.RecyclableMaterial;
import com.example.trash2cash.Entities.Reward;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.LoginRegisterActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler {
    private static final String IP = "172.19.0.1";

    private int id;
    private static final String PATH = "http://"+IP+"/trash2cash/";
    public OkHttpHandler() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static String getPATH(){
        return PATH;
    }

    public ArrayList<Reward> populateRewards(String url) throws Exception {
        ArrayList<Reward> rewardList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();

        try{
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int cost = jsonObject.getInt("COST");
                int level = jsonObject.getInt("LEVEL");
                String code = jsonObject.getString("CODE");
                String icon = jsonObject.getString("ICON");
                String title = jsonObject.getString("TITLE");

                Reward reward = new Reward(title, cost, level, icon, code);
                rewardList.add(reward);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return rewardList;
    }

    public void updateReward(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("PUT", body).build();
        client.newCall(request).execute();
    }

    public void insertReward(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        client.newCall(request).execute();
    }

    public void deleteReward(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("DELETE", body).build();
        client.newCall(request).execute();
    }

    public String uploadImage(String url, File file) {
        try {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");

            RequestBody req = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                            "image",
                            UUID.randomUUID().toString()+".jpg",
                            RequestBody.create(MEDIA_TYPE_PNG, file)
                    )
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(req)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return OkHttpHandler.getPATH()+response.body().string();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getRewardImages(String url) throws Exception {
        ArrayList<String> rewardImages = new ArrayList<>();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();

        try{
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                rewardImages.add(url.substring(0, url.indexOf("getRewardImages"))+"rewardImages/"+jsonArray.getString(i));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return rewardImages;
    }

    public ArrayList<RecyclableMaterial> populateRecyclableMaterialTypes(String url) throws Exception {
        ArrayList<RecyclableMaterial> recyclableMaterialArrayList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();

        try{
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int exp = jsonObject.getInt("EXP");
                int rewardAmount = jsonObject.getInt("REWARD_AMOUNT");
                String type = jsonObject.getString("TYPE");
                String image = "recyclableMaterialTypes"+jsonObject.getString("IMAGE");
                String colour = jsonObject.getString("COLOUR");

                RecyclableMaterial recyclableMaterial = new RecyclableMaterial(type, exp, rewardAmount, image, colour);
                recyclableMaterialArrayList.add(recyclableMaterial);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return recyclableMaterialArrayList;
    }

    public void updateMaterialType(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("PUT", body).build();
        client.newCall(request).execute();
    }

    public void takeUser(){
        String url = PATH+ "takeUser.php";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Handle the response
                    String responseData = response.body().string();
                    System.out.println(responseData);
                } else {
                    System.err.println("Request failed with status code: " + response.code());
                }
            }
        });
    }

    public boolean loginUser(String url, String email, String password) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://" + IP + url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        JsonObject jsonObject = parseUserData(response.body().string());
        assert response.body() != null;
        User user = new User(jsonObject.get("email").getAsString(),jsonObject.get("id").getAsInt(), jsonObject.get("level").getAsInt(), jsonObject.get("rewardPoints").getAsInt());
        RecyclableManager.getRecyclableManager().setActiveUser(user);
        return response.isSuccessful();
    }

    private JsonObject parseUserData(String responseData) {
        JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();

        if (jsonObject.has("error")) {
            System.err.println("Error: " + jsonObject.get("error").getAsString());
        } else {
            // Print user details
            System.out.println("User data: " + jsonObject.toString());
        }
        return jsonObject;
    }

    public void registerUser(String url, String email, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String urlTmp = "http://" + IP + "/trash2cash/createDBIfNotExists.php";

        Request requestTmp = new Request.Builder()
                .url(urlTmp)
                .build();

        client.newCall(requestTmp).execute();

        OkHttpClient clientRe = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("level", "0")
                .add("rewardPoints", "0")
                .build();

        Request request = new Request.Builder()
                .url("http://" + IP + url)
                .post(body)
                .build();



        Response response = clientRe.newCall(request).execute();

        System.out.println(response.code());
    }


}
