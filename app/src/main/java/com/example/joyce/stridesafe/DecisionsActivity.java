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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decisions);

        try {


            Bundle bundle = getIntent().getExtras();

            num1 = bundle.getString("firstNum");
            num2 = bundle.getString("secNum");
            num3 = bundle.getString("answer");

            Log.d("MainActivity","the first number pls " + num1);

        } catch(NullPointerException e) {
            Toast.makeText(getApplicationContext(),"Please enter your emergency contacts",Toast.LENGTH_SHORT).show();
        }
    }


    public String getStartLoc(View view)
    {
        EditText userStart = findViewById(R.id.UserStartLoc);
        String address1 = userStart.getText().toString();
        return address1;
    }

    public String getEndLoc(View view)
    {
        EditText userEnd = findViewById(R.id.UserEndLoc);
        String address2 = userEnd.getText().toString();
        return address2;
    }

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
