package com.example.trash2cash.MaterialLogger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.RecyclableItem;
import com.example.trash2cash.Entities.RecyclableItemType;
import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.RecyclableMaterial;
import com.example.trash2cash.Entities.RecyclableMaterialTypes;
import com.example.trash2cash.Entities.Request;
import com.example.trash2cash.Entities.RequestStatus;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaterialLoggerFragment extends Fragment {
    private static ArrayList<Request> itemList = new ArrayList<>();
    private MaterialLoggerAdapter myAdapter;
    private View rootView;

    private final OkHttpHandler okHttpHandler = new OkHttpHandler();

    public MaterialLoggerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_material_logger, container, false);

        spinnerInitialization();

        // Set up the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        myAdapter = new MaterialLoggerAdapter(itemList, getContext());

        new Thread(()->{
            try {
                itemList.clear();
                itemList.addAll(okHttpHandler.takeRequestsByUserId(RecyclableManager.getRecyclableManager().getUser().getId()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                requireActivity().runOnUiThread(() -> myAdapter.notifyDataSetChanged());
            } catch (Exception ignored) {}
        }).start();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(myAdapter);

        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);

        ImageView addItemBtn = rootView.findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(addItemCardListener());

        return rootView;
    }

    /**
     * Listener for the "Add Item" button.
     * @return the listener.
     */
    private View.OnClickListener addItemCardListener() {
        return view -> {
            String item = ((Spinner) rootView.findViewById(R.id.itemSelector)).getSelectedItem().toString();
            String material = ((Spinner) rootView.findViewById(R.id.materialSelector)).getSelectedItem().toString();

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
        Spinner itemSelector = rootView.findViewById(R.id.itemSelector);
        Spinner materialSelector = rootView.findViewById(R.id.materialSelector);

        itemSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView itemImage = rootView.findViewById(R.id.itemImage);
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
                    case "CARD_BOARD":
                        itemImage.setImageResource(R.drawable.card_board);
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
                CardView cardView = rootView.findViewById(R.id.addItemCardView);
                String selectedMaterial = materialSelector.getSelectedItem().toString();
                System.out.println("MATERIAL: " + selectedMaterial);
                switch (selectedMaterial) {
                    case "Aluminium" :
                        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.aluminium));
                        break;
                    case "Paper" :
                        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.paper));
                        break;
                    case "Glass" :
                        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.glass));
                        break;
                    case "Plastic" :
                        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.plastic));
                        break;
                    case "Other" :
                        cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.reward_card_grey));
                        break;
                    default: cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.reward_card));
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
        // Create an ArrayAdapter using the list of items and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new RecyclableMaterialTypes().getTypes());

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerItems);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}