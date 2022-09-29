package com.gtassignment.salary.task;

import org.springframework.stereotype.Component;
import java.util.concurrent.*;

@Component
public class QueueExecutor {

    private final ExecutorCompletionService completionService = new ExecutorCompletionService(
            Executors.newSingleThreadExecutor(),
            new LinkedBlockingQueue<>());

    public Future<Boolean> queueFile(Callable<Boolean> task) {
        System.out.println("Queued file " + task);
        return completionService.submit(task);
    }
}
