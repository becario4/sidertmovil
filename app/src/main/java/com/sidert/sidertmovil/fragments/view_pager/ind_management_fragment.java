package com.sidert.sidertmovil.fragments.view_pager;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.MapView;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.IndividualRecovery;
import com.sidert.sidertmovil.activities.PrintSeewoo;
import com.sidert.sidertmovil.activities.Signature;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ind_management_fragment extends Fragment {

    private Context ctx;

    private EditText etReasonNoPay;
    private EditText etOutDateInfo;
    private EditText etPaymentMethod;
    private EditText etDateDeath;
    private EditText etBank;
    private EditText etAmountPaymentMade;
    private EditText etDepositDate;
    private EditText etPhotoGallery;

    private RadioGroup rgClient;
    private RadioGroup rgPay;
    private RadioGroup rgCorrect;
    private RadioGroup rgIsManager;

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

    private String[] _outdate_info;
    private String[] _reason_not_pay;
    private String[] _banks;
    private String[] _payment_method;
    private String[] _photo_galery;

    private Button btnPrint;

    private Calendar myCalendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private final int REQUEST_CODE_SIGNATURE = 456;

    IndividualRecovery boostrap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view       = inflater.inflate(R.layout.fragment_ind_management, container, false);
        ctx             = getContext();

        boostrap            = (IndividualRecovery) getActivity();
        etReasonNoPay       = view.findViewById(R.id.etReasonNoPay);
        etOutDateInfo       = view.findViewById(R.id.etOutDateInfo);
        etBank              = view.findViewById(R.id.etBank);
        etDateDeath         = view.findViewById(R.id.etDateDeath);
        etPaymentMethod     = view.findViewById(R.id.etPaymentMethod);
        etAmountPaymentMade = view.findViewById(R.id.etAmountPaymentMade);
        etDepositDate       = view.findViewById(R.id.etDepositDate);
        etPhotoGallery      = view.findViewById(R.id.etPhotoGallery);

        rgClient        = view.findViewById(R.id.rgClient);
        rgPay           = view.findViewById(R.id.rgPay);
        rgCorrect       = view.findViewById(R.id.rgCorrect);
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

        btnPrint        = view.findViewById(R.id.btnPrint);

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

        myCalendar      = Calendar.getInstance();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
        _outdate_info = getResources().getStringArray(R.array.outdated_information);
        _banks = getResources().getStringArray(R.array.banks);
        _payment_method = getResources().getStringArray(R.array.method_pay);
        _reason_not_pay = getResources().getStringArray(R.array.reason_no_pay);
        _photo_galery = getResources().getStringArray(R.array.files);
        etReasonNoPay.setOnClickListener(etReasonNoPay_OnClick);
        etOutDateInfo.setOnClickListener(etOutDateInfo_OnClick);
        etDateDeath.setOnClickListener(etDateDeath_OnClick);
        etBank.setOnClickListener(etBank_OnClick);
        etPaymentMethod.setOnClickListener(etPaymentMethod_OnClick);
        etDepositDate.setOnClickListener(etDepositDate_OnClick);
        etPhotoGallery.setOnClickListener(etPhotoGallery_onClick);
        imbMap.setOnClickListener(imbMap_OnClick);
        imbPhoto.setOnClickListener(imbPhoto_OnClick);
        imbSignature.setOnClickListener(imbSignature_OnClick);

        btnPrint.setOnClickListener(btnPrint_OnClick);

        //RadioButton Click
        rbYesClient.setOnClickListener(rbYesClient_OnClick);
        rbNotClient.setOnClickListener(rbNotClient_OnClick);
        rbPay.setOnClickListener(rbPay_OnClick);
        rbNotPay.setOnClickListener(rbNotPay_OnClick);
        rbYes.setOnClickListener(rbYes_OnClick);
        rbNot.setOnClickListener(rbNot_OnClick);
        rbYesManager.setOnClickListener(rbYesManager_OnClick);
        rbNotManager.setOnClickListener(rbNotManager_OnClick);

    }

    private View.OnClickListener btnPrint_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DBhelper dBhelper = new DBhelper(ctx);

            //Log.v("Tag-UPdate",String.valueOf(dBhelper.updateImpressionsLog("log_impressions_r",)));

            /*Intent i = new Intent(ctx, PrintSeewoo.class);

            i.putExtra("tag",true);

            startActivityForResult(i,123);*/
        }
    };

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
                                    llOutdateInfo.setVisibility(View.GONE);
                                    llDataDeath.setVisibility(View.GONE);
                                    llspecify_cause.setVisibility(View.GONE);
                                    break;
                                case Constants.outdate_information:
                                    llphotoFacade.setVisibility(View.GONE);
                                    llDataDeath.setVisibility(View.GONE);
                                    llOutdateInfo.setVisibility(View.VISIBLE);
                                    llspecify_cause.setVisibility(View.GONE);
                                    break;
                                case Constants.death:
                                    llphotoFacade.setVisibility(View.VISIBLE);
                                    llOutdateInfo.setVisibility(View.GONE);
                                    llDataDeath.setVisibility(View.VISIBLE);
                                    llspecify_cause.setVisibility(View.GONE);
                                    break;
                                case Constants.other:
                                    llspecify_cause.setVisibility(View.VISIBLE);
                                    llphotoFacade.setVisibility(View.VISIBLE);
                                    llOutdateInfo.setVisibility(View.GONE);
                                    llDataDeath.setVisibility(View.GONE);
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
                                    llAmountRequiredPaid.setVisibility(View.VISIBLE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    etPhotoGallery.setText("");
                                    etPhotoGallery.setEnabled(true);
                                    llDepositDate.setVisibility(View.VISIBLE);
                                    break;
                                case Constants.oxxo:
                                    llBank.setVisibility(View.GONE);
                                    llAmountPaymentMade.setVisibility(View.VISIBLE);
                                    llDepositDate.setVisibility(View.VISIBLE);
                                    llAmountRequired.setVisibility(View.GONE);
                                    llAmountRequiredPaid.setVisibility(View.GONE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    etPhotoGallery.setText("");
                                    etPhotoGallery.setEnabled(true);
                                    llDepositDate.setVisibility(View.VISIBLE);
                                    break;
                                case Constants.cash:
                                    llBank.setVisibility(View.GONE);
                                    llAmountRequiredPaid.setVisibility(View.VISIBLE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    llPhotoGaleryButton.setVisibility(View.VISIBLE);
                                    llGallery.setVisibility(View.GONE);
                                    llPhoto.setVisibility(View.VISIBLE);
                                    etPhotoGallery.setText("Fotografía");
                                    etPhotoGallery.setEnabled(false);
                                    llDepositDate.setVisibility(View.GONE);
                                    break;
                                case Constants.sidert:
                                    llBank.setVisibility(View.GONE);
                                    llAmountRequiredPaid.setVisibility(View.VISIBLE);
                                    llPhotoGalery.setVisibility(View.VISIBLE);
                                    llPhotoGaleryButton.setVisibility(View.VISIBLE);
                                    llPhoto.setVisibility(View.VISIBLE);
                                    llGallery.setVisibility(View.GONE);
                                    etPhotoGallery.setText("Fotografía");
                                    etPhotoGallery.setEnabled(false);
                                    llDepositDate.setVisibility(View.GONE);
                                    break;
                            }
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

    private View.OnClickListener imbMap_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mapLocation.setVisibility(View.VISIBLE);
            imbMap.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener imbPhoto_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            //i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(i, 432);
        }
    };

    private View.OnClickListener imbSignature_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sig = new Intent(ctx, Signature.class);
            startActivityForResult(sig, REQUEST_CODE_SIGNATURE);
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
            dpd.getDatePicker().setMaxDate(new Date().getTime());
            dpd.show();
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
            dpd.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
            dpd.show();
        }
    };

    private View.OnClickListener etPhotoGallery_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(R.string.selected_option)
                    .setItems(R.array.files, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            etPhotoGallery.setText(_photo_galery[position]);
                            llPhotoGaleryButton.setVisibility(View.VISIBLE);
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

    private void setDatePicked(EditText et){
        sdf.setTimeZone(myCalendar.getTimeZone());
        et.setError(null);
        et.setText(sdf.format(myCalendar.getTime()));
    }

    private void initComponents (){
        llphotoFacade.setVisibility(View.GONE);
        llCommentNoContac.setVisibility(View.GONE);
        llDataDeath.setVisibility(View.GONE);
        llOutdateInfo.setVisibility(View.GONE);
        llReasonNotPay.setVisibility(View.GONE);
        llAmountRequired.setVisibility(View.GONE);
        llBank.setVisibility(View.GONE);
        llPaymentMethod.setVisibility(View.GONE);
        llManagementResult.setVisibility(View.GONE);
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
            case 123:
                if (resultCode == boostrap.RESULT_OK){
                    if (data != null){
                        Toast.makeText(ctx, data.getStringExtra("message"), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    /*
    * Eventos de Click en RadioButton
    * */

    private View.OnClickListener rbYesClient_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llManagementResult.setVisibility(View.VISIBLE);
            llCommentNoContac.setVisibility(View.GONE);
            llphotoFacade.setVisibility(View.GONE);
            rbPay.setChecked(false);
            rbNotPay.setChecked(false);
        }
    };

    private View.OnClickListener rbNotClient_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llCommentNoContac.setVisibility(View.VISIBLE);
            llphotoFacade.setVisibility(View.VISIBLE);
            llPaymentMethod.setVisibility(View.GONE);
            llAmountRequired.setVisibility(View.GONE);
            llAmountPaymentMade.setVisibility(View.GONE);
            llDepositDate.setVisibility(View.GONE);
            llPhotoGalery.setVisibility(View.GONE);
            llPhotoGaleryButton.setVisibility(View.GONE);
            llIsManager.setVisibility(View.GONE);
            llBalanceCutting.setVisibility(View.GONE);
            llCurrentBalance.setVisibility(View.GONE);
            llBank.setVisibility(View.GONE);
            llAmountRequiredPaid.setVisibility(View.GONE);
            llSignature.setVisibility(View.GONE);
            llManagementResult.setVisibility(View.GONE);
            llReasonNotPay.setVisibility(View.GONE);
            llOutdateInfo.setVisibility(View.GONE);
            rgPay.clearCheck();
        }
    };

    private View.OnClickListener rbPay_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llPaymentMethod.setVisibility(View.VISIBLE);
            llAmountRequired.setVisibility(View.VISIBLE);
            llBalanceCutting.setVisibility(View.VISIBLE);
            llCurrentBalance.setVisibility(View.VISIBLE);
            llspecify_cause.setVisibility(View.GONE);
            llphotoFacade.setVisibility(View.GONE);
            etPaymentMethod.setText("");
        }
    };

    private View.OnClickListener rbNotPay_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llAmountRequired.setVisibility(View.VISIBLE);
            llReasonNotPay.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener rbYes_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llAmountPaymentMade.setVisibility(View.VISIBLE);
            etAmountPaymentMade.setEnabled(false);
        }
    };

    private View.OnClickListener rbNot_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llAmountPaymentMade.setVisibility(View.VISIBLE);
            etAmountPaymentMade.setEnabled(true);
        }
    };

    private View.OnClickListener rbYesManager_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llSignature.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener rbNotManager_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llSignature.setVisibility(View.GONE);
        }
    };
    // Fin de Click de RadioButon
}
