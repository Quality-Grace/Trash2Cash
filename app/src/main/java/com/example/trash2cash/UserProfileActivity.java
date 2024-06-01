package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
    User user = new User("Evi", "abc", 100, "kati");
    //User user = RecyclableManager.getRecyclableManager().getUser();
    HashMap<String, Integer> materials_map;
    HashMap<String, HashMap<String,Integer>> items_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user.setPoints(340);

        //temp data for materials and their amounts
        user.putMaterials_and_Amounts("GLASS", 20);
        user.putMaterials_and_Amounts("PAPER", 50);
        user.putMaterials_and_Amounts("PLASTIC", 65);
        user.putMaterials_and_Amounts("ALUMINUM", 30);
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
        items_map = user.getItems_and_Amounts();

        SetData();
    }

    public void SetData() {

        //set data to users information
        setDataInfo();

        int sum = user.calculateTotalMaterialsAmount();

        //ArrayList with all percentages
        ArrayList<Float> percentages = new ArrayList<>();
        for (String key : materials_map.keySet()) {
            percentages.add(user.calculateMaterialAmountPercentage(key, sum));
        }

        //set data to piechart
        setDataPieChart(percentages);

        HashMap<String,HashMap<String,Float>> itemPercentages = new HashMap<>();
        for(String key: items_map.keySet()) {
            HashMap<String,Integer> values = items_map.get(key);
            HashMap<String, Float> tempPer = new HashMap<>(); //temp hashMap for items and their percentages
            for(String item: values.keySet()) {
                tempPer.put(item, user.calculateItemAmountPercentage(key,item, user.getMaterialAmount(key)));
            }
            itemPercentages.put(key,tempPer);//hashMap with the materials types with their items and the amounts of their items
        }

//        for(String key: items_map.keySet()) {
//            setDataBarChart(key, itemPercentages);
//        }

        //setDataBarChart("GLASS", itemPercentages);
        setDataBarChart("PAPER", itemPercentages);
        setDataBarChart("PLASTIC", itemPercentages);
        setDataBarChart("ALUMINUM", itemPercentages);

    }

    public void setDataInfo() {

        TextView username,level,points;

        username = findViewById(R.id.user_name);
        level = findViewById(R.id.user_level);
        points = findViewById(R.id.user_points);

        //set data to users info
        username.setText(user.getName());
        level.setText(String.valueOf(user.getLevel()));
        points.setText(String.valueOf(user.getPoints()));
    }

    public void setDataPieChart(ArrayList<Float> percentages) {

        //Take PieChart
        PieChart pieChart;
        pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> materials = new ArrayList<>();
        int pos =0;
        for (String key : materials_map.keySet()) {
            materials.add(new PieEntry(percentages.get(pos),key));
            pos++;
        }

        //my colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6495ED")); // glass color
        colors.add(Color.parseColor("#CCCC00")); // alum
        colors.add(Color.parseColor("#3CB371")); // paper
        colors.add(Color.parseColor("#DC143C")); // plastic

        PieDataSet pieDataSet = new PieDataSet(materials, "Materials");
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Materials and Amounts");


        //gia na baleis se kainourgio entry color
        //pieChart.setEntryLabelColor(Color.WHITE);


        // To animate the pie chart
        pieChart.animateY(2000);
        pieChart.invalidate(); // Refresh the chart
    }

    public void setDataBarChart(String material, HashMap<String,HashMap<String, Float>> percentages) {

        //Take barChart
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


        ArrayList<BarEntry> itemsAm = new ArrayList<>();
        ArrayList<String> items_names = new ArrayList<>();
        //take the specific value for each material and create a new BarEntry
        HashMap<String,Float> temp = percentages.get(material);
        int intex=0;
        for(String key: temp.keySet()) {
            itemsAm.add(new BarEntry(intex, temp.get(key)));
            items_names.add(key);
            intex++;
        }


        BarDataSet barDataSet = new BarDataSet(itemsAm, "Items");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

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


        barChart.animateY(2000);
        barChart.invalidate(); // Refresh the chart
    }
    

}