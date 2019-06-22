package com.sidert.sidertmovil.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.common.util.Strings;

public class BackendThreat {
    public Context ctx;

    public BackendThreat(Context paramContext, bitmapRequest parambitmapRequest, Strings paramStrings) {
        this.ctx = paramContext;
        (new asynImage(parambitmapRequest, paramStrings)).execute(new bitmapRequest[] { parambitmapRequest });
    }

    public BackendThreat(Context paramContext, cursorRequest paramcursorRequest) {
        this.ctx = paramContext;
        (new asynCursor(paramcursorRequest)).execute(new cursorRequest[] { paramcursorRequest });
    }

    public BackendThreat(Context paramContext, stringRequest paramstringRequest) {
        this.ctx = paramContext;
        (new asynString(paramstringRequest)).execute(new stringRequest[] { paramstringRequest });
    }

    private class asynCursor extends AsyncTask<cursorRequest, Void, Cursor> {
        BackendThreat.cursorRequest complete;

        asynCursor(BackendThreat.cursorRequest param1cursorRequest) { this.complete = param1cursorRequest; }

        protected Cursor doInBackground(BackendThreat.cursorRequest... param1VarArgs) { return param1VarArgs[0].doInBackground(); }

        protected void onPostExecute(Cursor param1Cursor) {
            super.onPostExecute(param1Cursor);
            this.complete.onSuccess(param1Cursor);
        }
    }

    private class asynImage extends AsyncTask<bitmapRequest, String, Bitmap> {
        BackendThreat.bitmapRequest complete;

        Strings langStrings;

        ProgressDialog loading;

        asynImage(BackendThreat.bitmapRequest param1bitmapRequest, Strings param1Strings) {
            this.complete = param1bitmapRequest;
            this.langStrings = param1Strings;
        }

        protected Bitmap doInBackground(BackendThreat.bitmapRequest... param1VarArgs) { return param1VarArgs[0].doInBackground(); }

        protected void onPostExecute(Bitmap param1Bitmap) {
            super.onPostExecute(param1Bitmap);
            if (this.loading != null)
                this.loading.dismiss();
            this.complete.onSuccess(param1Bitmap);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (this.langStrings != null)
                this.loading = ProgressDialog.show(BackendThreat.this.ctx, "Cargando", "Generando Vista Previa", true);
        }
    }

    private class asynString extends AsyncTask<stringRequest, Integer, String> {
        BackendThreat.stringRequest complete;

        asynString(BackendThreat.stringRequest param1stringRequest) { this.complete = param1stringRequest; }

        protected String doInBackground(BackendThreat.stringRequest... param1VarArgs) { return param1VarArgs[0].doInBackground(); }

        protected void onPostExecute(String param1String) {
            super.onPostExecute(param1String);
            this.complete.onSuccess(param1String);
        }

        protected void onPreExecute() { super.onPreExecute(); }
    }

    public static interface bitmapRequest {
        Bitmap doInBackground();

        void onSuccess(Bitmap param1Bitmap);
    }

    public static interface cursorRequest {
        Cursor doInBackground();

        void onSuccess(Cursor param1Cursor);
    }

    public static interface stringRequest {
        String doInBackground();

        void onSuccess(String param1String);
    }
}