package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;

public class Popups {

    private static int DIALOG_MESSAGE           = R.layout.popup_message;
    private static int DIALOG_CONFIRM           = R.layout.popup_confirm;
    private static int DIALOG_CONFIRM_IMAGE     = R.layout.popup_confirm_image;
    private static int DIALOG_LODING            = R.layout.popup_loading;
    private static int DIALOG_PROGRESS          = R.layout.popup_progress;
    public interface DialogMessage {
        void OnClickListener(AlertDialog dialog);
    }


    public static AlertDialog showDialogMessage(Context ctx, String icon, int message,
                                                int btOk, final DialogMessage btnOkClick) {
        // LOADING VIEWS
        View view           = LayoutInflater.from(ctx).inflate(DIALOG_MESSAGE, null);
        TextView tvMessage  = view.findViewById(R.id.tvMessage);
        Button btnOk        = view.findViewById(R.id.btnAccept);
        ImageView ivIcon    = view.findViewById(R.id.ivIcon);


        switch (icon){
            case Constants.not_network:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_wifi_off)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.face_happy:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_face_satisfied)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_green));
                break;
            case Constants.face_dissatisfied:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_dissatisfied)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.money:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_money_white)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.print_off:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_print_off)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.login:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_error)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.warning:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_warning)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_blue));
                break;
            default:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_question)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_blue));
                break;
        }
        // FILLING VIEWS
        tvMessage.setText(ctx.getResources().getString(message));

        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();


        btnOk.setText(ctx.getResources().getString(btOk));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOkClick.OnClickListener(dialog);
            }
        });

        return dialog;
    }

    public static AlertDialog showDialogMessage(Context ctx, String icon, String message,
                                                int btOk, final DialogMessage btnOkClick) {
        // LOADING VIEWS
        View view           = LayoutInflater.from(ctx).inflate(DIALOG_MESSAGE, null);
        TextView tvMessage  = view.findViewById(R.id.tvMessage);
        Button btnOk        = view.findViewById(R.id.btnAccept);
        ImageView ivIcon    = view.findViewById(R.id.ivIcon);


        switch (icon){
            case Constants.not_network:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_wifi_off)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.face_happy:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_face_satisfied)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_green));
                break;
            case Constants.face_dissatisfied:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_dissatisfied)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.money:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_money_white)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.print_off:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_print_off)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.login:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_error)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.warning:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_warning)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_blue));
                break;
            default:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_question)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_blue));
                break;
        }
        // FILLING VIEWS
        tvMessage.setText(message);

        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();


        btnOk.setText(ctx.getResources().getString(btOk));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOkClick.OnClickListener(dialog);
            }
        });

        return dialog;
    }

    public static AlertDialog showDialogConfirm(Context ctx, String icon, int message,
                                                int btOk, final DialogMessage btnOkClick,
                                                int btFail, final DialogMessage btnFailClick) {
        // LOADING VIEWS
        View view           = LayoutInflater.from(ctx).inflate(DIALOG_CONFIRM, null);
        TextView tvMessage  = view.findViewById(R.id.tvMessage);
        Button btnOk        = view.findViewById(R.id.btnAccept);
        Button btnFail      = view.findViewById(R.id.btnFail);
        ImageView ivIcon    = view.findViewById(R.id.ivIcon);

        switch (icon){
            case Constants.success:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_done)).into(ivIcon);
                break;
            case Constants.firma:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_firma_white)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_green));
                break;
            case Constants.print_off:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_print_off)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.camara:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_cam)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_green));
                break;
            default:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_question)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_blue));
                break;
        }
        // FILLING VIEWS
        tvMessage.setText(ctx.getResources().getString(message));

        btnFail.setVisibility(View.GONE);


        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();


        btnOk.setText(ctx.getResources().getString(btOk));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOkClick.OnClickListener(dialog);
            }
        });


        if(btFail != 0) {
            btnFail.setVisibility(View.VISIBLE);
            btnFail.setText(ctx.getResources().getString(btFail));
            btnFail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnFailClick.OnClickListener(dialog);
                }
            });
        }

        return dialog;
    }

    public static AlertDialog showDialogConfirmImage(Context ctx, String icon, int message,
                                                int btOption1, final DialogMessage btnOption1Click,
                                                int btOption2, final DialogMessage btnOption2Click,
                                                int btCancel, final DialogMessage btnCancelClick) {
        // LOADING VIEWS
        View view           = LayoutInflater.from(ctx).inflate(DIALOG_CONFIRM_IMAGE, null);
        TextView tvMessage  = view.findViewById(R.id.tvMessage);
        Button btnOption1       = view.findViewById(R.id.btnOption1);
        Button btnOption2       = view.findViewById(R.id.btnOption2);
        Button btnCancel        = view.findViewById(R.id.btnCancel);
        ImageView ivIcon    = view.findViewById(R.id.ivIcon);

        switch (icon){
            case Constants.success:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_done)).into(ivIcon);
                break;
            case Constants.firma:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_firma_white)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_green));
                break;
            case Constants.print_off:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_print_off)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_red));
                break;
            case Constants.camara:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_camera_white)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_green));
                break;
            default:
                Glide.with(ctx).load(ctx.getResources().getDrawable(R.drawable.ic_question)).into(ivIcon);
                ivIcon.setBackground(ctx.getResources().getDrawable(R.drawable.circle_button_blue));
                break;
        }
        // FILLING VIEWS
        tvMessage.setText(ctx.getResources().getString(message));


        final AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setCancelable(false)
                .setView(view).create();


        btnOption1.setText(ctx.getResources().getString(btOption1));
        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOption1Click.OnClickListener(dialog);
            }
        });

        btnOption2.setText(ctx.getResources().getString(btOption2));
        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOption2Click.OnClickListener(dialog);
            }
        });

        btnCancel.setText(ctx.getResources().getString(btCancel));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelClick.OnClickListener(dialog);
            }
        });


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

    public static AlertDialog showLoadingDialog(Context ctx, int title, int message) {
        View view = LayoutInflater.from(ctx).inflate(DIALOG_LODING, null);
        TextView tvTitle    = view.findViewById(R.id.tvTitle);
        TextView tvMessage  = view.findViewById(R.id.tvMessage);

        tvTitle.setText(ctx.getResources().getString(title));
        tvMessage.setText(ctx.getResources().getString(message));
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
