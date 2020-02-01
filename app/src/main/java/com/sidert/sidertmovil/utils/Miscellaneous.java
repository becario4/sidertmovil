package com.sidert.sidertmovil.utils;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Environment;
import android.telecom.ConnectionService;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.database.DBhelper;
import com.sidert.sidertmovil.models.ModeloColonia;
import com.sidert.sidertmovil.models.ModeloEstados;
import com.sidert.sidertmovil.models.ModeloGeolocalizacion;
import com.sidert.sidertmovil.models.ModeloMunicipio;
import com.sidert.sidertmovil.models.ModeloOcupaciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    /* Retorna si es una ficha 2 = grupal o 1 = individual */
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
    public static String ObtenerFecha(String tipo_formato) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = null;
        switch (tipo_formato){
            case "timestamp":
                sdf = new SimpleDateFormat(Constants.FORMAT_TIMESTAMP);
                break;
            case "fecha":
                sdf = new SimpleDateFormat(Constants.FORMAT_DATE_GNRAL);
                break;
        }
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

    /* Genera la cantidad en letra  */
    public static String cantidadLetra(String s) {
        StringBuilder result = new StringBuilder();
        BigDecimal totalBigDecimal = new BigDecimal(s).setScale(2, BigDecimal.ROUND_DOWN);
        long parteEntera = totalBigDecimal.toBigInteger().longValue();
        int triUnidades      = (int)((parteEntera % 1000));
        int triMiles         = (int)((parteEntera / 1000) % 1000);
        int triMillones      = (int)((parteEntera / 1000000) % 1000);
        int triMilMillones   = (int)((parteEntera / 1000000000) % 1000);

        Log.v("triUnidades", String.valueOf(triUnidades));
        Log.v("triMiles", String.valueOf(triMiles));
        Log.v("triMillones", String.valueOf(triMillones));

        if (parteEntera == 0) {
            result.append("Cero ");
            return result.toString();
        }

        if (triMilMillones > 0) result.append(triTexto(triMilMillones).toString() + "Mil ");
        if (triMillones > 0)    result.append(triTexto(triMillones).toString());

        if (triMilMillones == 0 && triMillones == 1) result.append("Millón ");
        else if (triMilMillones > 0 || triMillones > 0) result.append("Millones ");

        if (triMiles > 0)       result.append(triTexto(triMiles).toString() + "Mil ");
        if (triUnidades > 0)    result.append(triTexto(triUnidades).toString());

        return result.toString();
    }

    private static StringBuilder triTexto(int n) {
        StringBuilder result = new StringBuilder();
        int centenas = n / 100;
        int decenas  = (n % 100) / 10;
        int unidades = (n % 10);

        Log.v("Centenas", String.valueOf(centenas));
        Log.v("Decenas", String.valueOf(decenas));
        Log.v("Unidades", String.valueOf(unidades));


        switch (centenas) {
            case 0: break;
            case 1:
                if (decenas == 0 && unidades == 0) {
                    result.append("Cien ");
                    return result;
                }
                else result.append("Ciento ");
                break;
            case 2: result.append("Doscientos "); break;
            case 3: result.append("Trescientos "); break;
            case 4: result.append("Cuatrocientos "); break;
            case 5: result.append("Quinientos "); break;
            case 6: result.append("Seiscientos "); break;
            case 7: result.append("Setecientos "); break;
            case 8: result.append("Ochocientos "); break;
            case 9: result.append("Novecientos "); break;
        }

        switch (decenas) {
            case 0: break;
            case 1:
                if (unidades == 0) { result.append("Diez "); return result; }
                else if (unidades == 1) { result.append("Once "); return result; }
                else if (unidades == 2) { result.append("Doce "); return result; }
                else if (unidades == 3) { result.append("Trece "); return result; }
                else if (unidades == 4) { result.append("Catorce "); return result; }
                else if (unidades == 5) { result.append("Quince "); return result; }
                else result.append("Dieci");
                break;
            case 2:
                if (unidades == 0) { result.append("Veinte "); return result; }
                else result.append("Veinti ");
                break;
            case 3: result.append("Treinta "); break;
            case 4: result.append("Cuarenta "); break;
            case 5: result.append("Cincuenta "); break;
            case 6: result.append("Sesenta "); break;
            case 7: result.append("Setenta "); break;
            case 8: result.append("Ochenta "); break;
            case 9: result.append("Noventa "); break;
        }

        if (decenas > 2 && unidades > 0)
            result.append("y ");



        switch (unidades) {
            case 0: break;
            case 1: result.append("Un "); break;
            case 2: result.append("Dos "); break;
            case 3: result.append("Tres "); break;
            case 4: result.append("Cuatro "); break;
            case 5: result.append("Cinco "); break;
            case 6: result.append("Seis "); break;
            case 7: result.append("Siete "); break;
            case 8: result.append("Ocho "); break;
            case 9: result.append("Nueve "); break;
        }

        return result;
    }

    public static String clvEstado (int pos_estado){
        String clv_estado = "";
        switch (pos_estado){
            case 1:
                clv_estado = "AS";
                break;
            case 2:
                clv_estado = "BC";
                break;
            case 3:
                clv_estado = "BS";
                break;
            case 4:
                clv_estado = "CC";
                break;
            case 5:
                clv_estado = "CL";
                break;
            case 6:
                clv_estado = "CM";
                break;
            case 7:
                clv_estado = "CS";
                break;
            case 8:
                clv_estado = "CH";
                break;
            case 9:
                clv_estado = "DF";
                break;
            case 10:
                clv_estado = "DG";
                break;
            case 11:
                clv_estado = "GT";
                break;
            case 12:
                clv_estado = "GR";
                break;
            case 13:
                clv_estado = "HG";
                break;
            case 14:
                clv_estado = "JC";
                break;
            case 15:
                clv_estado = "MC";
                break;
            case 16:
                clv_estado = "MN";
                break;
            case 17:
                clv_estado = "MS";
                break;
            case 18:
                clv_estado = "NT";
                break;
            case 19:
                clv_estado = "NL";
                break;
            case 20:
                clv_estado = "OC";
                break;
            case 21:
                clv_estado = "PL";
                break;
            case 22:
                clv_estado = "QT";
                break;
            case 23:
                clv_estado = "QR";
                break;
            case 24:
                clv_estado = "SP";
                break;
            case 25:
                clv_estado = "SL";
                break;
            case 26:
                clv_estado = "SR";
                break;
            case 27:
                clv_estado = "TC";
                break;
            case 28:
                clv_estado = "TS";
                break;
            case 29:
                clv_estado = "TL";
                break;
            case 30:
                clv_estado = "VZ";
                break;
            case 31:
                clv_estado = "YN";
                break;
            case 32:
                clv_estado = "ZS";
                break;


        }
        return clv_estado;
    }

    public static char segundaConsonante( String sApellido ) {
        char consonante = 'X';
        for(int i=1; i <= sApellido.length()-1; i++){
            Log.e("char", sApellido.charAt(i)+"");
            if(esVocal(sApellido.charAt(i))){
                if (String.valueOf(sApellido.charAt(i)).equals("Ñ"))
                    consonante = 'X';
                else
                    consonante = sApellido.charAt(i);
                break;
            }
        }

        return consonante;
    }

    private static boolean esVocal(char c){
        Log.e("Value",String.valueOf(!String.valueOf(c).contains("AEIOU ")));
        if ("AEIOU ".contains(String.valueOf(c))){
            return false;
        }else{
            return true;
        }
    }

    /* Genera la autorización para la servicios */
    public static String authorization (String user, String pass){
        String credential = user + ":" + pass;
        return "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
    }

    public static String validString (String str){
        if (str == null || str.isEmpty() || str.equals("null")) {
            return "";
        } else {
            return  str;
        }
    }

    /* Retorna la dirección de acorde una latitud y longitud */
    public static String ObtenerDireccion (Context ctx, double lat, double lng){
        String address=null;
        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
        List<Address> list = null;
        try{
            list = geocoder.getFromLocation(lat, lng, 5);
        } catch(Exception e){
            Log.e("Error_direccion", e.getMessage());
            e.printStackTrace();
        }
        if(list == null){
            System.out.println("Fail to get address from location");
            return "No se encontró la dirección";
        }
        if(list.size() > 0){
            Address addr = list.get(0);

            address =
                      validString(addr.getThoroughfare()) + " " +
                      validString(addr.getSubThoroughfare()) + ", " +
                      validString(addr.getSubLocality()) + ", " +
                      validString(addr.getLocality());

        }
        return address;
    }

    /* Retorna si un servicio está activo o desactivado */
    public static boolean JobServiceEnable (Context ctx, int id_job, String servicio){
        boolean flag = false;
        JobScheduler scheduler = (JobScheduler) ctx.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;
        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == id_job ) {
                Log.e("Servicio Activo", servicio);
                flag = true;
                break ;
            }
        }
        return flag;
    }

    public static ModeloGeolocalizacion.Integrante GetIntegrante (List<ModeloGeolocalizacion.Integrante> integrantes, String tipo){
        ModeloGeolocalizacion.Integrante integrante = null;

        for (int i = 0; i<integrantes.size(); i++){
            if (integrantes.get(i).getIntegranteTipo().equals(tipo)){
                integrante = integrantes.get(i);
                break;
            }
        }

        if (integrante == null){
            for (int i = 0; i<integrantes.size(); i++){
                if (integrantes.get(i).getIntegranteTipo().equals("PRESIDENTE")){
                    integrante = integrantes.get(i);
                    break;
                }
            }
        }
        return integrante;
    }

    public static String GetColoniaTesorera (List<ModeloGeolocalizacion.Integrante> integrantes){
        String colonia = "";
        for (int i = 0; i<integrantes.size(); i++){
            if (integrantes.get(i).getIntegranteTipo().equals("TESORERO")){
                colonia = integrantes.get(i).getClienteColonia();
                break;
            }
        }
        return colonia;
    }

    public static String JsonConvertGpo (ModeloGeolocalizacion.Grupale modelo){
        Log.v("JsonConvert", new GsonBuilder().create().toJson(modelo));
        return new GsonBuilder().create().toJson(modelo);
    }

    public static String JsonConvertInd (ModeloGeolocalizacion.Individuale modelo){
        Log.v("JsonConvert", new GsonBuilder().create().toJson(modelo));
        return new GsonBuilder().create().toJson(modelo);
    }

    public static JSONObject GetIntegrante (JSONArray integrantes, String tipo){
        JSONObject item = null;
        for (int i = 0; i < integrantes.length(); i++){
            try {
                item = integrantes.getJSONObject(i);
                if (item.getString(Constants.INTEGRANTE_TIPO).equals(tipo)){
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    /* Obtener la edad de acorde a una fecha seleccionada */
    public static String GetEdad (String fecha_nac){
        Date fechaNac=null;
        try {
            fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_nac);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar fechaActual = Calendar.getInstance();
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.setTime(fechaNac);

        int year = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
        if(mes<0 || (mes==0 && dia<0)){
            year--;
        }

        return String.valueOf(year);
    }

    /* Borra duplicados de tabla de Geolocalizacion  */
    public static boolean BorrarDuplicadosGeo (Context ctx){
        boolean flag = false;
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor rows = dBhelper.getRecords(Constants.GEOLOCALIZACION, "", " GROUP BY ficha_id, num_solicitud ORDER BY _id ASC", null);
        if (rows.getCount() > 0){
            //Log.e("Total GroupID", ""+rows.getCount());
            String ids = "";
            rows.moveToFirst();
            for(int i = 0; i < rows.getCount(); i++){
                //Log.e("Ficha"+rows.getString(0), rows.getString(1)+" "+rows.getString(5));
                String _id = rows.getString(0);
                String ficha_id = rows.getString(1);
                String num_solucitud = rows.getString(5);
                int status = rows.getInt(21);
                if (i == 0)
                    ids = _id;
                else
                    ids += ","+_id;
                Cursor fichas = dBhelper.getRecords(Constants.GEOLOCALIZACION, " WHERE _id <> '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", " ORDER BY _id ASC", null);
                if(fichas.getCount() > 0){
                    fichas.moveToFirst();
                    //Log.e("Total Iguales", ""+fichas.getCount());
                    for (int j = 0; j < fichas.getCount(); j++){
                        /*if (fichas.getString(1).equals("1137")) {
                            Log.e("Ficha " + j, "cliente: " + fichas.getString(4));
                            Log.e("Ficha " + j, "res_uno: " + fichas.getString(13));
                            Log.e("Ficha " + j, "res_dos: " + fichas.getString(14));
                            Log.e("Ficha " + j, "res_tres: " + fichas.getString(15));
                            Log.e("Ficha " + j, "fecha_uno: " + fichas.getString(16));
                            Log.e("Ficha " + j, "fecha_dos: " + fichas.getString(17));
                            Log.e("Ficha " + j, "fecha_tres: " + fichas.getString(18));
                            Log.e("--", "--------------------------------------------------------------------------------------------");
                        }*/
                        if (!fichas.getString(13).trim().isEmpty()){
                            ContentValues valores = new ContentValues();
                            valores.put("res_uno",fichas.getString(13));
                            db.update(Constants.GEOLOCALIZACION, valores, "_id = '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", null);
                        }

                        if (!fichas.getString(14).trim().isEmpty()){
                            ContentValues valores = new ContentValues();
                            valores.put("res_dos",fichas.getString(14));
                            db.update(Constants.GEOLOCALIZACION, valores, "_id = '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", null);
                        }

                        if (!fichas.getString(15).trim().isEmpty()){
                            ContentValues valores = new ContentValues();
                            valores.put("res_tres",fichas.getString(15));
                            db.update(Constants.GEOLOCALIZACION, valores, "_id = '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", null);
                        }

                        if (!fichas.getString(16).trim().isEmpty() && fichas.getString(16).length() > 1){
                            ContentValues valores = new ContentValues();
                            valores.put("fecha_env_uno",fichas.getString(16));
                            db.update(Constants.GEOLOCALIZACION, valores, "_id = '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", null);
                        }

                        if (!fichas.getString(17).trim().isEmpty() && fichas.getString(17).length() > 1){
                            ContentValues valores = new ContentValues();
                            valores.put("fecha_env_dos",fichas.getString(17));
                            db.update(Constants.GEOLOCALIZACION, valores, "_id = '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", null);
                        }

                        if (!fichas.getString(18).trim().isEmpty() && fichas.getString(18).length() > 1){
                            ContentValues valores = new ContentValues();
                            valores.put("fecha_env_tres",fichas.getString(18));
                            db.update(Constants.GEOLOCALIZACION, valores, "_id = '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", null);
                        }

                        if (fichas.getInt(21) > status){
                            status = fichas.getInt(21);
                            ContentValues valores = new ContentValues();
                            valores.put("status",fichas.getInt(21));
                            db.update(Constants.GEOLOCALIZACION, valores, "_id = '"+_id+"' AND ficha_id = '"+ficha_id+"' AND num_solicitud = '"+num_solucitud+"'", null);
                        }

                        //Log.e("Igual",fichas.getString(0)+" "+fichas.getString(1)+" "+fichas.getString(5));
                        fichas.moveToNext();
                    }
                }
                rows.moveToNext();
            }

            db.execSQL("DELETE FROM " + Constants.GEOLOCALIZACION + " WHERE _id NOT  IN "+"("+ids+")");

            //Log.e("IDS", "("+ids+")");
        }
        return flag;
    }

    /* Descarga una imagen de un URL */
    public static byte[] descargarImagen (String urlImage){
        URL imageUrl = null;
        Bitmap imagen = null;
        byte[] compressedByteArray = null;
        try{
            imageUrl = new URL(urlImage);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagen.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            compressedByteArray = stream.toByteArray();
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return compressedByteArray;
    }

    /* Obtención de catálogo de Estados */
    public static ArrayList<ModeloEstados> GetCatalogoEstado (Context ctx){
        ArrayList<ModeloEstados> catalogo = new ArrayList<>();
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row = dBhelper.getRecords(Constants.ESTADOS,""," ORDER BY estado_nombre ASC", null);

        ModeloEstados mEstados = new ModeloEstados();
        mEstados.setId(0);
        mEstados.setNombre("SELECCIONE UNA OPCIÓN");
        mEstados.setPaisId(0);
        catalogo.add(mEstados);

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                mEstados = new ModeloEstados();
                mEstados.setId(row.getInt(1));
                mEstados.setNombre(row.getString(2));
                mEstados.setPaisId(row.getInt(3));
                catalogo.add(mEstados);
                row.moveToNext();
            }

        }

        return catalogo;
    }

    /* Obtención de catálogo de Municipios */
    public static ArrayList<ModeloMunicipio> GetCatalogoMunicipios (Context ctx, int estado_id){
        ArrayList<ModeloMunicipio> catalogo = new ArrayList<>();
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row = dBhelper.getRecords(Constants.MUNICIPIOS," WHERE estado_id = '" + estado_id + "'"," ORDER BY municipio_nombre ASC", null);

        ModeloMunicipio mMunicipios = new ModeloMunicipio();
        mMunicipios.setId(0);
        mMunicipios.setNombre("SELECCIONE UNA OPCIÓN");
        mMunicipios.setEstadoId(0);
        catalogo.add(mMunicipios);

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                mMunicipios = new ModeloMunicipio();
                mMunicipios.setId(row.getInt(1));
                mMunicipios.setNombre(row.getString(2));
                mMunicipios.setEstadoId(row.getInt(3));
                catalogo.add(mMunicipios);
                row.moveToNext();
            }

        }

        return catalogo;
    }

    /* Obtención de catálogo de Colonia */
    public static ArrayList<ModeloColonia> GetCatalogoColonias (Context ctx, int municipio_id){
        ArrayList<ModeloColonia> catalogo = new ArrayList<>();
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row = dBhelper.getRecords(Constants.COLONIAS," WHERE municipio_id = '" + municipio_id + "'"," ORDER BY colonia_nombre ASC", null);

        ModeloColonia mColonia = new ModeloColonia();
        mColonia.setId(0);
        mColonia.setNombre("SELECCIONE UNA OPCIÓN");
        mColonia.setMunicipioId(0);
        catalogo.add(mColonia);

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                Log.e("Colonia", row.getString(2));
                mColonia = new ModeloColonia();
                mColonia.setId(row.getInt(1));
                mColonia.setNombre(row.getString(2));
                mColonia.setCp(row.getInt(3));
                mColonia.setMunicipioId(row.getInt(4));
                catalogo.add(mColonia);
                row.moveToNext();
            }
        }
        else
            Log.e("Colonias", "Sin registros");
        return catalogo;
    }

    /* Obtención de catálogo de Ocupaciones */
    public static ArrayList<ModeloOcupaciones> GetCatalogoOcupaciones (Context ctx){
        ArrayList<ModeloOcupaciones> catalogo = new ArrayList<>();
        DBhelper dBhelper = new DBhelper(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        Cursor row = dBhelper.getRecords(Constants.OCUPACIONES,""," ORDER BY ocupacion_nombre ASC", null);

        ModeloOcupaciones mOcupaciones = new ModeloOcupaciones();
        mOcupaciones.setId(0);
        mOcupaciones.setNombre("SELECCIONE UNA OPCIÓN");
        mOcupaciones.setClave("");
        mOcupaciones.setNivelRiesgo(0);
        mOcupaciones.setSectorId(0);

        catalogo.add(mOcupaciones);

        if (row.getCount() > 0){
            row.moveToFirst();
            for (int i = 0; i < row.getCount(); i++){
                mOcupaciones = new ModeloOcupaciones();
                mOcupaciones.setId(row.getInt(1));
                mOcupaciones.setNombre(row.getString(2));
                mOcupaciones.setClave(row.getString(3));
                mOcupaciones.setNivelRiesgo(row.getInt(4));
                mOcupaciones.setSectorId(row.getInt(5));
                catalogo.add(mOcupaciones);
                row.moveToNext();
            }

        }

        return catalogo;
    }

    /* Para saber si es vocal */
    public static boolean esVocal(Character texto){
        if (texto == 'a' || texto == 'e' || texto == 'i' || texto == 'o' || texto == 'u'
                || texto == 'A' || texto == 'E' || texto == 'I' || texto == 'O' || texto == 'U'
                || texto == 'Á' || texto == 'É' || texto == 'Í' || texto == 'Ó' || texto == 'Ú'
                || texto == 'á' || texto == 'é' || texto == 'í' || texto == 'ó' || texto == 'ú'
                || texto == '/' || texto == '-' ){
            return true;
        }else{
            return false;
        }
    }

    /* Generador de CURP */
    public static String GenerarCurp (HashMap<Integer, String> params){

        String nombreTexto = params.get(0).toUpperCase();

        nombreTexto = nombreTexto.replaceAll("\\bMARIA\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bJOSE\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bMA\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bMA\\.\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bJOSE\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bJ\\.\\s\\b","");

        String primerApellidoTexto = params.get(1).toUpperCase();
        primerApellidoTexto = primerApellidoTexto.replaceAll("/","X");
        primerApellidoTexto = primerApellidoTexto.replaceAll("-","X");

        String segundoApellidoTexto = params.get(2).toUpperCase();

        String fechaNacimientoTexto = params.get(3).toUpperCase();

        String fechaNacimientoTextotest = fechaNacimientoTexto;

        String sexoTexto = params.get(4);
        String estadoTexto = params.get(5);

        // Lista Resultado

        List<Character> listaResultado = new ArrayList<>();

        // String Resultado

        String resultado = "";

        // Verificando datos

        Log.e("Curp","Nombre: "+nombreTexto);
        Log.e("Curp","Paterno: "+primerApellidoTexto);
        Log.e("Curp","Materno: "+segundoApellidoTexto);
        Log.e("Curp","Fecha: "+fechaNacimientoTexto);
        Log.e("Curp","Genero: "+sexoTexto);
        Log.e("Curp","Estado: "+estadoTexto);

        boolean datosCorrectos = true;

        if (nombreTexto.isEmpty() || primerApellidoTexto.isEmpty() || fechaNacimientoTexto.isEmpty()
                || fechaNacimientoTexto.length() < 6 || sexoTexto.isEmpty() || estadoTexto.isEmpty()){
            datosCorrectos = false;
            resultado = "Curp no válida";

        }

        if (datosCorrectos && nombreTexto.length() > 3 && primerApellidoTexto.length() > 3){

            // Convirtiendo textos a Listas

            List<Character> nombreLista = new ArrayList<>();
            for (int i=0; i< nombreTexto.length();i++){
                nombreLista.add(nombreTexto.charAt(i));
            }

            List<Character> primerApellidoLista = new ArrayList<>();
            for (int i=0; i< primerApellidoTexto.length();i++){
                primerApellidoLista.add(primerApellidoTexto.charAt(i));
            }

            List<Character> segundoApellidoLista = new ArrayList<>();
            for (int i=0; i< segundoApellidoTexto.length();i++){
                segundoApellidoLista.add(segundoApellidoTexto.charAt(i));
            }

            // Calculando 4 primeros caracteres

            boolean segundaLetraConsonante = false;
            boolean noSegundoApellido = false;

            // ¿Consonante segunda letra?

            if (!(esVocal(primerApellidoLista.get(1)))){
                segundaLetraConsonante = true;
            }

            if (segundoApellidoTexto == ""){
                noSegundoApellido = true;
            }

            // Lista Resultado

            //primera letra: si no segunda componante -> dos primeras letras = dos primeras letras 1er apellido

            int posicionPrimeraVocal = 0;

            if (segundaLetraConsonante == false){
                listaResultado.add(primerApellidoLista.get(0));
                listaResultado.add(primerApellidoLista.get(1));
            }else{
                boolean romperIf = false;
                for (int i=0; i < primerApellidoLista.size();i++){
                    if(esVocal(primerApellidoLista.get(i)) && romperIf == false){
                        listaResultado.add(primerApellidoLista.get(i));
                        posicionPrimeraVocal = i;
                        romperIf = true;
                    }
                }
            }

            //segunda letra: buscamos la primera vocal interna del apellido

            if (listaResultado.size() == 1){
                for (int i=posicionPrimeraVocal+1 ; i < primerApellidoLista.size(); i++){
                    if(esVocal(primerApellidoLista.get(i)) && listaResultado.size() < 2){
                        listaResultado.add(primerApellidoLista.get(i));
                    }
                }
            }

            if(listaResultado.size()==1){
                listaResultado.add('X');
            }

            // tercera letra: inicial del segundo apellido

            if (segundoApellidoLista.isEmpty() == true){
                listaResultado.add('X');
            }else{
                listaResultado.add(segundoApellidoLista.get(0));
            }

            // cuarta letra: inicial del nombre

            listaResultado.add(nombreLista.get(0));

            // Fecha de nacimiento

            fechaNacimientoTextotest = fechaNacimientoTextotest.replaceAll("-","");
            fechaNacimientoTextotest = fechaNacimientoTextotest.replaceAll("/","");

            if (fechaNacimientoTextotest.length() > 0){
                listaResultado.add(fechaNacimientoTextotest.charAt(2));
                listaResultado.add(fechaNacimientoTextotest.charAt(3));
                listaResultado.add(fechaNacimientoTextotest.charAt(4));
                listaResultado.add(fechaNacimientoTextotest.charAt(5));
                listaResultado.add(fechaNacimientoTextotest.charAt(6));
                listaResultado.add(fechaNacimientoTextotest.charAt(7));
            }
            /*for (int i=0;i<fechaNacimientoTextotest.length();i++){
                listaResultado.add(fechaNacimientoTextotest.charAt(i));
            }*/

            // Sexo

            listaResultado.add(sexoTexto.charAt(0));

            // Estado

            String codigoEstado = "";

            switch (estadoTexto){
                case "EXTRANJERO": codigoEstado="NE"; break;
                case "AGUASCALIENTES": codigoEstado="AS" ; break;
                case "BAJA CALIFORNIA": codigoEstado="BC" ; break;
                case "BAJA CALIFORNIA SUR": codigoEstado="BS" ; break;
                case "CAMPECHE": codigoEstado="CC" ; break;
                case "CHIAPAS": codigoEstado="CS" ; break;
                case "CHIHUAHUA": codigoEstado="CH" ; break;
                case "COAHUILA": codigoEstado="CL" ; break;
                case "COLIMA": codigoEstado="CM" ; break;
                case "CIUDAD DE MÉXICO": codigoEstado="DF" ; break;
                case "DURANGO": codigoEstado="DG" ; break;
                case "GUANAJUATO": codigoEstado="GT" ; break;
                case "GUERRERO": codigoEstado="GR" ; break;
                case "HIDALGO": codigoEstado="HG" ; break;
                case "JALISCO": codigoEstado="JC" ; break;
                case "ESTADO DE MÉXICO": codigoEstado="MC" ; break;
                case "MICHOACÁN": codigoEstado="MN" ; break;
                case "MORELOS": codigoEstado="MS" ; break;
                case "NAYARIT": codigoEstado="NT" ; break;
                case "NUEVO LEÓN": codigoEstado="NL" ; break;
                case "OAXACA": codigoEstado="OC" ; break;
                case "PUEBLA": codigoEstado="PL" ; break;
                case "QUERÉTARO": codigoEstado="QO" ; break;
                case "QUINTANA ROO": codigoEstado="QR" ; break;
                case "SAN LUIS POTOSI": codigoEstado="SP" ; break;
                case "SINALOA": codigoEstado="SL" ; break;
                case "SONORA": codigoEstado="SR" ; break;
                case "TABASCO": codigoEstado="TC" ; break;
                case "TAMAULIPAS": codigoEstado="TS" ; break;
                case "TLAXCALA": codigoEstado="TL" ; break;
                case "VERACRUZ": codigoEstado="VZ" ; break;
                case "YUCATÁN ": codigoEstado="YN" ; break;
                case "ZACATECAS": codigoEstado="ZS" ; break;
            }

            listaResultado.add(codigoEstado.charAt(0));
            listaResultado.add(codigoEstado.charAt(1));

            // Consonantes internas de nombre y apellidos

            // Consonante interna primer apellido

            for(int i=1; i < primerApellidoLista.size() ; i++){
                if(esVocal(primerApellidoLista.get(i)) == false){
                    listaResultado.add(primerApellidoLista.get(i));
                    break;
                }
            }

            if(listaResultado.size() < 14){
                listaResultado.add('X');
            }

            // Consonante interna segundo apellido

            for(int i=1; i < segundoApellidoLista.size() ; i++){
                if(segundoApellidoLista.isEmpty()){
                    listaResultado.add('X');
                    break;
                }

                if(esVocal(segundoApellidoLista.get(i)) == false){
                    listaResultado.add(segundoApellidoLista.get(i));
                    break;
                }
            }

            if (listaResultado.size() < 15){
                listaResultado.add('X');
            }

            // Consonante interna nombre

            for(int i=1; i < nombreLista.size() ; i++){
                if(esVocal(nombreLista.get(i)) == false){
                    listaResultado.add(nombreLista.get(i));
                    break;
                }
            }

            if (listaResultado.size()<16){
                listaResultado.add('X');
            }


            StringBuilder builder = new StringBuilder(listaResultado.size());
            for(Character ch: listaResultado)
            {
                builder.append(ch);
            }

            String listaResultadoTexto = builder.toString();

            listaResultadoTexto = listaResultadoTexto.replaceAll("Ñ","X");
            listaResultadoTexto = listaResultadoTexto.replaceAll("\\-","X");
            listaResultadoTexto = listaResultadoTexto.replaceAll("\\.", "X");
            listaResultadoTexto = listaResultadoTexto.replace('Ä','A')
                    .replace('Ë','E')
                    .replace('Ï','I')
                    .replace('Ö','O')
                    .replace('Ü','U');
            listaResultadoTexto = listaResultadoTexto.replaceAll(" ","");

            resultado = listaResultadoTexto;

        }
        else
            resultado = "Curp no válida";

        return resultado;
    }
}
