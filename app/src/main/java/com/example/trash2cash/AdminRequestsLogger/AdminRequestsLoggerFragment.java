package com.example.trash2cash.AdminRequestsLogger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trash2cash.Entities.Admin;
import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.R;

import java.util.ArrayList;

public class AdminRequestsLoggerFragment extends Fragment implements AdminRequestsLoggerInterface {

    private ArrayList<Request> requestList = new ArrayList<>();

    private AdminRequestsLoggerAdapter myAdapter;

    public AdminRequestsLoggerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_requests_logger, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.requestsRecyclerView);

        myAdapter = new AdminRequestsLoggerAdapter(requestList, getContext(), this);

        new Thread(()->{
            requestList.clear();
            Admin admin = Admin.getAdmin();

            for(Integer user_id : admin.getRecyclableRequests().keySet()){
                requestList.addAll(admin.getRecyclableRequests().get(user_id));
            }

            try{
                requireActivity().runOnUiThread(()-> myAdapter.notifyDataSetChanged());
            } catch (Exception ignored){}

        }).start();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(myAdapter);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    @Override
    public void onRequestClick(int position) {
        View currentFocus = requireActivity().getCurrentFocus();
        if(currentFocus != null) currentFocus.clearFocus();

        requestList.remove(position);
        myAdapter.notifyItemRemoved(position);
    }
}