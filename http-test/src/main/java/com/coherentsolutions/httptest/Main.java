package com.coherentsolutions.httptest;

public class Main {

    public static void main(String[] args) {
        ApiExecutor executor = new ApiExecutor(32);

        // 1
//        executor.execute(32, 5);
//        executor.execute(32, 2);

        // 2
//        executor.execute(64);

        // 3
//        executor.execute(32, 1);
//        executor.execute(32, 4);
//        executor.execute(32, 8);

        // 4
//        executor.execute(32, 2);
//        executor.execute(32);

//        executor.execute(32 * 8, 1);

        executor.shutdown();
    }

}