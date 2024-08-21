package com.example.trash2cash.ViewStats;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminStatisticsFragment extends Fragment {

    private UserStatisticsAdapter statisticsAdapter;
    private final List<UserStatistics> statisticsList = new ArrayList<>();
    public AdminStatisticsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_admin_statistics, container, false);
        ListView statisticsListView = rootView.findViewById(R.id.statisticsListView);
        statisticsAdapter = new UserStatisticsAdapter(getContext(), statisticsList);
        statisticsListView.setAdapter(statisticsAdapter);
        fetchStatistics();
        return rootView;
    }

    private void fetchStatistics() {
        new Thread(() -> {
            try {
                List<UserStatistics> userStatistics = new ArrayList<>();
                Map<Integer, User> users = new HashMap<>(RecyclableManager.getRecyclableManager().getUsers());
                for(User user : users.values()){
                    userStatistics.add(new UserStatistics(user.getId(), user.getLevel(), user.getRewardPoints(), user.getName()));
                }
                userStatistics.sort(Collections.reverseOrder());
                try{
                    requireActivity().runOnUiThread(() -> displayStatistics(userStatistics));
                } catch (Exception ignored){}
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