package com.coherentsolutions.httptest;

public class Main {

    public static void main(String[] args) {
        ApiExecutor executor = new ApiExecutor(4);
//        executor.execute(50, 2);
        executor.execute(10000);

        executor.shutdown();
    }

}