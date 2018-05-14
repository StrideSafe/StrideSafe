package com.example.joyce.stridesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String num1;
    public static String num2;
    public static String num3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public void goHelp(View view)
    {
        Log.d("MainActivity", "this is the first num " + num1);
        Intent intentHelp = new Intent(this, HelpActivity.class);
        intentHelp.putExtra("firstNum", num1);
        intentHelp.putExtra("secNum", num2);
        intentHelp.putExtra("answer", num3);
        startActivity(intentHelp);
    }

    public void goInformation(View view) {
        Intent intentInfo = new Intent(this, goInformation.class);
        startActivity(intentInfo);
    }

    public void goSettings(View view) {
        Intent intentSettings = new Intent(this, SettingsActivity.class);
        startActivity(intentSettings);
    }

    public void goContacts(View view) {
        Intent intentContacts = new Intent(this, ContactsActivity.class);
        startActivity(intentContacts);
    }

    public void goDecisions(View view) {
        Intent intentDecisions = new Intent(this, DecisionsActivity.class);
        intentDecisions.putExtra("firstNum", num1);
        intentDecisions.putExtra("secNum", num2);
        intentDecisions.putExtra("answer", num3);
        startActivity(intentDecisions);
    }

}