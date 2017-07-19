package com.reframe.lapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Resize {
    public static String withPath(String originalPath) {
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            Bitmap b = BitmapFactory.decodeFile(originalPath);

            float original_w = b.getWidth();
            float original_h = b.getHeight();

            float target_h = 600;
            float target_w = 600;

            if (original_h < target_h && original_w < target_w) {
                // need no conversion.
                return originalPath;
            }

            final float k = Math.max( target_h/original_h , target_w/original_w );

            final int w = (int)(k * original_w);
            final int h = (int)(k * original_h);

            Bitmap out;
            try {
                ExifInterface exif = new ExifInterface(originalPath);
                Matrix matrix = new Matrix();
                boolean imageNeedsMatrixTransformation = true;
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(-90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                        matrix.setScale(-1, 1);
                        break;
                    case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                        matrix.setRotate(180);
                        matrix.postScale(-1, 1);
                        break;
                    case ExifInterface.ORIENTATION_TRANSPOSE:
                        matrix.setRotate(90);
                        matrix.postScale(-1, 1);
                        break;
                    case ExifInterface.ORIENTATION_TRANSVERSE:
                        matrix.setRotate(-90);
                        matrix.postScale(-1, 1);
                        break;
                    default:
                        imageNeedsMatrixTransformation = false;
                }
                if (imageNeedsMatrixTransformation) {
                    out = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    out = Bitmap.createScaledBitmap(out, w, h, true);
                } else {
                    out = Bitmap.createScaledBitmap(b, w, h, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                out = Bitmap.createScaledBitmap(b, w, h, true);
            }

            File file = new File(dir, new Date().getTime() + ".png");
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(file);
                out.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();
                b.recycle();
                out.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return originalPath;
        }
    }
}
