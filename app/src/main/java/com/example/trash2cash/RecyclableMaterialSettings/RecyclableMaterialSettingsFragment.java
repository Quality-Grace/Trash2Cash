package com.example.trash2cash.RecyclableMaterialSettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trash2cash.Entities.RecyclableMaterialTypes;
import com.example.trash2cash.R;

public class RecyclableMaterialSettingsFragment extends Fragment {
    private final RecyclableMaterialTypes recyclableMaterialTypes = new RecyclableMaterialTypes();

    public RecyclableMaterialSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclable_material_settings, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclableMaterialSettingsRecyclerView);

        // Adds CardView components to the Recycler
        RecyclableMaterialSettingsRecyclerAdapter adapter = new RecyclableMaterialSettingsRecyclerAdapter(getContext(), recyclableMaterialTypes);
        recyclerView.setAdapter(adapter);

        // Sets a vertical layout for the Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        if(recyclableMaterialTypes.isEmpty())
            rootView.findViewById(R.id.recyclableMaterialsErrorText).setVisibility(View.VISIBLE);

        return rootView;
    }
}