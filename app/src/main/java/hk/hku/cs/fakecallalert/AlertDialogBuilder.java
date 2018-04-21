package hk.hku.cs.fakecallalert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;

public class AlertDialogBuilder extends Activity {
    private AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayAlert();
    }

    public void displayAlert() {
        ImageView image = getWarningImage();

        builder = new AlertDialog.Builder(this);
        //builder.setTitle("Unknown Caller");
        //builder.setMessage("Beware of fake identity!");
        builder.setCancelable(false);
        builder.setView(image);
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        /*
        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private ImageView getWarningImage() {
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.warning);
        try {
            Log.d("DEBUG","LOCAL IMAGE");
            File file = new File(getApplicationContext().getFilesDir(), UpdateService.FILE_NAME);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            image.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return image;
    }

}
