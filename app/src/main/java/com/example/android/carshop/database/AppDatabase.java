package com.example.android.carshop.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.android.carshop.model.Car;

@Database(entities = {Car.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CarDao carDao();
}
