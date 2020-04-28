package com.akram.vutest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.akram.vutest.Adapter.ImageAdapter;
import com.akram.vutest.apiCall.ApiClient;
import com.akram.vutest.apiCall.ApiInterface;
import com.akram.vutest.model.Image;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener{

    public static final String IMAGE_URl = "image_url";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";

    private ApiInterface apiInterface;
    private RecyclerView recyclerView;

    private ArrayList<Image.Datum> imageArrayList;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected(MainActivity.this)) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("No Internet!");
            dialog.setMessage("Please check your internet connection before proceed.");
            dialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });

            AlertDialog alert = dialog.create();
            alert.show();

        }

        recyclerView = findViewById(R.id.recyclerId);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager gridLayout = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayout);

        imageArrayList = new ArrayList<>();

        loadImage();

    }

    private void loadImage() {
        Call<Image> imageCall = apiInterface.getImage("1");

        imageCall.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {

                ArrayList<Image.Datum> lists = response.body().data;

                for (Image.Datum imageList : lists){

                    String avatar = imageList.getAvatar();

                    String email = imageList.getEmail();
                    String firstName = imageList.getFirstName();
                    String lastName = imageList.getLastName();

                    imageArrayList.add(new Image.Datum(email,firstName,lastName ,avatar));
                }

                imageAdapter = new ImageAdapter(MainActivity.this,imageArrayList);
                recyclerView.setAdapter(imageAdapter);
                imageAdapter.setOnItemClickListener(MainActivity.this);

            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong. Please try Again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) throw new AssertionError();
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnected()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;
    }

    @Override
    public void onImageClick(int position) {
        Intent ImageDetails = new Intent(MainActivity.this,ImageDetails.class);

        Image.Datum clickedItem = imageArrayList.get(position);

        ImageDetails.putExtra(EMAIL, clickedItem.getEmail());
        ImageDetails.putExtra(FIRST_NAME, clickedItem.getFirstName());
        ImageDetails.putExtra(LAST_NAME, clickedItem.getLastName());
        ImageDetails.putExtra(IMAGE_URl, clickedItem.getAvatar());

        startActivity(ImageDetails);

    }
}
