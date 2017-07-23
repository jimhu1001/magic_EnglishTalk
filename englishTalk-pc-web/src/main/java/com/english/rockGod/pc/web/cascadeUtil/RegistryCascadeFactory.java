package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class RegistryCascadeFactory implements CascadeFactory {
    private Reducer reducer;

    public RegistryCascadeFactory(Registry registry, CascadeFactoryConfig config) {
        this.reducer = this.createReducer(registry.getInvocationHandler(), config.getThreadCount());
    }

    private Reducer createReducer(InvocationHandler invocationHandler, int threadCount) {
        if(threadCount > 1) {
            LinkedBlockingQueue taskQueue = new LinkedBlockingQueue(threadCount);
            return new ParallelReducer(invocationHandler, new ThreadPoolExecutor(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS, taskQueue, (new ThreadFactoryBuilder()).setNameFormat("cascade-%d").build(), new ThreadPoolExecutor.CallerRunsPolicy()), taskQueue);
        } else {
            return new SerialReducer(invocationHandler);
        }
    }

    public Cascade create() {
        return new Cascade() {
            public Map process(List<Field> fields, Map contextParams) {
                return RegistryCascadeFactory.this.reducer.reduce(fields, ContextParams.create(contextParams));
            }
        };
    }
}
