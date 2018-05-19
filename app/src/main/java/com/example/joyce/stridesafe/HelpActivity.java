package com.example.joyce.stridesafe;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    public static String a;
    public static String b;
    public static String c;

    /**
     * Unpacks the bundle with the contact numbers.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Bundle bundle = getIntent().getExtras();

        a = bundle.getString("firstNum");
        b = bundle.getString("secNum");
        c = bundle.getString("answer");

    }

    /**
     * Calls onCall for contact one when the button is pushed.
     * @param view
     */
    public void call1(View view){
        // Get the Intent that started the activity, and extract the data
              onCall(a);
    }

    /**
     * Calls onCall for contact two when the button is pushed.
     * @param view
     */
    public void call2(View view){
        // Get the Intent that started the activity, and extract the data
              onCall(b);
    }

    /**
     * Calls onCall for contact three when the button is pushed.
     * @param view
     */
    public void call3(View view){
        onCall(c);
    }

    /**
     * Initiates a phone call with the number that is given to the method.
     * Base code from https://stackoverflow.com/questions/5230912/android-app-to-call-a-number-on-button-click
     * @param number
     */
    public void onCall(String number) {
        Log.d("HelpActivity",number);
        Log.d("HelpActivity","I am here" );
        Log.d("HelpActivity",number);
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class

           callIntent.setData(Uri.parse("tel:" + number));    //this is the phone number calling

        //check permission
        // NEED PERMISSION TO READ CONTACTS (BELIEVE THAT MAY BE THE SOURCE OF THE ERRORS)
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        }else {     //have got permission
            try{
                startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
            }
        }
    }



}
