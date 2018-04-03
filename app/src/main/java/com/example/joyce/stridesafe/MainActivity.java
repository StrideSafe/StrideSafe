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
        // Hi, this is my comment.........
        // sah dude
        // IIW IIS
    }

    public void Map(View view) {
        Intent intent = new Intent(this, MapTest.class);

        startActivity(intent);
    }
}
