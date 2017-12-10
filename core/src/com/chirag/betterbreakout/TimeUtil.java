package com.chirag.betterbreakout;

import java.util.ArrayList;

public class TimeUtil {
    static boolean active = false;
    private static long lastTime = System.nanoTime();

    private static class Task {
        long duration;
        Runnable runnable;

        private Task(long duration, Runnable runnable) {
            this.duration = duration;
            this.runnable = runnable;
        }

        void elapse(long millis) {
            duration -= millis;
        }

        boolean isDone() {
            return duration <= 0;
        }
    }
    private static ArrayList<Task> tasks = new ArrayList<Task>();

    static void doLater(final Runnable run, final int duration) {
        tasks.add(new Task(duration, run));
    }

    public static void update() {
        if(active) {
            long currentTime = System.nanoTime();
            ArrayList<Task> toDel = new ArrayList<Task>();
            for(Task t : tasks) {
                t.elapse((currentTime - lastTime) / 1000000);
                if(t.isDone()) {
                    t.runnable.run();
                    toDel.add(t);
                }
            }
            tasks.removeAll(toDel);
            lastTime = currentTime;
        } else {
            lastTime = System.nanoTime();
        }
    }

    static void clear() {
        tasks.clear();
    }
}

































































































































































































































































































//hi