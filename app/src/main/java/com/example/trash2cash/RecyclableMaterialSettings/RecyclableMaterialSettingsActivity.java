package com.example.trash2cash.RecyclableMaterialSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;

import com.example.trash2cash.R;
import com.example.trash2cash.RecyclableMaterialTypes;

public class RecyclableMaterialSettingsActivity extends AppCompatActivity {
    private final RecyclableMaterialTypes recyclableMaterialTypes = new RecyclableMaterialTypes();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclable_material_settings);

        RecyclerView recyclerView = findViewById(R.id.recyclableMaterialSettingsRecyclerView);

        // Adds CardView components to the Recycler
        RecyclableMaterialSettingsRecyclerAdapter adapter = new RecyclableMaterialSettingsRecyclerAdapter(this, recyclableMaterialTypes);
        recyclerView.setAdapter(adapter);

        // Sets a vertical layout for the Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        if(recyclableMaterialTypes.isEmpty())
            findViewById(R.id.recyclableMaterialsErrorText).setVisibility(View.VISIBLE);
    }
}