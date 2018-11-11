package com.example.david.helloworldone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Call_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__page);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //For the Medical Emergency Button
        Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starting = new Intent(getApplicationContext(), Emergency_Page.class);
                startActivity(starting);
            }
        });

        //For the Other Emergency button
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starting = new Intent(getApplicationContext(), Other_Emergency.class);
                startActivity(starting);
            }
        });
    }

}
