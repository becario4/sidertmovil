package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
/*import androidx.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.fragments.geo_miembros_fragment;
import com.sidert.sidertmovil.fragments.geo_presidente_fragment;
import com.sidert.sidertmovil.fragments.geo_secretaria_fragment;
import com.sidert.sidertmovil.fragments.geo_tesorera_fragment;
import com.sidert.sidertmovil.utils.BottomNavigationViewHelper;
import com.sidert.sidertmovil.utils.Constants;
import com.sidert.sidertmovil.utils.NameFragments;
import com.sidert.sidertmovil.utils.Popups;

import java.util.Objects;


/**Vista de geolocalizacion grupal donde visualiza el menu de PRESIDENTE, TESORERO, SECRETARIO, INTEGRANTES
 * cuenta con un menu bottom */
public class GeolocalizacionGpo extends AppCompatActivity {

    private BottomNavigationView nvMenu;
    private Context ctx;
    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_geolocalizacion_gpo);

        ctx     = this;
        nvMenu  = findViewById(R.id.nvMenu);
        //Fabric.with(this, new Crashlytics());
        BottomNavigationViewHelper.disableShiftMode(nvMenu);
        nvMenu.setOnNavigationItemSelectedListener(nvMenu_onClick);

        /**Obtiene los datos que se pasaron entre clases*/
        b = new Bundle();
        b.putInt(Constants._ID, getIntent().getIntExtra(Constants._ID,0));
        b.putString(Constants.NUM_SOLICITUD, getIntent().getStringExtra(Constants.NUM_SOLICITUD));

        /**Valida que opcion fue seleccionado para mostrar la vista*/
        switch (getIntent().getIntExtra(Constants.MODULO,0)){
            case 1:
                nvMenu.setSelectedItemId(R.id.nvPresidente);
                break;
            case 2:
                nvMenu.setSelectedItemId(R.id.nvTesorera);
                break;
            case 3:
                nvMenu.setSelectedItemId(R.id.nvSecretaria);
                break;
            default:
                nvMenu.setSelectedItemId(R.id.nvPresidente);
                break;
        }
    }

    /**Evento de menu para seleccionar que formulario de geolocalizacion*/
    private BottomNavigationView.OnNavigationItemSelectedListener nvMenu_onClick = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            final Fragment current;
            int itemId = item.getItemId();
            if (itemId == R.id.nvPresidente) {/**Valida si estaba en otra vista y quiere cambiar a vista de presidente y hay datos pendiente de guardar*/
                current = getSupportFragmentManager().findFragmentById(R.id.flMain);
                if ((current instanceof geo_tesorera_fragment)) {
                    if (((geo_tesorera_fragment) current).flag_edit) {
                        if (((geo_tesorera_fragment) current).latLngUbicacion != null ||
                                !((geo_tesorera_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                ((geo_tesorera_fragment) current).byteFotoFachada != null ||
                                !((geo_tesorera_fragment) current).etComentario.getText().toString().trim().isEmpty()) {
                            AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            setFragment(NameFragments.GEO_PRESIDENTE, b);
                                            dialog.dismiss();

                                        }
                                    }, R.string.no, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            nvMenu.setSelectedItemId(R.id.nvTesorera);
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            guardar_info_dlg.show();
                        } else
                            setFragment(NameFragments.GEO_PRESIDENTE, b);
                    } else {
                        setFragment(NameFragments.GEO_PRESIDENTE, b);
                    }
                } else if ((current instanceof geo_secretaria_fragment)) {
                    if (((geo_secretaria_fragment) current).flag_edit) {
                        if (((geo_secretaria_fragment) current).latLngUbicacion != null ||
                                !((geo_secretaria_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                ((geo_secretaria_fragment) current).byteFotoFachada != null ||
                                !((geo_secretaria_fragment) current).etComentario.getText().toString().trim().isEmpty()) {
                            AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            setFragment(NameFragments.GEO_PRESIDENTE, b);
                                            dialog.dismiss();

                                        }
                                    }, R.string.no, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            nvMenu.setSelectedItemId(R.id.nvSecretaria);
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            guardar_info_dlg.show();
                        } else
                            setFragment(NameFragments.GEO_PRESIDENTE, b);
                    } else
                        setFragment(NameFragments.GEO_PRESIDENTE, b);
                } else if ((current instanceof geo_miembros_fragment)) {
                    setFragment(NameFragments.GEO_PRESIDENTE, b);
                } else
                    setFragment(NameFragments.GEO_PRESIDENTE, b);
            } else if (itemId == R.id.nvTesorera) {/**Valida si estaba en otra vista y quiere cambiar a vista de tesorera y hay datos pendiente de guardar*/
                current = getSupportFragmentManager().findFragmentById(R.id.flMain);

                if ((current instanceof geo_presidente_fragment)) {
                    if (((geo_presidente_fragment) current).flag_edit) {
                        if (((geo_presidente_fragment) current).latLngUbicacion != null ||
                                !((geo_presidente_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                ((geo_presidente_fragment) current).byteFotoFachada != null ||
                                !((geo_presidente_fragment) current).etComentario.getText().toString().trim().isEmpty()) {
                            AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            setFragment(NameFragments.GEO_TESORERA, b);
                                            dialog.dismiss();
                                        }
                                    }, R.string.no, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            nvMenu.setSelectedItemId(R.id.nvPresidente);
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            guardar_info_dlg.show();
                        } else
                            setFragment(NameFragments.GEO_TESORERA, b);

                    } else {
                        setFragment(NameFragments.GEO_TESORERA, b);
                    }
                } else if ((current instanceof geo_secretaria_fragment)) {
                    if (((geo_secretaria_fragment) current).flag_edit) {
                        if (((geo_secretaria_fragment) current).latLngUbicacion != null ||
                                !((geo_secretaria_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                ((geo_secretaria_fragment) current).byteFotoFachada != null ||
                                !((geo_secretaria_fragment) current).etComentario.getText().toString().trim().isEmpty()) {
                            AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            setFragment(NameFragments.GEO_TESORERA, b);
                                            dialog.dismiss();

                                        }
                                    }, R.string.no, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            nvMenu.setSelectedItemId(R.id.nvSecretaria);
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            guardar_info_dlg.show();
                        } else
                            setFragment(NameFragments.GEO_TESORERA, b);
                    } else
                        setFragment(NameFragments.GEO_TESORERA, b);
                } else if ((current instanceof geo_miembros_fragment)) {
                    setFragment(NameFragments.GEO_PRESIDENTE, b);
                } else
                    setFragment(NameFragments.GEO_TESORERA, b);
            } else if (itemId == R.id.nvSecretaria) {/**Valida si estaba en otra vista y quiere cambiar a vista de secretaria y hay datos pendiente de guardar*/
                current = getSupportFragmentManager().findFragmentById(R.id.flMain);

                if ((current instanceof geo_presidente_fragment)) {
                    if (((geo_presidente_fragment) current).flag_edit) {
                        if (((geo_presidente_fragment) current).latLngUbicacion != null ||
                                !((geo_presidente_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                ((geo_presidente_fragment) current).byteFotoFachada != null ||
                                !((geo_presidente_fragment) current).etComentario.getText().toString().trim().isEmpty()) {
                            AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            setFragment(NameFragments.GEO_SECRETARIA, b);
                                            dialog.dismiss();
                                        }
                                    }, R.string.no, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            nvMenu.setSelectedItemId(R.id.nvPresidente);
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            guardar_info_dlg.show();
                        } else
                            setFragment(NameFragments.GEO_SECRETARIA, b);

                    } else {
                        setFragment(NameFragments.GEO_SECRETARIA, b);
                    }
                } else if ((current instanceof geo_tesorera_fragment)) {
                    if (((geo_tesorera_fragment) current).flag_edit) {
                        if (((geo_tesorera_fragment) current).latLngUbicacion != null ||
                                !((geo_tesorera_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                                ((geo_tesorera_fragment) current).byteFotoFachada != null ||
                                !((geo_tesorera_fragment) current).etComentario.getText().toString().trim().isEmpty()) {
                            AlertDialog guardar_info_dlg = Popups.showDialogConfirm(ctx, Constants.question,
                                    R.string.confirm_guardar, R.string.yes, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            setFragment(NameFragments.GEO_SECRETARIA, b);
                                            dialog.dismiss();

                                        }
                                    }, R.string.no, new Popups.DialogMessage() {
                                        @Override
                                        public void OnClickListener(AlertDialog dialog) {
                                            nvMenu.setSelectedItemId(R.id.nvTesorera);
                                            dialog.dismiss();
                                        }
                                    });
                            Objects.requireNonNull(guardar_info_dlg.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
                            guardar_info_dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            guardar_info_dlg.show();
                        } else
                            setFragment(NameFragments.GEO_SECRETARIA, b);

                    } else
                        setFragment(NameFragments.GEO_SECRETARIA, b);
                } else if ((current instanceof geo_miembros_fragment)) {
                    setFragment(NameFragments.GEO_PRESIDENTE, b);
                } else
                    setFragment(NameFragments.GEO_SECRETARIA, b);
            } else if (itemId == R.id.nvIntegrantes) {/**Manda a lista de integrantes*/
                setFragment(NameFragments.GEO_MIEMBROS, b);
            }
            return true;
        }
    };

    /**Funcion para hacer las transiciones de fragmentos de las vistas de geolocalizacion*/
    public void setFragment(String fragment, Bundle extras) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.flMain);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        String tokenFragment = "";
        if (fragment.equals(NameFragments.GEO_PRESIDENTE)) {
            if (!(current instanceof geo_presidente_fragment)) {
                geo_presidente_fragment presidente = new geo_presidente_fragment();
                presidente.setArguments(extras);
                transaction.replace(R.id.flMain, presidente, NameFragments.GEO_PRESIDENTE);
                tokenFragment = NameFragments.GEO_PRESIDENTE;
            } else
                return;
        } else if (fragment.equals(NameFragments.GEO_TESORERA)) {
            if (!(current instanceof geo_tesorera_fragment)) {
                geo_tesorera_fragment tesorera = new geo_tesorera_fragment();
                tesorera.setArguments(extras);
                transaction.replace(R.id.flMain, tesorera, NameFragments.GEO_TESORERA);
                tokenFragment = NameFragments.GEO_TESORERA;
            } else
                return;
        } else if (fragment.equals(NameFragments.GEO_SECRETARIA)) {
            if (!(current instanceof geo_secretaria_fragment)) {
                geo_secretaria_fragment secretaria = new geo_secretaria_fragment();
                secretaria.setArguments(extras);
                transaction.replace(R.id.flMain, secretaria, NameFragments.GEO_AVAL);
                tokenFragment = NameFragments.GEO_SECRETARIA;
            } else
                return;
        } else if (fragment.equals(NameFragments.GEO_MIEMBROS)) {
            if (!(current instanceof geo_miembros_fragment)) {
                geo_miembros_fragment miembros = new geo_miembros_fragment();
                miembros.setArguments(extras);
                transaction.replace(R.id.flMain, miembros, NameFragments.GEO_MIEMBROS);
                tokenFragment = NameFragments.GEO_MIEMBROS;
            } else
                return;
        }

        if(!tokenFragment.equals(NameFragments.GEO_PRESIDENTE) && !tokenFragment.equals(NameFragments.GEO_TESORERA) && !tokenFragment.equals(NameFragments.GEO_SECRETARIA) && !tokenFragment.equals(NameFragments.GEO_MIEMBROS)) {
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

    /**Funcion de retroceso para salir de la vista*/
    @Override
    public void onBackPressed() {
        final Fragment current = getSupportFragmentManager().findFragmentById(R.id.flMain);
        AlertDialog guardar_info_dlg;
        final boolean[] flag = {false};
        if ((current instanceof geo_presidente_fragment)){
            if (((geo_presidente_fragment) current).flag_edit){
                if (((geo_presidente_fragment) current).latLngUbicacion != null ||
                        !((geo_presidente_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                        ((geo_presidente_fragment) current).byteFotoFachada != null ||
                        !((geo_presidente_fragment) current).etComentario.getText().toString().trim().isEmpty()){
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
        else if ((current instanceof geo_tesorera_fragment)){
            if (((geo_tesorera_fragment) current).flag_edit){
                if (((geo_tesorera_fragment) current).latLngUbicacion != null ||
                        !((geo_tesorera_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                        ((geo_tesorera_fragment) current).byteFotoFachada != null ||
                        !((geo_tesorera_fragment) current).etComentario.getText().toString().trim().isEmpty()){
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
        else if ((current instanceof geo_secretaria_fragment)){
            if (((geo_secretaria_fragment) current).flag_edit){
                if (((geo_secretaria_fragment) current).latLngUbicacion != null ||
                        !((geo_secretaria_fragment) current).etCodigoBarras.getText().toString().trim().isEmpty() ||
                        ((geo_secretaria_fragment) current).byteFotoFachada != null ||
                        !((geo_secretaria_fragment) current).etComentario.getText().toString().trim().isEmpty()){
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
        else if ((current instanceof geo_miembros_fragment)){
            flag[0] = true;
        }
        if (flag[0]){
            super.onBackPressed();
        }

    }
}
