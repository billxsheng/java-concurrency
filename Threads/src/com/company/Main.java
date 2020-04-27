package com.company;
import static com.company.ThreadColor.ANSI_PURPLE;
import static com.company.ThreadColor.ANSI_GREEN;
import static com.company.ThreadColor.ANSI_RED;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from the main thread.");

//         Cannot start a thread that is already run(), must create a new instance of the thread subclass
//         This is used when you have task that has to be run > 1 time
        final Thread anotherThread = new AnotherThread();
        anotherThread.setName("== Another Thread ==");

//         calling run() instead of start() will run the thread on the main thread
        anotherThread.start();

//         anonymous class
//         this is how you start a thread that you want to run once
        new Thread() {
            public void run() {
//                System.out.println(ANSI_GREEN + "Hello from the anonymous class thread.");
            }
        }.start();

//         runnable is more flexible because of Java API's and lambda expressions
        Thread myRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ANSI_RED + "Hello from the anonymous class's implementation of run()");
                try {
//                    Parameter is time before thread "times out"
                    anotherThread.join();
//                    AnotherThread terminates before the following code runs
                    System.out.println(ANSI_RED + "AnotherThread terminated, or timed out, so I'm running again.");
                } catch(InterruptedException e) {
                    System.out.println(ANSI_RED + "I couldn't wait after all. I was interrupted.");
                }
            }
        });

        myRunnableThread.start();


//        anotherThread.interrupt();

        System.out.println(ANSI_PURPLE + "Hello again from the main thread.");

    }
}
