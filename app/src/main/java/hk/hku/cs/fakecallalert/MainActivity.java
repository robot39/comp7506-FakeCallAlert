package hk.hku.cs.fakecallalert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int requestCode = 0;
        String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.READ_CONTACTS};
        for(String permission : permissions) {
            requestPermission(permission, requestCode);
        }
        startService(new Intent(this, AlarmService.class));
        Toast.makeText(this,"FakeCallAlert service has started.", Toast.LENGTH_LONG).show();
        finish();
    }

    private void requestPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }
}
