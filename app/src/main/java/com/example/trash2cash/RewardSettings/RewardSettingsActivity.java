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

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.imageGallery.ImagePickerActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.Entities.Reward;
import com.example.trash2cash.Entities.RewardList;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;

public class RewardSettingsActivity extends AppCompatActivity implements RewardRecyclerInterface{
    private final RewardList rewardList = new RewardList();
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

        // Adds a listener to the addCardButton
        // When the addCardButton is clicked a new card is added the recycler view
        ImageView addCardButton = findViewById(R.id.addCardButton);
        addCardButton.setOnClickListener(addCardListener());

        ImageView image = findViewById(R.id.addRewardImage);

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


        image.setOnClickListener(view -> {
            Intent intent = new Intent(this, ImagePickerActivity.class);
            launcher.launch(intent);
        });
    }

    // Adds a new reward to the list
    public View.OnClickListener addCardListener(){
        return view -> {
            String title = UserInputParser.parseEditableTextToString(((EditText) findViewById(R.id.addRewardTitleText)).getText());
            int cost = UserInputParser.parseEditableTextToInt(((EditText) findViewById(R.id.addCostText)).getText());
            int level = UserInputParser.parseEditableTextToInt(((EditText) findViewById(R.id.addLevelRequiredText)).getText());

            String icon = (String) findViewById(R.id.addRewardImage).getTag();

            if(icon == null) {
                icon = "android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+R.drawable.ic_launcher_foreground;
            }

            Reward reward = new Reward(title, cost, level, icon);
            rewardList.add(reward);
            int position = rewardList.size()==0 ? 0: rewardList.size()-1;
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            try {
                okHttpHandler.insertReward(OkHttpHandler.getPATH()+"insertReward.php?POSITION=" + position + "&COST=" + reward.getCost() + "&LEVEL=" + reward.getLevel() + "&ICON=" + reward.getIcon() + "&TITLE=" + reward.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }

            adapter.notifyItemInserted(position);
        };
    }

    // Removes a reward from the list
    @Override
    public void removeCardOnClick(int position) {
        View currentFocus = getCurrentFocus();
        if(currentFocus!=null) currentFocus.clearFocus();

        OkHttpHandler okHttpHandler = new OkHttpHandler();
        try{
            okHttpHandler.deleteReward(OkHttpHandler.getPATH()+"deleteReward.php?POSITION=" + position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rewardList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void moveCardUp(int position) {
        if(position>0){
            Collections.swap(rewardList, position, position-1);
            try {
                updateReward(rewardList.get(position), position);
                updateReward(rewardList.get(position-1), position-1);
            } catch(Exception e){
                e.printStackTrace();
            }
            adapter.notifyItemMoved(position, position-1);
        } else if(rewardList.size()>0){
            Collections.swap(rewardList, position, position+1);
            try {
                updateReward(rewardList.get(position), position);
                updateReward(rewardList.get(position+1), position+1);
            } catch(Exception e){
                e.printStackTrace();
            }
            adapter.notifyItemMoved(position, position+1);
        }
    }

    @Override
    public void updateReward(Reward reward, int position) throws Exception {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.updateReward(OkHttpHandler.getPATH()+"updateReward.php?POSITION=" + position + "&COST=" + reward.getCost() + "&LEVEL=" + reward.getLevel() + "&ICON=" + reward.getIcon() + "&TITLE=" + reward.getTitle());
    }
}