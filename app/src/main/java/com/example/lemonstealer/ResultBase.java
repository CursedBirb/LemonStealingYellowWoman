package com.example.lemonstealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


public class ResultBase extends AppCompatActivity {

    // TODO: Dodać bazę danych pobierającą z json'a

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_base);

        TextView showLemons = (TextView)findViewById(R.id.showLemonsView);
        DataBase db = new DataBase(this);

        String lemons = db.getAllRecords(1);

        System.out.println("Lemons that should have been in database: " + lemons);
        showLemons.setText(lemons);

    }

}
