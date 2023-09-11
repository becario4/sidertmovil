package com.sidert.sidertmovil.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.sidert.sidertmovil.R;

public class CanvasCustom2 extends View {
    String fecha;
    Context ctx;
    int tipoImg = 0;
    Paint  paint = new Paint();
    Paint textPaint = new Paint();
    Paint backgroundPaint = new Paint();
    String texto = "Fotografía tomada del Original: ";
    float textSize = 20, textX=0, textY=0,textWidth=0, textHeight=0,cornerRadius=0,backgroundWidth=0,backgroundHeight=0;

    public CanvasCustom2(Context context, String fecha, int tipoImg){
        super(context);
        this.ctx = context;
        this.fecha = fecha;
        this.tipoImg = tipoImg;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(tipoImg == 1){
            imgNormales(canvas);
        }else if(tipoImg == 2){
            imgIne(canvas);
        }

    }

    public void imgNormales(Canvas canvas){
        String text = texto + fecha;
        textSize = 20;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        textX = 20;
        textY = 195;
        textWidth = textPaint.measureText(text);
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        textHeight = metrics.bottom - metrics.top;
        cornerRadius = 10;
        backgroundWidth = textWidth + 20;
        backgroundHeight = textHeight + 20 ;
        backgroundPaint.setColor(ctx.getResources().getColor(R.color.white));
        backgroundPaint.setStyle(Paint.Style.FILL);
        canvas.save();
        canvas.rotate(-90, textX + 142, textY + 142);
        RectF backgroundRect = new RectF(textX, textY - textHeight, textX + backgroundWidth, textY + 10);
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint);
        canvas.drawText(text, textX + 10, textY, textPaint);
        canvas.restore();
    }

    public void imgIne(Canvas canvas){
        String text = texto + fecha;
        textSize = 20;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        textX = 140;
        textY = 40;
        textWidth = textPaint.measureText(text);
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        textHeight = metrics.bottom - metrics.top;
        cornerRadius = 10;
        backgroundWidth = textWidth + 20;
        backgroundHeight = textHeight + 20;
        backgroundPaint.setColor(ctx.getResources().getColor(R.color.white));
        backgroundPaint.setStyle(Paint.Style.FILL);
        RectF backgroundRect = new RectF(textX, textY - textHeight, textX + backgroundWidth, textY + 10);
        canvas.drawRoundRect(backgroundRect, cornerRadius, cornerRadius, backgroundPaint);
        canvas.rotate(360, textX, textY);
        canvas.drawText(text, textX + 10, textY, textPaint);
    }
}
