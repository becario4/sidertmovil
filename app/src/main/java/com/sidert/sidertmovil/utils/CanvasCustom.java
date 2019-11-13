package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.sidert.sidertmovil.R;

public class CanvasCustom extends View {
    String fecha;
    Context ctx;

    public CanvasCustom(Context context, String fecha) {
        super(context);
        this.ctx = context;
        this.fecha = fecha;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(ctx.getResources().getColor(R.color.blue_sidert_flourecente));
        paint.setTextSize(20);
        canvas.rotate(-90,20,195);
        canvas.drawText(fecha, 20, 195, paint);

    }
}
