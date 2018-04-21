package hk.hku.cs.fakecallalert;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.content.ContentResolver;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class MyPhoneStateListener extends PhoneStateListener {
    private Context context;
    private List<String> phoneNumbers;
    //private String callnumber="68192751";

    public MyPhoneStateListener(Context context) {
        super();
        Log.d("DEBUG","LISTENER");
        phoneNumbers = new ArrayList<String>();
        this.context = context;
        fetchAllContacts();
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        switch(state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG","RINGING");
                Log.d("DEBUG", "INCOMING NUMBER: " + incomingNumber);

                if(!compareNumber(incomingNumber)) { // incomingNumber not in contacts
                    Log.d("DEBUG","NOT IN CONTACTS - POTENTIAL RISK");
                    Intent intent = new Intent();
                    intent.setClass(context, AlertDialogBuilder.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Log.d("DEBUG","IN CONTACTS - SAFE");
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG","OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG","IDLE");
                break;
            default:
                Log.d("DEBUG","DEFAULT");
        }
    }

    public void fetchAllContacts() {
        ContentResolver contentResolver = context.getContentResolver();
        String[] columns= {ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = contentResolver.query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                columns, null, null, null);
        if(cursor.getCount()<=0){}
        while(cursor.moveToNext()) {
            String phoneNo=cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumbers.add(removeSymbols(phoneNo));
            Log.d("DEBUG", "Contact: "+phoneNo);
        }
        cursor.close();
    }

    public boolean compareNumber(String callNumber){
        String tempNumber = removeSymbols(callNumber);
        for(String number:phoneNumbers){
            if(tempNumber.equals(number)) {
                return true;
            }
        }
        return false;
    }

    private String removeSymbols(String phoneNumber) {
        return phoneNumber.replace("(","").replace(")","").replace("-","").replace("+","").replace(" ","");
    }
}
