package com.example.android.carshop.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.carshop.R;
import com.example.android.carshop.databinding.CarBinding;
import com.example.android.carshop.model.Car;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CarListAdapter extends RecyclerView.Adapter {

//    private List<Car> cars = new ArrayList<>();
    private List<CarModelView> cars = new ArrayList<>();
    private OnItemSelectedListener listener;
    private LayoutInflater layoutInflater;

    public CarListAdapter(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_car_list_item, parent, false);
//        return new CarListViewHolder(v);

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CarBinding carBinding = CarBinding.inflate(layoutInflater, parent, false);
        return new CarListViewHolder(carBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Picasso.get()
//                .load(cars.get(position).getImageUri())
//                .error(R.drawable.error_image)
//                .placeholder(R.drawable.progress_animation)
//                .resize(100, 100)
//                .centerCrop()
//                .into(((CarListViewHolder) holder).image);
//        ((CarListViewHolder) holder).model.setText(cars.get(position).getModel());
//        ((CarListViewHolder) holder).price.setText(cars.get(position).getPrice());

        CarModelView carModel = cars.get(position);
        ((CarListViewHolder) holder).bind(carModel);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void add(CarModelView car) {
        this.cars.add(car);
        notifyDataSetChanged();
    }

    public void remove(CarModelView car) {
        this.cars.remove(car);
        notifyDataSetChanged();
    }

    public void set(Collection<CarModelView> cars) {
        this.cars = new ArrayList<>(cars);
        notifyDataSetChanged();
    }

    class CarListViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {

//        @BindView(R.id.list_car_image)
//        ImageView image;
//
//        @BindView(R.id.list_car_model)
//        TextView model;
//
//        @BindView(R.id.list_car_price)
//        TextView price;
        private CarBinding carBinding;

        public CarListViewHolder(CarBinding carBinding/*View itemView*/) {
            super(carBinding.getRoot()/*itemView*/);
            this.carBinding = carBinding;
//            ButterKnife.bind(this, itemView);
//            itemView.setOnCreateContextMenuListener(this);
            carBinding.getRoot().setOnCreateContextMenuListener(this);
        }

        public void bind(CarModelView carModel) {
            this.carBinding.setCarModelView(carModel);
        }

        public CarBinding getCarBinding() {
            return carBinding;
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
                CarModelView carModel = cars.get(getAdapterPosition());
                Car car = new Car(carModel.getImageUri(), carModel.getModel(), carModel.getPrice());
                car.setId(carModel.getId());
                listener.onMenuAction(car, item);
            }
            return false;
        }
    }
}
