package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.sidert.sidertmovil.R;

public class CustomCanvasResumen extends View {

    private Context ctx;
    private String fecha;
    private String nombre;

    public CustomCanvasResumen(Context context, String fecha, String nombre) {
        super(context);
        this.ctx = context;
        this.fecha = fecha;
        this.nombre = nombre;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(ctx.getResources().getColor(R.color.blue_sidert_flourecente));
        paint.setTextSize(20);
        canvas.rotate(-90,20,195);
        canvas.drawText(fecha, 20, 195, paint);
        canvas.drawText(nombre, 60, 200, paint);

    }
}
