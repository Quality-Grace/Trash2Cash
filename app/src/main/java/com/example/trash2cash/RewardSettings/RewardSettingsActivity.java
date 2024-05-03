package com.example.trash2cash.RewardSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.trash2cash.R;
import com.example.trash2cash.Reward;
import com.example.trash2cash.RewardList;

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
    }

    public View.OnClickListener addCardListener(){
        return view -> {
            int cost = parseEditableTextToInt(((EditText) findViewById(R.id.addCostText)).getText());
            int level = parseEditableTextToInt(((EditText) findViewById(R.id.addLevelRequiredText)).getText());
            // TODO
            // Allow user to pick image
            int icon = R.drawable.ic_launcher_foreground;

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

    @Override
    public void removeCardOnClick(int position) {
        rewardList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}