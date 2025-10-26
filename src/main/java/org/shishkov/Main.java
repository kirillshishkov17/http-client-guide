package org.shishkov;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        ApiClient apiClient = new ApiClient("https://www.google.com/", semaphore);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            executor.execute(apiClient::getGooglePage);
        }
        executor.shutdown();
    }
}
