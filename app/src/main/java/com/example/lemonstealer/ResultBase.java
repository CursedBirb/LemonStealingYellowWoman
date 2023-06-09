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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_base);

        String json="";
        SharedPreferences prefMaxButton=getSharedPreferences("DATA", Context.MODE_PRIVATE);
        json=prefMaxButton.getString("KEY_ID_VALUE2", "FALSE");
        String finalJson = json;

        DataBase db = new DataBase(this);

        String lemons = db.getAllRecords(1);
        TextView showLemons = (TextView)findViewById(R.id.showLemonsView);

        showLemons.setText(lemons);

    }

}
