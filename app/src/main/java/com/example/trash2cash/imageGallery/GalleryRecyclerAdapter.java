package com.example.trash2cash.imageGallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<String> imageList;
    private final ImagePickerInterface imagePickerInterface;

    public GalleryRecyclerAdapter(Context context, ArrayList<String> imageList, ImagePickerInterface imagePickerInterface){
        this.context = context;
        this.imageList = imageList;
        this.imagePickerInterface = imagePickerInterface;
    }

    @NonNull
    @Override
    public GalleryRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gallery_recycler_image, parent, false);
        return new GalleryRecyclerAdapter.MyViewHolder(view, imagePickerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryRecyclerAdapter.MyViewHolder holder, int position) {
        // Loads the default and saved images for the image picker
        Picasso.with(context).load(imageList.get(position)).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;

        public MyViewHolder(@NonNull View itemView, ImagePickerInterface imagePickerInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView2);
            imageView.setOnClickListener(view -> {
                if(imagePickerInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        imagePickerInterface.endActivityWithImageFromGallery(pos);
                    }
                }
            });
        }
    }
}
