package com.example.android.carshop.addition;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.android.carshop.MainActivity;
import com.example.android.carshop.R;
import com.example.android.carshop.model.Car;
import com.example.android.carshop.model.CarMapper;
import com.example.android.carshop.model.CarParcelable;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

import static com.example.android.carshop.Constants.CAR_PARCELABLE;
import static com.example.android.carshop.Constants.IS_CHANGE_FRAGMENT;

public class CarAdditionFragment extends Fragment implements CarAdditionView {

    @BindView(R.id.add_car_image)
    ImageView image;

    @BindView(R.id.add_car_model)
    EditText modelEditText;

    @BindView(R.id.add_car_price)
    EditText priceEditText;

    @BindView(R.id.save_btn)
    Button saveButton;

    private Unbinder unbinder;
    private CarAdditionPresenter presenter;

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    @NonNull
    public static CarAdditionFragment newInstance(boolean isChangeFragment, CarParcelable carParcelable) {
        CarAdditionFragment fragment = new CarAdditionFragment();

        Bundle args = new Bundle();
        args.putBoolean(IS_CHANGE_FRAGMENT, isChangeFragment);
        args.putParcelable(CAR_PARCELABLE, carParcelable);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_addition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        presenter = new CarAdditionPresenter();
        presenter.setView(this);
        presenter.setDatabase(((MainActivity) getActivity()).getDatabase());

        if (isChangeFragment()) {
            Car car = CarMapper.mapFromParcelable(getCarParcelable());
            modelEditText.setText(car.getModel());
            priceEditText.setText(car.getPrice());
            presenter.loadImageByUri(image, car.getImageUri());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.save_btn)
    public void onSaveClick() {
        presenter.saveCar(modelEditText.getText().toString(), priceEditText.getText().toString());
    }

    @OnLongClick(R.id.add_car_image)
    public boolean onImageLongClick() {
        presenter.renderAlertForImageChoice(image);
        return false;
    }

    @OnTextChanged({R.id.add_car_model, R.id.add_car_price})
    public void onTextChanged() {
        saveButton.setEnabled(!modelEditText.getText().toString().isEmpty()
                && !priceEditText.getText().toString().isEmpty());
    }

    @Override
    public boolean isChangeFragment() {
        return getArguments().getBoolean(IS_CHANGE_FRAGMENT, false);
    }

    @Override
    public CarParcelable getCarParcelable() {
        return getArguments().getParcelable(CAR_PARCELABLE);
    }

    @Override
    public void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void popBackStack() {
        ((MainActivity) getActivity()).popBackStack();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissAlertDialog() {
        alertDialog.dismiss();
    }

    @Override
    public AlertDialog buildAlertDialog(String title, CharSequence[] values, int position, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setSingleChoiceItems(values, position, listener);
        alertDialog = builder.create();
        return alertDialog;
    }

    @Override
    public void showAlertWithImageLoading(ImageView image) {
        alertDialog = presenter.createAlertDialogWithRadioButtonGroup();
        alertDialog.setOnDismissListener(dialog -> presenter.loadImage(image));
        alertDialog.show();
    }
}
