package com.example.trash2cash.AdminRequestsLogger;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.Entities.Admin;
import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.R;


import java.util.List;

public class AdminRequestsLoggerAdapter extends RecyclerView.Adapter<AdminRequestsLoggerAdapter.MyViewHolder> {
    private final List<Request> requestList;

    private final Context context;

    private final AdminRequestsLoggerInterface adminRequestsLoggerInterface;

    public AdminRequestsLoggerAdapter(List<Request> requestList, Context context, AdminRequestsLoggerInterface adminRequestsLoggerInterface) {
        this.requestList = requestList;
        this.context = context;
        this.adminRequestsLoggerInterface = adminRequestsLoggerInterface;
    }

    @NonNull
    @Override
    public AdminRequestsLoggerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.request_card, parent, false);
        return new AdminRequestsLoggerAdapter.MyViewHolder(view, requestList, adminRequestsLoggerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRequestsLoggerAdapter.MyViewHolder holder, int position) {
        holder.itemTextView.setText(requestList.get(position).getRequestItem().getType().toString());
        holder.materialTextView.setText(requestList.get(position).getRequestItem().getMaterial().getType());
        holder.imageView.setImageResource(requestList.get(position).getRequestItem().getImage());
        RecyclableManager recyclableManager = RecyclableManager.getRecyclableManager();
        User user = recyclableManager.getUserById(requestList.get(position).getUser_id());
        holder.userTextView.setText(String.format("From %s", user.getName()));
    }

    @Override
    public int getItemCount() {
        if (requestList == null) {
            return 0;
        }
        return requestList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTextView;
        public TextView materialTextView;
        public ImageView imageView;
        public ImageView approveView;
        public ImageView rejectView;
        public TextView userTextView;
        public MyViewHolder(View requestView, List<Request> requestList, AdminRequestsLoggerInterface adminRequestsLoggerInterface) {
            super(requestView);
            itemTextView = requestView.findViewById(R.id.ItemTextView);
            materialTextView = requestView.findViewById(R.id.materialTextView);
            imageView = requestView.findViewById(R.id.requestItemIMage);
            approveView = requestView.findViewById(R.id.approveBtn);
            rejectView = requestView.findViewById(R.id.rejectBtn);
            userTextView = requestView.findViewById(R.id.userTextView);

            approveView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                RecyclableManager recyclableManager = RecyclableManager.getRecyclableManager();
                Request request = requestList.get(position);
                int user_id = request.getUser_id();
                Admin.getAdmin().approve(user_id, request);
                adminRequestsLoggerInterface.onRequestClick(position);

            });
            rejectView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                RecyclableManager recyclableManager = RecyclableManager.getRecyclableManager();
                Request request = requestList.get(position);
                int user_id = request.getUser_id();
                Admin.getAdmin().reject(user_id, request);
                adminRequestsLoggerInterface.onRequestClick(position);
            });
        }
    }
    
}
