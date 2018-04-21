package hk.hku.cs.fakecallalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DEBUG","BOOT RECEIVED");
        context.startService(new Intent(context, AlarmService.class));
    }
}
