package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trash2cash.Entities.User;
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

import java.util.ArrayList;
import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {

    //temp user
    User user = new User("Evi", 0, 100, 0);
    //Find the active user
    //User user = RecyclableManager.getRecyclableManager().getUser();
    //HashMap for user's materials and amounts
    HashMap<String, Integer> materials_map;
    //HashMap for user's specific items and amounts
    HashMap<String, HashMap<String,Integer>> items_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Actual Code
        //store the data in the user's attributes
//        user.storeApprovedRequests();
//        user.storeMaterialsAndItemsAmounts();

        //temp data for materials and their amounts
        user.putMaterials_and_Amounts("PAPER", 20);
        user.putMaterials_and_Amounts("GLASS", 50);
        user.putMaterials_and_Amounts("ALUMINUM", 65);
        user.putMaterials_and_Amounts("PLASTIC", 30);
        //Actual Code
        materials_map = user.getMaterials_and_Amounts();

        //temp data for items and their amounts
        for(String key: materials_map.keySet()) {
            HashMap<String, Integer> temp = new HashMap<>();
            int value = materials_map.get(key)/5;
            temp.put("BAG", value);
            temp.put("BOTTLE", value);
            temp.put("CAN", value);
            temp.put("BOX", value);
            temp.put("CARD_BOARD", value);

            user.putItems_and_Amounts(key,temp);
        }
        //Actual code
        items_map = user.getItems_and_Amounts();

        SetData();
    }

    public void SetData() {

        //set data to users information
        setDataInfo();

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

        username = findViewById(R.id.user_name);
        level = findViewById(R.id.user_level);
        points = findViewById(R.id.user_points);
        image = findViewById(R.id.profile_photo);

        //set data to users info
        username.setText(user.getName());
        level.setText(String.valueOf(user.getLevel()));
        points.setText(String.valueOf(user.getRewardPoints()));
        //image.setImageResource(user.getImage());
    }

    public void setDataPieChart(ArrayList<Float> percentages) {

        //Take the PieChart
        PieChart pieChart;
        pieChart = findViewById(R.id.pieChart);

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
        colors.add(Color.parseColor("#6495ED")); // glass color
        colors.add(Color.parseColor("#CCCC00")); // aluminum color
        colors.add(Color.parseColor("#3CB371")); // paper color
        colors.add(Color.parseColor("#DC143C")); // plastic color

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
        if(material.equals("GLASS")) {
            barChart = findViewById(R.id.glass_barChart);
        } else if(material.equals("PLASTIC")) {
            barChart = findViewById(R.id.plastic_barChart);
        } else if(material.equals("PAPER")) {
            barChart = findViewById(R.id.paper_barChart);
        } else  {
           barChart = findViewById(R.id.aluminum_barChart);
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