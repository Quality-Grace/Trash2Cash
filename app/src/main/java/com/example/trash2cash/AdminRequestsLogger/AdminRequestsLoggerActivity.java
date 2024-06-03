package com.example.trash2cash.AdminRequestsLogger;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.trash2cash.Entities.Admin;
import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.R;

import java.util.ArrayList;

public class AdminRequestsLoggerActivity extends AppCompatActivity implements AdminRequestsLoggerInterface{

    private ArrayList<Request> itemList = new ArrayList<>();

    private AdminRequestsLoggerAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_requests_logger);

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.requestsRecyclerView);

        Admin admin = Admin.getAdmin();

        for(Integer user_id : admin.getRecyclableRequests().keySet()){
            itemList.addAll(admin.getRecyclableRequests().get(user_id));
        }


        myAdapter = new AdminRequestsLoggerAdapter(itemList, this, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(myAdapter);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onRequestClick(int position) {
        View currentFocus = getCurrentFocus();
        if(currentFocus != null) currentFocus.clearFocus();

        itemList.remove(position);
        myAdapter.notifyItemRemoved(position);
    }
}