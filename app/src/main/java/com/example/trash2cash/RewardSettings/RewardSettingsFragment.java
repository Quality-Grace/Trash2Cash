package com.example.trash2cash.RewardSettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.Reward;
import com.example.trash2cash.Entities.RewardList;
import com.example.trash2cash.R;
import com.example.trash2cash.imageGallery.ImagePickerActivity;
import com.squareup.picasso.Picasso;

public class RewardSettingsFragment extends Fragment implements RewardSettingsRecyclerInterface {
    private final RewardList rewardList = new RewardList();
    private RewardSettingsRecyclerAdapter adapter;
    private View rootView;

    public RewardSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_reward_settings, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.rewardSettingsRecyclerView);

        // Adds CardView components to the Recycler
        adapter = new RewardSettingsRecyclerAdapter(getContext(), rewardList, this);
        recyclerView.setAdapter(adapter);

        new Thread(()->{
            rewardList.clear();
            rewardList.addAll(new RewardList(OkHttpHandler.getPATH()+"populateRewards.php"));
            try{
                requireActivity().runOnUiThread(()-> adapter.notifyDataSetChanged());
            } catch(Exception ignored){}
        }).start();


        // Sets a vertical layout for the Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        // Allows the user to long click the card in order to add a reward to the reward list
        rootView.findViewById(R.id.rewardSettingsCard).setOnLongClickListener(view -> {
            addCard();
            return true;
        });

        ImageView image = rootView.findViewById(R.id.addRewardImage);

        // Handles the result from the image picker
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String urlString = data.getStringExtra("url");
                            Picasso.with(getContext()).load(urlString).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(image);
                            image.setTag(urlString);
                        }
                    }
                });

        // Initializes the image listener that starts the image picker
        image.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ImagePickerActivity.class);
            launcher.launch(intent);
        });

        // Helpful toast message to inform the admin quickly on how to add or remove a reward
        Toast.makeText(getContext(), "Long tap on card to add/remove a reward", Toast.LENGTH_LONG).show();

        return rootView;
    }

    // Adds a new reward to the list
    public void addCard(){
        String title = UserInputParser.parseEditableTextToString(((EditText) rootView.findViewById(R.id.addRewardTitleText)).getText());
        int cost = UserInputParser.parseEditableTextToInt(((EditText) rootView.findViewById(R.id.addCostText)).getText());
        int level = UserInputParser.parseEditableTextToInt(((EditText) rootView.findViewById(R.id.addLevelRequiredText)).getText());

        String icon = (String) rootView.findViewById(R.id.addRewardImage).getTag();

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
        Toast.makeText(getContext(), "New reward was added to the list!", Toast.LENGTH_SHORT).show();
    }

    // Removes a reward from the list
    @Override
    public void removeCardOnLongClick(int position) {
        View currentFocus = requireActivity().getCurrentFocus();
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
        Toast.makeText(getContext(), "The reward was removed from the list!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateReward(Reward reward, int position) throws Exception {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.updateReward(OkHttpHandler.getPATH()+"updateReward.php?COST=" + reward.getCost() + "&LEVEL=" + reward.getLevel() + "&ICON=" + reward.getIcon() + "&TITLE=" + reward.getTitle() + "&CODE=\"" + reward.getCode() + "\"");
    }
}