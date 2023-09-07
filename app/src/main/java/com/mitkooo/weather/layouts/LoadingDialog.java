package com.mitkooo.weather.layouts;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import com.mitkooo.weather.R;

//used for creating LoadingDialogService
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.setCancelable(false);
        this.addContentView(new ProgressBar(context), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
}
