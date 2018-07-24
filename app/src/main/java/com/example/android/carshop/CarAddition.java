package com.example.android.carshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarAddition extends Fragment {

    @BindView(R.id.add_car_image)
    ImageView image;

    @BindView(R.id.add_car_model)
    EditText modelEditText;

    @BindView(R.id.add_car_price)
    EditText priceEditText;

    @BindView(R.id.save_btn)
    Button saveButton;

    private Unbinder unbinder;

    @NonNull
    public static CarAddition newInstance() {
        return new CarAddition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_addition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
