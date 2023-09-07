package com.mitkooo.weather.services;

import android.content.Context;

import com.mitkooo.weather.layouts.LoadingDialog;

//LoadingDialogService is used when there is a request in progress. Its purpose is the block the user from interacting with the application
// until the request is completed
public class LoadingDialogService {

    private static LoadingDialogService instance;
    private LoadingDialog loadingdialog;

    //make the class singleton
    public static LoadingDialogService getInstance() {
        if (instance == null) {
            instance = new LoadingDialogService();
        }
        return instance;
    }

    public void createLoadingDialog(Context context) {
        loadingdialog = new LoadingDialog(context);
    }

    public void show() {
        if (!loadingdialog.isShowing()) {
            loadingdialog.show();
        }
    }

    public void dismiss() {
        loadingdialog.dismiss();
    }

}
