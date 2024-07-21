package com.example.trash2cash;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.UserClaimedRewardsScreen.UserClaimedRewardsFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class UserProfileFragment extends Fragment {
    //Find the active user
    User user;
    //HashMap for user's materials and amounts
    HashMap<String, Integer> materials_map;
    //HashMap for user's specific items and amounts
    HashMap<String, HashMap<String,Integer>> items_map;
    private View rootView;

    public UserProfileFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        //store the data in the user's attributes
        user = RecyclableManager.getRecyclableManager().getUser();
        user.storeApprovedRequests();
        user.storeMaterialsAndItemsAmounts();
        materials_map = user.getMaterials_and_Amounts();
        items_map = user.getItems_and_Amounts();

        SetData();

        // Setup on click listener for the claimed rewards screen
        rootView.findViewById(R.id.my_rewards).setOnClickListener(view -> switchFragment(new UserClaimedRewardsFragment()));

        return rootView;
    }
    public void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }
    public void SetData() {

        //set data to users information
        setDataInfo();

        //set data to level bar
        //levelProgressBar();

        //set data to progress bar
        setProgressBarData();

        //ArrayList with all percentages of materials
        ArrayList<Float> percentages = new ArrayList<>();
        for (String key : materials_map.keySet()) {
            percentages.add(user.calculateMaterialAmountPercentage(key));
        }

        //set data to PieChart
        setDataPieChart(percentages);

        //HashMap with percentages of each item
        HashMap<String,HashMap<String,Float>> itemPercentages = new HashMap<>();
        for(String key: items_map.keySet()) {
            HashMap<String,Integer> values = items_map.get(key);
            HashMap<String, Float> tempPer = new HashMap<>(); //temp hashMap for items and their percentages
            for(String item: values.keySet()) {
                tempPer.put(item, user.calculateItemAmountPercentage(key,item));
            }
            itemPercentages.put(key,tempPer);
        }

        //set data to bar Charts
        for(String key: items_map.keySet()) {
            setDataBarChart(key, itemPercentages);
        }

    }

    public void setDataInfo() {

        TextView username,level,points;
        ImageView image;

        username = rootView.findViewById(R.id.user_name);
        level = rootView.findViewById(R.id.user_level);
        points = rootView.findViewById(R.id.user_points);
        image = rootView.findViewById(R.id.profile_photo);

        //set data to users info
        username.setText(username.getText().toString()+" "+user.getName());
        level.setText(String.valueOf(user.getLevel()));
        points.setText(String.valueOf((int) user.getRewardPoints()));

        Picasso.with(getContext()).load(user.getImage()).placeholder(R.drawable.profile_icon).error(R.drawable.profile_icon).into(image);
    }

    public void setProgressBarData() {
        int[] remainingValues = user.getRemainingValues();
        rewardProgressBar(remainingValues[0]);
        levelProgressBar(remainingValues[1]);
    }

    public void levelProgressBar(int max) {
        ProgressBar level_bar = rootView.findViewById(R.id.levelProgressBar);
        int currentLevel = (int)Math.floor(user.getLevel());
        level_bar.setMax(max);
        level_bar.setProgress(currentLevel);
        ((TextView)rootView.findViewById(R.id.remainingLevelsProgressBar)).setText(currentLevel + "/" + max);
        level_bar.invalidate();
    }

    public void rewardProgressBar(int max) {
        ProgressBar pr = rootView.findViewById(R.id.rewardProgressBar);
        int currentProgress = (int) Math.floor(user.getRewardPoints());
        pr.setMax(max);
        pr.setProgress(currentProgress);
        ((TextView)rootView.findViewById(R.id.remainingRewardPointsProgressBar)).setText(currentProgress + "/" + max);
        pr.invalidate();

    }

    public void setDataPieChart(ArrayList<Float> percentages) {

        //Take the PieChart
        PieChart pieChart;
        pieChart = rootView.findViewById(R.id.pieChart);

        //ArrayList for PieChartEntries
        ArrayList<PieEntry> materials = new ArrayList<>();
        int pos =0;
        for (String key : materials_map.keySet()) {
            //Create PieEntry with percentage and material
            materials.add(new PieEntry(percentages.get(pos),key));
            pos++;
        }

        //Define colors for each material
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#42adff")); // glass color
        colors.add(Color.parseColor("#fff842")); // aluminum color
        colors.add(Color.parseColor("#42ff45")); // paper color
        colors.add(Color.parseColor("#ff4242")); // plastic color
        colors.add(Color.parseColor("#6a6a6a")); // other color

        //Create PieDataSet and configure properties
        PieDataSet pieDataSet = new PieDataSet(materials, "Materials");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        //Create PieData object and set it to the PieChar
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        //Make PieChart appearance
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Materials and Amounts");

        // To animate the pie chart
        pieChart.animateY(2000);
        // Refresh the chart
        pieChart.invalidate();
    }

    public void setDataBarChart(String material, HashMap<String,HashMap<String, Float>> percentages) {

        //Take the proper barChart
        BarChart barChart;
        switch (material) {
            case "Glass":
                barChart = rootView.findViewById(R.id.glass_barChart);
                break;
            case "Plastic":
                barChart = rootView.findViewById(R.id.plastic_barChart);
                break;
            case "Paper":
                barChart = rootView.findViewById(R.id.paper_barChart);
                break;
            case "Aluminium":
                barChart = rootView.findViewById(R.id.aluminum_barChart);
                break;
            default:
                barChart = rootView.findViewById(R.id.other_barChart);
                break;
        }

        //ArrayList for BarEntries
        ArrayList<BarEntry> itemsAm = new ArrayList<>();
        //ArrayList for xAxis labels
        ArrayList<String> items_names = new ArrayList<>();
        //take the specific value for each material and create a new BarEntry
        HashMap<String,Float> tempItemsAmounts = percentages.get(material);
        int index=0;
        for(String key: tempItemsAmounts.keySet()) {
            // Create BarEntry with index and percentage
            itemsAm.add(new BarEntry(index, tempItemsAmounts.get(key)));
            // Add item type (key) to item names list
            items_names.add(key);
            index++;
        }

        //Create BarDataSet and configure properties
        BarDataSet barDataSet = new BarDataSet(itemsAm, "Items");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        //Create BarData object and set it to the BarChart
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.setData(barData);
        barChart.getDescription().setText(material + "Amounts");

        //Custom XAxis in BarChart
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(items_names));

        //Animate the BarChart
        barChart.animateY(2000);
        // Refresh the chart
        barChart.invalidate();
    }
}