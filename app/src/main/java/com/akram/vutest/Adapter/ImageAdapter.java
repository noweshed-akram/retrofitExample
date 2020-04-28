package com.akram.vutest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akram.vutest.R;
import com.akram.vutest.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Image.Datum> imageArrayList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onImageClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ImageAdapter(Context context, ArrayList<Image.Datum> images) {
        mContext = context;
        imageArrayList = images;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_layout, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        Image.Datum currentImage = imageArrayList.get(position);

        String imageUrl = currentImage.getAvatar();

        Picasso.get().load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        
        private ImageView imageView;

        private ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onImageClick(position);
                        }
                    }
                }
            });

        }
    }
}
