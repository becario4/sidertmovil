package com.sidert.sidertmovil.views.pdfreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;

import org.apache.commons.collections4.KeyValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocumentOpenedFragment extends Fragment {
    private Context ctx;
    private PdfReaderActivity boostrap;

    private ImageView ivPdfPageView;
    private Button btnPrevious;
    private Button btnNext;
    private Button btnIndex;

    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;

    private Integer currentPageNumber = 0;

    private String CURRENT_PAGE_INDEX_KEY = "com.sidert.sidertmovil.actionopendocument.state.CURRENT_PAGE_INDEX_KEY";

    private Uri documentUri;

    public List<Indice> indice;
    public String[] _titulosDelindice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document_opened, container, false);
        ctx       = getContext();
        boostrap  = (PdfReaderActivity) getActivity();

        ivPdfPageView = view.findViewById(R.id.ivDocumentOpened);
        btnPrevious   = view.findViewById(R.id.btnPrevious);
        btnNext       = view.findViewById(R.id.btnNext);
        btnIndex      = view.findViewById(R.id.btnIndex);
        btnIndex      = view.findViewById(R.id.btnIndex);

        llenaIndice();

        File guiaRapida = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/guia_rapida.pdf");
        documentUri = Uri.fromFile(guiaRapida);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState !=  null)
            currentPageNumber = savedInstanceState.getInt(CURRENT_PAGE_INDEX_KEY, 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnPrevious.setOnClickListener(view -> {
            showPage(currentPage.getIndex() - 1);
        });

        btnNext.setOnClickListener(view -> {
            showPage(currentPage.getIndex() + 1);
        });

        btnIndex.setOnClickListener(btnIndex_OnClick);
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            openRenderer(ctx, documentUri);
            showPage(currentPageNumber);
        }catch (IOException ioException)
        {
            Log.d("DOCUMENTOPENEDFRAGMENT", "Exception opening document", ioException);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        closeRenderer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_PAGE_INDEX_KEY, currentPage.getIndex());
        super.onSaveInstanceState(outState);
    }

    private void openRenderer(Context ctx, Uri documentUri) throws IOException {
        if (ctx == null) return;

        ParcelFileDescriptor fileDescriptor = ctx.getContentResolver().openFileDescriptor(documentUri, "r");

        pdfRenderer = new PdfRenderer(fileDescriptor);
        currentPage = pdfRenderer.openPage(currentPageNumber);
    }

    private void closeRenderer() {
        currentPage.close();
        pdfRenderer.close();
    }

    private void showPage(Integer index) {
        if(index < 0 || index >= pdfRenderer.getPageCount()){
            return;
        }

        currentPage.close();
        currentPage = pdfRenderer.openPage(index);

        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);

        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        ivPdfPageView.setImageBitmap(bitmap);

        Integer pageCount = pdfRenderer.getPageCount();
        btnPrevious.setEnabled(0 != index);
        btnNext.setEnabled(index + 1 < pageCount);

        boostrap.setTitle("Guía Rápida " + index + 1 + "/" + pageCount);
    }

    private View.OnClickListener btnIndex_OnClick = v -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(R.string.selected_option)
                .setItems(_titulosDelindice, (dialog, position) -> {
                    showPage(indice.get(position).getPagina());
                });
        builder.create();
        builder.show();
    };

    private void llenaIndice(){
        int posicion = 0;

        indice = new ArrayList<>();
        indice.add(new Indice(0, "Inicio"));
        indice.add(new Indice(6, "Secciones de Sidert Móvil"));
        indice.add(new Indice(15, "Contenido de una ficha"));
        indice.add(new Indice(19, "Sincronización de información"));
        indice.add(new Indice(23, "Referencias bancarias"));
        indice.add(new Indice(32, "Mesa de ayuda"));
        indice.add(new Indice(36, "Apoyo de gastos funarios"));
        indice.add(new Indice(40, "Círculo de crédito"));

        _titulosDelindice = new String[indice.size()];

        for(Indice i : indice)
        {
            _titulosDelindice[posicion] = i.getTitulo();

            posicion++;
        }
    }
}