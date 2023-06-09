package com.example.lemonstealer;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class GameEndingView extends View {

    private Context context;

    public GameEndingView(Context context) {

        super(context);
        this.context = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

    }

    public void setOnTouchListener(GameEnding gameEnding) {



    }
}
