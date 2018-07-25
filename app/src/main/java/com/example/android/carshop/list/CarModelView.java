package com.example.android.carshop.list;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.android.carshop.R;
import com.example.android.carshop.model.Car;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class CarModelView {

    private long id;
    private String model;
    private String price;
    private String imageUri;

    public CarModelView(Car car) {
        this.id = car.getId();
        this.model = car.getModel();
        this.price = car.getPrice();
        this.imageUri = car.getImageUri();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModelView carModel = (CarModelView) o;
        return id == carModel.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @BindingAdapter("bind:imageUri")
    public static void loadImage(ImageView view, String imageUri) {
        Picasso.get()
                .load(imageUri)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.progress_animation)
                .resize(100, 100)
                .centerCrop()
                .into(view);
    }

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUri() {
        return imageUri;
    }
}
