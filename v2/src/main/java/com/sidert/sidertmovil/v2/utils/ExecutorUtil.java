package com.sidert.sidertmovil.v2.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Response;

@Singleton
@Named
public class ExecutorUtil {

    private final ExecutorService executorService;

    @Inject
    public ExecutorUtil(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public <T> Response<T> process(Call<T> call) throws ExecutionException, InterruptedException {
        Future<Response<T>> future = executorService.submit(call::execute);
        return future.get();
    }

    public void runTaskInThread(Runnable runnable) throws ExecutionException, InterruptedException {
        Future<?> task = executorService.submit(runnable);
        task.get();
    }

    public <V> V runTaskInThread(Callable<V> callable) throws ExecutionException, InterruptedException, TimeoutException {
        return executorService.submit(callable).get(10, TimeUnit.HOURS);
    }

}
