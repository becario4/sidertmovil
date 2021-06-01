package com.sidert.sidertmovil.utils;

import android.content.Context;

import com.lvrenyang.io.BTPrinting;

public class TaskOpen implements Runnable
{
    BTPrinting bt = null;
    String address = null;
    Context context = null;

    public TaskOpen(BTPrinting bt, String address, Context context)
    {
        this.bt = bt;
        this.address = address;
        this.context = context;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        bt.Open(address,context);
    }
}