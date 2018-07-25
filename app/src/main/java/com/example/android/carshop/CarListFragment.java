package com.example.android.carshop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.carshop.database.AppDatabase;
import com.example.android.carshop.database.Car;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CarListFragment extends Fragment implements OnItemSelectedListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.no_cars)
    TextView noCarsTextView;

    private Unbinder unbinder;
    private CarListAdapter adapter;
    private AppDatabase database;

    @NonNull
    public static CarListFragment newInstance() {
        return new CarListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        database = ((MainActivity) getActivity()).getDatabase();

        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CarListAdapter(this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        loadModels();
    }

    @SuppressLint("CheckResult")
    private void loadModels() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Извлечение моделей...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        Observable.fromCallable(database.carDao()::getAllCars)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                })
                .subscribe(list -> {
                    if (list == null || list.isEmpty()) {
                        noCarsTextView.setVisibility(View.VISIBLE);
                        adapter.set(new ArrayList<>());
                    } else {
                        noCarsTextView.setVisibility(View.GONE);
                        adapter.set(list);
                    }
                }, Throwable::printStackTrace);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).navigateToCarAdditionFragment();
        }
    }

    @Override
    public void onMenuAction(Car car, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                // Add edit screen
                break;
            case R.id.menu_delete:
                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Сохранение модели...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                Completable.fromAction(() ->
                        database.carDao().removeCar(car))
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
                                adapter.remove(car);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getContext(), "Ошибка при удалении", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }

    }
}
