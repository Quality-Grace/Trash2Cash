package com.example.trash2cash.imageGallery;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.trash2cash.R;

import java.util.ArrayList;
import java.util.Objects;


public class ImagePickerActivity extends AppCompatActivity implements ImagePickerInterface {
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private static final ArrayList<Integer> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        // Fills the imageList array with image ids
        initializeImageList();

        RecyclerView recyclerView = findViewById(R.id.imageGallery);

        // Adds images to the recyclerView
        GalleryRecyclerAdapter adapter = new GalleryRecyclerAdapter(this, imageList, this);
        recyclerView.setAdapter(adapter);

        // Sets a grid layout for the Recycler
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if(uri != null){
                endActivity(uri);
            }
        });

        Button button = findViewById(R.id.openImageGallery);

        // Opens image gallery
        button.setOnClickListener(view -> pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()));
    }

    public void endActivity(Uri resultData){
        Intent resultIntent = new Intent();
        resultIntent.setData(resultData);
        // Used by the RewardSettingsActivity to check if the selected image came from the phone's storage
        resultIntent.putExtra("type", "fromGallery");
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    // Same functionality with the endActivity method except it's for the images from the recycler view
    @Override
    public void endActivityWithImageFromGallery(int position) {
        Intent resultIntent = new Intent();
        resultIntent.setData(Uri.parse("android.resource://"+ Objects.requireNonNull(R.class.getPackage()).getName()+"/"+imageList.get(position)));
        // Used by the RewardSettingsActivity to check if the selected image is from app images
        resultIntent.putExtra("type", "fromDrawables");
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    // Method to initialize the image list array
    // TODO Fill the array with the content of an xml file
    public void initializeImageList(){
        for(int i=0; i<7; i++){
            imageList.add(R.drawable.glass_type);
            imageList.add(R.drawable.other_type);
            imageList.add(R.drawable.metal_type);
        }
    }
}