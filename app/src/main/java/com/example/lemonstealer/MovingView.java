package com.example.lemonstealer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MovingView extends View {

    private static final int REQUEST_CODE = 1;
    private Paint paint;
    private int speed;
    private int screenHeight;
    public int screenWidth;
    private int buttonHeight;

    private int randomX, randomY;
    private int randomIncrease = 11;

    //    Collector collector;
    public int collector = 0;
    Player player;

    Bitmap goodLemonSprite;
    Bitmap badLemonSprite;

    Random random = new Random();

    int lemonNumber = 10;
    GoodLemon[] goodLemonList;
    BadLemon badLemon;
//    Lemon[] lemons;

    //    long slowdown = 500;
//    long startTime = System.currentTimeMillis();
    int slowdown = 0;
    private Context context;

    public int gameEnded = 0;

    Intent gameEndReached;
    String fileName = "endEndCheck.txt";

    boolean canRun = true;

    public MovingView(Context context) {

        super(context);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.RED);

        gameEndReached = new Intent(MovingView.this.getContext(), GameEnding.class);

        speed = 10;
        buttonHeight = 240;

        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        goodLemonList = new GoodLemon[lemonNumber];

        player = new Player(200, 1100, 100, 100);
        for (int i = 0; i < goodLemonList.length; i++) {

            goodLemonList[i] = new GoodLemon(randomX = random.nextInt(screenWidth - 70) + 1, 0, 20 * 5, 14 * 5);
//            enemyList[i] = new Enemy(randomX = random.nextInt(screenWidth - 70) + 0, randomY = random.nextInt(-100) + 0, 20 * 5, 14 * 5);

        }
        //enemy = new Enemy(0, 0, 20 * 5, 14 * 5);
        badLemon = new BadLemon(randomX = random.nextInt(screenWidth - 70) + 1, 0, 20 * 5, 14 * 5);

//        collector = new Collector();

        //20x14
        goodLemonSprite = BitmapFactory.decodeResource(getResources(), R.drawable.lemon);
        goodLemonSprite = Bitmap.createScaledBitmap(goodLemonSprite, 20 * 5, 14 * 5, true);

        badLemonSprite = BitmapFactory.decodeResource(getResources(), R.drawable.badlemon);
        badLemonSprite = Bitmap.createScaledBitmap(badLemonSprite, 20 * 5, 14 * 5, true);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        //canvas.drawRect(eX, eY, eX + 100, eY + 100, paint);
        canvas.drawRect(player.x, player.y, player.x + player.width, player.y + player.height, paint);

        for (int i = 0; i < goodLemonList.length; i++) {

            //canvas.drawBitmap(bmp, enemy.x, enemy.y, null);
            canvas.drawBitmap(goodLemonSprite, goodLemonList[i].x, goodLemonList[i].y, null);

        }

        canvas.drawBitmap(badLemonSprite, badLemon.x, badLemon.y, null);

        saveToFile(fileName);

    }

    public void saveToFile(String fileName) {

        String badLemonCollisionOff = "0";
        String badLemonCollisionOn = "1";

        if (gameEnded != 1) {

            try {


                FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

                outputStream.write(badLemonCollisionOff.getBytes());

                outputStream.close();

            } catch (FileNotFoundException e) {

                throw new RuntimeException(e);

            } catch (IOException e) {

                throw new RuntimeException(e);

            }

        } else if (gameEnded == 1) {


            try {

                FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

                outputStream.write(badLemonCollisionOn.getBytes());

                outputStream.close();

            } catch (FileNotFoundException e) {

                throw new RuntimeException(e);

            } catch (IOException e) {

                throw new RuntimeException(e);

            }

        }

    }

    public void moveDown() {

        if(speed != 0) {

            if(gameEnded != 1) {

                speed = random.nextInt(randomIncrease - 1) + 1;

                for (int i = 0; i < goodLemonList.length; i++) {

                    //            while (System.currentTimeMillis() - startTime < slowdown) { }
                    while (slowdown < 500) {

                        slowdown++;

                    }

                    speed = random.nextInt(randomIncrease - 1) + 1;
                    goodLemonList[i].y += speed;
                    //enemy.y += speed;

                    checkGoodLemonCollision();
                    checkBadLemonCollision();
                    //            checkCollision(goodLemonList);
                    //            checkCollision(new Lemon[] { badLemon });

                }

            }

            if(gameEnded != 1) {

                speed = random.nextInt(randomIncrease - 1) + 1;
                badLemon.y += speed;

            } else { badLemon.y += 0; }

            checkBadLemonCollision();

            invalidate();

        }

    }

    public void moveLeft() {

        if(gameEnded != 1) {

            player.x -= 25;
            invalidate();

        }

    }

    public void moveStop() {

        player.x = player.x;
        invalidate();

    }

    public void moveRight() {

        if(gameEnded != 1) {

            player.x += 25;
            invalidate();

        }

    }

    public void speedIncrease(){

        if (collector % 10 == 0) {

            randomIncrease = randomIncrease + 3;

        }

    }

    public void speedZero(){

        speed = 0;

    }

