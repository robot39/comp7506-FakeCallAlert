package hk.hku.cs.fakecallalert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UpdateService extends Service {
    static final String IMAGE_URL = "http://i.cs.hku.hk/~twchim/police/warning.jpg";
    static final String FILE_NAME = "warning.jpg";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DEBUG", "UPDATE SERVICE");

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... str) {
                try {
                    String fileUrl = str[0];
                    String fileName = str[1];

                    URL url = new URL(fileUrl);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                    File file = new File(getApplicationContext().getFilesDir(), fileName);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    byte data[] = new byte[1024];
                    int count;
                    while ((count = inputStream.read(data)) > 0) {
                        fileOutputStream.write(data, 0, count);
                    }

                    fileOutputStream.flush();
                    fileOutputStream.close();
                    inputStream.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
                stopSelf();
                return null;
            }
        }.execute(IMAGE_URL, FILE_NAME);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
