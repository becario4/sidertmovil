package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SessionManager {

    private final String APP_PREF       = "com.sidert.sidertmovil";
    private final String MAILBOX        = "mailbox";
    private final String DATE           = "date";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context ctx;


    public SessionManager(Context ctx) {
        this.ctx = ctx;
    }

    // ===================  Count MailBox ==========================
    public void setMailBox (String date, int count){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(DATE, date);
        editor.putInt(MAILBOX, count);
        editor.commit();
    }

    public ArrayList<String> getMailBox (){
        ArrayList<String> launch = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        launch.add(preferences.getString(DATE, ""));
        launch.add(preferences.getString(MAILBOX,"0"));
        return launch;
    }

    public void deleteMailBox (){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(MAILBOX);
        editor.remove(DATE);
        editor.apply();
    }

}
