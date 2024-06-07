package com.example.trash2cash.MaterialLogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.RecyclableItem;
import com.example.trash2cash.Entities.RecyclableItemType;
import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.RecyclableMaterial;
import com.example.trash2cash.Entities.RecyclableMaterialTypes;
import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.Entities.RequestStatus;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.MainActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.RewardScreen.RewardsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaterialLoggerActivity extends AppCompatActivity {

    private static ArrayList<Request> itemList;
    private MaterialLoggerAdapter myAdapter;

    private final OkHttpHandler okHttpHandler = new OkHttpHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_logger);

        spinnerInitialization();
        try {
            itemList = okHttpHandler.takeRequestsByUserId(RecyclableManager.getRecyclableManager().getUser().getId());
        } catch (Exception e) {
            itemList = new ArrayList<>();
        }

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        myAdapter = new MaterialLoggerAdapter(itemList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(myAdapter);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        ImageView addItemBtn = findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(addItemCardListener());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.RecycleItem);
        setupNavigationListener(bottomNavigationView);
    }

    public void setupNavigationListener(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RewardsItem) {
                startActivity(new Intent(MaterialLoggerActivity.this, RewardsActivity.class));
            } else if(item.getItemId() == R.id.ProfileItem) {
                startActivity(new Intent(MaterialLoggerActivity.this, MainActivity.class));
            }

            return true;
        });
    }

    /**
     * Listener for the "Add Item" button.
     * @return the listener.
     */
    private View.OnClickListener addItemCardListener() {
        return view -> {
            String item = ((Spinner) findViewById(R.id.itemSelector)).getSelectedItem().toString();
            String material = ((Spinner) findViewById(R.id.materialSelector)).getSelectedItem().toString();

            RecyclableManager recyclableManager = RecyclableManager.getRecyclableManager();
            User user = recyclableManager.getUser();
            RecyclableMaterial recyclableMaterial = recyclableManager.getRecyclableMaterial(material);
            RecyclableItem recyclableItem = recyclableManager.createRecyclableItem(recyclableMaterial, RecyclableItemType.valueOf(item));
            int item_id;
            try {
                item_id = okHttpHandler.addItem(recyclableMaterial.getType(), recyclableItem.getType().getItemType());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Request request = recyclableManager.addRequest(new RecyclableItem(recyclableMaterial, recyclableItem.getType(), recyclableItem.getImage(), item_id), user.getId(), RequestStatus.PENDING);
            itemList.add(request);
            myAdapter.notifyItemInserted(itemList.size() - 1);
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

        materialSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CardView cardView = findViewById(R.id.addItemCardView);
                String selectedMaterial = materialSelector.getSelectedItem().toString();
                cardView.setCardBackgroundColor(new RecyclableMaterialTypes().getRecyclableMaterial(selectedMaterial).getColour());
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
        // Create an ArrayAdapter using the list of items and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new RecyclableMaterialTypes().getTypes());

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