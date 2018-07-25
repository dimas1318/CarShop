package com.example.android.carshop.addition;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;

import com.example.android.carshop.model.CarParcelable;

public interface CarAdditionView {

    boolean isChangeFragment();

    CarParcelable getCarParcelable();

    void showProgressDialog(String message);

    void hideProgressDialog();

    void popBackStack();

    void showToast(String message);

    void dismissAlertDialog();

    AlertDialog buildAlertDialog(String title, CharSequence[] values, int position, DialogInterface.OnClickListener listener);

    void showAlertWithImageLoading(ImageView image);
}
