package com.example.android.carshop;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.carshop.database.Car;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class CarListAdapter extends RecyclerView.Adapter {

    private List<Car> cars = new ArrayList<>();
    private OnItemSelectedListener listener;

    public CarListAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_car_list_item, parent, false);
        return new CarListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Picasso.get()
                .load(cars.get(position).getImageUri())
                .error(R.drawable.error_image)
                .placeholder(R.drawable.progress_animation)
                .resize(100, 100)
                .centerCrop()
                .into(((CarListViewHolder) holder).image);
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

    class CarListViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {

        @BindView(R.id.list_car_image)
        ImageView image;

        @BindView(R.id.list_car_model)
        TextView model;

        @BindView(R.id.list_car_price)
        TextView price;

        public CarListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.menu_item, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (listener != null) {
                Car car = cars.get(getAdapterPosition());
                listener.onMenuAction(car, item);
            }
            return false;
        }
    }
}
