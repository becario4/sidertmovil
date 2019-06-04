package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;

public class Popups {

    private static int DIALOG_MESSAGE  = R.layout.popup_message;
    private static int DIALOG_SUCCESS  = R.layout.popup_success;
    private static int DIALOG_LODING   = R.layout.popup_loading;
    private static int DIALOG_PROGRESS = R.layout.popup_progress;
    public interface DialogMessage {
        void OnClickListener(AlertDialog dialog);
    }

    public static AlertDialog showDialogMessage(Context ctx, int message,
                                                int btnOk, final DialogMessage btnOkClick,
                                                int btnFail, final DialogMessage btnFailClick) {

        // LOADING VIEWS
        View view          = LayoutInflater.from(ctx).inflate(DIALOG_MESSAGE, null);
        TextView TVmessage = view.findViewById(R.id.TVmessage);
        Button BTNok       = view.findViewById(R.id.btnAccept);
        Button BTNfail     = view.findViewById(R.id.btnCancel);

        // FILLING VIEWS
        TVmessage.setText(ctx.getString(message));

        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();

        if(btnOk != 0) {
            BTNok.setText(ctx.getString(btnOk));
            BTNok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOkClick.OnClickListener(dialog);
                }
            });
        }

        if(btnFail != 0) {
            BTNfail.setText(ctx.getString(btnFail));
            BTNfail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFailClick.OnClickListener(dialog);
                }
            });
        }

        return dialog;
    }

    public static AlertDialog showDialogMessage(Context ctx, String message,
                                                String btnOk, final DialogMessage btnOkClick,
                                                int btnFail, final DialogMessage btnFailClick) {
        // LOADING VIEWS
        View view          = LayoutInflater.from(ctx).inflate(DIALOG_MESSAGE, null);
        TextView TVmessage = view.findViewById(R.id.TVmessage);
        Button BTNok       = view.findViewById(R.id.btnAccept);
        Button BTNfail     = view.findViewById(R.id.btnCancel);

        // FILLING VIEWS
        TVmessage.setText(message);

        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();

        if(btnOk != "") {
            BTNok.setText(btnOk);
            BTNok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOkClick.OnClickListener(dialog);
                }
            });
        }

        if(btnFail != 0) {
            BTNfail.setText(ctx.getString(btnFail));
            BTNfail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFailClick.OnClickListener(dialog);
                }
            });
        }

        return dialog;
    }

    public static AlertDialog showDialogMessage(Context ctx, String icon, String message,
                                                String btnOk, final DialogMessage btnOkClick,
                                                String btnFail, final DialogMessage btnFailClick) {
        // LOADING VIEWS
        View view           = LayoutInflater.from(ctx).inflate(DIALOG_MESSAGE, null);
        TextView TVmessage  = view.findViewById(R.id.tvMessage);
        Button BTNok        = view.findViewById(R.id.btnAccept);
        Button BTNfail      = view.findViewById(R.id.btnFail);
        ImageView IVicon    = view.findViewById(R.id.ivIcon);

        switch (icon){
            case Constants.not_network:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_close_white)).into(IVicon);
                break;
        }
        // FILLING VIEWS
        TVmessage.setText(message);

        BTNfail.setVisibility(View.GONE);


        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();

        if(btnOk != null) {
            BTNok.setText(btnOk);
            BTNok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOkClick.OnClickListener(dialog);
                }
            });
        }

        if(btnFail != null) {
            BTNfail.setVisibility(View.VISIBLE);
            BTNfail.setText(btnFail);
            BTNfail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFailClick.OnClickListener(dialog);
                }
            });
        }

        return dialog;
    }

    public static AlertDialog showDialogSuccess(Context ctx, String icon, String message,
                                                String btnOk, final DialogMessage btnOkClick,
                                                String btnFail, final DialogMessage btnFailClick) {
        // LOADING VIEWS
        View view           = LayoutInflater.from(ctx).inflate(DIALOG_SUCCESS, null);
        TextView TVmessage  = view.findViewById(R.id.tvMessage);
        Button BTNok        = view.findViewById(R.id.btnAccept);
        Button BTNfail      = view.findViewById(R.id.btnFail);
        ImageView IVicon    = view.findViewById(R.id.ivIcon);

        switch (icon){
            case Constants.success:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_done)).into(IVicon);
                break;
        }
        // FILLING VIEWS
        TVmessage.setText(message);

        BTNfail.setVisibility(View.GONE);


        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();

        if(btnOk != null) {
            BTNok.setText(btnOk);
            BTNok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOkClick.OnClickListener(dialog);
                }
            });
        }

        if(btnFail != null) {
            BTNfail.setVisibility(View.VISIBLE);
            BTNfail.setText(btnFail);
            BTNfail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFailClick.OnClickListener(dialog);
                }
            });
        }

        return dialog;
    }

    public static AlertDialog showDialogMessage(Context ctx, String message,
                                                int btnOk, final DialogMessage btnOkClick,
                                                int btnFail, final DialogMessage btnFailClick) {
        // LOADING VIEWS
        View view          = LayoutInflater.from(ctx).inflate(DIALOG_MESSAGE, null);
        TextView TVmessage = view.findViewById(R.id.TVmessage);
        Button BTNok       = view.findViewById(R.id.btnAccept);
        Button BTNfail     = view.findViewById(R.id.btnCancel);

        // FILLING VIEWS
        TVmessage.setText(message);

        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();

        if(btnOk != 0) {
            BTNok.setText(ctx.getString(btnOk));
            BTNok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnOkClick.OnClickListener(dialog);
                }
            });
        }

        if(btnFail != 0) {
            BTNfail.setText(ctx.getString(btnFail));
            BTNfail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFailClick.OnClickListener(dialog);
                }
            });
        }

        return dialog;
    }

    public static AlertDialog showLoadingDialog(Context ctx, int message) {
        View view = LayoutInflater.from(ctx).inflate(DIALOG_LODING, null);
        TextView TVmessage = view.findViewById(R.id.TVmessage);
        TVmessage.setText(ctx.getString(message));
        return new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();
    }

    public static AlertDialog showLoadingDialog(Context ctx, String title, String message) {
        View view = LayoutInflater.from(ctx).inflate(DIALOG_LODING, null);
        TextView tvTitle    = view.findViewById(R.id.tvTitle);
        TextView tvMessage  = view.findViewById(R.id.tvMessage);

        tvTitle.setText(title);
        tvMessage.setText(message);
        return new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();
    }

    public static AlertDialog showProgressDialog(Context ctx, int message) {
        View view = LayoutInflater.from(ctx).inflate(DIALOG_PROGRESS, null);
        ProgressBar PBloading = view.findViewById(R.id.PBloading);
        TextView TVmessage    = view.findViewById(R.id.TVmessage);
        TVmessage.setText(ctx.getString(message));
        PBloading.setProgress(0);
        return new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();
    }

    public static AlertDialog showProgressDialog(Context ctx, String message) {
        View view = LayoutInflater.from(ctx).inflate(DIALOG_PROGRESS, null);
        ProgressBar PBloading = view.findViewById(R.id.PBloading);
        TextView TVmessage    = view.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        PBloading.setProgress(0);
        return new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();
    }

    public static void setProgress(AlertDialog dialog, int progress) {
        ProgressBar PBloading = dialog.getWindow().findViewById(R.id.PBloading);
        PBloading.setProgress(progress);
    }
}
