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
    @EventHandler(priority = EventPriority.LOW)
    public void onTestEvent(TestEvent event){

        System.out.println("321321321312312");
        System.out.println("事件已经触发42432432432432:  " + event.getTest());
        System.out.println("事件已经触发42432432432432:  " + event.getTest());
        System.out.println("事件已经触发42432432432432:  " + event.getTest());
    }


}