package cn.junoyi.server;

import cn.junoyi.framework.log.core.JunoYiLogger;
import cn.junoyi.framework.log.util.JunoYiLogUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Test {
    private final Logger log = LoggerFactory.getLogger(Test.class);
    private final JunoYiLogUtils junoYiLogUtils;
    
    public Test(JunoYiLogUtils junoYiLogUtils) {
        this.junoYiLogUtils = junoYiLogUtils;
    }
    
    @PostConstruct
    public void init() {
        // 基础日志测试
        testBasicLogs();
        
        // 异常日志测试
        testExceptionLogs();
        
        // MDC上下文测试
        testMDCContext();
        
        // 异步日志测试
        testAsyncLogs();
        
        // 性能测试
        testPerformance();
    }
    
    /**
     * 测试基础日志输出
     */
    public void testBasicLogs() {
        System.out.println("\n=== 基础日志测试 ===");
        
        log.info("测试普通日志打印输出");
        log.warn("测试警告日志打印输出");
        log.error("测试错误日志打印输出");
        log.debug("测试调试日志打印输出");
        log.trace("测试跟踪日志打印输出");
        
        // 使用JunoYi框架日志
        JunoYiLogger.info("JUNOYI", "使用JunoYi框架日志输出");
        JunoYiLogger.warn("JUNOYI", "JunoYi框架警告日志");
        JunoYiLogger.error("JUNOYI", "JunoYi框架错误日志");
    }
    
    /**
     * 测试异常日志输出
     */
    public void testExceptionLogs() {
        System.out.println("\n=== 异常日志测试 ===");
        
        try {
            // 测试简单异常
            throw new IllegalArgumentException("这是一个测试异常");
        } catch (Exception e) {
            log.error("捕获到简单异常", e);
        }
        
        try {
            // 测试嵌套异常
            try {
                throw new RuntimeException("内层异常");
            } catch (RuntimeException inner) {
                throw new BusinessException("外层业务异常", inner);
            }
        } catch (Exception e) {
            log.error("捕获到嵌套异常", e);
        }
        
        try {
            // 测试空指针异常
            String str = null;
            str.length();
        } catch (Exception e) {
            log.error("捕获到空指针异常", e);
        }
        
        try {
            // 测试数组越界异常
            int[] array = {1, 2, 3};
            System.out.println(array[5]);
        } catch (Exception e) {
            log.error("捕获到数组越界异常", e);
        }
    }
    
    /**
     * 测试MDC上下文日志
     */
    public void testMDCContext() {
        System.out.println("\n=== MDC上下文测试 ===");
        
        try {
            // 设置MDC上下文
            MDC.put("userId", "12345");
            MDC.put("sessionId", "abc-def-ghi");
            MDC.put("requestId", "req-001");
            MDC.put("operation", "testMDC");
            
            log.info("带有MDC上下文的信息日志");
            log.warn("带有MDC上下文的警告日志");
            log.error("带有MDC上下文的错误日志");
            
            // 使用JunoYiLogger的MDC方法
            Map<String, String> context = Map.of(
                "userId", "67890",
                "sessionId", "xyz-abc-def", 
                "requestId", "req-002",
                "operation", "junoYiMDC"
            );
            
            JunoYiLogger.info("JUNOYI", "使用JunoYiLogger的MDC日志", context);


        } finally {
            // 清理MDC
            MDC.clear();
        }
        
//   /**/     log.info("MDC清理后的日志");
    }
    
    /**
     * 测试异步日志
     */
    public void testAsyncLogs() {
        System.out.println("\n=== 异步日志测试 ===");
        
        // 使用JunoYiLogUtils的异步方法
        junoYiLogUtils.logAsync("INFO", "异步信息日志测试");
        junoYiLogUtils.logAsync("WARN", "异步警告日志测试");
        junoYiLogUtils.logAsync("ERROR", "异步错误日志测试");
        
        // 异步执行带上下文的日志
        Map<String, String> asyncContext = Map.of(
            "threadId", Thread.currentThread().getName(),
            "async", "true"
        );
        
        JunoYiLogger.info("ASYNC", "异步带上下文日志", asyncContext);
        
        // 测试异步性能日志
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        junoYiLogUtils.logPerformanceAsync("异步操作测试", startTime);
        
        // 测试MDC异步日志
        Map<String, String> mdcContext = Map.of(
            "userId", "asyncUser",
            "operation", "asyncTest"
        );
        JunoYiLogUtils.logWithMDC(mdcContext, "INFO", "使用MDC的异步日志测试");
    }
    
    /**
     * 测试性能和大量日志
     */
    public void testPerformance() {
        System.out.println("\n=== 性能测试 ===");
        
        long startTime = System.currentTimeMillis();
        
        // 批量日志测试
        for (int i = 0; i < 100; i++) {
            log.info("批量日志测试 - 第{}条记录", i + 1);
        }
        
        long endTime = System.currentTimeMillis();
        log.info("100条日志输出耗时: {}ms", endTime - startTime);
        
        // 测试不同长度的日志
        log.info("短日志");
        log.info("中等长度的日志消息，包含更多内容和详细信息");
        log.info("这是一个非常长的日志消息，用来测试日志框架对长文本的处理能力。" +
                "包含了很多字符和内容，用来验证日志格式化和显示效果是否正常。" +
                "这样可以确保在实际应用中遇到长日志消息时也能正确显示。");
    }
    
    /**
     * 测试业务场景日志
     */
    public void testBusinessScenarios() {
        System.out.println("\n=== 业务场景测试 ===");
        
        // 模拟用户操作
        String userId = "user123";
        String action = "login";
        
        MDC.put("userId", userId);
        MDC.put("action", action);
        
        try {
            log.info("用户开始执行操作: {}", action);
            
            // 模拟业务处理
            if (Math.random() > 0.5) {
                log.info("用户操作成功: {}", action);
            } else {
                log.warn("用户操作需要重试: {}", action);
                throw new BusinessException("业务处理失败");
            }
            
        } catch (Exception e) {
            log.error("用户操作失败: {}, 用户ID: {}", action, userId, e);
        } finally {
            MDC.clear();
        }
    }
    
    /**
     * 自定义业务异常类
     */
    public static class BusinessException extends Exception {
        public BusinessException(String message) {
            super(message);
        }
        
        public BusinessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}