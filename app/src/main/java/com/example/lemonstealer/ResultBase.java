package com.example.lemonstealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultBase extends AppCompatActivity {

    // TODO: Dodać bazę danych pobierającą z json'a
    int retrievedLemons = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_base);

        String json="";
        SharedPreferences prefMaxButton=getSharedPreferences("DATA", Context.MODE_PRIVATE);
        json=prefMaxButton.getString("KEY_ID_VALUE2", "FALSE");
        String finalJson = json;

        retrievedLemons = 0;

        try {

            JSONObject jsonPointsCollector = new JSONObject(finalJson);
            retrievedLemons = jsonPointsCollector.getInt("collection");

        } catch (JSONException e) {

            e.printStackTrace();

        }

        DataBase db = new DataBase(this);

        LemonsBase mleko = new LemonsBase("Lemons: " + retrievedLemons, true);
        db.addLemons(mleko, 1);

        String towary = db.getAllRecords(1);
        TextView textViewTowar = (TextView)findViewById(R.id.textViewTowar);

        textViewTowar.setText(towary);

    }

}
