package com.sidert.sidertmovil.fragments.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.DialogFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Constants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

public class dialog_ine_photo extends DialogFragment {

    private Context ctx;
    private String clienteCode;
    private ImageView ivIneClient;
    private ImageView ivClose;

    private static final String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "//sidert//ine//";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_ine_photo,container,false);
        ctx             = getContext();
        ivIneClient     = view.findViewById(R.id.ivIneClient);
        ivClose         = view.findViewById(R.id.ivClose);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
        clienteCode = getArguments().getString(Constants.client_code);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String pureBase64Encoded = loadSettingFile(clienteCode).substring(loadSettingFile(clienteCode).indexOf(",") + 1);

        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

        Glide.with(ctx).load(decodedBytes).into(ivIneClient);

        PhotoViewAttacher photo = new PhotoViewAttacher(ivIneClient);
        photo.update();

        ivClose.setOnClickListener(ivClose_OnClick);
    }

    private View.OnClickListener ivClose_OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDialog().dismiss();
        }
    };

    private String loadSettingFile(String claveCliente) {
        String lastConnAddr = "";
        int rin = 0;
        char [] buf = new char[999999];
        try {
            FileReader fReader = new FileReader(fileName+"1074469.txt");
            rin = fReader.read(buf);
            if(rin > 0) {
                lastConnAddr = new String(buf,0,rin);
            }
            fReader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return  lastConnAddr;
    }

}
