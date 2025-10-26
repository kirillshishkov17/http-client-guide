package org.shishkov;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private final ObjectMapper mapper = new ObjectMapper();
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl;
    private final Semaphore semaphore;
    private final int requestLimit = 2;
    private final int requestLimitInterval = 10;

    public ApiClient(String baseUrl, Semaphore semaphore) {
        this.baseUrl = baseUrl;
        this.semaphore = semaphore;

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> semaphore.release(requestLimit - semaphore.availablePermits()), requestLimitInterval, requestLimitInterval, TimeUnit.SECONDS);
    }

    public void getGooglePage() {
        try {
            semaphore.acquire();
            Request request = new Request.Builder().url(baseUrl).build();
            try (Response response = client.newCall(request).execute()) {
                System.out.println(Thread.currentThread().getName() + " " + response.code());
            } catch (IOException e) {
                System.err.println("Http request failed");
            }
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted");
        }
    }
}
