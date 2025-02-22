package com.example.android2dgamedevelopment_;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.util.Log;

public class Gameloop extends Thread{
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    public static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private Game game;
    private double averageUPS;
    private double averageFPS;

    public Gameloop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        Log.d("Gameloop.java", "startLoop");
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        Log.d("Gameloop.java", "run");
        super.run();

        // Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        // Game loop
        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while(isRunning) {

            // Try to update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount ++;
                    game.draw(canvas);
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Skip frames to keep up with target UPS
            while(sleepTime < 0 && updateCount < MAX_UPS -1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            // Calculates average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }

    public void stopLoop() {
        Log.d("Gameloop.java", "stopLoop");
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
