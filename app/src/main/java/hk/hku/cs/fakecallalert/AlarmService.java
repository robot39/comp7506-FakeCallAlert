package hk.hku.cs.fakecallalert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {
    static final int UPDATE_INTERVAL = 24*60*60*1000; // a day

    public AlarmService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DEBUG", "ALARM SERVICE");
        this.startService(new Intent(this, UpdateService.class));
        createAlarm();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createAlarm() {
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+UPDATE_INTERVAL, UPDATE_INTERVAL, pendingIntent);
    }
}
