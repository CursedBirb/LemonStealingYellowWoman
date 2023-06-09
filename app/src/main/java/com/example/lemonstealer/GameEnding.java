package com.example.lemonstealer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class GameEnding extends AppCompatActivity {

    // TODO: Pokazać wynik pobierany z json'a, dodać textView obrażający Lumi i przycisk do powrotu do menu/restartu/bazy danych
    private GameEndingView gameEndingView;

    int retrievedLemons = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ending);

        gameEndingView = new GameEndingView(this);
        ConstraintLayout constraintLayout = findViewById(R.id.container2);
        constraintLayout.addView(gameEndingView);
        gameEndingView.setOnTouchListener(this);

        GameEndingView gameEndingView = new GameEndingView(GameEnding.this);

        TextView swearView = findViewById(R.id.swearView);
        TextView lemonView = findViewById(R.id.coinsView);

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

        swearView.setText("Serves you right. You should have not stole our lemons. You stupid *****.");
        lemonView.setText("You collected " + retrievedLemons + " lemons.");

    }

}