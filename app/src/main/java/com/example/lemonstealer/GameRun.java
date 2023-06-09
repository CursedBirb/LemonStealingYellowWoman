package com.example.lemonstealer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameRun extends AppCompatActivity implements View.OnTouchListener {


    TextView textView;
    Button myButton1;
    Button myButton2;
    //Bitmap bmp;
    private MovingView movingView;
    private Thread animationThread;
    private Thread checkThread;
    int collectedLemons;
    private boolean isAnimating = false;
    private boolean isChecking = false;
    private int dotykX;

    int retrievedLemons = 0;
    int gameEnded = 0;

    String fileName;
    String content;
    public int contentInt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_run);

        //sbmp= BitmapFactory.decodeResource(getResources(), R.drawable.lemon);

        textView = findViewById(R.id.coinsView);
        myButton1 = findViewById(R.id.myButtonId1);
        myButton2 = findViewById(R.id.myButtonId2);

        movingView = new MovingView(this);

        ConstraintLayout constraintLayout = findViewById(R.id.container);

        constraintLayout.addView(movingView);
        movingView.setOnTouchListener(this);

        MovingView movingView = new MovingView(GameRun.this);

//        collector = new Collector();
//        collectedLemons = collector.returnPoints();

        fileName = "endEndCheck.txt";
        content = "";

        myButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                movingView.moveLeft();
                movingView.invalidate();

            }

        });

//        myButton1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()) {
//
//                    case MotionEvent.ACTION_DOWN:
//
//                        movingView.moveLeft();
//                        movingView.invalidate();
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//
//                        movingView.moveStop();
//                        movingView.invalidate();
//                        break;
//
//                }
//
//                return true;
//
//            }
//
//        });



        myButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                movingView.moveRight();
                movingView.invalidate();

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        textView.setText("Lemons: 0");

//        SharedPreferences sharedPreferences = getSharedPreferences("DATA", Context.MODE_PRIVATE);
//        boolean gameEnded = sharedPreferences.getBoolean("GAME_ENDED", false);
//
//        if (gameEnded && this.gameEnded != gameEnded) {
//
//            this.gameEnded = gameEnded;
//            //startActivity(gameEndReached);
//
//        }

        startAnimation();
        startChecking();

        System.out.println("Lemons: " + retrievedLemons);
//        System.out.println("Just to check if it will work: " + gameEnded);

//        try {
//
//            FileInputStream fis = openFileInput(fileName);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//            String line;
//            while ((line = br.readLine()) != null) {
//
//                content += line;
//            }
//
//            br.close();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//

    }

    @Override
    protected void onPause() {

        super.onPause();
        stopAnimation();
    }


    private void startAnimation() {

        isAnimating = true;
        animationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isAnimating) {

                    movingView.moveDown();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            movingView.invalidate();
                            Intent gameEndReached = new Intent(GameRun.this, GameEnding.class);

                            try {

                                FileInputStream fis = openFileInput(fileName);
                                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                                String line;
                                while ((line = br.readLine()) != null) {

                                    content = line;
                                    contentInt = Integer.parseInt(content);
                                }

                                br.close();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }

                            System.out.println("Wartość: " + contentInt);

                            //
                            //        if (content == "1") {
                            //
                            //            startActivity(gameEndReached);
                            //
                            //        }

                            if (contentInt == 1) {

                                System.out.println("Aktywność powinna zostać zmieniona");
                                startActivity(gameEndReached);

                            }

                        }

                    });

                    try {

                        Thread.sleep(16);

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                }

            }

        });

        animationThread.start();

    }

    private void stopAnimation() {

//        if(speed == 0) {

        isAnimating = false;
        if (animationThread != null) {

            try {

                animationThread.join();

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }

    private void startChecking() {

        isChecking = true;
        checkThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isChecking) {

                    String json="";
                    SharedPreferences prefMaxButton=getSharedPreferences("DATA", Context.MODE_PRIVATE);
                    json=prefMaxButton.getString("KEY_ID_VALUE2", "FALSE");
                    String finalJson = json;
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            retrievedLemons = 0;
                            gameEnded = 0;

                            try {

                                JSONObject jsonPointsCollector = new JSONObject(finalJson);
                                retrievedLemons = jsonPointsCollector.getInt("collection");
//                                gameEnded = jsonPointsCollector.getInt("gameBoolean");
//                                if(gameEnded) {
//
//                                    startActivity(gameEndReached);
//
//                                }
//                                int retrievedLemons = jsonPointsCollector.optInt("collection", -1);
//                                Log.d("MyActivity", "Pobrana wartość: " + collectedLemons);
//                                System.out.println("Wartość: " + collectedLemons);

                                if (retrievedLemons != -1) {

                                    textView.setText("Lemons: " + retrievedLemons);

                                } else {

                                    textView.setText("Uknown Value");
                                }

//                                if (gameEnded != -1) {
//
//                                    textView.append(" Game End:" + gameEnded);
//
////                                    if(gameEnded == 1) { startActivity(gameEndReached); }
//
//                                } else {
//
//                                    textView.append(" Game End:" + gameEnded);
//
//                                }

                            } catch (JSONException e) {

                                e.printStackTrace();

                            }

//                            textView.setText(collectedLemons);

                        }

                    });

                    try {

                        Thread.sleep(16);

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                }

            }

        });

        checkThread.start();

    }

    private void stopCheck() {

//        if(speed == 0) {

        isChecking = false;
        if (checkThread != null) {

            try {

                checkThread.join();

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        dotykX = (int)event.getX();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (dotykX <= (movingView.screenWidth / 2)) {

                    movingView.moveLeft();
                    break;

                } else if (dotykX >= ((movingView.screenWidth / 2) + 1)) {

                    movingView.moveRight();
                    break;

                }


            case MotionEvent.ACTION_UP:

                movingView.moveStop();
                break;

        }

        movingView.invalidate();
        return true;

    }

}

