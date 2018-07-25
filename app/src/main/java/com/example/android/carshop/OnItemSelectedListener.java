package com.example.android.carshop;

import android.view.MenuItem;

import com.example.android.carshop.database.Car;

public interface OnItemSelectedListener {

    void onMenuAction(Car car, MenuItem item);
}
