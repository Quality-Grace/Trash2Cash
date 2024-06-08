package com.example.trash2cash.AdminRequestsLogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.trash2cash.Entities.Admin;
import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.LoginRegisterActivity;
import com.example.trash2cash.MainActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsActivity;
import com.example.trash2cash.RewardSettings.RewardSettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AdminRequestsLoggerActivity extends AppCompatActivity implements AdminRequestsLoggerInterface{

    private ArrayList<Request> requestList = new ArrayList<>();

    private AdminRequestsLoggerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests_logger);

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.requestsRecyclerView);

        Admin admin = Admin.getAdmin();

        for(Integer user_id : admin.getRecyclableRequests().keySet()){
            requestList.addAll(admin.getRecyclableRequests().get(user_id));
        }


        myAdapter = new AdminRequestsLoggerAdapter(requestList, this, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(myAdapter);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setupNavigationListener(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.RecycleItem);
    }

    public void setupNavigationListener(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RewardsItem) {
                startActivity(new Intent(AdminRequestsLoggerActivity.this, RewardSettingsActivity.class));
            } else if(item.getItemId() == R.id.StatsItem) {
                startActivity(new Intent(AdminRequestsLoggerActivity.this, MainActivity.class));
            } else if(item.getItemId() == R.id.LogoutItem) {
                startActivity(new Intent(AdminRequestsLoggerActivity.this, LoginRegisterActivity.class));
           } else if(item.getItemId() == R.id.MaterialsItem) {
                startActivity(new Intent(AdminRequestsLoggerActivity.this, RecyclableMaterialSettingsActivity.class));
            }

            return true;
        });
    }

    @Override
    public void onRequestClick(int position) {
        View currentFocus = getCurrentFocus();
        if(currentFocus != null) currentFocus.clearFocus();

        requestList.remove(position);
        myAdapter.notifyItemRemoved(position);
    }
}