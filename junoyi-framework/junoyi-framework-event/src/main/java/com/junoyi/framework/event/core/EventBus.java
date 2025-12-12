package com.junoyi.framework.event.core;

import com.junoyi.framework.log.core.JunoYiLog;
import com.junoyi.framework.log.core.JunoYiLogFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事件总线
 *
 * @author Fan
 */
public class EventBus {

    private final JunoYiLog log = JunoYiLogFactory.getLogger(EventBus.class);

    private static final EventBus INSTANCE = new EventBus();

    /**
     * 获取事件总线单例实例
     *
     * @return EventBus单例实例
     */
    public static EventBus get() {
        return INSTANCE;
    }

    private final EventRegistry registry = new EventRegistry();

    private final ExecutorService asyncExecutor;

    private final AtomicInteger threadCounter = new AtomicInteger(0);

    private EventBus() {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = corePoolSize * 2;
        asyncExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("EventBus-Async-" + threadCounter.incrementAndGet());
                    thread.setDaemon(true);
                    return thread;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 注册事件监听器
     *
     * @param listener 要注册的监听器对象
     */
    public void registerListener(Listener listener){
        registry.registerListener(listener);
    }

    /**
     * 触发事件，调用所有注册的事件处理器
     *
     * @param event 要触发的事件对象
     * @param <T> 事件类型
     */
    public <T extends Event> void callEvent(T event){
        // 获取该事件类型对应的所有已注册处理器
        List<RegisteredHandler> handlers = registry.getHandlers(event.getClass());
        int listenerCount = handlers.size();
        log.info("EventTrigger", "Event="+event.getClass().getSimpleName() + " | " + "ListenerCount=" + listenerCount );
        // 遍历并调用每个处理器的方法
        for (RegisteredHandler handler : handlers){
            if (handler.async()) {
                // 异步执行
                asyncExecutor.submit(() -> executeHandler(handler, event));
            } else {
                // 同步执行
                executeHandler(handler, event);
            }
        }
    }


    public <T extends Event> void callAsyncEvent(T event){
        // 获取该事件类型对应的所有已注册处理器
        List<RegisteredHandler> handlers = registry.getHandlers(event.getClass());
        int listenerCount = handlers.size();
        log.info("AsyncEventTrigger", "Event="+event.getClass().getSimpleName() + " | " + "ListenerCount=" + listenerCount );
        // 所有处理器都异步执行
        for (RegisteredHandler handler : handlers){
            asyncExecutor.submit(() -> executeHandler(handler, event));
        }
    }

    /**
     * 执行事件处理器
     *
     * @param handler 事件处理器
     * @param event 事件对象
     * @param <T> 事件类型
     */
    private <T extends Event> void executeHandler(RegisteredHandler handler, T event) {
        try {
            handler.method().invoke(handler.listener(), event);
        } catch (Exception e){
            log.error("EventHandlerError", "Failed to execute handler: " + handler.method().getName(), e);
        }
    }

    /**
     * 关闭事件总线，释放线程池资源
     */
    public void shutdown() {
        asyncExecutor.shutdown();
        try {
            if (!asyncExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                asyncExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            asyncExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
