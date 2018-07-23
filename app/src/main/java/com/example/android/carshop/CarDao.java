package com.example.android.carshop;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Collection;

@Dao
public interface CarDao {

    @Insert
    void addCar(Car car);

    @Insert
    void addCars(Collection<Car> cars);

    @Query("SELECT * FROM cars")
    Car getAllCars();

    @Query("SELECT * FROM cars WHERE id = :id")
    Car findCar(long id);

    @Update
    void updateCar(Car car);

    @Delete
    void removeCar(Car car);
}
