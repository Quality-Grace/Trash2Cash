package com.example.trash2cash.ViewStats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trash2cash.R;

import java.util.List;

public class UserStatisticsAdapter extends ArrayAdapter<UserStatistics> {
    private Context context;
    private List<UserStatistics> userStatisticsList;

    public UserStatisticsAdapter(Context context, List<UserStatistics> userStatisticsList) {
        super(context, R.layout.list_item_statistics, userStatisticsList);
        this.context = context;
        this.userStatisticsList = userStatisticsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_statistics, parent, false);
        }

        UserStatistics userStatistics = userStatisticsList.get(position);

        TextView rankTextView = convertView.findViewById(R.id.rankTextView);
        ImageView badgeImageView = convertView.findViewById(R.id.badgeImageView);
        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
        TextView recycledItemsTextView = convertView.findViewById(R.id.recycledItemsTextView);

        rankTextView.setText(String.valueOf(position + 1));
        userNameTextView.setText(userStatistics.getName());
        recycledItemsTextView.setText("Total Reward Points: " + userStatistics.getRewardPoints());

        // Set badge based on position
        if (position == 0) {
            badgeImageView.setImageResource(R.drawable.gold_badge);
        } else if (position == 1) {
            badgeImageView.setImageResource(R.drawable.silver_badge);
        } else if (position == 2) {
            badgeImageView.setImageResource(R.drawable.bronze_badge);
        } else {
            badgeImageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
