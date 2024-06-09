package com.example.trash2cash.ViewStats;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trash2cash.AdminRequestsLogger.AdminRequestsLoggerActivity;
import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.LoginRegisterActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsActivity;
import com.example.trash2cash.RewardSettings.RewardSettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminStatisticsActivity extends AppCompatActivity {

    private ListView statisticsListView;
    private UserStatisticsAdapter statisticsAdapter;
    private List<UserStatistics> statisticsList = new ArrayList<>();
    private OkHttpHandler okHttpHandler = new OkHttpHandler();
    private static final String STATS_URL = OkHttpHandler.getPATH() + "getUserStatistics.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_statistics);

        statisticsListView = findViewById(R.id.statisticsListView);
        statisticsAdapter = new UserStatisticsAdapter(this, statisticsList);
        statisticsListView.setAdapter(statisticsAdapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setupNavigationListener(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.StatsItem);
        fetchStatistics();
    }

    private void fetchStatistics() {
        new Thread(() -> {
            try {
                List<UserStatistics> userStatistics = new ArrayList<>();
                Map<Integer, User> users = new HashMap<>(RecyclableManager.getRecyclableManager().getUsers());
                for(User user : users.values()){
                    userStatistics.add(new UserStatistics(user.getId(), user.getLevel(), user.getRewardPoints(), user.getName()));
                }
                Collections.sort(userStatistics, Collections.reverseOrder());
                runOnUiThread(() -> displayStatistics(userStatistics));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void displayStatistics(List<UserStatistics> userStatistics) {
        statisticsList.clear();
        statisticsList.addAll(userStatistics);
        statisticsAdapter.notifyDataSetChanged();
    }

    public void setupNavigationListener(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RewardsItem) {
                startActivity(new Intent(AdminStatisticsActivity.this, RewardSettingsActivity.class));
            } else if(item.getItemId() == R.id.RecycleItem) {
                startActivity(new Intent(AdminStatisticsActivity.this, AdminRequestsLoggerActivity.class));
            } else if(item.getItemId() == R.id.LogoutItem){
                startActivity(new Intent(AdminStatisticsActivity.this, LoginRegisterActivity.class));
            } else if(item.getItemId() == R.id.MaterialsItem){
                startActivity(new Intent(AdminStatisticsActivity.this, RecyclableMaterialSettingsActivity.class));
            }

            return true;
        });
    }
}
