package com.example.lemonstealer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        GameEndingView gameEndingView = new GameEndingView(GameEnding.this);

        TextView swearView = findViewById(R.id.swearView);
        TextView lemonView = findViewById(R.id.coinsView);

        Intent menu = new Intent(GameEnding.this, MainActivity.class);
        Intent restart = new Intent(GameEnding.this, GameRun.class);
        Intent database = new Intent(GameEnding.this, ResultBase.class);

        Button backToMenuButton = findViewById(R.id.backToMenu);
        Button replayButton = findViewById(R.id.replay);
        Button goToDataBaseButton = findViewById(R.id.goToDataBase);

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

        LemonsBase lemon = new LemonsBase(String.valueOf(retrievedLemons));
        db.addLemons(lemon, 1);

        swearView.setText("Serves you right. You should have not stole our lemons. You stupid *****.");
        lemonView.setText("You collected " + retrievedLemons + " lemons.");

        backToMenuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(menu);

            }

        });

        replayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(restart);

            }

        });

        goToDataBaseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(database);

            }

        });

    }

}