//    public void preSend(JSONObject jsonPointsCollector) {
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String tekstJson = jsonPointsCollector.toString();
//        editor.putString("KEY_ID_VALUE2", tekstJson);
//        editor.commit();
//
//    }

    public void jSonSender() {

        int collectedLemons = collector;
        JSONObject jsonPointsCollector;

        try {

            jsonPointsCollector = new JSONObject();
            jsonPointsCollector.put("collection", collectedLemons);

//            if(gameEnded != 1) {
//
//            jsonPointsCollector.put("gameBoolean", 0);
//
//            } else { jsonPointsCollector.put("gameBoolean", 1); }


            SharedPreferences sharedPreferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String tekstJson = jsonPointsCollector.toString();
            editor.putString("KEY_ID_VALUE2", tekstJson);
            editor.commit();

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }

    private void checkGoodLemonCollision() {

        for (int i = 0; i < goodLemonList.length; i++) {

            if (goodLemonList[i].y > (screenHeight - buttonHeight)) {

                goodLemonList[i].y = 0;
                goodLemonList[i].x = random.nextInt(screenWidth - 70) + 1;

            }

            if ((goodLemonList[i].y + goodLemonList[i].height) > player.y && (goodLemonList[i].y + goodLemonList[i].height) < (player.y + 100)) {

                if (goodLemonList[i].x < (player.x + 100) && (goodLemonList[i].x + goodLemonList[i].width) > player.x) {

                    goodLemonList[i].y = 0;
                    goodLemonList[i].x = random.nextInt(379) + 1;
//                    collector.collect();
                    collector++;
                    jSonSender();
                    speedIncrease();


                    //String json = jsonPointsSender.toString();

                }

            }

        }

    }

    private void checkBadLemonCollision() {


        if (badLemon.y > (screenHeight - buttonHeight)) {

            badLemon.y = 0;
            badLemon.x = random.nextInt(screenWidth - 70) + 1;

        }

        if ((badLemon.y + badLemon.height) > player.y && (badLemon.y + badLemon.height) < (player.y + 100)) {

            if (badLemon.x < (player.x + 100) && (badLemon.x + badLemon.width) > player.x) {

//                speedZero();
                gameEnded = 1;

//                context.startActivity(gameEndReached);

                //String json = jsonPointsSender.toString();

            } else { gameEnded = 0; }

//            }

        }

    }



//    private void checkCollision() {
//
//        for (int i = 0; i < goodLemonList.length; i++) {
//
//            if (goodLemonList[i].y > (screenHeight - buttonHeight)) {
//
//                goodLemonList[i].y = 0;
//                goodLemonList[i].x = random.nextInt(screenWidth - 70) + 1;
//
//            }
//
//            if ((goodLemonList[i].y + goodLemonList[i].height) > player.y && (goodLemonList[i].y + goodLemonList[i].height) < (player.y + 100)) {
//
//                if (goodLemonList[i].x < (player.x + 100) && (goodLemonList[i].x + goodLemonList[i].width) > player.x) {
//
//                    if (i == (goodLemonNumber - 1)) {
//
//                        speedZero();
//
//                    } else {
//                        goodLemonList[i].y = 0;
//                        goodLemonList[i].x = random.nextInt(379) + 1;
////                    collector.collect();
//                        collector++;
//                        jSonSender();
//                        speedIncrease();
//
//                    }
//
//                    //String json = jsonPointsSender.toString();
//
//                }
//
//            }
//
//        }
//
//    }

//        if (enemy.y > (screenHeight - buttonHeight)) {
//
//            enemy.y = 0;
//            enemy.x = random.nextInt(379) + 1;
//
//        }
//
//        if ((enemy.y + enemy.height) > player.y && (enemy.y + enemy.height) < (player.y + 100)) {
//
//            if(enemy.x < (player.x + 100) && (enemy.x + enemy.width) > player.x) {
//
//                enemy.y = 0;
//                enemy.x = random.nextInt(379) + 1;
//                collector.pointCollected++;
//
//            }
//
//        }

}


