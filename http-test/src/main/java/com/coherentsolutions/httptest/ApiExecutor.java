package com.coherentsolutions.httptest;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ApiExecutor {

    private static final String API_URL = "http://51.15.124.217:8080/api/r/task?t=";
    private static Map<Integer, Callable<Response>> tasks = new HashMap<>();
    private ExecutorService executorService;
    private AtomicLong counter;

    public ApiExecutor(int nThreads) {
        this.executorService = Executors.newFixedThreadPool(nThreads);
        this.counter = new AtomicLong(0);
    }

    public void execute(int num, int timeout, Consumer<String> consumer) {
        execute(IntStream.range(0, num)
                .mapToObj((o) -> getTask(timeout))
                .collect(Collectors.toList()), consumer);
    }

    public void execute(int num, Consumer<String> consumer) {
        Random random = new Random();
        execute(Stream.generate(() -> random.nextInt(10) + 1)
                .map(this::getTask)
                .limit(num)
                .collect(Collectors.toList()), consumer);
    }

    public void execute(int num) {
        execute(num, System.out::println);
    }

    public void execute(int num, int timeout) {
        execute(num, timeout, System.out::println);
    }

    private void execute(List<Callable<Response>> tasks, Consumer<String> consumer) {
        List<Future<Response>> results = new ArrayList<>();

        for (Callable<Response> task : tasks) {
            results.add(executorService.submit(task));
        }
        results.forEach(future -> {
            try {
                consumer.accept(future.get().returnContent().asString());
                System.out.println("Total: " + counter.incrementAndGet());
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Callable<Response> getTask(final int timeout) {
        return tasks.computeIfAbsent(timeout, (t) ->
                () -> Request.Get(API_URL + t)
                        .execute());
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
