package com.junoyi.system.listener;

import com.junoyi.framework.event.annotation.EventHandler;
import com.junoyi.framework.event.core.Listener;
import com.junoyi.framework.event.enums.EventPriority;
import com.junoyi.system.event.TestEvent;

/**
 * Test事件监听器
 */
public class TestEventListener implements Listener {


    /**
     * 事件监听器
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onTestEvent(TestEvent event){

        System.out.println("优先级：高");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTest2Event(TestEvent event){

        System.out.println("优先级：监控（最高的）");
    }

    @EventHandler
    public void onTest3Event(TestEvent event){

        System.out.println("优先级：默认");
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onTest4Event(TestEvent event){

        System.out.println("优先级：低");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTest5Event(TestEvent event){

        System.out.println("优先级：最低");
    }
}