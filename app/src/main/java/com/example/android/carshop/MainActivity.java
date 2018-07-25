package com.example.android.carshop;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.carshop.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(this,
                AppDatabase.class, "car_database").build();

        navigateToCarListFragment();
    }

    private void navigateToCarListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CarListFragment.newInstance())
                .addToBackStack(CarListFragment.class.getSimpleName())
                .commit();
    }

    public void navigateToCarAdditionFragment() {
        navigateToCarAdditionFragment(false, null);
    }

    public void navigateToCarAdditionFragment(boolean isChangeFragment, CarParcelable carParcelable) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CarAdditionFragment.newInstance(isChangeFragment, carParcelable))
                .addToBackStack(CarAdditionFragment.class.getSimpleName())
                .commit();
    }

    public void popBackStack() {
        getSupportFragmentManager()
                .popBackStack();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
