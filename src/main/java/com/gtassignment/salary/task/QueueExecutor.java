package com.gtassignment.salary.task;

import org.springframework.stereotype.Component;
import java.util.concurrent.*;

@Component
public class QueueExecutor {

    private ExecutorCompletionService completionService =
            new ExecutorCompletionService(Executors.newSingleThreadExecutor());

    public Future<Boolean> queueFile(Callable task) throws Exception {
        return completionService.submit(task);
    }
}