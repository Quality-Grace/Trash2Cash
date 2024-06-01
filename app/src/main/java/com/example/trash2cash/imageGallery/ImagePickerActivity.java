package com.example.trash2cash.imageGallery;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.R;

import java.io.File;
import java.util.ArrayList;


public class ImagePickerActivity extends AppCompatActivity implements ImagePickerInterface {
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        // Fills the imageList array with image ids
        initializeImageList();

        if(imageList.size()==0) {
            findViewById(R.id.imageGalleryIsEmptyText).setVisibility(View.VISIBLE);
        }

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

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = this.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void endActivity(Uri resultData){
        Intent resultIntent = new Intent();

        OkHttpHandler okHttpHandler = new OkHttpHandler();
        File file = new File(getRealPathFromURI(resultData));
        String urlString = okHttpHandler.uploadImage(OkHttpHandler.getPATH() + "uploadImage.php", file);

        resultIntent.putExtra("url", urlString);
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }

    // Same functionality with the endActivity method except it's for the images from the recycler view
    @Override
    public void endActivityWithImageFromGallery(int position) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("url", imageList.get(position));
        setResult(Activity.RESULT_OK, resultIntent);

        finish();
    }


    public void initializeImageList(){
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        try {
            imageList = okHttpHandler.getRewardImages(OkHttpHandler.getPATH()+"getRewardImages.php");
        } catch (Exception e) {
            findViewById(R.id.openImageGallery).setVisibility(View.GONE);
            e.printStackTrace();
        }
    }
}