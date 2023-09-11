package com.sidert.sidertmovil.views.expedientes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.activities.VencidaIndividual;
import com.sidert.sidertmovil.models.documentosclientes.DocumentoCliente;
import com.sidert.sidertmovil.models.documentosclientes.DocumentoClienteDao;
import com.sidert.sidertmovil.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import uk.co.senab.photoview.PhotoView;


public class DocumentosFragment extends Fragment {
    private Context ctx;
    private VencidaIndividual vencidaIndividual;

    private PhotoView ivDocumentOpened;
    private EditText etNumeroCliente;
    private RadioGroup rgDocumentos;

    public String id_prestamo = "";

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_documentos, container, false);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        ctx = getContext();

        vencidaIndividual = (VencidaIndividual) getActivity();
        assert vencidaIndividual != null;
        vencidaIndividual.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        etNumeroCliente = view.findViewById(R.id.etNumeroCliente);
        rgDocumentos    = view.findViewById(R.id.rgDocumentos);
        ivDocumentOpened = (PhotoView) view.findViewById(R.id.ivDocumentOpened);

        id_prestamo = getArguments().getString(Constants.ID_PRESTAMO);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rgDocumentos.check(R.id.rbIne);

        try {
            showIne();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rgDocumentos.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbIne) {
                try {
                    showIne();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (checkedId == R.id.rbComprobanteDomicilio) {
                try {
                    showComprobanteDomicilio();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showIne() throws IOException {
        DocumentoClienteDao documentoClienteDao = new DocumentoClienteDao(ctx);
        DocumentoCliente documentoCliente = documentoClienteDao.findByPrestamoIdAndTipo( Integer.valueOf(id_prestamo), DocumentoCliente.TIPO_INE);

        if(documentoCliente != null) {
            byte[] decodedString = Base64.decode(documentoCliente.getArchivoBase64(), Base64.DEFAULT);
            //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            File pdffile = new File(Environment.getExternalStorageDirectory().toString() + "/temp/expedientes/" + DocumentoCliente.TIPO_INE + "/" + documentoCliente.getPrestamoId());

            if (!pdffile.exists()) {
                pdffile.getParentFile().mkdirs();
                pdffile.createNewFile();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.write(pdffile.toPath(), decodedString);

                ParcelFileDescriptor fileDescriptor = ctx.getContentResolver().openFileDescriptor(Uri.fromFile(pdffile), "r");
                PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);
                PdfRenderer.Page currentPage = pdfRenderer.openPage(0);
                Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
                currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                ivDocumentOpened.setImageBitmap(bitmap);
            }
            else
            {
                ivDocumentOpened.setImageBitmap(null);
            }
        }
        else
        {
            ivDocumentOpened.setImageBitmap(null);
        }
    }

    private void showComprobanteDomicilio() throws IOException {
        DocumentoClienteDao documentoClienteDao = new DocumentoClienteDao(ctx);
        DocumentoCliente documentoCliente = documentoClienteDao.findByPrestamoIdAndTipo( Integer.valueOf(id_prestamo), DocumentoCliente.TIPO_COM_DOM);

        if(documentoCliente != null) {
            byte[] decodedString = Base64.decode(documentoCliente.getArchivoBase64(), Base64.DEFAULT);
            //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            File pdffile = new File(Environment.getExternalStorageDirectory().toString() + "/temp/expedientes/" + DocumentoCliente.TIPO_COM_DOM + "/" + documentoCliente.getPrestamoId());

            if (!pdffile.exists()) {
                pdffile.getParentFile().mkdirs();
                pdffile.createNewFile();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.write(pdffile.toPath(), decodedString);

                ParcelFileDescriptor fileDescriptor = ctx.getContentResolver().openFileDescriptor(Uri.fromFile(pdffile), "r");
                PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);
                PdfRenderer.Page currentPage = pdfRenderer.openPage(0);
                Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
                currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                ivDocumentOpened.setImageBitmap(bitmap);
            }
            else
            {
                ivDocumentOpened.setImageBitmap(null);
            }
        }
        else
        {
            ivDocumentOpened.setImageBitmap(null);
        }
    }
}
