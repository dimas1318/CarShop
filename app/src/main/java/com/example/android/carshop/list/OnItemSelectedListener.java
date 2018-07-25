package com.example.android.carshop.list;

import android.view.MenuItem;

import com.example.android.carshop.model.Car;

public interface OnItemSelectedListener {

    void onMenuAction(Car car, MenuItem item);
}
