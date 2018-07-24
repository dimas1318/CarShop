package com.example.android.carshop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import com.example.android.carshop.database.AppDatabase;
import com.example.android.carshop.database.Car;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class CarAdditionFragment extends Fragment {

    @BindView(R.id.add_car_image)
    ImageView image;

    @BindView(R.id.add_car_model)
    EditText modelEditText;

    @BindView(R.id.add_car_price)
    EditText priceEditText;

    @BindView(R.id.save_btn)
    Button saveButton;

    private Unbinder unbinder;
    private AppDatabase database;

    @NonNull
    public static CarAdditionFragment newInstance() {
        return new CarAdditionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_addition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        database = ((MainActivity) getActivity()).getDatabase();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.save_btn)
    public void onSaveClick() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Сохранение модели...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        Completable.fromAction(() ->
                database.carDao().addCar(new Car(image.getTransitionName(),
                        modelEditText.getText().toString(),
                        priceEditText.getText().toString())))
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                })
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        ((MainActivity) getActivity()).popBackStack();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
