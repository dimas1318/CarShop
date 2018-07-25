package com.example.android.carshop.model;

public class CarMapper {

    public static CarParcelable mapToParcelable(Car car) {
        CarParcelable parcelable = new CarParcelable();
        parcelable.setId(car.getId());
        parcelable.setModel(car.getModel());
        parcelable.setPrice(car.getPrice());
        parcelable.setImageUri(car.getImageUri());
        return parcelable;
    }

    public static Car mapFromParcelable(CarParcelable parcelable) {
        Car car = new Car();
        car.setId(parcelable.getId());
        car.setModel(parcelable.getModel());
        car.setPrice(parcelable.getPrice());
        car.setImageUri(parcelable.getImageUri());
        return car;
    }
}
