package com.company;

public class Main {

    public static void main(String[] args) {
//        you can prevent thread interference by creating an instance of Countdown for each thread
//        ^ this solution often wont work for example a bank account object, maintain data integrity

        Countdown countdown = new Countdown();

        CountdownThread t1 = new CountdownThread(countdown);
        t1.setName("Thread 1");

        CountdownThread t2 = new CountdownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

class Countdown {

    private int i;
//        you could synchronize a method or synchronize a block of code
//        when a method is synchronized, only one thread can execute the method at one time, other threads must wait until thread that has called the method have returned
//        must synchronize all areas where interference may happen
//        public synchronized void doCountdown() {
    public void doCountdown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
        }

//        copy of i in each thread stack
//        for(int i = 10; i > 9; i--)
//        switching to instance variable, jvm allocates i onto heap which threads share
//        they share the variable on the heap
//        can lead to thread interference aka race condition (when two or more threads are writing a shared resource)

//        thread can be suspended in for loop before decrementing i, before condition, and before printing the value
//        synchronize color wont work because it is a local variable
//        "this" synchronizes the countdown object that the threads share
        synchronized (this) {
            for (i = 10; i > 0; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i =" + i);
            }
        }
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;

    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }
}
