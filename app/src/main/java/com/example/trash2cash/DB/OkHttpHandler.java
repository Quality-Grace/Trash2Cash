package com.example.trash2cash.DB;

import android.os.StrictMode;

import com.example.trash2cash.Entities.RecyclableItem;
import com.example.trash2cash.Entities.RecyclableItemType;
import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.RecyclableMaterial;
import com.example.trash2cash.Entities.RequestStatus;
import com.example.trash2cash.Entities.Reward;
import com.example.trash2cash.Entities.RewardList;
import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.Entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler {
    private static final String IP = "Your Ip";
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
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).build();
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
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("PUT", body).build();
        client.newCall(request).execute();
    }

    public void insertReward(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).build();
        client.newCall(request).execute();
    }

    public void deleteReward(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("DELETE", body).build();
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

            okhttp3.Request request = new okhttp3.Request.Builder()
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
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).build();
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
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();

        try{
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int exp = jsonObject.getInt("EXP");
                int rewardAmount = jsonObject.getInt("REWARD_AMOUNT");
                int recycleAmount = jsonObject.getInt("RECYCLE_AMOUNT");
                String type = jsonObject.getString("TYPE");
                String image = "recyclableMaterialTypes"+jsonObject.getString("IMAGE");
                String colour = jsonObject.getString("COLOUR");

                RecyclableMaterial recyclableMaterial = new RecyclableMaterial(type, exp, rewardAmount, recycleAmount, image, colour);
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
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("PUT", body).build();
        client.newCall(request).execute();
    }

    public boolean userExists (String email, String password) throws IOException {
        initializeDB_Server();
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH+"loginUser.php")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return !response.body().string().equals("0");
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loginUser(String email, String password) throws IOException {
        initializeDB_Server();
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH+"loginUser.php")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        JsonObject jsonObject = parseUserData(response.body().string());
        assert response.body() != null;

        Gson gson = new Gson();
        RewardList rewardList =  gson.fromJson(jsonObject.get("rewardList").getAsString(), RewardList.class);

        User user = new User(jsonObject.get("email").getAsString(), jsonObject.get("username").getAsString(),jsonObject.get("id").getAsInt(), jsonObject.get("level").getAsFloat(), jsonObject.get("rewardPoints").getAsFloat(), jsonObject.get("image").getAsString(), rewardList);
        RecyclableManager.getRecyclableManager().setActiveUser(user);
        return response.isSuccessful();
    }

    private JsonObject parseUserData(String responseData) {
        JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();

        if (jsonObject.has("error")) {
            System.err.println("Error: " + jsonObject.get("error").getAsString());
        } else {
            // Print user details
            System.out.println("User data: " + jsonObject);
        }
        return jsonObject;
    }

    public int registerUser(String email, String username, String password, String image) throws IOException {
        initializeDB_Server();

        Gson gson = new Gson();
        String rewardList = gson.toJson(new RewardList());

        OkHttpClient clientRe = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("username", username)
                .add("password", password)
                .add("level", "0")
                .add("rewardPoints", "0")
                .add("image", image)
                .add("rewardList", rewardList)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH + "addUser.php")
                .post(body)
                .build();



        Response response = clientRe.newCall(request).execute();

        System.out.println(response.code());
        if(response.code()==200){
            loginUser(email, password);
        }

        return response.code();
    }

    private static void initializeDB_Server() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String urlTmp = PATH+"createDBIfNotExists.php";

        okhttp3.Request requestTmp = new okhttp3.Request.Builder()
                .url(urlTmp)
                .build();

        client.newCall(requestTmp).execute();
    }

    public int addItem(String material_type, String item_type) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = new FormBody.Builder()
                .add("item_type", item_type)
                .add("material_type", material_type)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(body)
                .url(PATH+"addItem.php")
                .build();
        Response response = client.newCall(request).execute();

        Gson gson = new Gson();
        JsonObject jsonResponse = gson.fromJson(response.body().string(), JsonObject.class);
        return jsonResponse.get("id").getAsInt();
    }

    public ArrayList<Request> takeRequestsByUserId(Integer user_id){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = new FormBody.Builder()
                .add("user_id", String.valueOf(user_id))
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(body)
                .url(PATH+"takeUserRequests.php")
                .build();

        ArrayList<Request> requests = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            JsonArray jsonResponse = gson.fromJson(response.body().string(), JsonArray.class);
            for (int i = 0; i < jsonResponse.size(); i++) {
                JsonObject jsonObject = jsonResponse.get(i).getAsJsonObject();
                int id = jsonObject.get("id").getAsInt();
                int item_id = jsonObject.get("item_id").getAsInt();
                int userId = jsonObject.get("user_id").getAsInt();
                String status = jsonObject.get("status").getAsString();
                RecyclableItem recyclableItem = takeItemById(item_id);
                Request requestObject = new Request(recyclableItem, RequestStatus.valueOf(status), id, userId);
                requests.add(requestObject);
            }
        } catch (Exception e){
            System.out.println("Error");
        }


        return requests;
    }

    public RecyclableItem takeItemById(Integer item_id){
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("item_id", String.valueOf(item_id))
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(body)
                .url(PATH+"takeItemById.php")
                .build();

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.body().string(), JsonObject.class);
            String material_type = jsonResponse.get("material_type").getAsString();
            String item_type = jsonResponse.get("item_type").getAsString();
            int id = jsonResponse.get("id").getAsInt();
            RecyclableMaterial recyclableMaterial = RecyclableManager.getRecyclableManager().getRecyclableMaterial(material_type);
            RecyclableItem recyclableItem = RecyclableManager.getRecyclableManager().createRecyclableItem(recyclableMaterial, RecyclableItemType.fromString(item_type));
            return new RecyclableItem(recyclableMaterial, RecyclableItemType.fromString(item_type), recyclableItem.getImage(), id);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }


    public int addRequest(Integer user_id, Integer item_id, RequestStatus status) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("user_id", String.valueOf(user_id))
                .add("item_id", String.valueOf(item_id))
                .add("status", status.getRequestStatus())
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH+"addRequest.php")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return Integer.parseInt(response.body().string());
    }

    public List<Integer> getAllUserIds() {
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH+"takeAllUsers.php")
                .post(RequestBody.create(MediaType.parse("application/json"), "{}"))
                .build();

        List<Integer> userIds = new ArrayList<>();

        try {
            Response response = client.newCall(request).execute();

            Gson gson = new Gson();
            JsonArray jsonResponse = gson.fromJson(response.body().string(), JsonArray.class)
                    .getAsJsonArray();
            for (int i = 0; i < jsonResponse.size(); i++) {
                Integer id = jsonResponse.get(i).getAsInt();
                userIds.add(id);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return userIds;
    }

    public void alterRequestStatus(Integer request_id, RequestStatus status){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = new FormBody.Builder()
                .add("id", String.valueOf(request_id))
                .add("status", status.getRequestStatus())
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH+"updateReqStatus.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUser(User user){
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = new FormBody.Builder()
                .add("id", String.valueOf(user.getId()))
                .add("level", String.valueOf(user.getLevel()))
                .add("reward_points", String.valueOf(user.getRewardPoints()))
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH+"updateUser.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUserRewardList(User user){
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Gson gson = new Gson();
        String rewardList = gson.toJson(user.getRewardList());

        RequestBody body = new FormBody.Builder()
                .add("id", String.valueOf(user.getId()))
                .add("rewardList", rewardList)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(PATH+"updateUserRewardList.php")
                .post(body)
                .build();

        try {
           client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
