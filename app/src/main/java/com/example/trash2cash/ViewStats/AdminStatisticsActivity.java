package com.example.trash2cash.ViewStats;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.R;

import java.util.ArrayList;
import java.util.List;

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

        fetchStatistics();
    }

    private void fetchStatistics() {
        new Thread(() -> {
            try {
                List<UserStatistics> userStatistics = okHttpHandler.getTopUserStatistics(STATS_URL);
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
}
