package net.ruigeng.myapplication;

import android.content.Context;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by rui.geng on 6/4/17.
 */

public class AndFixUtil {

    public static final String PATH = "out.apatch";
    public static final String URL = "http://192.168.0.119/out.apatch";

    public static void inject(final Context context) {

        final PatchManager patchManager = new PatchManager(context);

        patchManager.init(BuildConfig.VERSION_CODE + "");//current version

        patchManager.loadPatch();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count;
                try {
                    URL url = new URL(URL);
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream(),
                            8192);

                    // Output stream
                    OutputStream output = new FileOutputStream(context.getDir("patch", Context.MODE_PRIVATE).getAbsolutePath() + "/" + PATH);

                    byte data[] = new byte[1024];

                    while ((count = input.read(data)) != -1) {
                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                try {
                    String patchPath = context.getDir("patch", Context.MODE_PRIVATE).getAbsolutePath() + "/" + PATH;
                    File file = new File(patchPath);
                    if (file.exists()) {
                        patchManager.addPatch(patchPath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
