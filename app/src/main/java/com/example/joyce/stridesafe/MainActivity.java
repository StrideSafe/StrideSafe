package com.example.joyce.stridesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goHelp(View view)
    {
        Intent intentHelp = new Intent(this, HelpActivity.class);
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
        startActivity(intentDecisions);
    }

}