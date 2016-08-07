package br.com.example.ormliteteste.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.example.ormliteteste.dao.DataBaseHelper;

/**
 * Created by wesley on 7/30/16.
 */
public class Util {

    public static void message (Context context, String texto){
        Toast.makeText(context, texto, Toast.LENGTH_LONG).show();
    }

    public static Date formatDate(String data, boolean bd) {
        if (data == null || data.isEmpty())
            return null;

        String pattern = "dd/MM/yyyy";
        if (bd)
            pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDate(Date data, boolean bd) {
        if (data == null)
            return null;

        String pattern = "dd/MM/yyyy";
        if (bd)
            pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return sdf.format(data);
    }

    public static boolean exportDB(String namePackage) {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {

                String currentDBPath = "//data//" + namePackage
                        + "//databases//" + DataBaseHelper.DATABASE_NAME;
                String backupDBPath = DataBaseHelper.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
