package com.sidert.sidertmovil.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageManager {
    public static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2) {
        int k = paramOptions.outHeight;
        int m = paramOptions.outWidth;
        int j = 1;
        int i = 1;
        if (k > paramInt2 || m > paramInt1) {
            k /= 2;
            m /= 2;
            while (true) {
                j = i;
                if (k / i >= paramInt2) {
                    j = i;
                    if (m / i >= paramInt1) {
                        i *= 2;
                        continue;
                    }
                }
                break;
            }
        }
        return j;
    }

    public static Bitmap centerCrop(Bitmap paramBitmap) { return Bitmap.createBitmap(paramBitmap, paramBitmap.getWidth() / 4, paramBitmap.getHeight() / 4, paramBitmap.getWidth() / 2, paramBitmap.getHeight() / 2); }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, options);
        options.inSampleSize = calculateInSampleSize(options, paramInt1, paramInt2);
        options.inJustDecodeBounds = false;
        return paramBoolean ? centerCrop(BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, options)) : BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(paramString, options);
        options.inSampleSize = calculateInSampleSize(options, paramInt1, paramInt2);
        options.inJustDecodeBounds = false;
        return paramBoolean ? centerCrop(BitmapFactory.decodeFile(paramString, options)) : BitmapFactory.decodeFile(paramString, options);
    }

    public static Bitmap decodeSampledBitmapFromResources(Resources paramResources, int paramInt1, int paramInt2, int paramInt3) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(paramResources, paramInt1, options);
        options.inSampleSize = calculateInSampleSize(options, paramInt2, paramInt3);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(paramResources, paramInt1, options);
    }
}