package com.akram.vutest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.akram.vutest.MainActivity.EMAIL;
import static com.akram.vutest.MainActivity.FIRST_NAME;
import static com.akram.vutest.MainActivity.IMAGE_URl;
import static com.akram.vutest.MainActivity.LAST_NAME;

public class ImageDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        imageView = findViewById(R.id.fullImageId);
        textView = findViewById(R.id.detailsTextId);

        Intent getImage = getIntent();

        Picasso.get().load(getImage.getStringExtra(IMAGE_URl)).into(imageView);
        textView.setText(getImage.getStringExtra(EMAIL)+"\n"
                +getImage.getStringExtra(FIRST_NAME)+" "+getImage.getStringExtra(LAST_NAME));

    }
}
