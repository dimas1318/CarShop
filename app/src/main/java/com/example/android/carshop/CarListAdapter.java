package com.example.android.carshop;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.carshop.database.Car;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CarListAdapter extends RecyclerView.Adapter {

    private List<Car> cars = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_car_list_item, parent, false);
        return new CarListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ((CarListViewHolder) holder).image.setImageResource(cars.get(position).getImageUri());
        ((CarListViewHolder) holder).model.setText(cars.get(position).getModel());
        ((CarListViewHolder) holder).price.setText(cars.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void add(Car car) {
        this.cars.add(car);
        notifyDataSetChanged();
    }

    public void remove(Car car) {
        this.cars.remove(car);
        notifyDataSetChanged();
    }

    public void set(Collection<Car> cars) {
        this.cars = new ArrayList<>(cars);
        notifyDataSetChanged();
    }
}
