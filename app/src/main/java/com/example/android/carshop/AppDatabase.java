package com.example.android.carshop;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Car.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CarDao carDao();
}
