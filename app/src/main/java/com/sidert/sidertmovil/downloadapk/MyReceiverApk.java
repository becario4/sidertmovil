package com.sidert.sidertmovil.downloadapk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
//import android.support.v4.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import com.sidert.sidertmovil.BuildConfig;
import com.sidert.sidertmovil.R;
import com.sidert.sidertmovil.utils.Miscellaneous;
import com.sidert.sidertmovil.utils.Popups;

import java.io.File;

import static android.provider.MediaStore.MediaColumns.MIME_TYPE;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;

public class MyReceiverApk extends BroadcastReceiver {

    DownloadManager myDownloadManager;
    long size;
    IntentFilter myIntentFilter;
    private Context ctx;
    private Activity myActivity;
    private AlertDialog loading;

    public MyReceiverApk(Activity activity) {
        this.ctx = activity;
        this.myActivity = activity;

        myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("EventoAction", intent.getAction());
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);

            Log.e("DOWNLOADID", String.valueOf(downloadId));
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(size);

            Cursor cursor = myDownloadManager.query(query);

            if (cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL ==cursor.getInt(columnIndex)){
                    @SuppressLint("Range") String uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                    if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
                        String[] apkName = uriString.split("/");

                        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");

                        File file = new File(path, apkName[7]);


                        Uri apkUri = FileProvider.getUriForFile(ctx,
                                BuildConfig.APPLICATION_ID + ".downloadapk.MyReceiverApk", file);

                        Intent prompInstall = new Intent(Intent.ACTION_VIEW);
                        prompInstall.setData(apkUri);
                        prompInstall.addCategory("android.intent.category.DEFAULT");
                        prompInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        prompInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        prompInstall.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        prompInstall.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        prompInstall.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                        prompInstall.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        myActivity.startActivity(prompInstall);
                    }
                    else{
                        File file = new File(uriString);
                        Intent screenInstall = new Intent(Intent.ACTION_VIEW);
                        screenInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        screenInstall.setDataAndType(Uri.parse(file.getPath()), "application/vnd.android.package-archive");
                        myActivity.startActivity(screenInstall);
                    }



                    Log.e("MsjDescarga","Se descargó sin problema");
                }
                loading.dismiss();
            }
        }
    }

    public void DownloadApk(String url){
        Log.e("URLXD", url);
        loading = Popups.showLoadingDialog(ctx, R.string.please_wait, R.string.loading_info);
        loading.setCancelable(false);
        loading.show();
        DownloadManager.Request myRequest;
        myDownloadManager = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
        myRequest = new DownloadManager.Request(Uri.parse(url));
        myRequest.setMimeType("application/vnd.android.package-archive");
        myRequest.setTitle("SidertMovilApk");
        myRequest.setDescription("Versión de sidert móvil actualizado "+ Miscellaneous.ObtenerFecha(TIMESTAMP));
        String fileExtesion = MimeTypeMap.getFileExtensionFromUrl(url);
        String name = URLUtil.guessFileName(url, null, fileExtesion);

        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        boolean isCreated = myFile.exists();
        if (!isCreated)
            isCreated = myFile.mkdir();

        myRequest.setDestinationInExternalPublicDir("Download/", name);
        String h = myRequest.setDestinationInExternalPublicDir("Download/", name).toString();


        Log.e("ruta_apk", h);
        Log.e("Descargar", "Ok");
        size = myDownloadManager.enqueue(myRequest);
    }

    public void Register(MyReceiverApk myReceiverApk){
        ctx.registerReceiver(myReceiverApk, myIntentFilter);
    }

    public void DeleteRegister(MyReceiverApk myReceiverApk){
        ctx.unregisterReceiver(myReceiverApk);
    }
}
