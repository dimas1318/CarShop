package com.example.android.carshop;

import android.os.Parcel;
import android.os.Parcelable;

public class CarParcelable implements Parcelable {

    private long id;
    private String price;
    private String model;
    private String imageUri;

    public CarParcelable () {

    }

    protected CarParcelable(Parcel in) {
        id = in.readLong();
        price = in.readString();
        model = in.readString();
        imageUri = in.readString();
    }

    public static final Creator<CarParcelable> CREATOR = new Creator<CarParcelable>() {
        @Override
        public CarParcelable createFromParcel(Parcel in) {
            return new CarParcelable(in);
        }

        @Override
        public CarParcelable[] newArray(int size) {
            return new CarParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(price);
        dest.writeString(model);
        dest.writeString(imageUri);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
