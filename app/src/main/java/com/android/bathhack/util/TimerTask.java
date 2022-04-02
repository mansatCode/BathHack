package com.android.bathhack.util;

import java.util.Random;
import java.util.Timer;

public class TimerTask extends java.util.TimerTask {

    private final Timer timer;
    private final Random random;


    public TimerTask(Timer timer, Random random) {
        this.timer = timer;
        this.random = random;
    }

    @Override
    public void run() {
        System.out.println("TEST");
        timer.schedule(new TimerTask(timer, random), random.nextInt(10000));
    }
}
