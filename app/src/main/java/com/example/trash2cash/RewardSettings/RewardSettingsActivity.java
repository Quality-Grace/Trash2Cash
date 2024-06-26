package com.example.trash2cash.RewardSettings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trash2cash.AdminRequestsLogger.AdminRequestsLoggerActivity;
import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.LoginRegisterActivity;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsActivity;
import com.example.trash2cash.ViewStats.AdminStatisticsActivity;
import com.example.trash2cash.imageGallery.ImagePickerActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.Entities.Reward;
import com.example.trash2cash.Entities.RewardList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class RewardSettingsActivity extends AppCompatActivity implements RewardSettingsRecyclerInterface {
    private final RewardList rewardList = new RewardList(OkHttpHandler.getPATH()+"populateRewards.php");
    private RewardSettingsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_settings);

        RecyclerView recyclerView = findViewById(R.id.rewardSettingsRecyclerView);

        // Adds CardView components to the Recycler
        adapter = new RewardSettingsRecyclerAdapter(this, rewardList, this);
        recyclerView.setAdapter(adapter);

        // Sets a vertical layout for the Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        // Allows the user to long click the card in order to add a reward to the reward list
        findViewById(R.id.rewardSettingsCard).setOnLongClickListener(view -> {
            addCard();
            return true;
        });

        ImageView image = findViewById(R.id.addRewardImage);

        // Handles the result from the image picker
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String urlString = data.getStringExtra("url");
                            try {
                                URL url = new URL(urlString);
                                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                image.setImageBitmap(bmp);
                                image.setTag(urlString);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        // Initializes the image listener that starts the image picker
        image.setOnClickListener(view -> {
            Intent intent = new Intent(this, ImagePickerActivity.class);
            launcher.launch(intent);
        });

        // Helpful toast message to inform the admin quickly on how to add or remove a reward
        Toast.makeText(getApplicationContext(), "Long tap on card to add/remove a reward", Toast.LENGTH_LONG).show();

        // Initializes the navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setupNavigationListener(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.RewardsItem);
    }

    public void setupNavigationListener(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RecycleItem){
                startActivity(new Intent(RewardSettingsActivity.this, AdminRequestsLoggerActivity.class));
            } else if(item.getItemId() == R.id.MaterialsItem) {
                startActivity(new Intent(RewardSettingsActivity.this, RecyclableMaterialSettingsActivity.class));
            } else if(item.getItemId() == R.id.StatsItem) {
                startActivity(new Intent(RewardSettingsActivity.this, AdminStatisticsActivity.class));
            } else if(item.getItemId() == R.id.LogoutItem){
                startActivity(new Intent(RewardSettingsActivity.this, LoginRegisterActivity.class));
            }

            return true;
        });
    }

    // Adds a new reward to the list
    public void addCard(){
        String title = UserInputParser.parseEditableTextToString(((EditText) findViewById(R.id.addRewardTitleText)).getText());
        int cost = UserInputParser.parseEditableTextToInt(((EditText) findViewById(R.id.addCostText)).getText());
        int level = UserInputParser.parseEditableTextToInt(((EditText) findViewById(R.id.addLevelRequiredText)).getText());

        String icon = (String) findViewById(R.id.addRewardImage).getTag();

        // If the icon doesn't have a valid url, a default image will load
        if(icon == null) {
            icon = "android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+R.drawable.ic_launcher_foreground;
        }

        // Creates the reward and adds it to the rewardList
        Reward reward = new Reward(title, cost, level, icon);
        rewardList.add(reward);
        int position = rewardList.size()==0 ? 0: rewardList.size()-1;

        // Inserts the reward to the db
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        try {
            okHttpHandler.insertReward(OkHttpHandler.getPATH()+"insertReward.php?COST=" + reward.getCost() + "&LEVEL=" + reward.getLevel() + "&ICON=" + reward.getIcon() + "&TITLE=" + reward.getTitle() + "&CODE=" + reward.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Updates the adapter
        adapter.notifyItemInserted(position);
        // Informs the admin that a reward was added
        Toast.makeText(getApplicationContext(), "New reward was added to the list!", Toast.LENGTH_SHORT).show();
    }

    // Removes a reward from the list
    @Override
    public void removeCardOnLongClick(int position) {
        View currentFocus = getCurrentFocus();
        if(currentFocus!=null) currentFocus.clearFocus();

        // Removes the reward from the db
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        try{
            okHttpHandler.deleteReward(OkHttpHandler.getPATH()+"deleteReward.php?CODE=\"" + rewardList.get(position).getCode() + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Removes the reward from the reward list and updates the adapter
        rewardList.remove(rewardList.get(position));
        adapter.notifyItemRemoved(position);

        // Informs the admin that the reward was removed
        Toast.makeText(getApplicationContext(), "The reward was removed from the list!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateReward(Reward reward, int position) throws Exception {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.updateReward(OkHttpHandler.getPATH()+"updateReward.php?COST=" + reward.getCost() + "&LEVEL=" + reward.getLevel() + "&ICON=" + reward.getIcon() + "&TITLE=" + reward.getTitle() + "&CODE=\"" + reward.getCode() + "\"");
    }
}