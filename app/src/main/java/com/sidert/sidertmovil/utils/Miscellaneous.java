package com.sidert.sidertmovil.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sidert.sidertmovil.database.DBhelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
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

import androidx.appcompat.app.AppCompatActivity;

import static com.sidert.sidertmovil.utils.Constants.COLONIAS;
import static com.sidert.sidertmovil.utils.Constants.ESTADOS;
import static com.sidert.sidertmovil.utils.Constants.FECHA;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_DATE_GNRAL;
import static com.sidert.sidertmovil.utils.Constants.FORMAT_TIMESTAMP;
import static com.sidert.sidertmovil.utils.Constants.LOCALIDADES;
import static com.sidert.sidertmovil.utils.Constants.MUNICIPIOS;
import static com.sidert.sidertmovil.utils.Constants.OCUPACIONES;
import static com.sidert.sidertmovil.utils.Constants.SECTORES;
import static com.sidert.sidertmovil.utils.Constants.TBL_ARQUEO_CAJA_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_CATALOGOS_CAMPANAS;
import static com.sidert.sidertmovil.utils.Constants.TBL_CREDITO_IND_REN;
import static com.sidert.sidertmovil.utils.Constants.TBL_DATOS_ENTREGA_CARTERA;
import static com.sidert.sidertmovil.utils.Constants.TBL_DESTINOS_CREDITO;
import static com.sidert.sidertmovil.utils.Constants.TBL_ESTADOS_CIVILES;
import static com.sidert.sidertmovil.utils.Constants.TBL_IDENTIFICACIONES_TIPO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MEDIOS_CONTACTO;
import static com.sidert.sidertmovil.utils.Constants.TBL_MEDIOS_PAGO_ORI;
import static com.sidert.sidertmovil.utils.Constants.TBL_MIEMBROS_PAGOS_T;
import static com.sidert.sidertmovil.utils.Constants.TBL_NIVELES_ESTUDIOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_PARENTESCOS;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECIBOS_AGF_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_RECUPERACION_RECIBOS_CC;
import static com.sidert.sidertmovil.utils.Constants.TBL_VIVIENDA_TIPOS;
import static com.sidert.sidertmovil.utils.Constants.TIMESTAMP;


public class Miscellaneous extends AppCompatActivity {

    static {
        GSON = new GsonBuilder().create();
    }

    private static final Gson GSON;

    /*Validación de que no sea null ni vacio y colocar primera leta mayúscula*/
    public static String ucFirst(String str) {
        if (str.equals("null") || str.isEmpty()) {
            return "";
        } else {
            return  Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
        }
    }

    /*Validación de que no sea null ni vacio*/
    public static String validStr(Object str) {
        if (str == null ||str.equals("null")) {
            return "";
        } else {
            return String.valueOf(str).trim();
        }
    }

    /*Validación de que no sea null ni vacio*/
    public static Integer validInt(Object str) {
        if (str == null) {
            return 0;
        } else {
            return Integer.parseInt(String.valueOf(str));
        }
    }

    /*Validación de que no sea null ni vacio*/
    public static Double validDbl(Object str) {
        if (str == null) {
            return 0.0;
        } else {
            return Double.parseDouble(String.valueOf(str));
        }
    }

    /*Generar formato de moneda*/
    public static String moneyFormat(String money) {
        String currency;
        if (money.isEmpty())
            money = "0";
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        currency = format.format(Double.parseDouble(money));

        return currency;
    }

    /*Generar a formato double TextView*/
    public static  double doubleFormatTV (TextView tv){
        String money = tv.getText().toString();
        return Double.parseDouble(money.replace("$","").replace(",",""));
    }

    /* Obtener fecha actual */
    public static String ObtenerFecha(String tipo_formato) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = null;
        switch (tipo_formato){
            case "timestamp":
                sdf = new SimpleDateFormat(FORMAT_TIMESTAMP);
                break;
            case "fecha":
                sdf = new SimpleDateFormat(FORMAT_DATE_GNRAL);
                break;
            case "fecha_atraso":
                sdf = new SimpleDateFormat("dd-MM-yyyy");
                break;
        }
        //Log.e("fecha_hoy",sdf.format(cal.getTime()));
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
            case 4:     // Documentos
                tempDir = new File(Constants.ROOT_PATH+"Documentos");
                if(!tempDir.exists())
                {
                    Log.v("Carpeta", "No existe Documentos");
                    tempDir.mkdir();
                }
                path = Constants.ROOT_PATH+"Documentos/" + name;
                break;
            case 5:     // Cierre de dia
                tempDir = new File(Constants.ROOT_PATH+"CierreDia");
                if(!tempDir.exists())
                {
                    Log.v("Carpeta", "No existe CierreDia");
                    tempDir.mkdir();
                }
                path = Constants.ROOT_PATH+"CierreDia/" + name;
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

    /* Convertir de URI a byte[] */
    public static byte[] getBytesUri (Context ctx, Uri uri_img, int tipo_imagen){
        byte[] compressedByteArray = null;

        switch (tipo_imagen){
            case 0: //Fachada
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver() , uri_img);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    compressedByteArray = stream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1: //Evidencia
                try {
                    InputStream iStream =   ctx.getContentResolver().openInputStream(uri_img);
                    ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];

                    int len = 0;
                    while ((len = iStream.read(buffer)) != -1) {
                        byteBuffer.write(buffer, 0, len);
                    }
                    compressedByteArray = byteBuffer.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return compressedByteArray;
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

    /* Genera la autorización para la servicios */
    public static String authorization (String user, String pass){
        String credential = user + ":" + pass;
        return "Basic " + Base64.encodeToString(credential.getBytes(), Base64.NO_WRAP);
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
                      validStr(addr.getThoroughfare()) + " " +
                      validStr(addr.getSubThoroughfare()) + ", " +
                      validStr(addr.getSubLocality()) + ", " +
                      validStr(addr.getLocality());

        }
        return address;
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


    public static String GetEdad2 (String fecha_nac){
        Date fechaNac=null;
        try {
            fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_nac);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar fechaActual = Calendar.getInstance();
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.setTime(fechaNac);

        int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);
        int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
        int year = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);


        if(mes<0 || (mes==0 && dia<0)){
            year--;
        }

