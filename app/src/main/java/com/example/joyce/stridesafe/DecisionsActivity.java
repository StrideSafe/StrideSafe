package com.example.joyce.stridesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DecisionsActivity extends AppCompatActivity {
    public static String num1;
    public static String num2;
    public static String num3;

    /**
     * Unpacks the bundle of contact data from the ContactsActivity and stores contact data in local variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decisions);

        try {


            Bundle bundle = getIntent().getExtras();

            num1 = bundle.getString("firstNum");
            num2 = bundle.getString("secNum");
            num3 = bundle.getString("answer");

        } catch(NullPointerException e) {
            Toast.makeText(getApplicationContext(),"Please enter your emergency contacts",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Stores the user inputted starting address as a string object
     * @param view
     * @return The starting address of the user
     */
    public String getStartLoc(View view)
    {
        EditText userStart = findViewById(R.id.UserStartLoc);
        String address1 = userStart.getText().toString();
        return address1;
    }

    /**
     * Stores the user inputted ending address as a string object
     * @param view
     * @return The ending address of the user
     */
    public String getEndLoc(View view)
    {
        EditText userEnd = findViewById(R.id.UserEndLoc);
        String address2 = userEnd.getText().toString();
        return address2;
    }

    /**
     * Bundles the addresses and contact data and sends it to the MapTest class
     * @param view
     * @return
     */
    public void sendAddresses(View view)
    {
        String address1 = getStartLoc(view);
        String address2 = getEndLoc(view);

        Intent intent = new Intent(this, MapTest.class);
        intent.putExtra("UserStartLoc", address1);
        intent.putExtra("UserEndLoc", address2);
        intent.putExtra("firstNum", num1);
        intent.putExtra("secNum", num2);
        intent.putExtra("answer", num3);

        startActivity(intent);
    }
}
