package com.example.lemonstealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultBase extends AppCompatActivity {

    // TODO: Inne tło

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_base);

        Intent menu = new Intent(ResultBase.this, MainActivity.class);

        Button backToMenuButton = findViewById(R.id.backToMenu);
        Button clearDatabase = findViewById(R.id.clearDatabase);

        TextView showLemons = (TextView)findViewById(R.id.showLemonsView);
        DataBase db = new DataBase(this);

        boolean isEmpty = db.isDatabaseEmpty();

        if (isEmpty) {

            showLemons.setText("Play to get any score");

        } else {

            String lemons = db.getAllRecords(1);

            System.out.println("Lemons that should have been in database: " + lemons);
            showLemons.setText(lemons);

        }



        backToMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(menu);

            }

        });

        clearDatabase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                db.resetDatabase();

            }

        });
    }

}
