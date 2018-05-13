package com.example.joyce.stridesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DecisionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decisions);
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

        startActivity(intent);
    }
}
