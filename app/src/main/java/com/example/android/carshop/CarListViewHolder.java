package com.example.android.carshop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

class CarListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_car_image)
    ImageView image;

    @BindView(R.id.list_car_model)
    TextView model;

    @BindView(R.id.list_car_price)
    TextView price;

    public CarListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
