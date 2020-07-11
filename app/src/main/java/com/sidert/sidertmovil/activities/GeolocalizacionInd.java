package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.geo_aval_fragment;
import com.sidert.sidertmovil.fragments.geo_cliente_fragment;
import com.sidert.sidertmovil.fragments.geo_negocio_fragment;
import com.sidert.sidertmovil.utils.BottomNavigationViewHelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;

import java.util.Objects;



public class GeolocalizacionInd extends AppCompatActivity {

    private BottomNavigationView nvMenu;
    private Context ctx;
    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_geolocalizacion_ind);

        ctx     = this;
        nvMenu  = findViewById(R.id.nvMenu);
        //Fabric.with(this, new Crashlytics());
        BottomNavigationViewHelper.disableShiftMode(nvMenu);
        nvMenu.setOnNavigationItemSelectedListener(nvMenu_onClick);

         b = new Bundle();
         b.putInt(Constants._ID, getIntent().getIntExtra(Constants._ID,0));
         b.putString(Constants.NUM_SOLICITUD, getIntent().getStringExtra(Constants.NUM_SOLICITUD));
        switch (getIntent().getIntExtra(Constants.MODULO,0)){
            case 1:
                nvMenu.setSelectedItemId(R.id.nvCliente);
                break;
            case 2:
                nvMenu.setSelectedItemId(R.id.nvNegocio);
                break;
            case 3:
                nvMenu.setSelectedItemId(R.id.nvAval);
                break;
            default:
                nvMenu.setSelectedItemId(R.id.nvCliente);
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener nvMenu_onClick = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            final Fragment current;
            switch (item.getItemId()) {
                case R.id.nvCliente:
                    current = getSupportFragmentManager().findFragmentById(R.id.flMain);
                    if ((current instanceof geo_negocio_fragment)){
                        if (((geo_negocio_fragment) current).flag_edit){
                            if (((geo_negocio_fragment) current).latLngUbicacion != null ||
                                    !((geo_negocio_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                    ((geo_negocio_fragment) current).byteFotoFachada != null ||
                                    !((geo_negocio_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                        R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                setFragment(NameFragments.GEO_CLIENTE, b);
                                                dialog.dismiss();

                                            }
                                        }, R.string.no, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                nvMenu.setSelectedItemId(R.id.nvNegocio);
                                                dialog.dismiss();
                                            }
                                        });
                                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                guardar_info_dlg.show();
                            }
                            else
                                setFragment(NameFragments.GEO_CLIENTE, b);
                        }
                        else {
                            setFragment(NameFragments.GEO_CLIENTE, b);
                        }
                    }
                    else  if ((current instanceof geo_aval_fragment)){
                        if (((geo_aval_fragment) current).flag_edit){
                            if (((geo_aval_fragment) current).latLngUbicacion != null ||
                                    !((geo_aval_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                    ((geo_aval_fragment) current).byteFotoFachada != null ||
                                    !((geo_aval_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                        R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                setFragment(NameFragments.GEO_CLIENTE, b);
                                                dialog.dismiss();

                                            }
                                        }, R.string.no, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                nvMenu.setSelectedItemId(R.id.nvAval);
                                                dialog.dismiss();
                                            }
                                        });
                                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                guardar_info_dlg.show();
                            }
                            else
                                setFragment(NameFragments.GEO_CLIENTE, b);
                        }
                        else
                            setFragment(NameFragments.GEO_CLIENTE, b);
                    }
                    else
                        setFragment(NameFragments.GEO_CLIENTE, b);

                    break;
                case R.id.nvNegocio:
                    current = getSupportFragmentManager().findFragmentById(R.id.flMain);

                    if ((current instanceof geo_cliente_fragment)){
                        if (((geo_cliente_fragment) current).flag_edit){
                            if (((geo_cliente_fragment) current).latLngUbicacion != null ||
                                    !((geo_cliente_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                    ((geo_cliente_fragment) current).byteFotoFachada != null ||
                                    !((geo_cliente_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                        R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                setFragment(NameFragments.GEO_NEGOCIO, b);
                                                dialog.dismiss();
                                            }
                                        }, R.string.no, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                nvMenu.setSelectedItemId(R.id.nvCliente);
                                                dialog.dismiss();
                                            }
                                        });
                                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                guardar_info_dlg.show();
                            }
                            else
                                setFragment(NameFragments.GEO_NEGOCIO, b);

                        }
                        else {
                            setFragment(NameFragments.GEO_NEGOCIO, b);
                        }
                    }
                    else  if ((current instanceof geo_aval_fragment)){
                        if (((geo_aval_fragment) current).flag_edit){
                            if (((geo_aval_fragment) current).latLngUbicacion != null ||
                                    !((geo_aval_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                    ((geo_aval_fragment) current).byteFotoFachada != null ||
                                    !((geo_aval_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                        R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                setFragment(NameFragments.GEO_NEGOCIO, b);
                                                dialog.dismiss();

                                            }
                                        }, R.string.no, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                nvMenu.setSelectedItemId(R.id.nvAval);
                                                dialog.dismiss();
                                            }
                                        });
                                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                guardar_info_dlg.show();
                            }
                            else
                                setFragment(NameFragments.GEO_NEGOCIO, b);
                        }
                        else
                            setFragment(NameFragments.GEO_NEGOCIO, b);
                    }
                    else
                        setFragment(NameFragments.GEO_NEGOCIO, b);
                    break;
                case R.id.nvAval:
                    current = getSupportFragmentManager().findFragmentById(R.id.flMain);

                    if ((current instanceof geo_cliente_fragment)){
                        if (((geo_cliente_fragment) current).flag_edit){
                            if (((geo_cliente_fragment) current).latLngUbicacion != null ||
                                    !((geo_cliente_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                    ((geo_cliente_fragment) current).byteFotoFachada != null ||
                                    !((geo_cliente_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                        R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                setFragment(NameFragments.GEO_AVAL, b);
                                                dialog.dismiss();
                                            }
                                        }, R.string.no, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                nvMenu.setSelectedItemId(R.id.nvCliente);
                                                dialog.dismiss();
                                            }
                                        });
                                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                guardar_info_dlg.show();
                            }
                            else
                                setFragment(NameFragments.GEO_AVAL, b);

                        }
                        else {
                            setFragment(NameFragments.GEO_AVAL, b);
                        }
                    }
                    else  if ((current instanceof geo_negocio_fragment)) {
                        if (((geo_negocio_fragment) current).flag_edit) {
                            if (((geo_negocio_fragment) current).latLngUbicacion != null ||
                                    !((geo_negocio_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                    ((geo_negocio_fragment) current).byteFotoFachada != null ||
                                    !((geo_negocio_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                                AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                        R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                setFragment(NameFragments.GEO_AVAL, b);
                                                dialog.dismiss();

                                            }
                                        }, R.string.no, new Popups.DialogMessage() {
                                            @Override
                                            public void OnClickListener(AlertDialog dialog) {
                                                nvMenu.setSelectedItemId(R.id.nvNegocio);
                                                dialog.dismiss();
                                            }
                                        });
                                Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                                guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                guardar_info_dlg.show();
                            }
                            else
                                setFragment(NameFragments.GEO_AVAL, b);

                        } else
                            setFragment(NameFragments.GEO_AVAL, b);
                    }
                    else
                        setFragment(NameFragments.GEO_AVAL, b);
                    break;
            }
            return true;
        }
    };

    public void setFragment(String fragment, Bundle extras) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.flMain);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String tokenFragment = "";

        switch (fragment) {
            case NameFragments.GEO_CLIENTE:
                if (!(current instanceof geo_cliente_fragment)){
                    geo_cliente_fragment cliente = new geo_cliente_fragment();
                    cliente.setArguments(extras);
                    transaction.replace(R.id.flMain, cliente, NameFragments.GEO_CLIENTE);

                    tokenFragment = NameFragments.GEO_CLIENTE;
                } else
                    return;
                break;
            case NameFragments.GEO_NEGOCIO:
                if (!(current instanceof geo_negocio_fragment)){
                    geo_negocio_fragment negocio = new geo_negocio_fragment();
                    negocio.setArguments(extras);
                    transaction.replace(R.id.flMain, negocio, NameFragments.GEO_NEGOCIO);

                    tokenFragment = NameFragments.GEO_NEGOCIO;
                } else
                    return;

                break;
            case NameFragments.GEO_AVAL:
                if (!(current instanceof geo_aval_fragment)){
                    geo_aval_fragment aval = new geo_aval_fragment();
                    aval.setArguments(extras);
                    transaction.replace(R.id.flMain, aval, NameFragments.GEO_AVAL);

                    tokenFragment = NameFragments.GEO_AVAL;
                } else
                    return;
                break;

        }

        if(!tokenFragment.equals(NameFragments.GEO_CLIENTE) && !tokenFragment.equals(NameFragments.GEO_NEGOCIO) && !tokenFragment.equals(NameFragments.GEO_AVAL)) {
            int count = manager.getBackStackEntryCount();
            Toast.makeText(ctx, String.valueOf(count), Toast.LENGTH_SHORT).show();
            if(count > 0) {
                int index = count - 1;
                String tag = manager.getBackStackEntryAt(index).getName();
                if(!tag.equals(tokenFragment)) {
                    transaction.addToBackStack(tokenFragment);
                }
            } else {
                transaction.addToBackStack(tokenFragment);
            }
        } else {
            cleanFragments();
        }
        transaction.commit();
    }

    public void cleanFragments() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onBackPressed() {
        final Fragment current = getSupportFragmentManager().findFragmentById(R.id.flMain);
        AlertDialog guardar_info_dlg;
        final boolean[] flag = {false};
        if ((current instanceof geo_cliente_fragment)){
            if (((geo_cliente_fragment) current).flag_edit){
                if (((geo_cliente_fragment) current).latLngUbicacion != null ||
                        !((geo_cliente_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                        ((geo_cliente_fragment) current).byteFotoFachada != null ||
                        !((geo_cliente_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                    guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                            R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    finish();
                                    dialog.dismiss();

                                }
                            }, R.string.no, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    guardar_info_dlg.show();
                }
                else
                    flag[0] = true;

            }
            else
                flag[0] = true;

        }
        else if ((current instanceof geo_negocio_fragment)){
            if (((geo_negocio_fragment) current).flag_edit){
                if (((geo_negocio_fragment) current).latLngUbicacion != null ||
                        !((geo_negocio_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                        ((geo_negocio_fragment) current).byteFotoFachada != null ||
                        !((geo_negocio_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                    guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                            R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    finish();
                                    dialog.dismiss();

                                }
                            }, R.string.no, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    guardar_info_dlg.show();
                }
                else
                    flag[0] = true;

            }
            else
                flag[0] = true;
        }
        else if ((current instanceof geo_aval_fragment)){
            if (((geo_aval_fragment) current).flag_edit){
                if (((geo_aval_fragment) current).latLngUbicacion != null ||
                        !((geo_aval_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                        ((geo_aval_fragment) current).byteFotoFachada != null ||
                        !((geo_aval_fragment) current).etComentario.getText().toString().trim().isEmpty()){
                    guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                            R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    finish();
                                    dialog.dismiss();

                                }
                            }, R.string.no, new Popups.DialogMessage() {
                                @Override
                                public void OnClickListener(AlertDialog dialog) {
                                    dialog.dismiss();
                                }
                            });
                    Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                    guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    guardar_info_dlg.show();
                }
                else
                    flag[0] = true;

            }
            else
                flag[0] = true;
        }
        if (flag[0]){
            super.onBackPressed();
        }

    }
}
