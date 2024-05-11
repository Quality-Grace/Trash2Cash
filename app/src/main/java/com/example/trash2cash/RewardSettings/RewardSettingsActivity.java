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
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.trash2cash.imageGallery.ImagePickerActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.Reward;
import com.example.trash2cash.RewardList;

import java.util.Objects;

public class RewardSettingsActivity extends AppCompatActivity implements RewardRecyclerInterface{
    private static final RewardList rewardList = new RewardList();
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
        ImageView addCardButton = (ImageView) findViewById(R.id.addCardButton);
        addCardButton.setOnClickListener(addCardListener());

        ImageView image = (ImageView) findViewById(R.id.addRewardImage);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri resultUri = data.getData();
                            if(data.getStringExtra("type").equals("fromGallery")){
                                getContentResolver().takePersistableUriPermission(resultUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            }

                            image.setImageURI(resultUri);
                            image.setTag(String.valueOf(resultUri));
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
            int cost = parseEditableTextToInt(((EditText) findViewById(R.id.addCostText)).getText());
            int level = parseEditableTextToInt(((EditText) findViewById(R.id.addLevelRequiredText)).getText());

            String icon = (String) ((ImageView) findViewById(R.id.addRewardImage)).getTag();

            if(icon == null) {
                icon = "android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+R.drawable.ic_launcher_foreground;
            }

            rewardList.add(new Reward(cost, level, icon));
            int position = rewardList.size()==0 ? 0: rewardList.size()-1;
            adapter.notifyItemInserted(position);
        };
    }

    public int parseEditableTextToInt(Editable text){
        try{
            return Integer.parseInt(String.valueOf(text));
        } catch(Exception e) {
            return 0;
        }
    }

    // Removes a reward from the list
    @Override
    public void removeCardOnClick(int position) {
        rewardList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}