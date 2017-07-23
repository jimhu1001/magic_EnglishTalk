package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.ConstructorProperties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/23/023.
 */
public class ParallelReducer implements Reducer  {
    private static final Log log = LogFactory.getLog(ParallelReducer.class);
    private InvocationHandler invocationHandler;
    private ExecutorService executorService;
    private BlockingQueue<Runnable> taskQueue;

    public ParallelReducer(InvocationHandler invocationHandler, ExecutorService executorService, BlockingQueue<Runnable> taskQueue) {
        this.invocationHandler = invocationHandler;
        this.executorService = executorService;
        this.taskQueue = taskQueue;
    }

    public Map reduce(List<Field> fields, ContextParams contextParams) {
        ParallelReducer.RootCompleteNotifier root = new ParallelReducer.RootCompleteNotifier(fields.size());
        this.executorService.execute(new ParallelReducer.FieldsRunner(root, fields, contextParams));
        return this.waitForComplete(fields, root);
    }

    private Map createMapWithAdditionalSize(Map baseMap, int additionalCount) {
        HashMap ret = Maps.newHashMapWithExpectedSize(baseMap.size() + additionalCount);
        ret.putAll(baseMap);
        return ret;
    }

    private Map waitForComplete(List<Field> fields, ParallelReducer.RootCompleteNotifier root) {
        int maxEnterCount = 50;

        while(root.remainCount.get() != 0) {
            if(maxEnterCount > 0) {
                --maxEnterCount;
                if(maxEnterCount == 0) {
                    log.error("max run count arrived: " + fields);
                }
            }

            Runnable runnable = null;

            try {
                runnable = (Runnable)this.taskQueue.poll(50L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException var6) {
                ;
            }

            if(runnable != null) {
                runnable.run();
            }
        }

        return root.getResults();
    }

    private class ListResultsRunner implements Runnable {
        private List parentResults;
        private ParallelReducer.CompleteNotifier completeNotifier;
        private List<Field> fields;
        private ContextParams parentContextParams;

        public void run() {
            int index = 0;

            for(Iterator var2 = this.parentResults.iterator(); var2.hasNext(); ++index) {
                Object o = var2.next();
                Map parentResultsMap = Util.toMap(o);
                ParallelReducer.this.executorService.execute(ParallelReducer.this.new FieldsRunner(new ParallelReducer.MapCompleteNotifier(ParallelReducer.this.createMapWithAdditionalSize(parentResultsMap, this.fields.size()), this.completeNotifier, Integer.valueOf(index), this.fields.size()), this.fields, this.parentContextParams.extend(parentResultsMap)));
            }

        }

        @ConstructorProperties({"parentResults", "completeNotifier", "fields", "parentContextParams"})
        public ListResultsRunner(List var1, ParallelReducer.CompleteNotifier parentResults, List<Field> completeNotifier, ContextParams fields) {
            this.parentResults = (List)parentResults;
            this.completeNotifier = (ParallelReducer.CompleteNotifier)completeNotifier;
            this.fields = (List<Field>) fields;
            this.parentContextParams = parentContextParams;
        }
    }

    private class FieldsRunner implements Runnable {
        private ParallelReducer.CompleteNotifier completenotifier;
        private List<Field> fields;
        private ContextParams parentContextParams;

        public void run() {
            Iterator var1 = this.fields.iterator();

            while(var1.hasNext()) {
                Field field = (Field)var1.next();
                ParallelReducer.this.executorService.execute(ParallelReducer.this.new FieldRunner(this.completenotifier, field, this.parentContextParams));
            }

        }

        @ConstructorProperties({"completenotifier", "fields", "parentContextParams"})
        public FieldsRunner(ParallelReducer.CompleteNotifier var1, List<Field> completenotifier, ContextParams fields) {
            this.completenotifier = (ParallelReducer.CompleteNotifier)completenotifier;
            this.fields = (List<Field>)fields;
            this.parentContextParams = parentContextParams;
        }
    }

    private class FieldRunner implements Runnable {
        private ParallelReducer.CompleteNotifier completeNotifier;
        private Field field;
        private ContextParams parentContextParams;

        public void run() {
            ContextParams contextParams = this.parentContextParams.extend(this.field.getParams());
            Object result = ParallelReducer.this.invocationHandler.invoke(this.field, contextParams);
            if(this.field.getChildren().size() != 0 && !Util.canNotHasChildren(result)) {
                if(result instanceof Collection) {
                    Object resultMap = result instanceof List?(List)result: Lists.newArrayList((Collection)result);
                    ParallelReducer.this.executorService.execute(ParallelReducer.this.new ListResultsRunner((List)resultMap, new ParallelReducer.ListCompleteNotifier((List)resultMap, this.completeNotifier, this.field.getComputedAs(), ((List)resultMap).size()), this.field.getChildren(), contextParams));
                } else {
                    Map resultMap1 = Util.toMap(result);
                    ParallelReducer.this.executorService.execute(ParallelReducer.this.new FieldsRunner(new ParallelReducer.MapCompleteNotifier(ParallelReducer.this.createMapWithAdditionalSize(resultMap1, this.field.getChildren().size()), this.completeNotifier, this.field.getComputedAs(), this.field.getChildren().size()), this.field.getChildren(), contextParams.extend(resultMap1)));
                }
            } else {
                this.completeNotifier.emit(this.field.getComputedAs(), result);
            }

        }

        @ConstructorProperties({"completeNotifier", "field", "parentContextParams"})
        public FieldRunner(ParallelReducer.CompleteNotifier completeNotifier, Field field, ContextParams parentContextParams) {
            this.completeNotifier = completeNotifier;
            this.field = field;
            this.parentContextParams = parentContextParams;
        }
    }

    private static class RootCompleteNotifier implements ParallelReducer.CompleteNotifier {
        private Map results;
        private AtomicInteger remainCount;

        public RootCompleteNotifier(int remainCount) {
            this.remainCount = new AtomicInteger(remainCount);
            this.results = Maps.newHashMapWithExpectedSize(remainCount);
        }

        public void emit(Object key, Object value) {
            this.results.put(key, value);
            this.remainCount.decrementAndGet();
        }

        public Map getResults() {
            return this.results;
        }
    }

    private static class ListCompleteNotifier extends ParallelReducer.CollectionCompleteNotifier<List> {
        public ListCompleteNotifier(List parentResults, ParallelReducer.CompleteNotifier parent, Object keyInParent, int remainCount) {
            super(parentResults, parent, keyInParent, remainCount);
        }

        protected void setData(Object key, Object value) {
            ((List)this.parentResults).set(((Integer)key).intValue(), value);
        }
    }

    private static class MapCompleteNotifier extends ParallelReducer.CollectionCompleteNotifier<Map> {
        public MapCompleteNotifier(Map parentResults, ParallelReducer.CompleteNotifier parent, Object keyInParent, int remainCount) {
            super(parentResults, parent, keyInParent, remainCount);
        }

        protected void setData(Object key, Object value) {
            ((Map)this.parentResults).put(key, value);
        }
    }

    private abstract static class CollectionCompleteNotifier<T> implements ParallelReducer.CompleteNotifier {
        protected T parentResults;
        private ParallelReducer.CompleteNotifier parent;
        private Object keyInParent;
        private AtomicInteger remainCount;

        protected CollectionCompleteNotifier(T parentResults, ParallelReducer.CompleteNotifier parent, Object keyInParent, int remainCount) {
            this.parentResults = parentResults;
            this.parent = parent;
            this.keyInParent = keyInParent;
            this.remainCount = new AtomicInteger(remainCount);
            if(remainCount == 0) {
                parent.emit(keyInParent, parentResults);
            }

        }

        public void emit(Object key, Object value) {
            this.setData(key, value);
            if(this.remainCount.decrementAndGet() == 0) {
                this.parent.emit(this.keyInParent, this.parentResults);
            }

        }

        protected abstract void setData(Object var1, Object var2);
    }

    private interface CompleteNotifier {
        void emit(Object var1, Object var2);
    }
}
