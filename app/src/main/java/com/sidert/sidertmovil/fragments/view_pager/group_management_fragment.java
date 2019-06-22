package com.sidert.sidertmovil.fragments.view_pager;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.MapView;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.CameraVertical;
import com.sidert.sidertmovil.activities.GroupRecovery;
import com.sidert.sidertmovil.activities.Signature;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.CustomWatcher;
import com.sidert.sidertmovil.utils.Miscellaneous;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class group_management_fragment extends Fragment {

    private Context ctx;

    private EditText etReasonNoPay;
    private EditText etOutDateInfo;
    private EditText etPaymentMethod;
    private EditText etDateDeath;
    private EditText etBank;
    private EditText etAmountPaymentMade;
    private EditText etDepositDate;
    private EditText etPhotoGallery;
    private EditText etCommentNoContact;
    private EditText etRecoveryDate;
    private EditText etAmountRequired;

    private RadioGroup rgClient;
    private RadioGroup rgSolidary;
    private RadioGroup rgCorrect;
    private RadioGroup rgIsManager;
    private RadioGroup rgPay;

    private RadioButton rbYesClient;
    private RadioButton rbNotClient;
    private RadioButton rbPay;
    private RadioButton rbNotPay;
    private RadioButton rbYes;
    private RadioButton rbNot;
    private RadioButton rbYesManager;
    private RadioButton rbNotManager;

    private ImageButton imbMap;
    private ImageButton imbPhoto;
    private ImageButton imbGallery;
    private ImageButton imbSignature;

    private ImageView ivSignature;

    private MapView mapLocation;

    private LinearLayout llCommentNoContac;
    private LinearLayout llphotoFacade;
    private LinearLayout llDataDeath;
    private LinearLayout llOutdateInfo;
    private LinearLayout llReasonNotPay;
    private LinearLayout llAmountRequired;
    private LinearLayout llBank;
    private LinearLayout llPaymentMethod;
    private LinearLayout llManagementResult;
    private LinearLayout llspecify_cause;
    private LinearLayout llBalanceCutting;
    private LinearLayout llCurrentBalance;
    private LinearLayout llAmountRequiredPaid;
    private LinearLayout llAmountPaymentMade;
    private LinearLayout llDepositDate;
    private LinearLayout llPhotoGalery;
    private LinearLayout llIsManager;
    private LinearLayout llSignature;
    private LinearLayout llPhotoGallery;
    private LinearLayout llPhotoGaleryButton;
    private LinearLayout llPhoto;
    private LinearLayout llGallery;
    private LinearLayout llSolidaryPayment;
    private LinearLayout llMembers;

    private String[] _outdate_info;
    private String[] _reason_not_pay;
    private String[] _banks;
    private String[] _payment_method;
    private String[] _photo_galery;

    private Calendar myCalendar, currentDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private final int REQUEST_CODE_SIGNATURE = 456;

    GroupRecovery boostrap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_management, container, false);
        ctx             = getContext();
        boostrap        = (GroupRecovery) getActivity();
        etReasonNoPay   = view.findViewById(R.id.etReasonNoPay);
        etOutDateInfo   = view.findViewById(R.id.etOutDateInfo);
        etBank          = view.findViewById(R.id.etBank);
        etDateDeath     = view.findViewById(R.id.etDateDeath);
        etPaymentMethod = view.findViewById(R.id.etPaymentMethod);
        etAmountPaymentMade = view.findViewById(R.id.etAmountPaymentMade);
        etDepositDate       = view.findViewById(R.id.etDepositDate);
        etPhotoGallery      = view.findViewById(R.id.etPhotoGallery);
        etCommentNoContact  = view.findViewById(R.id.etCommentNoContact);
        etRecoveryDate      = view.findViewById(R.id.etRecoveryDate);
        etAmountRequired    = view.findViewById(R.id.etAmountRequired);

        rgClient        = view.findViewById(R.id.rgClient);
        rgPay           = view.findViewById(R.id.rgPay);
        rgCorrect       = view.findViewById(R.id.rgCorrect);
        rgSolidary      = view.findViewById(R.id.rgSolidary);
        rgIsManager     = view.findViewById(R.id.rgIsManager);

        rbYesClient     = view.findViewById(R.id.rbYesClient);
        rbNotClient     = view.findViewById(R.id.rbNotClient);
        rbPay           = view.findViewById(R.id.rbPay);
        rbNotPay        = view.findViewById(R.id.rbNotPay);
        rbYes           = view.findViewById(R.id.rbYes);
        rbNot           = view.findViewById(R.id.rbNot);
        rbYesManager    = view.findViewById(R.id.rbYesManager);
        rbNotManager    = view.findViewById(R.id.rbNotManager);

        imbMap          = view.findViewById(R.id.imbMap);
        imbPhoto        = view.findViewById(R.id.imbPhoto);
        imbGallery      = view.findViewById(R.id.imbGallery);
        imbSignature    = view.findViewById(R.id.imbSignature);

        ivSignature     = view.findViewById(R.id.ivSignature);

        mapLocation     = view.findViewById(R.id.mapLocation);

        llCommentNoContac   = view.findViewById(R.id.llCommenNoContac);
        llManagementResult  = view.findViewById(R.id.llManagementResult);
        llphotoFacade       = view.findViewById(R.id.llPhotoFacade);
        llDataDeath         = view.findViewById(R.id.llDataDeath);
        llOutdateInfo       = view.findViewById(R.id.llOutDataInfo);
        llReasonNotPay      = view.findViewById(R.id.llReasonNotPay);
        llAmountRequired    = view.findViewById(R.id.llAmountRequired);
        llBank              = view.findViewById(R.id.llBank);
        llPaymentMethod     = view.findViewById(R.id.llPaymentMethod);
        llspecify_cause     = view.findViewById(R.id.llspecify_cause);
        llBalanceCutting    = view.findViewById(R.id.llBalanceCutting);
        llCurrentBalance    = view.findViewById(R.id.llCurrentBalance);
        llAmountRequiredPaid    = view.findViewById(R.id.llAmountRequiredPaid);
        llAmountPaymentMade     = view.findViewById(R.id.llAmountPaymentMade);
        llDepositDate           = view.findViewById(R.id.llDepositDate);
        llPhotoGalery           = view.findViewById(R.id.llPhotoGallery);
        llIsManager             = view.findViewById(R.id.llIsManager);
        llSignature             = view.findViewById(R.id.llSignature);
        llPhotoGaleryButton     = view.findViewById(R.id.llPhotoGaleryButton);
        llPhoto                 = view.findViewById(R.id.llPhoto);
        llGallery               = view.findViewById(R.id.llGallery);
        llSolidaryPayment       = view.findViewById(R.id.llSolidaryPayment);
        llMembers               = view.findViewById(R.id.llMembers);

        myCalendar      = Calendar.getInstance();
        currentDate     = Calendar.getInstance();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentDate.set(Calendar.YEAR,currentDate.get(Calendar.YEAR));
        currentDate.set(Calendar.MONTH,currentDate.get(Calendar.MONTH));
        currentDate.set(Calendar.DAY_OF_MONTH,(currentDate.get(Calendar.DAY_OF_MONTH)));

        etRecoveryDate.setText(sdf.format(currentDate.getTime()));

        _outdate_info = getResources().getStringArray(R.array.outdated_information);
        _banks = getResources().getStringArray(R.array.banks);
        _payment_method = getResources().getStringArray(R.array.method_pay);
        _reason_not_pay = getResources().getStringArray(R.array.reason_no_pay);
        _photo_galery = getResources().getStringArray(R.array.files);

        //EditText Click
        etReasonNoPay.setOnClickListener(etReasonNoPay_OnClick);
        etOutDateInfo.setOnClickListener(etOutDateInfo_OnClick);
        etDateDeath.setOnClickListener(etDateDeath_OnClick);
        etPaymentMethod.setOnClickListener(etPaymentMethod_OnClick);
        etBank.setOnClickListener(etBank_OnClick);
        etDepositDate.setOnClickListener(etDepositDate_OnClick);
        etPhotoGallery.setOnClickListener(etPhotoGallery_OnClick);

        //RadioButton Click
        rbYesClient.setOnClickListener(rbYesClient_OnClick);
        rbNotClient.setOnClickListener(rbNotClient_OnClick);
        rbPay.setOnClickListener(rbPay_OnClick);
        rbNotPay.setOnClickListener(rbNotPay_OnClick);
        rbYes.setOnClickListener(rbYes_OnClick);
        rbNot.setOnClickListener(rbNot_OnClick);
        rbYesManager.setOnClickListener(rbYesManager_OnClick);
        rbNotManager.setOnClickListener(rbNotManager_OnClick);

        //ImageButton Click
        imbSignature.setOnClickListener(imbSignature_OnClick);
        imbPhoto.setOnClickListener(imbPhoto_OnClick);

        etAmountPaymentMade.addTextChangedListener(new CustomWatcher(etAmountPaymentMade));
        etAmountRequired.addTextChangedListener(new CustomWatcher(etAmountRequired));
    }

    /*
    * Evento click de EditText
    * */
    private View.OnClickListener etReasonNoPay_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.reason_no_pay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etReasonNoPay.setText(_reason_not_pay[position]);
                            switch(etReasonNoPay.getText().toString()){
                                case Constants.negative_payment:
                                    llphotoFacade.setVisibility(View.VISIBLE);
                                    llDataDeath.setVisibility(View.GONE);
                                    llOutdateInfo.setVisibility(View.GONE);
                                    llspecify_cause.setVisibility(View.GONE);
                                    break;
                                case Constants.outdate_information:
                                    llOutdateInfo.setVisibility(View.VISIBLE);
                                    llphotoFacade.setVisibility(View.GONE);
                                    llDataDeath.setVisibility(View.GONE);
                                    llspecify_cause.setVisibility(View.GONE);
                                    break;
                                case Constants.death:
                                    llphotoFacade.setVisibility(View.VISIBLE);
                                    llDataDeath.setVisibility(View.VISIBLE);
                                    llOutdateInfo.setVisibility(View.GONE);
                                    llspecify_cause.setVisibility(View.GONE);
                                    break;
                                case Constants.other:
                                    llspecify_cause.setVisibility(View.VISIBLE);
                                    llphotoFacade.setVisibility(View.VISIBLE);
                                    llDataDeath.setVisibility(View.GONE);
                                    llOutdateInfo.setVisibility(View.GONE);
                                    break;
                            }
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener etOutDateInfo_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.outdated_information, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etOutDateInfo.setText(_outdate_info[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener etDateDeath_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog dpd = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR,year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    setDatePicked(etDateDeath);

                }
            },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),Calendar.DAY_OF_MONTH);
            dpd.getDatePicker().setMaxDate(currentDate.getTimeInMillis());
            dpd.show();
        }
    };

    private View.OnClickListener etPaymentMethod_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.method_pay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etPaymentMethod.setText(_payment_method[position]);
                            switch(etPaymentMethod.getText().toString()){
                                case Constants.bank:
                                    llBank.setVisibility(View.VISIBLE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    etPhotoGallery.setText("");
                                    etPhotoGallery.setEnabled(true);
                                    //llPhotoGaleryButton.setVisibility(View.GONE);
                                    llPhoto.setVisibility(View.GONE);
                                    llGallery.setVisibility(View.GONE);
                                    break;
                                case Constants.oxxo:
                                    llBank.setVisibility(View.GONE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    etPhotoGallery.setText("");
                                    etPhotoGallery.setEnabled(true);
                                    //llPhotoGaleryButton.setVisibility(View.GONE);
                                    llPhoto.setVisibility(View.GONE);
                                    llGallery.setVisibility(View.GONE);
                                    break;
                                case Constants.cash:
                                    llBank.setVisibility(View.GONE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    etPhotoGallery.setText("Fotografía");
                                    etPhotoGallery.setEnabled(false);
                                    //llPhotoGaleryButton.setVisibility(View.VISIBLE);
                                    llPhoto.setVisibility(View.VISIBLE);
                                    llGallery.setVisibility(View.GONE);
                                    break;
                                case Constants.sidert:
                                    llBank.setVisibility(View.GONE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    etPhotoGallery.setText("Fotografía");
                                    etPhotoGallery.setEnabled(false);
                                    //llPhotoGaleryButton.setVisibility(View.VISIBLE);
                                    llPhoto.setVisibility(View.VISIBLE);
                                    llGallery.setVisibility(View.GONE);
                                    break;
                            }
                            llAmountRequired.setVisibility(View.VISIBLE);
                            llAmountRequiredPaid.setVisibility(View.VISIBLE);
                            llIsManager.setVisibility(View.VISIBLE);

                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener etBank_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.banks, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etBank.setText(_banks[position]);
                        }
                    });
            builder.create();
            builder.show();
        }
    };

    private View.OnClickListener etDepositDate_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DatePickerDialog dpd = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR,year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    setDatePicked(etDepositDate);

                }
            },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),Calendar.DAY_OF_MONTH);
            dpd.getDatePicker().setMaxDate(currentDate.getTimeInMillis());
            dpd.show();
        }
    };

    private View.OnClickListener etPhotoGallery_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.files, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etPhotoGallery.setText(_photo_galery[position]);
                            //llPhotoGaleryButton.setVisibility(View.VISIBLE);
                            switch (etPhotoGallery.getText().toString()){
                                case Constants.photo:
                                    llPhoto.setVisibility(View.VISIBLE);
                                    llGallery.setVisibility(View.GONE);
                                    break;
                                case Constants.galery:
                                    llPhoto.setVisibility(View.GONE);
                                    llGallery.setVisibility(View.VISIBLE);
                                    break;
                            }
                        }
                    });
            builder.create();
            builder.show();
        }
    };
    // Fin de EditText

    /*
    * Evento click de RadioButton
    * */
    private View.OnClickListener rbYesClient_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llManagementResult.setVisibility(View.VISIBLE);
            llCommentNoContac.setVisibility(View.GONE);
            llphotoFacade.setVisibility(View.GONE);
            llPaymentMethod.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener rbNotClient_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llCommentNoContac.setVisibility(View.VISIBLE);
            llphotoFacade.setVisibility(View.VISIBLE);
            llManagementResult.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener rbPay_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llSolidaryPayment.setVisibility(View.VISIBLE);
            llPaymentMethod.setVisibility(View.VISIBLE);
            llBalanceCutting.setVisibility(View.VISIBLE);
            llCurrentBalance.setVisibility(View.VISIBLE);
            llDataDeath.setVisibility(View.GONE);
            llReasonNotPay.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener rbNotPay_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llReasonNotPay.setVisibility(View.VISIBLE);
            llSolidaryPayment.setVisibility(View.GONE);
            llPaymentMethod.setVisibility(View.GONE);
            llBalanceCutting.setVisibility(View.GONE);
            llCurrentBalance.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener rbYes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llAmountPaymentMade.setVisibility(View.VISIBLE);
            llMembers.setVisibility(View.GONE);
            llDepositDate.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener rbNot_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llMembers.setVisibility(View.VISIBLE);
            llAmountPaymentMade.setVisibility(View.GONE);
            llDepositDate.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener rbYesManager_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("Tag-Money",etAmountPaymentMade.getText().toString().replace("$",""));
            llSignature.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener rbNotManager_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llSignature.setVisibility(View.GONE);
        }
    };
    // Fin de RadioButton

    /*
    * Evento de click Button
    * */

    // Fin de Button

    /*
    * Evento de ImageButton
    * */
    private View.OnClickListener imbSignature_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, Signature.class);
            startActivityForResult(sig, REQUEST_CODE_SIGNATURE);
        }
    };

    private View.OnClickListener imbPhoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, CameraVertical.class);
            startActivityForResult(i, 432);
            //setCameraDisplayOrientation(boostrap,-1,camera);
        }
    };
    //Fin de ImageButton


    private void setDatePicked(EditText et){
        sdf.setTimeZone(myCalendar.getTimeZone());
        et.setError(null);
        et.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SIGNATURE:
                if (resultCode == boostrap.RESULT_OK){
                    if (data != null){
                        imbSignature.setVisibility(View.GONE);
                        ivSignature.setVisibility(View.VISIBLE);
                        Glide.with(ctx)
                                .load(data.getStringExtra(Constants.uri_signature))
                                .into(ivSignature);
                    }
                }
                break;
        }
    }
}
