package net.mjc.zip;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Utils {
    public static void saveBitmap(File dir, ActivityState state, Bitmap bitmap) {

        final ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);

        final String name = state.getPerson().getMatterId() + "-" + state.getCurrentIdCheck().getCode() + ".png";
        final File file = new File(dir, name);

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(bs.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
