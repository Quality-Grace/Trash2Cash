package com.example.trash2cash.MaterialLogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.Entities.RequestStatus;
import com.example.trash2cash.R;

import java.util.List;

public class MaterialLoggerAdapter extends RecyclerView.Adapter<MaterialLoggerAdapter.MyViewHolder> {

    private final List<Request> requestList;

    private final Context context;

    public MaterialLoggerAdapter(List<Request> requestList, Context context) {
        this.requestList = requestList;
        this.context = context;
    }

    @NonNull
    @Override
    public MaterialLoggerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_card, parent, false);
        return new MaterialLoggerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemTextView.setText(requestList.get(position).getRequestItem().getType().toString());
        holder.materialTextView.setText(requestList.get(position).getRequestItem().getMaterial().getType());
        holder.imageView.setImageResource(requestList.get(position).getRequestItem().getImage());
        holder.cardView.setCardBackgroundColor(getStatusColor(requestList.get(position).getRequestStatus()));
    }

    private int getStatusColor(RequestStatus request) {
        switch (request){
            case PENDING:
                return context.getResources().getColor(R.color.pending);
            case APPROVED:
                return context.getResources().getColor(R.color.approved);
            case REJECTED:
                return context.getResources().getColor(R.color.rejected);
            default:
                return context.getResources().getColor(R.color.pending);
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTextView;
        public TextView materialTextView;
        public ImageView imageView;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.ItemTextView);
            materialTextView = view.findViewById(R.id.materialTextView);
            imageView = view.findViewById(R.id.requestItemIMage);
            cardView = view.findViewById(R.id.addRequestedItemCardView);
        }
    }
}

