package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Miscellaneous {

    public static String ucFirst(String str) {
        if (str.equals("null") || str.isEmpty()) {
            return "";
        } else {
            return  Character.toUpperCase(str.charAt(0)) + str.substring(1, str.length()).toLowerCase();
        }
    }

    public static String getTypeOrderValue(String str) {
        String type = "";
        if (str.indexOf("ri") > -1 || str.indexOf("rg") > -1)
        {
            type = Constants.RECOVERY;
        }
        else if (str.indexOf("cvi") > -1 || str.indexOf("cvg") > -1 || str.indexOf("cci") > -1 || str.indexOf("ccg") > -1){
            type = Constants.WALLET_EXPIRED;
        }
        else if (str.indexOf("ci") > -1 || str.indexOf("cg") > -1){
            type = Constants.COLLECTION;
        }
        else{
            type = Constants.ERROR;
        }
        return type;

    }

    public static String readJson (JSONObject json) throws JSONException {
        String conditionals = "";
        JSONArray jsonWhere =(JSONArray) json.get("where");
        JSONArray jsonOrder =(JSONArray) json.get("order");
        if (jsonWhere.length() > 0)
        {
            conditionals = "WHERE ";
            for (int i = 0; i < jsonWhere.length(); i++)
            {
                JSONObject item = jsonWhere.getJSONObject(i);

                if (i == 0)
                {
                    conditionals +=  item.getString("key")+ " = " + item.getString("value");
                }
                else {
                    conditionals += " AND " + item.getString("key")+ " = " + item.getString("value");
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
                else {
                    conditionals += ", " + jsonOrder.get(i);
                }
            }
        }

        return conditionals;
    }

}