        return String.valueOf(year);
    }

    /* Descarga una imagen de un URL */
    public static byte[] descargarImagen (String urlImage){
        Log.e("UrlImage", urlImage);
        URL imageUrl = null;
        Bitmap imagen = null;
        byte[] compressedByteArray = null;
        try{
            imageUrl = new URL(urlImage);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagen.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            compressedByteArray = stream.toByteArray();
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return compressedByteArray;
    }

    public static String getipoClienteCreditoA(String tipoCliente){
        String tipo = " ";
        if(tipoCliente.equals("1")){
            tipo = "CREDITO INDIVIDUAL";
        }else if(tipoCliente.equals("2")){
            tipo ="CREDITO GRUPAL";
        }
        return tipo;
    }

    public static String Rango(int dias){
        String rango = "";
        if (dias <= 0)
            rango = "0 Días";
        else if(dias >= 1 && dias <= 7)
            rango = "1 - 7 Días";
        else if (dias >= 8 && dias <= 15)
            rango = "8 - 15 Días";
        else if (dias >= 16 && dias <= 30)
            rango = "16 - 30 Días";
        else if (dias >= 31 && dias <= 60)
            rango = "31 - 60 Días";
        else if (dias >= 61 && dias <= 90)
            rango = "61 - 90 Días";
        else if (dias >= 91 && dias <= 120)
            rango = "91 - 120 Días";
        else
            rango = "Más de 120 Días";

        return rango;
    }

    public static String DiaSemana(String fecha){
        String[] fechaDes = fecha.split("-");
        Calendar c = Calendar.getInstance();

        c.set(Integer.valueOf(fechaDes[0]), (Integer.valueOf(fechaDes[1]) - 1), Integer.valueOf(fechaDes[2]));
        int nD=c.get(Calendar.DAY_OF_WEEK);
        String diaDesembolso = "";
        switch (nD){
            case 1: diaDesembolso = "DOMINGO";
                break;
            case 2: diaDesembolso = "LUNES";
                break;
            case 3: diaDesembolso = "MARTES";
                break;
            case 4: diaDesembolso = "MIÉRCOLES";
                break;
            case 5: diaDesembolso = "JUEVES";
                break;
            case 6: diaDesembolso = "VIERNES";
                break;
            case 7: diaDesembolso = "SÁBADO";
                break;
        }
        return diaDesembolso;
    }

    /* Para saber si es vocal */
    public static boolean esVocal(Character texto){
        Log.e("TEXTO", texto.toString());
        if (texto == 'a' || texto == 'e'|| texto == 'i' || texto == 'o' || texto == 'u'
                || texto == 'A' || texto == 'E' || texto == 'I' || texto == 'O' || texto == 'U'
                || texto == 'Á' || texto == 'É' || texto == 'Í' || texto == 'Ó' || texto == 'Ú'
                || texto == 'á' || texto == 'é' || texto == 'í' || texto == 'ó' || texto == 'ú'
                || texto == '/' || texto == '-' ){
            return true;
        }else{
            return false;
        }
    }

    public static Boolean IsCurrentWeek(String fechaVenci){

        boolean res = false;

        try {

            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVenci);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(ObtenerFecha(FECHA.toLowerCase()));

            int dias_atraso = (int) ((date2.getTime()-date1.getTime())/86400000);

            res = dias_atraso <= 0;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return res;
    }

    /* Generador de CURP */
    public static String GenerarCurp (HashMap<Integer, String> params){

        String nombreTexto = RemoveTildes(params.get(0).toUpperCase());

        nombreTexto = nombreTexto.replaceAll("\\bMARIA\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bMARÍA\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bJOSE\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bMA\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bMA\\.\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bJOSE\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bJOSÉ\\s\\b","");
        nombreTexto = nombreTexto.replaceAll("\\bJ\\.\\s\\b","");

        String primerApellidoTexto = RemoveTildes(params.get(1).toUpperCase().replace("Ñ","X"));
        primerApellidoTexto = primerApellidoTexto.replaceAll("/","X");
        primerApellidoTexto = primerApellidoTexto.replaceAll("-","X");

        primerApellidoTexto = (!primerApellidoTexto.trim().isEmpty())?primerApellidoTexto:"XXXX";

        String segundoApellidoTexto = RemoveTildes(params.get(2).toUpperCase().replace("Ñ","X"));
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
            resultado = "";

        }

        if (datosCorrectos && nombreTexto.length() > 3 && primerApellidoTexto.length() > 2){

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
                if (!esVocal(primerApellidoLista.get(0))){
                    listaResultado.add(primerApellidoLista.get(0));
                    for (int i=0; i < primerApellidoLista.size();i++){
                        if(esVocal(primerApellidoLista.get(i))){
                            listaResultado.add(primerApellidoLista.get(i));
                            break;
                        }
                    }

                }
                else{
                    boolean primerletra = true;
                    boolean primervocal = true;
                    for (int i=0; i < primerApellidoLista.size();i++){
                        Log.e("Letra--", primerApellidoLista.get(i).toString());
                        Log.e("Letra--", ""+esVocal(primerApellidoLista.get(i)));
                        if(primerletra){
                            listaResultado.add(primerApellidoLista.get(i));
                            primerletra = false;
                        }
                        else if (primervocal){
                            if(esVocal(primerApellidoLista.get(i))){
                                listaResultado.add(primerApellidoLista.get(i));
                                primervocal = false;
                            }
                        }

                    }

                }
            }

            //segunda letra: buscamos la primera vocal interna del apellido

            /*if (listaResultado.size() == 1){
                for (int i = 0 ; i < primerApellidoLista.size(); i++){
                    if(esVocal(primerApellidoLista.get(i)) && listaResultado.size() < 2){
                        listaResultado.add(primerApellidoLista.get(i));
                    }
                }
            }*/

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
                case "CIUDAD DE MEXICO": codigoEstado="DF" ; break;
                case "COAHUILA DE ZARAGOZA": codigoEstado="CL" ; break;
                case "COLIMA": codigoEstado="CM" ; break;
                case "DURANGO": codigoEstado="DG" ; break;
                case "GUANAJUATO": codigoEstado="GT" ; break;
                case "GUERRERO": codigoEstado="GR" ; break;
                case "HIDALGO": codigoEstado="HG" ; break;
                case "JALISCO": codigoEstado="JC" ; break;
                case "MEXICO": codigoEstado="MC" ; break;
                case "MICHOACAN DE OCAMPO": codigoEstado="MN" ; break;
                case "MORELOS": codigoEstado="MS" ; break;
                case "NAYARIT": codigoEstado="NT" ; break;
                case "NUEVO LEON": codigoEstado="NL" ; break;
                case "OAXACA": codigoEstado="OC" ; break;
                case "PUEBLA": codigoEstado="PL" ; break;
                case "QUERETARO": codigoEstado="QO" ; break;
                case "QUINTANA ROO": codigoEstado="QR" ; break;
                case "SAN LUIS POTOSI": codigoEstado="SP" ; break;
                case "SINALOA": codigoEstado="SL" ; break;
                case "SONORA": codigoEstado="SR" ; break;
                case "TABASCO": codigoEstado="TC" ; break;
                case "TAMAULIPAS": codigoEstado="TS" ; break;
                case "TLAXCALA": codigoEstado="TL" ; break;
                case "VERACRUZ": codigoEstado="VZ" ; break;
                case "YUCATAN": codigoEstado="YN" ; break;
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
            listaResultadoTexto = listaResultadoTexto.replace("Ä","A")
                    .replace("Ë","E")
                    .replace("Ï","I")
                    .replace("Ö","O")
                    .replace("Ü","U");
            listaResultadoTexto = listaResultadoTexto.replaceAll(" ","");

            resultado = listaResultadoTexto;

        }
        else
            resultado = "";

        return resultado;
    }

    public static String RemoveTildes(String str){
        String cadenaNormalize = Normalizer.normalize(str, Normalizer.Form.NFD);
        String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
        return cadenaSinAcentos;
    }

    public static String RemoveTildesVocal(String str){
        String cadenaSinAcentos = str.toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");
        return cadenaSinAcentos;
    }

    public static String GetPlazo (Integer p){
        String plazo = "";
        switch (p){
            case 4:
                plazo = "4 MESES";
                break;
            case 5:
                plazo = "5 MESES";
                break;
            case 6:
                plazo = "6 MESES";
                break;
            case 9:
                plazo = "9 MESES";
                break;
        }
        return plazo;
    }

    public static Integer GetPlazo (String plazo){
        int no_plazo = 0;
        switch (plazo){
            case "4 MESES":
                no_plazo = 4;
                break;
            case "5 MESES":
                no_plazo = 5;
                break;
            case "6 MESES":
                no_plazo = 6;
                break;
            case "9 MESES":
                no_plazo = 9;
                break;
        }
        return no_plazo;
    }

    public static Integer GetPeriodicidad (String periodo){
        int periodicidad = 0;
        switch (periodo){
            case "SEMANAL":
                periodicidad = 7;
                break;
            case "CATORCENAL":
                periodicidad = 14;
                break;
            case "QUINCENAL":
                periodicidad = 15;
                break;
            case "MENSUAL":
                periodicidad = 30;
                break;
        }
        return periodicidad;
    }

    public static String GetPeriodicidad (Integer p){
        String periodicidad = "";
        switch (p){
            case 7:
                periodicidad = "SEMANAL";
                break;
            case 14:
                periodicidad = "CATORCENAL";
                break;
            case 15:
                periodicidad = "QUINCENAL";
                break;
            case 30:
                periodicidad = "MENSUAL";
                break;
        }
        return periodicidad;
    }

    public static Integer GetRiesgo (String riesgo){
        int riesgo_id = 0;
        switch (riesgo){
            case "ALTO":
                riesgo_id = 3;
                break;
            case "MEDIO":
                riesgo_id = 2;
                break;
            case "BAJO":
                riesgo_id = 1;
                break;
        }
        return riesgo_id;
    }

    public static String GetRiesgo (int riesgo_id){
        String riesgo = "";
        switch (riesgo_id){
            case 3:
                riesgo = "ALTO";
                break;
            case 2:
                riesgo = "MEDIO";
                break;
            case 1:
                riesgo = "BAJO";
                break;
        }
        return riesgo;
    }

    public static boolean CurpValidador(String curp){
        String diccionario = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        String curp17 = curp.substring(0,17);
        Log.e("Curp17", curp17);
        double lngSuma = 0.0;
        double lngDigito = 0.0;
        for (int i = 0; i < 17; i++){
            lngSuma = lngSuma + diccionario.indexOf(curp17.charAt(i)) * (18 - i);
        }
        lngDigito = 10 - lngSuma % 10;
        if (lngDigito == 10) lngDigito = 0;

        Log.e("CurpValidator", String.valueOf(lngDigito));
        //Log.e("CurpValidator", String.valueOf(Integer.parseInt(curp.substring(17,18))));
        Log.e("CurpValidator", String.valueOf(lngDigito == Integer.parseInt(curp.substring(17,18))));

        if (lngDigito == Integer.parseInt(curp.substring(17,18)))
            return true;
        else
            return false;
    }
    
    public static String GenerarRFC (String rfc, String nombre, String ap_paterno, String ap_materno){
        String nombre_completo = RemoveTildes(ap_paterno.trim()) + " " + RemoveTildes(ap_materno.trim()) + " " + RemoveTildes(nombre.trim());
        Log.e("nombre", nombre_completo);
        String numero = "0";
        String letra;
        String numero1 = null;
        String numero2 = null;
        int numeroSuma = 0;
        //alert(nombre_completo);
        //alert(nombre_completo.length);
        for (int i = 0; i < nombre_completo.length(); i++) {
            Log.e("letra",String.valueOf(nombre_completo.charAt(i)));
            letra = String.valueOf(nombre_completo.charAt(i)).toLowerCase();
            switch (letra) {
                case "ñ":
                    numero = numero + "10";
                    break;
                case "ü":
                    numero = numero + "10";
                    break;
                case "a":
                    numero = numero + "11";
                    break;
                case "b":
                    numero = numero + "12";
                    break;
                case "c":
                    numero = numero + "13";
                    break;
                case "d":
                    numero = numero + "14";
                    break;
                case "e":
                    numero = numero + "15";
                    break;
                case "f":
                    numero = numero + "16";
                    break;
                case "g":
                    numero = numero + "17";
                    break;
                case "h":
                    numero = numero + "18";
                    break;
                case "i":
                    numero = numero + "19";
                    break;
                case "j":
                    numero = numero + "21";
                    break;
                case "k":
                    numero = numero + "22";
                    break;
                case "l":
                    numero = numero + "23";
                    break;
                case "m":
                    numero = numero + "24";
                    break;
                case "n":
                    numero = numero + "25";
                    break;
                case "o":
                    numero = numero + "26";
                    break;
                case "p":
                    numero = numero + "27";
                    break;
                case "q":
                    numero = numero + "28";
                    break;
                case "r":
                    numero = numero + "29";
                    break;
                case "s":
                    numero = numero + "32";
                    break;
                case "t":
                    numero = numero + "33";
                    break;
                case "u":
                    numero = numero + "34";
                    break;
                case "v":
                    numero = numero + "35";
                    break;
                case "w":
                    numero = numero + "36";
                    break;
                case "x":
                    numero = numero + "37";
                    break;
                case "y":
                    numero = numero + "38";
                    break;
                case "z":
                    numero = numero + "39";
                    break;
                case " ":
                    numero = numero + "00";
                    break;
            }
        }
        Log.e("xxxxx", numero);
        //alert(numero);
        for (int i = 0; i < numero.length() + 1; i++) {
            //Log.e("iteraciones", ""+(i+1));
            try{
                numero1 = numero.substring(i, i+2);
            }
            catch (Exception e){
                numero1 = "0";
            }

            try{
                numero2 = numero.substring(i + 1, i+2);
            }
            catch (Exception e){
                numero2 = "0";
            }
            try{
                numeroSuma = numeroSuma + (Integer.parseInt(numero1) * Integer.parseInt(numero2));
            }
            catch (Exception e){}
        }

        double numero3 = numeroSuma % 1000;
        double numero4 = numero3 / 34;
        String numero5 = String.valueOf(numero4).replace(".",",").split(",")[0];
        double numero6 = numero3 % 34;
        String homonimio = "";
        switch (numero5) {
            case "0":
                homonimio = "1";
                break;
            case "1":
                homonimio = "2";
                break;
            case "2":
                homonimio = "3";
                break;
            case "3":
                homonimio = "4";
                break;
            case "4":
                homonimio = "5";
                break;
            case "5":
                homonimio = "6";
                break;
            case "6":
                homonimio = "7";
                break;
            case "7":
                homonimio = "8";
                break;
            case "8":
                homonimio = "9";
                break;
            case "9":
                homonimio = "A";
                break;
            case "10":
                homonimio = "B";
                break;
            case "11":
                homonimio = "C";
                break;
            case "12":
                homonimio = "D";
                break;
            case "13":
                homonimio = "E";
                break;
            case "14":
                homonimio = "F";
                break;
            case "15":
                homonimio = "G";
                break;
            case "16":
                homonimio = "H";
                break;
            case "17":
                homonimio = "I";
                break;
            case "18":
                homonimio = "J";
                break;
            case "19":
                homonimio = "K";
                break;
            case "20":
                homonimio = "L";
                break;
            case "21":
                homonimio = "M";
                break;
            case "22":
                homonimio = "N";
                break;
            case "23":
                homonimio = "P";
                break;
            case "24":
                homonimio = "Q";
                break;
            case "25":
                homonimio = "R";
                break;
            case "26":
                homonimio = "S";
                break;
            case "27":
                homonimio = "T";
                break;
            case "28":
                homonimio = "U";
                break;
            case "29":
                homonimio = "V";
                break;
            case "30":
                homonimio = "W";
                break;
            case "31":
                homonimio = "X";
                break;
            case "32":
                homonimio = "Y";
                break;
            case "33":
                homonimio = "Z";
                break;

        }

        switch ((int)numero6) {
            case 0:
                homonimio = homonimio + "1";
                break;
            case 1:
                homonimio = homonimio + "2";
                break;
            case 2:
                homonimio = homonimio + "3";
                break;
            case 3:
                homonimio = homonimio + "4";
                break;
            case 4:
                homonimio = homonimio + "5";
                break;
            case 5:
                homonimio = homonimio + "6";
                break;
            case 6:
                homonimio = homonimio + "7";
                break;
            case 7:
                homonimio = homonimio + "8";
                break;
            case 8:
                homonimio = homonimio + "9";
                break;
            case 9:
                homonimio = homonimio + "A";
                break;
            case 10:
                homonimio = homonimio + "B";
                break;
            case 11:
                homonimio = homonimio + "C";
                break;
            case 12:
                homonimio = homonimio + "D";
                break;
            case 13:
                homonimio = homonimio + "E";
                break;
            case 14:
                homonimio = homonimio + "F";
                break;
            case 15:
                homonimio = homonimio + "G";
                break;
            case 16:
                homonimio = homonimio + "H";
                break;
            case 17:
                homonimio = homonimio + "I";
                break;
            case 18:
                homonimio = homonimio + "J";
                break;
            case 19:
                homonimio = homonimio + "K";
                break;
            case 20:
                homonimio = homonimio + "L";
                break;
            case 21:
                homonimio = homonimio + "M";
                break;
            case 22:
                homonimio = homonimio + "N";
                break;
            case 23:
                homonimio = homonimio + "P";
                break;
            case 24:
                homonimio = homonimio + "Q";
                break;
            case 25:
                homonimio = homonimio + "R";
                break;
            case 26:
                homonimio = homonimio + "S";
                break;
            case 27:
                homonimio = homonimio + "T";
                break;
            case 28:
                homonimio = homonimio + "U";
                break;
            case 29:
                homonimio = homonimio + "V";
                break;
            case 30:
                homonimio = homonimio + "W";
                break;
            case 31:
                homonimio = homonimio + "X";
                break;
            case 32:
                homonimio = homonimio + "Y";
                break;
            case 33:
                homonimio = homonimio + "Z";
                break;

        }

        return rfc+homonimio+RFCDigitoVerificador(rfc+homonimio);
    }

    public static String RFCDigitoVerificador(String rfc) {
        ArrayList<String> rfcsuma = new ArrayList<>();
        int nv = 0;
        int y = 0;
        for (int i = 0; i < rfc.length(); i++) {
            String letra = rfc.substring(i, i+1);

            switch (letra) {
                case "0":
                    rfcsuma.add("00");
                    break;
                case "1":
                    rfcsuma.add("01");
                    break;
                case "2":
                    rfcsuma.add("02");
                    break;
                case "3":
                    rfcsuma.add("03");
                    break;
                case "4":
                    rfcsuma.add("04");
                    break;
                case "5":
                    rfcsuma.add("05");
                    break;
                case "6":
                    rfcsuma.add("06");
                    break;
                case "7":
                    rfcsuma.add("07");
                    break;
                case "8":
                    rfcsuma.add("08");
                    break;
                case "9":
                    rfcsuma.add("09");
                    break;
                case "A":
                    rfcsuma.add("10");
                    break;
                case "B":
                    rfcsuma.add("11");
                    break;
                case "C":
                    rfcsuma.add("12");
                    break;
                case "D":
                    rfcsuma.add("13");
                    break;
                case "E":
                    rfcsuma.add("14");
                    break;
                case "F":
                    rfcsuma.add("15");
                    break;
                case "G":
                    rfcsuma.add("16");
                    break;
                case "H":
                    rfcsuma.add("17");
                    break;
                case "I":
                    rfcsuma.add("18");
                    break;
                case "J":
                    rfcsuma.add("19");
                    break;
                case "K":
                    rfcsuma.add("20");
                    break;
                case "L":
                    rfcsuma.add("21");
                    break;
                case "M":
                    rfcsuma.add("22");
                    break;
                case "N":
                    rfcsuma.add("23");
                    break;
                case "&":
                    rfcsuma.add("24");
                    break;
                case "O":
                    rfcsuma.add("25");
                    break;
                case "P":
                    rfcsuma.add("26");
                    break;
                case "Q":
                    rfcsuma.add("27");
                    break;
                case "R":
                    rfcsuma.add("28");
                    break;
                case "S":
                    rfcsuma.add("29");
                    break;
                case "T":
                    rfcsuma.add("30");
                    break;
                case "U":
                    rfcsuma.add("31");
                    break;
                case "V":
                    rfcsuma.add("32");
                    break;
                case "W":
                    rfcsuma.add("33");
                    break;
                case "X":
                    rfcsuma.add("34");
                    break;
                case "Y":
                    rfcsuma.add("35");
                    break;
                case "Z":
                    rfcsuma.add("36");
                    break;
                case " ":
                    rfcsuma.add("37");
                    break;
                case "Ñ":
                    rfcsuma.add("38");
                    break;
                default:
                    rfcsuma.add("00");
            }
        }

        for (int i = 13; i > 1; i--) {
            nv = nv + ((Integer.parseInt(rfcsuma.get(y))) * i);
            y++;
        }
        nv = nv % 11;
        String id_verificador = "";

        Log.e("NV: ", String.valueOf(nv));
        //alert(nv);
        if (nv == 0) {
            id_verificador = String.valueOf(nv);
            rfc = rfc + nv;
        } else if (nv <= 10) {
            nv = 11 - nv;
            if (nv == 10) {
                id_verificador = "A";
            }else
                id_verificador = String.valueOf(nv);
            rfc = rfc + nv;
        } else if (nv == 10) {
            nv = 'A';
            id_verificador = "A";
            rfc = rfc + nv;
        }

        Log.e("digito_verificiador", id_verificador);
        
        return id_verificador;
    }

    public static String ConvertToJson (Object obj){
        Log.v("JsonConvert", GSON.toJson(obj));
        return GSON.toJson(obj);
    }

    /*Obtiene el texto de los edittext y textview*/
    public static String GetStr(Object obj) {
        String stringvalue;
        if (obj instanceof EditText) {
            stringvalue = ((EditText) obj).getText().toString();
        } else if (obj instanceof TextView) {
            stringvalue = ((TextView) obj).getText().toString();
        } else {
            stringvalue = (String) obj;
        }
        return stringvalue.trim().toUpperCase();
    }

    public static boolean ValidTextView (TextView tv){
        boolean flag = false;
        if (tv.getText().toString().trim().isEmpty()){
            tv.setError("Este campo es requerido");
            tv.setFocusable(true);
            //tv.setFocusableInTouchMode(true);*/
            flag = true;
        }

        if (!tv.getText().toString().trim().isEmpty() && tv.getText().toString().trim().toUpperCase().contains("NO SE ENCONTRÓ INFORMACIÓN")){
            tv.setError("Este campo es requerido");
            tv.setFocusable(true);
            //tv.setFocusableInTouchMode(true);*/
            flag = true;
        }



        return flag;
    }

    public static String GetCodigoEstado(String estado){
        String codigoEstado = "";

        switch (estado){
            case "AGUASCALIENTES": codigoEstado="AGS" ; break;
            case "BAJA CALIFORNIA": codigoEstado="BC" ; break;
            case "BAJA CALIFORNIA SUR": codigoEstado="BCS" ; break;
            case "CAMPECHE": codigoEstado="CAMP" ; break;
            case "CHIAPAS": codigoEstado="CHIS" ; break;
            case "CHIHUAHUA": codigoEstado="CHIH" ; break;
            case "CIUDAD DE MEXICO": codigoEstado="CDMX" ; break;
            case "COAHUILA DE ZARAGOZA": codigoEstado="COAH" ; break;
            case "COLIMA": codigoEstado="COL" ; break;
            case "DURANGO": codigoEstado="DGO" ; break;
            case "GUANAJUATO": codigoEstado="GTO" ; break;
            case "GUERRERO": codigoEstado="GRO" ; break;
            case "HIDALGO": codigoEstado="HGO" ; break;
            case "JALISCO": codigoEstado="JAL" ; break;
            case "MEXICO": codigoEstado="MEX" ; break;
            case "MICHOACAN DE OCAMPO": codigoEstado="MICH" ; break;
            case "MORELOS": codigoEstado="MOR" ; break;
            case "NAYARIT": codigoEstado="NAY" ; break;
            case "NUEVO LEON": codigoEstado="NL" ; break;
            case "OAXACA": codigoEstado="OAX" ; break;
            case "PUEBLA": codigoEstado="PUE" ; break;
            case "QUERETARO": codigoEstado="QRO" ; break;
            case "QUINTANA ROO": codigoEstado="QROO" ; break;
            case "SAN LUIS POTOSI": codigoEstado="SLP" ; break;
            case "SINALOA": codigoEstado="SIN" ; break;
            case "SONORA": codigoEstado="SON" ; break;
            case "TABASCO": codigoEstado="TAB" ; break;
            case "TAMAULIPAS": codigoEstado="TAMP" ; break;
            case "TLAXCALA": codigoEstado="TLAX" ; break;
            case "VERACRUZ": codigoEstado="VER" ; break;
            case "YUCATAN": codigoEstado="YUC" ; break;
            case "ZACATECAS": codigoEstado="ZAC" ; break;
        }

        return codigoEstado;
    }

    public static Integer selectCampana(Context ctx ,String campana){
        Integer id_campana = 0;

        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "Select id_campana from " + TBL_CATALOGOS_CAMPANAS + " WHERE tipo_campana = ?";
        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(campana)});

        if(row.getCount()>0){
            row.moveToFirst();
            id_campana = Integer.parseInt(row.getString(0).trim().toUpperCase());
        }
        row.close();
        return id_campana;
    }

    public static Integer selectCarteraEn(Context ctx, String carteraEn){
        Integer id_carteraEn = 0;

        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = " SELECT id_tipocartera from " + TBL_DATOS_ENTREGA_CARTERA + " WHERE tipo_EntregaCartera = ?";

        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(carteraEn)});

        if(row.getCount()>0){
            row.moveToFirst();
            id_carteraEn = Integer.parseInt(row.getString(0).trim().toUpperCase());
        }
        row.close();
        return id_carteraEn;
    }

    public static String tipoCampana(Context ctx, String id_campana){
        String tipo_campana=" ";

        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = " SELECT tipo_campana from " + TBL_CATALOGOS_CAMPANAS + " WHERE id_campana = ?";
        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(id_campana)});

        if(row.getCount()>0){
            row.moveToFirst();
            tipo_campana = row.getString(0).trim().toUpperCase();
        }
        row.close();
        return tipo_campana;
    }
    public static String tipoEntregaCartera(Context ctx, String id_entregaCartera){
        String tipo_entregaCa = " ";

        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = " SELECT tipo_EntregaCartera from " + TBL_DATOS_ENTREGA_CARTERA + " WHERE id_tipocartera = ?";
        Cursor row = db.rawQuery(sql,new String[]{String.valueOf(id_entregaCartera)});

        if(row.getCount()>0){
            row.moveToFirst();
            tipo_entregaCa = row.getString(0).trim().toUpperCase();
        }
        row.close();
        return tipo_entregaCa;
    }


    public static String GetColonia(Context ctx, int id){
        String colonia = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT colonia_nombre FROM " + COLONIAS + " WHERE colonia_id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            colonia = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return colonia;

    }

    public static String GetLocalidad(Context ctx, int id){
        String localidad = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + LOCALIDADES + " WHERE id_localidad = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            localidad = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return localidad;

    }

    public static String GetMunicipio(Context ctx, int id){
        String municipio = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT municipio_nombre FROM " + MUNICIPIOS + " WHERE municipio_id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            municipio = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return municipio;

    }

    public static String GetEstado(Context ctx, int id){
        String estado = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT estado_nombre FROM " + ESTADOS + " WHERE estado_id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            estado = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return estado;

    }

    public static String GetOcupacion(Context ctx, int id){
        String ocupacion = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT ocupacion_nombre FROM " + OCUPACIONES + " WHERE ocupacion_id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            ocupacion = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return ocupacion;

    }

    public static String GetSector(Context ctx, int id){
        String ocupacion = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT sector_nombre FROM " + OCUPACIONES + " AS o INNER JOIN " + SECTORES + " AS s ON s.sector_id = o.sector_id WHERE o.ocupacion_id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            ocupacion = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return ocupacion;

    }

    public static String GetEstudio(Context ctx, int id){
        String estudio = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + TBL_NIVELES_ESTUDIOS + " WHERE id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            estudio = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return estudio;

    }

    public static String GetParentesco(Context ctx, int id){
        String parentesco = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + TBL_PARENTESCOS + " WHERE id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            parentesco = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return parentesco;

    }

    public static String GetEstadoCivil(Context ctx, int id){
        String estadoCivil = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + TBL_ESTADOS_CIVILES + " WHERE id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            estadoCivil = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return estadoCivil;
    }

    public static String GetTipoIdentificacion(Context ctx, int id){
        String tipoIdentificacion = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + TBL_IDENTIFICACIONES_TIPO + " WHERE id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            tipoIdentificacion = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return tipoIdentificacion;
    }

    public static String GetMediosPagoSoli(Context ctx, String ids){
        String mediosPago = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String sql = "SELECT nombre FROM " + TBL_MEDIOS_PAGO_ORI + " WHERE id IN ("+ids+")";
        Cursor r = db.rawQuery(sql, null);
        if (r.getCount() > 0){
            r.moveToFirst();
            for (int i = 0; i < r.getCount(); i++){
                if (i == 0)
                    mediosPago = r.getString(0);
                else
                    mediosPago += ","+r.getString(0);
                r.moveToNext();
            }
        }
        r.close();

        Log.e("----","------------------------------------");
        Log.e("MEDIOSPAGO",mediosPago+" <-----");
        Log.e("----","------------------------------------");
        return mediosPago;
    }

    public static String GetViviendaTipo(Context ctx, int id){
        String viviendaTipo = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + TBL_VIVIENDA_TIPOS + " WHERE id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            viviendaTipo = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return viviendaTipo;
    }

    public static String GetMedioContacto(Context ctx, int id){
        String medioContacto = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + TBL_MEDIOS_CONTACTO + " WHERE id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            medioContacto = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return medioContacto;
    }

    public static String GetDestinoCredito(Context ctx, int id){
        String medioContacto = "";
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT nombre FROM " + TBL_DESTINOS_CREDITO + " WHERE id = ?";
        Cursor row = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (row.getCount() > 0){
            row.moveToFirst();
            medioContacto = row.getString(0).trim().toUpperCase();
        }
        row.close();

        return medioContacto;
    }

    public static int GetTipoCartera (String str_tipo_cartera){
        int tipo = 0;
        if (str_tipo_cartera.toUpperCase().equals("INDIVIDUAL")){
            tipo = 1;
        }
        else if (str_tipo_cartera.toUpperCase().equals("GRUPAL")){
            tipo = 2;
        }

        return tipo;
    }

    public static JSONObject RowTOJson (Cursor row, Context ctx){
        JSONObject json_respuesta = new JSONObject();
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat nFormat = new DecimalFormat("#,##0.00", symbols);

        try {
            json_respuesta.put("tipo_gestion", row.getInt(28));
            json_respuesta.put("latitud", row.getDouble(2));
            json_respuesta.put("longitud", row.getDouble(3));
            json_respuesta.put("contacto", GetIdContacto(row.getString(4)));
            switch (GetIdContacto(row.getString(4))){
                case 0: //SI CONTACTO
                    json_respuesta.put("actualizar_telefono", GetIdConfirmacion(row.getString(7).trim()));
                    if (GetIdConfirmacion(row.getString(7).trim()) == 0){
                        json_respuesta.put("telefono_nuevo", row.getString(8).trim());
                    }
                    json_respuesta.put("resultado_pago", GetIdPago(row.getString(9).trim()));
                    switch (GetIdPago(row.getString(9).trim())){
                        case 0: //PAGO
                            json_respuesta.put("medio_pago", GetMedioPagoId(row.getString(12).trim()));
                            switch (GetMedioPagoId(row.getString(12))){
                                case 0://BANAMEX
                                case 1://BANORTE
                                case 2://BANCOMER
                                case 3://TELECOM
                                case 4://BANSEFI
                                case 5://OXXO
                                case 8://BANAMEX722
                                    json_respuesta.put("fecha_pago", row.getString(13).trim());
                                    break;
                                case 6://EFECTIVO
                                    json_respuesta.put("imprimir_recibo", GetIdImpresion(row.getString(16)));
                                    json_respuesta.put("folio", row.getString(17));
                                    json_respuesta.put("res_impresion", row.getInt(24));
                                    if (row.getInt(28) == 2 && row.getDouble(15) >= 10000 && row.getString(30).equals("VIGENTE")){
                                        Cursor row_arqueo;

                                        row_arqueo = dBhelper.getRecords(TBL_ARQUEO_CAJA_T, " WHERE id_gestion = ?", "", new String[]{row.getString(0)});

                                        if (row_arqueo.getCount() > 0){
                                            row_arqueo.moveToFirst();
                                            JSONObject json_arqueo = new JSONObject();
                                            json_arqueo.put("mil", row_arqueo.getInt(2));
                                            json_arqueo.put("quinientos", row_arqueo.getInt(3));
                                            json_arqueo.put("doscientos", row_arqueo.getInt(4));
                                            json_arqueo.put("cien", row_arqueo.getInt(5));
                                            json_arqueo.put("cincuenta", row_arqueo.getInt(6));
                                            json_arqueo.put("veinte", row_arqueo.getInt(7));
                                            json_arqueo.put("diez", row_arqueo.getInt(8));
                                            json_arqueo.put("cinco", row_arqueo.getInt(9));
                                            json_arqueo.put("dos", row_arqueo.getInt(10));
                                            json_arqueo.put("peso", row_arqueo.getInt(11));
                                            json_arqueo.put("c_cincuenta", row_arqueo.getInt(12));
                                            json_arqueo.put("c_veinte", row_arqueo.getInt(13));
                                            json_arqueo.put("c_diez", row_arqueo.getInt(14));

                                            double total = (row_arqueo.getInt(2) * 1000) + (row_arqueo.getInt(3) * 500) + (row_arqueo.getInt(4) * 200) + (row_arqueo.getInt(5) * 100) + (row_arqueo.getInt(6) * 50) + (row_arqueo.getInt(7) * 20) + (row_arqueo.getInt(8) * 10) + (row_arqueo.getInt(9) * 5) + (row_arqueo.getInt(10) * 2) + row_arqueo.getInt(11) + (row_arqueo.getInt(12) * 0.50) + (row_arqueo.getInt(13) * 0.20) + (row_arqueo.getInt(14) * 0.10);
                                            json_arqueo.put("total", nFormat.format(total).replace(",",""));
                                            json_respuesta.put("arqueo_caja", json_arqueo);

                                        }
                                        row_arqueo.close();
                                    }

                                    if (row.getString(38).equals("VENCIDA")){
                                        json_respuesta.put("serial_id", row.getString(40));
                                    }
                                    break;
                                case 7://SIDERT
                                    json_respuesta.put("imprimir_recibo", 1);
                                    json_respuesta.put("folio", row.getString(17));
                                    break;
                            }
                            switch (row.getInt(28)){
                                case 1: //INDIVIDUAL
                                    json_respuesta.put("pagara_requerido", GetIdConfirmacion(row.getString(14).trim()));
                                    break;
                                case 2: //GRUPAL
                                    if (row.getString(38).equals("VIGENTE")) {
                                        json_respuesta.put("detalle_ficha", GetIdConfirmacion(row.getString(14).trim()));
                                        if (GetIdConfirmacion(row.getString(14)) == 0) {
                                            JSONArray pagos_integrantes = new JSONArray();
                                            Cursor row_integrantes = dBhelper.getRecords(TBL_MIEMBROS_PAGOS_T, " WHERE id_gestion = ?", "", new String[]{row.getString(0)});

                                            if (row_integrantes.getCount() > 0) {
                                                row_integrantes.moveToFirst();
                                                for (int i = 0; i < row_integrantes.getCount(); i++) {
                                                    JSONObject detalle_integrante = new JSONObject();
                                                    detalle_integrante.put("cliente_id", row_integrantes.getInt(2));
                                                    detalle_integrante.put("monto_requerido", nFormat.format(row_integrantes.getDouble(5)).replace(",", ""));
                                                    detalle_integrante.put("pago_realizado", nFormat.format(row_integrantes.getDouble(6)).replace(",", ""));
                                                    detalle_integrante.put("pago_adelanto", nFormat.format(row_integrantes.getDouble(7)).replace(",", ""));
                                                    detalle_integrante.put("pago_solidario", nFormat.format(row_integrantes.getDouble(8)).replace(",", ""));
                                                    pagos_integrantes.put(detalle_integrante);
                                                    row_integrantes.moveToNext();
                                                }
                                                json_respuesta.put("pagos_integrantes", pagos_integrantes);
                                            }

                                            row_integrantes.close();
                                        }
                                    }else
                                        json_respuesta.put("pagara_requerido", GetIdConfirmacion(row.getString(14).trim()));
                                    break;
                            }

                            json_respuesta.put("pago_realizado", nFormat.format(row.getDouble(15)).replace(",",""));
                            json_respuesta.put("estatus", row.getString(25));
                            json_respuesta.put("saldo_corte", nFormat.format(row.getDouble(26)).replace(",",""));
                            json_respuesta.put("saldo_actual", nFormat.format(row.getDouble(27)).replace(",",""));
                            json_respuesta.put("evidencia", row.getString(18).trim());
                            json_respuesta.put("tipo_imagen", GetIdImagen(row.getString(19).trim()));

                            break;
                        case 1: //NO PAGO
                            Log.e("MOTIVONOPAGO", GetIdMotivoNoPago(row.getString(10).trim())+" ID");
                            json_respuesta.put("motivo_no_pago", GetIdMotivoNoPago(row.getString(10).trim()));
                            if (GetIdMotivoNoPago(row.getString(10).trim()) == 1){
                                Log.e("FECHADEFUNCION", row.getString(11)+" fecha");
                                json_respuesta.put("fecha_defuncion", row.getString(11));
                            }
                            else if (GetIdMotivoNoPago(row.getString(10).trim()) == 3){
                                json_respuesta.put("fecha_promesa_pago", row.getString(36));
                                json_respuesta.put("monto_promesa", nFormat.format(row.getDouble(37)).replace(",",""));
                            }
                            json_respuesta.put("comentario", row.getString(6).toUpperCase().trim());
                            json_respuesta.put("evidencia", row.getString(18).trim());
                            json_respuesta.put("tipo_imagen", GetIdImagen(row.getString(19).trim()));
                            break;
                    }
                    break;
                case 1: //NO CONTACTO
                    json_respuesta.put("comentario", row.getString(6).toUpperCase().trim());
                    json_respuesta.put("evidencia", row.getString(18).trim());
                    json_respuesta.put("tipo_imagen", GetIdImagen(row.getString(19).trim()));
                    break;
                case 2: //ACLARACION
                    if (row.getString(38).equals("VIGENTE"))
                        json_respuesta.put("motivo_aclaracion", GetIdAclaracion(row.getString(5)));
                    json_respuesta.put("comentario", row.getString(6).toUpperCase().trim());
                    break;
            }

            json_respuesta.put("supervision_gerente", GetIdConfirmacion(row.getString(20)));
            if(GetIdConfirmacion(row.getString(20)) == 0)
                json_respuesta.put("firma", row.getString(21).trim());

            if (row.getString(38).equals("VENCIDA") && row.getInt(28) == 2)
                json_respuesta.put("prestamo_id_integrante", row.getLong(39));

            json_respuesta.put("fecha_inicio_gestion", row.getString(22));
            json_respuesta.put("fecha_fin_gestion", row.getString(23));
            json_respuesta.put("fecha_establecida", row.getString(30));
            json_respuesta.put("dia_semana", row.getString(31));
            String monto_requerido = row.getString(32).trim().replace(",","");
            json_respuesta.put("monto_requerido", nFormat.format(Double.parseDouble(monto_requerido)).replace(",",""));
            json_respuesta.put("fecha_envio_dispositivo", ObtenerFecha(TIMESTAMP));
            json_respuesta.put("tipo_prestamo_id", GetIdTipoPrestamo(row.getString(33)));
            json_respuesta.put("monto_amortizacion", nFormat.format(row.getDouble(34)).replace(",",""));
            json_respuesta.put("atraso", row.getInt(35));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json_respuesta;
    }

    public static int GetIdTipoPrestamo (String str){
        int id = -1;
        switch (str){
            case "VIGENTE":
                id = 0;
                break;
            case "COBRANZA":
                id = 1;
                break;
            case "VENCIDA VIGENTE":
                id = 2;
                break;
            case "VENCIDA 2019":
                id = 3;
                break;
            case "VENCIDA":
                id = 4;
                break;
        }
        return id;
    }

    //=============  PAGO REQUERIDO  =================
    public static int PagoRequerido(TextView tv){
        int requerido = -1;
        switch (tv.getText().toString().trim().toUpperCase()){
            case "SI":
                requerido = 0;
                break;
            case "NO":
                requerido = 1;
                break;
        }
        return requerido;
    }

    //=============  ACLARACION  =====================
    public static int GetIdAclaracion (String str){
        int id = -1;
        switch (str){
            case "GARANTIA":
                id = 0;
                break;
            case "REFINACIAMIENTO":
                id = 1;
                break;
            case "CONDONACION":
                id = 2;
                break;
            case "CAPTURA DE PAGO":
                id = 3;
                break;
        }
        return id;
    }

    //============  RESULTADO PAGO  ===================
    public static int GetIdPago (String str){
        int id = -1;
        switch (str){
            case "PAGO":
                id = 0;
                break;
            case "NO PAGO":
                id = 1;
                break;
        }
        return id;
    }

    //============ CONTACTO CLIENTE  ==================
    public static int GetIdContacto (String str){
        int id = -1;
        switch (str){
            case "SI":
                id = 0;
                break;
            case "NO":
                id = 1;
                break;
            case "ACLARACION":
                id = 2;
                break;
        }
        return id;
    }

    //============ MOTIVO NO PAGO  =====================
    public static int GetIdMotivoNoPago (String str){
        int id = -1;
        switch (str){
            case "NEGACION DE PAGO":
                id = 0;
                break;
            case "FALLECIMIENTO":
                id = 1;
                break;
            case "OTRO":
                id = 2;
                break;
            case "PROMESA DE PAGO":
                id = 3;
                break;
        }
        return id;
    }

    //============ CONFIRMACION  =======================
    public static String GetConfirmacion(int id){
        String confirmacion = "";
        switch (id){
            case 0:
                confirmacion = "SI";
                break;
            case 1:
                confirmacion = "NO";
                break;
        }
        return confirmacion;
    }

    public static int GetIdConfirmacion (String str){
        int id = -1;
        switch (str){
            case "SI":
                id = 0;
                break;
            case "NO":
                id = 1;
                break;
        }
        return id;
    }

    //============  TIPOS DE MEDIOS DE PAGO  ============
    public static String GetMedioPago(int id){
        String medioPago = "";
        switch (id){
            case 0:
                medioPago = "BANAMEX";
                break;
            case 1:
                medioPago = "BANORTE";
                break;
            case 2:
                medioPago = "BANCOMER";
                break;
            case 3:
                medioPago = "TELECOM";
                break;
            case 4:
                medioPago = "BANSEFI";
                break;
            case 5:
                medioPago = "OXXO";
                break;
            case 6:
                medioPago = "EFECTIVO";
                break;
            case 7:
                medioPago = "SIDERT";
                break;
            case 8:
                medioPago = "BANAMEX722";
                break;
        }
        return medioPago;
    }

    public static int GetMedioPagoId(String str){
        int medioPago = -1;
        switch (str){
            case "BANAMEX":
                medioPago = 0;
                break;
            case "BANORTE":
                medioPago = 1;
                break;
            case "BANCOMER":
                medioPago = 2;
                break;
            case "TELECOM":
                medioPago = 3;
                break;
            case "BANSEFI":
                medioPago = 4;
                break;
            case "OXXO":
                medioPago = 5;
                break;
            case "EFECTIVO":
                medioPago = 6;
                break;
            case "SIDERT":
                medioPago = 7;
                break;
            case "BANAMEX722":
                medioPago = 8;
                break;
        }
        return medioPago;
    }

    //============  IMPRESIONES  ========================
    public static int GetIdImpresion (String str){
        int id = -1;
        switch (str){
            case "SI":
                id = 0;
                break;
            case "NO CUENTA CON BATERIA":
                id = 1;
                break;
        }
        return id;
    }

    public static String GetImprimirRecibo(int id){
        String imprimir = "";
        switch (id){
            case 0:
                imprimir = "SI";
                break;
            case 1:
                imprimir = "NO CUENTA CON BATERIA";
                break;
        }
        return imprimir;
    }

    //============  TIPOS DE IMAGENES  ==================
    public static int GetIdImagen (String str){
        int id = -1;
        switch (str){
            case "FACHADA":
                id = 0;
                break;
            case "FOTOGRAFIA":
                id = 1;
                break;
            case "GALERIA":
                id = 2;
                break;
        }
        return id;
    }

    public static String GetTipoImagen (int id){
        String tipo = "";
        switch (id){
            case 0:
                tipo = "FACHADA";
                break;
            case 1:
                tipo = "FOTOGRAFIA";
                break;
            case 2:
                tipo = "GALERIA";
                break;
        }
        return tipo;
    }
    //===================================================

    public static int GetDiasAtraso (String fecha_establecida){
        int dias_atraso = 0;
        try {

            Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(fecha_establecida);
            Date date2 = new SimpleDateFormat("dd-MM-yyy").parse(ObtenerFecha("fecha_atraso"));
            dias_atraso = (int) ((date2.getTime()-date1.getTime())/86400000);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dias_atraso;
    }

    public static HashMap<Integer, String> GetNumPrestamo (String externalID){
        HashMap<Integer, String> params = new HashMap<>();
        if (externalID.contains("cvg") || externalID.contains("cvi")){
            String subS = "";

            if (externalID.contains("cvg")){
                //202016528975863cvg
                subS = externalID.substring(9,15);
                Log.e("NumPrestamoVe", subS);
            }
            else{
                //202016528965103-L007cvi1
                subS = externalID.substring(9,20);
                Log.e("NumPrestamoVe", subS);
            }
            params.put(0, "Vencida");
            params.put(1, subS);
        }
        else{
            String subS = "";
            if (externalID.contains("rg") || externalID.contains("cg")){
                //202016528975863cvg
                subS = externalID.substring(9,15);
                Log.e("NumPrestamoVi", subS);
            }
            else{
                //202016528965103-L007cvi1
                subS = externalID.substring(9,20);
                Log.e("NumPrestamoVi", subS);
            }
            params.put(0, "Vigente");
            params.put(1, subS);
        }
        return params;
    }

    public static String GetNomenclatura(int tipoGestion, String tipoPrestamo){
        String nomenclatura = "";
        switch (tipoPrestamo){
            case "VIGENTE":
                if (tipoGestion == 1)
                    nomenclatura = "ri";
                else
                    nomenclatura = "rg";
                break;
            case "COBRANZA":
                if (tipoGestion == 1)
                    nomenclatura = "ci";
                else
                    nomenclatura = "cg";
                break;
            case "VENCIDA VIGENTE":
                if (tipoGestion == 1)
                    nomenclatura = "vicvi";
                else
                    nomenclatura = "vicvg";
                break;
            case "VENCIDA 2019":
                if (tipoGestion == 1)
                    nomenclatura = "vncvi";
                else
                    nomenclatura = "vncvg";
                break;
            case "VENCIDA":
                if (tipoGestion == 1)
                    nomenclatura = "cvi";
                else
                    nomenclatura = "cvg";
                break;
        }

        return nomenclatura;
    }

    public static String[] RemoverRepetidos(List<String> nombres){
        String[] data;
        List<String> nombreUnico = new ArrayList<>();

        for (int i = 0; i < nombres.size(); i++){
            String nombre = nombres.get(i);
            if (nombreUnico.indexOf(nombre) < 0) {
                nombreUnico.add(nombre);
            }
        }

        data = new String[nombreUnico.size()];
        for (int j = 0; j < nombreUnico.size(); j++){
            data[j] = nombreUnico.get(j);
        }

        return data;
    }

    public static String GetFechaDomingo(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        int dias_add = (7 - cal.get(Calendar.DAY_OF_WEEK)) + 1;
        cal.add(Calendar.DAY_OF_MONTH, dias_add);

        return sdf.format(cal.getTime());
    }

    public static String[] GetNivelesEstudio(Context ctx){
        String[] estudios = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_NIVELES_ESTUDIOS;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            estudios = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                estudios[i] = row.getString(2);
                row.moveToNext();
            }

        }
        row.close();
        return estudios;
    }

    public static String[] GetEstadoCiviles(Context ctx){
        String[] civiles = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_ESTADOS_CIVILES;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            civiles = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                civiles[i] = row.getString(2);
                row.moveToNext();
            }
        }
        row.close();
        return civiles;
    }

    public static String[] GetMediosPagoOri(Context ctx){
        String[] medios_pago = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_MEDIOS_PAGO_ORI;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            medios_pago = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                medios_pago[i] = row.getString(2);
                row.moveToNext();
            }
        }
        row.close();
        return medios_pago;
    }

    public static String[] GetParentesco(Context ctx, String condicion){
        String[] parentesco = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String where = "";
        if (condicion.equals("PARENTESCO"))
            where = " WHERE nombre <> 'ARRENDATARIO'";

        String sql = "SELECT * FROM " + TBL_PARENTESCOS + where;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            parentesco = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                parentesco[i] = row.getString(2);
                row.moveToNext();
            }
        }
        row.close();
        return parentesco;
    }

    public static String[] GetIdentificacion(Context ctx){
        String[] identificaciones = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_IDENTIFICACIONES_TIPO;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            identificaciones = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                identificaciones[i] = row.getString(2);
                row.moveToNext();
            }
        }
        row.close();
        return identificaciones;
    }

    public static String[] GetViviendaTipos(Context ctx){
        String[] viviendaTipos = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_VIVIENDA_TIPOS;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            viviendaTipos = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                viviendaTipos[i] = row.getString(2);
                row.moveToNext();
            }
        }
        row.close();
        return viviendaTipos;
    }

    public static String[] GetMediosContacto(Context ctx){
        String[] mediosContacto = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_MEDIOS_CONTACTO;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            mediosContacto = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                mediosContacto[i] = row.getString(2);
                row.moveToNext();
            }
        }
        row.close();
        return mediosContacto;
    }

    public static String[] GetDestinosCredito(Context ctx){
        String[] destinosCredito = {};
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();

        String sql = "SELECT * FROM " + TBL_DESTINOS_CREDITO;
        Cursor row = db.rawQuery(sql, null);

        if (row.getCount() > 0){
            row.moveToFirst();
            destinosCredito = new String[row.getCount()];
            for (int i = 0; i < row.getCount(); i++){
                destinosCredito[i] = row.getString(2);
                row.moveToNext();
            }
        }
        row.close();
        return destinosCredito;
    }

    public static int obtenerNumCliente(String id_solicitud, Context ctx){
        DBhelper dBhelper = DBhelper.getInstance(ctx);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        String sql = "SELECT num_cliente FROM " + TBL_CREDITO_IND_REN + " WHERE id_solicitud="+id_solicitud;
        @SuppressLint("Recycle") Cursor dato = db.rawQuery(sql, null);
        int aux = 0;
        if(dato.getCount() > 0){
            dato.moveToFirst();
            aux = dato.getInt(0);
        }
        return aux;
    }



    public static String EncodePassword(String password) {
        String passEncryp = Base64.encodeToString(password.getBytes(), Base64.DEFAULT);
        String symbols = "";
        if (passEncryp.contains("==")){
            symbols = "==";
            passEncryp = passEncryp.replace("==","");
        }
        else if (passEncryp.contains("=")){
            symbols = "=";
            passEncryp = passEncryp.replace("=","");
        }
        String passEncode = "";
        if(passEncryp.length() > 6) {
            String pass1 = passEncryp.substring(0, 6);
            String pass2 = passEncryp.substring(6, passEncryp.length());
            passEncode = new StringBuilder(pass2).reverse().toString() + new StringBuilder(pass1).reverse().toString() + symbols;

        }
        else {
            passEncode = new StringBuilder(passEncryp).reverse().toString();
        }

        return passEncode;
    }

    public static void SendMess(String number, String message){
        try {
            SmsManager sms = SmsManager.getDefault(); // using android SmsManager
            sms.sendTextMessage(number, null, message, null, null); // adding number and text
            Log.e("Mess", "Envia Mensaje");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Mess", "Error al enviar mensaje");
        }
    }

    public static void SendMess(Context ctx, final String prestamoId, final String phone, final String nombre, final String monto, final String fecha, final String folio, SessionManager session) {
        URL url = null;
        try {
            url = new URL(session.getDominio() + WebServicesRoutes.CONTROLLER_MOVIL + "pagos/" + AES.encrypt(prestamoId));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String clienteGpo = nombre;
        if (nombre.length() > 17)
            clienteGpo = nombre.substring(0,15);

        Log.e("Comienza SMS", "Prepara SMS");
        String numeroCelular = phone; /**Coloca el numero celular del cliente y en caso que el asesor que esta imprimiendo sea el ASESOR de pruebas manda SMS al numero de SOPORTE*/

        if (session.getUser().get(9).equals("134")) /**USUARIO_ID del usuario ASESOR de pruebas*/
            numeroCelular = "2292641103"; //Telefono para cuando se quieren hacer pruebas
        String mess = clienteGpo + " pago por $"+monto+" pesos. Folio "+folio+".\n Valida tus pagos: "+url.toString();
        Log.e("Mensaje", mess);
        Miscellaneous.SendMess(numeroCelular, mess);

    }

    public static String DecodePassword(String password) {
        String symbols = "";
        if (password.contains("==")){
            symbols = "==";
            password = password.replace("==","");
        }
        else if (password.contains("=")){
            symbols = "=";
            password = password.replace("=","");
        }

        String passReverse = new StringBuilder(password).reverse().toString();

        byte[] bytesDecodificado = Base64.decode(passReverse+symbols, Base64.DEFAULT);
        String passDecodificada = new String(bytesDecodificado);

        return passDecodificada;
    }

    public static int validarEstatus(String tabla, Context ctx){
        int respuesta=0;
        String tablaCheck = tabla;
        int status = 1;
        Cursor dato;
        DBhelper dBhelper = DBhelper.getInstance(ctx);

        /*if(tablaCheck.contains("tb_tabla_test")){
            dato = dBhelper.validarEstatus("tb_tabla_test", "estatus=?",new String[]{String.valueOf(status)});

            if(dato.getCount()>0){
                respuesta = 1; //SE RETORNA 1 SI EXISTE UN VALOR
            }else
                respuesta = 0; //SE RETORNA 0 SI NO EXISTE EL VALOR

        }*/
        if(tablaCheck.contains(TBL_RECUPERACION_RECIBOS_CC)){
            dato = dBhelper.validarEstatus(TBL_RECUPERACION_RECIBOS_CC, "estatus=?",new String[]{String.valueOf(status)});
            if(dato.getCount()>0){
                respuesta = 1;
            }else{
                respuesta = 0;
            }

        }

        if(tablaCheck.contains(TBL_RECIBOS_AGF_CC)){
           Cursor datoA = dBhelper.validarEstatus(TBL_RECIBOS_AGF_CC,"estatus=?",new String[]{String.valueOf(status)});
            if(datoA.getCount()>0){
                respuesta = 1;
            }else{
                respuesta = 0;
            }
        }

        respuesta += respuesta;

        return respuesta;
    }

    public static byte[] etiquetasIne(Bitmap img,Context ctx){
        Bitmap imagen = img;
        byte byteImagenes [] = new byte[0];
        Context ctx1 = null;
        int tipoImg = 2;
        String currentDateTimeString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        int imgIneWidth = imagen.getWidth();
        int imgIneHeight = imagen.getHeight();
        if(imgIneWidth <= 0 || imgIneHeight <= 0){
            return byteImagenes;
        }
        float marginInCm = 0.5f;
        float dpi = ctx.getResources().getDisplayMetrics().densityDpi;
        float marginInPx = marginInCm * dpi / 2.54f;
        int backgroundWidth = (int) (imgIneWidth + 2 * marginInPx);
        int backgroundHeight = (int) (imgIneHeight + 2 * marginInPx);
        Bitmap backgroundBitMap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, imagen.getConfig());
        Canvas canvasBackground = new Canvas(backgroundBitMap);
        canvasBackground.drawColor(Color.LTGRAY);
        Bitmap resultBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight, imagen.getConfig());
        Canvas canvasResult = new Canvas(resultBitmap);
        canvasResult.drawBitmap(backgroundBitMap, 0, 0, null);
        canvasResult.drawBitmap(imagen, marginInPx, marginInPx, null);
        CanvasCustom2 customCanvas = new CanvasCustom2(ctx, currentDateTimeString, tipoImg);
        customCanvas.draw(canvasResult);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byteImagenes =  baos.toByteArray();
        return byteImagenes;
    }

    public static byte[] etiquetasFotoNormales(byte[] imagen, Context ctx) {
        byte imagenResultado[] = imagen;
        Bitmap imgBitmap = null;
        View vCanvas = null;
        Bitmap bitmap = null;
        Bitmap.Config config = null;
        Canvas canvas = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        vCanvas = new CanvasCustom2(ctx, new SimpleDateFormat(FORMAT_TIMESTAMP).format(Calendar.getInstance().getTime()),1);
        bitmap = BitmapFactory.decodeByteArray(imagenResultado, 0, imagenResultado.length);

        config = bitmap.getConfig();

        imgBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        canvas = new Canvas(imgBitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);

        vCanvas.draw(canvas);

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        imagenResultado = baos.toByteArray();

        return imagenResultado;
    }


}