package com.sidert.sidertmovil.utils;


import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class Miscellaneous {

    /*Validación de que no sea null ni vacio y colocar primera leta mayúscula*/
    public static String ucFirst(String str) {
        if (str.equals("null") || str.isEmpty()) {
            return "";
        } else {
            return  Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
        }
    }

    /* Obtener que tipo de orden */
    public static String getTypeOrderValue(String str) {
        String type;
        if (str.contains("ri") || str.contains("rg"))
        {
            type = Constants.RECOVERY;
        }
        else if (str.contains("cvi") || str.contains("cvg"))
        {
            type = Constants.WALLET_EXPIRED;
        }
        else if (str.contains("ci") || str.contains("cg"))
        {
            type = Constants.COLLECTION;
        }
        else{
            type = Constants.ERROR;
        }
        return type;
    }

    public static int getIndorGpo(String str) {
        Log.v("Formulario", str);
        int res;

        if (str.contains("ri") || str.contains("ci") || str.contains("cvi"))
            res =  1;
        else if (str.contains("rg") || str.contains("cg") || str.contains("cvg"))
            res = 2;
        else
            res = 0;

        return res;

    }

    /* Obtener que tipo de orden */
    public static String getTableLog(String str) {
        String table;
        if (str.contains("ri") || str.contains("rg") || str.contains("ci") || str.contains("cg"))
        {
            table = Constants.LOG_ASESSOR;
        }
        else if (str.contains("cvi") || str.contains("cvg"))
        {
            table = Constants.LOG_MANAGER;
        }
        else{
            table = Constants.ERROR;
        }
        return table;
    }

    /* Descomponer json para obteción de registros de DB  */
    public static String readJson (JSONObject json) throws JSONException {
        String conditionals = "";
        JSONArray jsonWhere =(JSONArray) json.get(Constants.WHERE);
        JSONArray jsonOrder =(JSONArray) json.get(Constants.ORDER);
        if (jsonWhere.length() > 0)
        {
            conditionals = " WHERE ";
            for (int i = 0; i < jsonWhere.length(); i++)
            {
                JSONObject item = jsonWhere.getJSONObject(i);

                if (i == 0)
                {
                    conditionals +=  item.getString(Constants.KEY)+ " = " + item.getString(Constants.VALUE);
                }
                else {
                    conditionals += " AND " + item.getString(Constants.KEY)+ " = " + item.getString(Constants.VALUE);
                }
            }
        }

        if (jsonOrder.length() > 0)
        {
            conditionals = (jsonWhere.length() > 0)?conditionals:"";

            for (int i = 0; i < jsonOrder.length(); i++)
            {
                if (i == 0)
                {
                    conditionals +=  " ORDER BY " + jsonOrder.get(i);
                }
                else if(i > 0 && i < jsonOrder.length() - 1){
                    conditionals += ", " + jsonOrder.get(i);
                }
                else {
                    conditionals +=  jsonOrder.get(i);
                }
            }
        }
        return conditionals;
    }

    /* Obtiene el contenido de un archivo */
    public static String loadSettingFile(String fileName) {
        String text = "";
        int rin;
        char [] buf = new char[128];
        try
        {
            FileReader fReader = new FileReader(fileName);
            rin = fReader.read(buf);
            if(rin > 0)
            {
                text = new String(buf,0,rin);
            }
            fReader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            //Log.i(TAG, "Connection history not exists.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            //Log.e(TAG, e.getMessage(), e);
        }

        return text;
    }

    /*Generar formato de moneda*/
    public static String moneyFormat(String money) {
        String currency;
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        currency = format.format(Double.parseDouble(money));

        return currency;
    }

    /*Generar a formato double*/
    public static  double doubleFormat (EditText et){
        String money = et.getText().toString();
        return Double.parseDouble(money.replace("$","").replace(",",""));
    }

    /* Obtener fecha actual */
    public static String ObtenerFecha() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_TIMESTAMP);
        return sdf.format(cal.getTime());
    }

    /* Guardado de fotos (fachada, tickets, firma) */
    public static String save(byte[] bytes, int tipo_img) throws IOException {
        String path = "";
        String name = UUID.randomUUID().toString() + ".jpg";
        File tempDir;
        switch (tipo_img){
            case 1:      //Fachada
                tempDir = new File(Constants.ROOT_PATH+"Fachada");
                if(!tempDir.exists())
                {
                    Log.v("Carpeta", "No existe Fachada");
                    tempDir.mkdir();
                }
                path = Constants.ROOT_PATH+"Fachada/" +  name;
                break;
            case 2:      //Evidencia
                tempDir = new File(Constants.ROOT_PATH+"Evidencia");
                if(!tempDir.exists())
                {
                    Log.v("Carpeta", "No existe Evidencia");
                    tempDir.mkdir();
                }
                path = Constants.ROOT_PATH+"Evidencia/" +  name;
                break;
            case 3:     // Firma
                tempDir = new File(Constants.ROOT_PATH+"Firma");
                if(!tempDir.exists())
                {
                    Log.v("Carpeta", "No existe Firma");
                    tempDir.mkdir();
                }
                path = Constants.ROOT_PATH+"Firma/" + name;
                break;
        }

        File file = new File(path);

        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        }finally {
            if(outputStream != null)
                outputStream.close();
        }

        return name;
    }


}
