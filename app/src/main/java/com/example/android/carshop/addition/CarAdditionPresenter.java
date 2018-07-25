package com.example.android.carshop.addition;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ImageView;

import com.example.android.carshop.R;
import com.example.android.carshop.database.AppDatabase;
import com.example.android.carshop.model.Car;
import com.example.android.carshop.model.CarMapper;
import com.squareup.picasso.Picasso;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CarAdditionPresenter {

    private CarAdditionView view;
    private AppDatabase database;
    private String selectedUri;

    public CarAdditionPresenter() {

    }

    public void setView(CarAdditionView view) {
        this.view = view;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public AlertDialog createAlertDialogWithRadioButtonGroup() {
        CharSequence[] values = {"Машина 1", "Машина 2", "Машина 3"};

        DialogInterface.OnClickListener listener = (dialog, item) -> {
            switch (item) {
                case 0:
                    selectedUri = "http://upload.wikimedia.org/wikipedia/en/2/2d/Front_left_of_car.jpg";
                    break;
                case 1:
                    selectedUri = "http://www.cstatic-images.com/stock/1170x1170/51/img2009963547-1523467186951.jpg";
                    break;
                case 2:
                    selectedUri = "http://i.ytimg.com/vi/UKKIUoNsG08/maxresdefault.jpg";
                    break;
            }
            view.dismissAlertDialog();
        };

        return view.buildAlertDialog("Выберите фото", values,
                view.isChangeFragment() ? findUriPosition(selectedUri) : -1, listener);
    }

    private int findUriPosition(String uri) {
        switch (uri) {
            case "http://upload.wikimedia.org/wikipedia/en/2/2d/Front_left_of_car.jpg":
                return 0;
            case "http://www.cstatic-images.com/stock/1170x1170/51/img2009963547-1523467186951.jpg":
                return 1;
            case "http://i.ytimg.com/vi/UKKIUoNsG08/maxresdefault.jpg":
                return 2;
            default:
                return -1;
        }
    }

    public void renderAlertForImageChoice(ImageView image) {
        view.showAlertWithImageLoading(image);
    }

    public void saveCar(String model, String price) {
        view.showProgressDialog(view.isChangeFragment() ? "Сохранение изменений..." : "Сохранение модели...");

        Completable.fromAction(() -> {
                if (view.isChangeFragment()) {
                    Car car = CarMapper.mapFromParcelable(view.getCarParcelable());
                    car.setModel(model);
                    car.setPrice(price);
                    car.setImageUri(selectedUri);
                    database.carDao().updateCar(car);
                } else {
                    database.carDao().addCar(new Car(selectedUri, model, price));
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(() -> view.hideProgressDialog())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onComplete() {
                    view.popBackStack();
                }

                @Override
                public void onError(Throwable e) {
                    view.showToast("Ошибка при сохранении");
                }
            });
    }

    public void loadImage(ImageView image) {
        loadImageByUri(image, selectedUri);
    }

    public void loadImageByUri(ImageView image, String imageUri) {
        Picasso.get()
                .load(imageUri)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.progress_animation)
                .resize(100, 100)
                .centerCrop()
                .into(image);
        if (!imageUri.equals(selectedUri)) {
            selectedUri = imageUri;
        }
    }
}
