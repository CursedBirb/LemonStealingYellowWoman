package com.example.lemonstealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Intent gameActivity, resultBase;
    Button loadGame, LoadResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadGame = (Button) findViewById(R.id.gameActivity);
        LoadResult = (Button) findViewById(R.id.databaseActivity);

        gameActivity = new Intent(MainActivity.this, GameRun.class);
        resultBase = new Intent(MainActivity.this, ResultBase.class);

        loadGame.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {

            startActivity(gameActivity);

        } });

        LoadResult.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {

            startActivity(resultBase);

        } });

    }

}