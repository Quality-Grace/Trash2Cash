package com.example.trash2cash.MaterialLogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.trash2cash.Entities.MaterialType;
import com.example.trash2cash.Entities.RecyclableItemType;
import com.example.trash2cash.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialLoggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_logger);

        spinnerInitialization();

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        ImageView addItemBtn = findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(addItemCardListener());
    }

    private View.OnClickListener addItemCardListener() {
        return view -> {
            String item = ((Spinner) findViewById(R.id.itemSelector)).getSelectedItem().toString();
            String material = ((Spinner) findViewById(R.id.materialSelector)).getSelectedItem().toString();



        };
    }

    private void spinnerInitialization() {
        // Set up the Spinner
        Spinner itemSelector = findViewById(R.id.itemSelector);
        Spinner materialSelector = findViewById(R.id.materialSelector);

        itemSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView itemImage = findViewById(R.id.itemImage);
                String selectedItem = itemSelector.getSelectedItem().toString();
                switch (selectedItem){
                    case "BAG":
                        itemImage.setImageResource(R.drawable.bag);
                        break;
                    case "BOTTLE":
                        itemImage.setImageResource(R.drawable.bottle);
                    break;
                    case "CAN":
                        itemImage.setImageResource(R.drawable.can);
                    break;
                    case "BOX":
                        itemImage.setImageResource(R.drawable.box);
                    break;
                    default:
                        itemImage.setImageResource(R.drawable.ic_launcher_foreground);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Apply the adapter to the spinner
        itemSelector.setAdapter(getStringItemArrayAdapter());
        materialSelector.setAdapter(getStringMaterialArrayAdapter());
    }

    @NonNull
    private SpinnerAdapter getStringMaterialArrayAdapter() {
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add(MaterialType.PLASTIC.toString());
        spinnerItems.add(MaterialType.ALUMINUM.toString());
        spinnerItems.add(MaterialType.GLASS.toString());
        spinnerItems.add(MaterialType.PAPER.toString());

        // Create an ArrayAdapter using the list of items and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @NonNull
    private ArrayAdapter<String> getStringItemArrayAdapter() {
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add(RecyclableItemType.BAG.toString());
        spinnerItems.add(RecyclableItemType.BOTTLE.toString());
        spinnerItems.add(RecyclableItemType.CAN.toString());
        spinnerItems.add(RecyclableItemType.BOX.toString());
        spinnerItems.add(RecyclableItemType.CARD_BOARD.toString());

        // Create an ArrayAdapter using the list of items and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}