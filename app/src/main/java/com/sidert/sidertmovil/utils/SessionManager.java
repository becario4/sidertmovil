package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SessionManager {

    private final String APP_PREF       = "com.sidert.sidertmovil";
    private final String MAILBOX        = "mailbox";
    private final String DATE           = "date";
    private final String ID_USER        = "id_user";
    private final String NAME_USER      = "name_user";
    private final String TYPE_USER      = "type_user";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context ctx;


    public SessionManager(Context ctx) {
        this.ctx = ctx;
    }

    // ===================  Info User ==========================
    public void setUser (String id_user, String name_user, String type_user){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(ID_USER, id_user);
        editor.putString(NAME_USER, name_user);
        editor.putString(TYPE_USER, type_user);
        editor.commit();
    }

    public ArrayList<String> getUser (){
        ArrayList<String> user = new ArrayList<>();
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        user.add(preferences.getString(ID_USER, null));
        user.add(preferences.getString(NAME_USER,null));
        user.add(preferences.getString(TYPE_USER, null));
        return user;
    }

    public void deleteUser (){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.remove(ID_USER);
        editor.remove(NAME_USER);
        editor.remove(TYPE_USER);
        editor.apply();
    }

    // ===================  Count MailBox ==========================
    public void setMailBox (String date, String count){
        preferences = ctx.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(DATE, date);
        editor.putString(MAILBOX, count);
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
