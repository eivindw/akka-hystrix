package eivindw;

import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import eivindw.hystrix.HelloCommand;

public class TestApp {

   public static void main(String[] args) throws Exception {
      System.out.println("Start!");

      HystrixRequestContext context = HystrixRequestContext.initializeContext();

      System.out.println("Direct: " + new HelloCommand("run direct").execute());

      new HelloCommand("observe").observe()
         .subscribe(str -> System.out.println("Observe: " + str));
      new HelloCommand("observe").observe()
         .subscribe(str -> System.out.println("Observe: " + str));

      System.out.println("Queue: " + new HelloCommand("queue").queue().get());

      for (int i = 0; i < 10; i++) {
         new HelloCommand("observe-loop-" + i).observe()
            .subscribe(
               str -> System.out.println("Observe-loop: " + str),
               err -> System.err.println("Error: " + err.getMessage())
            );
      }

      Thread.sleep(750);

      printMetrics();

      Thread.sleep(2000);

      printMetrics();

      context.shutdown();
      System.exit(0);
   }

   private static void printMetrics() {
      for (HystrixCommandMetrics metric : HystrixCommandMetrics.getInstances()) {
         System.out.println("Metric: " + metric.getCommandGroup().name());
         System.out.println("execution-time-mean: " + metric.getExecutionTimeMean());
         System.out.println("total-time-mean: " + metric.getTotalTimeMean());
         System.out.println("total-count: " + metric.getHealthCounts().getTotalRequests());
         System.out.println("error-count: " + metric.getHealthCounts().getErrorCount());
         System.out.println("error-%: " + metric.getHealthCounts().getErrorPercentage());
      }

      for (HystrixThreadPoolMetrics metrics : HystrixThreadPoolMetrics.getInstances()) {
         System.out.println("cumulative-count-threads-executed: " + metrics.getCumulativeCountThreadsExecuted());
         System.out.println("current-active-count: " + metrics.getCurrentActiveCount());
         System.out.println("current-completed-task-count: " + metrics.getCurrentCompletedTaskCount());
         System.out.println("current-core-pool-size: " + metrics.getCurrentCorePoolSize());
         System.out.println("current-largest-pool-size: " + metrics.getCurrentLargestPoolSize());
         System.out.println("current-max-pool-size: " + metrics.getCurrentMaximumPoolSize());
         System.out.println("current-pool-size: " + metrics.getCurrentPoolSize());
         System.out.println("current-queue-size: " + metrics.getCurrentQueueSize());
         System.out.println("current-task-count: " + metrics.getCurrentTaskCount());
         System.out.println("rolling-count-threads-executed: " + metrics.getRollingCountThreadsExecuted());
         System.out.println("rolling-max-active-threads: " + metrics.getRollingMaxActiveThreads());
      }
   }
}
